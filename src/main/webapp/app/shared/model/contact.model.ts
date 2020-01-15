import { Moment } from 'moment';
import { IEntitySeekingFund } from 'app/shared/model/entity-seeking-fund.model';
import { IInvestor } from 'app/shared/model/investor.model';
import { IVendor } from 'app/shared/model/vendor.model';

export interface IContact {
  id?: number;
  firstName?: string;
  lastName?: string;
  address?: string;
  telephoneNumber?: string;
  emailAddress?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  entitySeekingFund?: IEntitySeekingFund;
  investor?: IInvestor;
  vendor?: IVendor;
}

export const defaultValue: Readonly<IContact> = {};
