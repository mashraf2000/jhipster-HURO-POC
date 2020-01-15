import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOrganizationProfile, defaultValue } from 'app/shared/model/organization-profile.model';

export const ACTION_TYPES = {
  FETCH_ORGANIZATIONPROFILE_LIST: 'organizationProfile/FETCH_ORGANIZATIONPROFILE_LIST',
  FETCH_ORGANIZATIONPROFILE: 'organizationProfile/FETCH_ORGANIZATIONPROFILE',
  CREATE_ORGANIZATIONPROFILE: 'organizationProfile/CREATE_ORGANIZATIONPROFILE',
  UPDATE_ORGANIZATIONPROFILE: 'organizationProfile/UPDATE_ORGANIZATIONPROFILE',
  DELETE_ORGANIZATIONPROFILE: 'organizationProfile/DELETE_ORGANIZATIONPROFILE',
  RESET: 'organizationProfile/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOrganizationProfile>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type OrganizationProfileState = Readonly<typeof initialState>;

// Reducer

export default (state: OrganizationProfileState = initialState, action): OrganizationProfileState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ORGANIZATIONPROFILE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ORGANIZATIONPROFILE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ORGANIZATIONPROFILE):
    case REQUEST(ACTION_TYPES.UPDATE_ORGANIZATIONPROFILE):
    case REQUEST(ACTION_TYPES.DELETE_ORGANIZATIONPROFILE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ORGANIZATIONPROFILE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ORGANIZATIONPROFILE):
    case FAILURE(ACTION_TYPES.CREATE_ORGANIZATIONPROFILE):
    case FAILURE(ACTION_TYPES.UPDATE_ORGANIZATIONPROFILE):
    case FAILURE(ACTION_TYPES.DELETE_ORGANIZATIONPROFILE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORGANIZATIONPROFILE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORGANIZATIONPROFILE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ORGANIZATIONPROFILE):
    case SUCCESS(ACTION_TYPES.UPDATE_ORGANIZATIONPROFILE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ORGANIZATIONPROFILE):
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

const apiUrl = 'api/organization-profiles';

// Actions

export const getEntities: ICrudGetAllAction<IOrganizationProfile> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ORGANIZATIONPROFILE_LIST,
    payload: axios.get<IOrganizationProfile>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IOrganizationProfile> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ORGANIZATIONPROFILE,
    payload: axios.get<IOrganizationProfile>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IOrganizationProfile> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ORGANIZATIONPROFILE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOrganizationProfile> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ORGANIZATIONPROFILE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOrganizationProfile> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ORGANIZATIONPROFILE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
