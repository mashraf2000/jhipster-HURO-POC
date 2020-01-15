import { IRegion } from 'app/shared/model/region.model';
import { ICompliance } from 'app/shared/model/compliance.model';

export interface ICountry {
  id?: number;
  countryName?: string;
  regions?: IRegion[];
  compliances?: ICompliance[];
}

export const defaultValue: Readonly<ICountry> = {};
