import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICompliance, defaultValue } from 'app/shared/model/compliance.model';

export const ACTION_TYPES = {
  FETCH_COMPLIANCE_LIST: 'compliance/FETCH_COMPLIANCE_LIST',
  FETCH_COMPLIANCE: 'compliance/FETCH_COMPLIANCE',
  CREATE_COMPLIANCE: 'compliance/CREATE_COMPLIANCE',
  UPDATE_COMPLIANCE: 'compliance/UPDATE_COMPLIANCE',
  DELETE_COMPLIANCE: 'compliance/DELETE_COMPLIANCE',
  RESET: 'compliance/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICompliance>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ComplianceState = Readonly<typeof initialState>;

// Reducer

export default (state: ComplianceState = initialState, action): ComplianceState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COMPLIANCE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COMPLIANCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_COMPLIANCE):
    case REQUEST(ACTION_TYPES.UPDATE_COMPLIANCE):
    case REQUEST(ACTION_TYPES.DELETE_COMPLIANCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_COMPLIANCE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COMPLIANCE):
    case FAILURE(ACTION_TYPES.CREATE_COMPLIANCE):
    case FAILURE(ACTION_TYPES.UPDATE_COMPLIANCE):
    case FAILURE(ACTION_TYPES.DELETE_COMPLIANCE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMPLIANCE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMPLIANCE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_COMPLIANCE):
    case SUCCESS(ACTION_TYPES.UPDATE_COMPLIANCE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_COMPLIANCE):
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

const apiUrl = 'api/compliances';

// Actions

export const getEntities: ICrudGetAllAction<ICompliance> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_COMPLIANCE_LIST,
    payload: axios.get<ICompliance>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ICompliance> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COMPLIANCE,
    payload: axios.get<ICompliance>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICompliance> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COMPLIANCE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICompliance> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COMPLIANCE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICompliance> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COMPLIANCE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
