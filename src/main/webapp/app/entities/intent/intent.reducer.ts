import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IIntent, defaultValue } from 'app/shared/model/intent.model';

export const ACTION_TYPES = {
  FETCH_INTENT_LIST: 'intent/FETCH_INTENT_LIST',
  FETCH_INTENT: 'intent/FETCH_INTENT',
  CREATE_INTENT: 'intent/CREATE_INTENT',
  UPDATE_INTENT: 'intent/UPDATE_INTENT',
  DELETE_INTENT: 'intent/DELETE_INTENT',
  RESET: 'intent/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IIntent>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type IntentState = Readonly<typeof initialState>;

// Reducer

export default (state: IntentState = initialState, action): IntentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_INTENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_INTENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_INTENT):
    case REQUEST(ACTION_TYPES.UPDATE_INTENT):
    case REQUEST(ACTION_TYPES.DELETE_INTENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_INTENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_INTENT):
    case FAILURE(ACTION_TYPES.CREATE_INTENT):
    case FAILURE(ACTION_TYPES.UPDATE_INTENT):
    case FAILURE(ACTION_TYPES.DELETE_INTENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_INTENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_INTENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_INTENT):
    case SUCCESS(ACTION_TYPES.UPDATE_INTENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_INTENT):
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

const apiUrl = 'api/intents';

// Actions

export const getEntities: ICrudGetAllAction<IIntent> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_INTENT_LIST,
    payload: axios.get<IIntent>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IIntent> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_INTENT,
    payload: axios.get<IIntent>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IIntent> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_INTENT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IIntent> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_INTENT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IIntent> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_INTENT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
