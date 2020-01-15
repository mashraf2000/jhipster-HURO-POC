import { Moment } from 'moment';
import { IRegion } from 'app/shared/model/region.model';
import { ICountry } from 'app/shared/model/country.model';

export interface ICompliance {
  id?: number;
  name?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  regions?: IRegion[];
  countries?: ICountry[];
}

export const defaultValue: Readonly<ICompliance> = {};
