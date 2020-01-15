import { Language } from 'app/shared/model/enumerations/language.model';

export interface IOrganizationProfile {
  id?: number;
  language?: Language;
}

export const defaultValue: Readonly<IOrganizationProfile> = {};
