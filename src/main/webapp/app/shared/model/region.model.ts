import { IEntitySeekingFund } from 'app/shared/model/entity-seeking-fund.model';
import { IInvestor } from 'app/shared/model/investor.model';
import { IVendor } from 'app/shared/model/vendor.model';
import { ICountry } from 'app/shared/model/country.model';
import { ICompliance } from 'app/shared/model/compliance.model';

export interface IRegion {
  id?: number;
  regionName?: string;
  entitySeekingFunds?: IEntitySeekingFund[];
  investors?: IInvestor[];
  vendors?: IVendor[];
  country?: ICountry;
  compliances?: ICompliance[];
}

export const defaultValue: Readonly<IRegion> = {};
