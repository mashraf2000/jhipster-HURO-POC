import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGroupMessage, defaultValue } from 'app/shared/model/group-message.model';

export const ACTION_TYPES = {
  FETCH_GROUPMESSAGE_LIST: 'groupMessage/FETCH_GROUPMESSAGE_LIST',
  FETCH_GROUPMESSAGE: 'groupMessage/FETCH_GROUPMESSAGE',
  CREATE_GROUPMESSAGE: 'groupMessage/CREATE_GROUPMESSAGE',
  UPDATE_GROUPMESSAGE: 'groupMessage/UPDATE_GROUPMESSAGE',
  DELETE_GROUPMESSAGE: 'groupMessage/DELETE_GROUPMESSAGE',
  RESET: 'groupMessage/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGroupMessage>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type GroupMessageState = Readonly<typeof initialState>;

// Reducer

export default (state: GroupMessageState = initialState, action): GroupMessageState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GROUPMESSAGE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GROUPMESSAGE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_GROUPMESSAGE):
    case REQUEST(ACTION_TYPES.UPDATE_GROUPMESSAGE):
    case REQUEST(ACTION_TYPES.DELETE_GROUPMESSAGE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_GROUPMESSAGE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GROUPMESSAGE):
    case FAILURE(ACTION_TYPES.CREATE_GROUPMESSAGE):
    case FAILURE(ACTION_TYPES.UPDATE_GROUPMESSAGE):
    case FAILURE(ACTION_TYPES.DELETE_GROUPMESSAGE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_GROUPMESSAGE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_GROUPMESSAGE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_GROUPMESSAGE):
    case SUCCESS(ACTION_TYPES.UPDATE_GROUPMESSAGE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_GROUPMESSAGE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/group-messages';

// Actions

export const getEntities: ICrudGetAllAction<IGroupMessage> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_GROUPMESSAGE_LIST,
    payload: axios.get<IGroupMessage>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IGroupMessage> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GROUPMESSAGE,
    payload: axios.get<IGroupMessage>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IGroupMessage> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GROUPMESSAGE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGroupMessage> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GROUPMESSAGE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGroupMessage> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GROUPMESSAGE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
