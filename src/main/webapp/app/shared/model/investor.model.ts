import { Moment } from 'moment';
import { IContact } from 'app/shared/model/contact.model';
import { IIntent } from 'app/shared/model/intent.model';
import { IRegion } from 'app/shared/model/region.model';

export interface IInvestor {
  id?: number;
  name?: string;
  address?: string;
  telephoneNumber?: string;
  emailAddress?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  contacts?: IContact[];
  intents?: IIntent[];
  region?: IRegion;
}

export const defaultValue: Readonly<IInvestor> = {};
