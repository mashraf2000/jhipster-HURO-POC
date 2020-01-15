import { Moment } from 'moment';
import { IEntitySeekingFund } from 'app/shared/model/entity-seeking-fund.model';
import { IInvestor } from 'app/shared/model/investor.model';
import { IVendor } from 'app/shared/model/vendor.model';
import { IntentStatus } from 'app/shared/model/enumerations/intent-status.model';

export interface IIntent {
  id?: number;
  name?: string;
  expectedDateOfCompletion?: Moment;
  fundingAmountDesired?: number;
  status?: IntentStatus;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  entitySeekingFunds?: IEntitySeekingFund[];
  investors?: IInvestor[];
  vendors?: IVendor[];
}

export const defaultValue: Readonly<IIntent> = {};
