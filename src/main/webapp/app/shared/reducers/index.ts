import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import groupMessage, {
  GroupMessageState
} from 'app/entities/group-message/group-message.reducer';
// prettier-ignore
import message, {
  MessageState
} from 'app/entities/message/message.reducer';
// prettier-ignore
import organizationProfile, {
  OrganizationProfileState
} from 'app/entities/organization-profile/organization-profile.reducer';
// prettier-ignore
import entitySeekingFund, {
  EntitySeekingFundState
} from 'app/entities/entity-seeking-fund/entity-seeking-fund.reducer';
// prettier-ignore
import vendor, {
  VendorState
} from 'app/entities/vendor/vendor.reducer';
// prettier-ignore
import investor, {
  InvestorState
} from 'app/entities/investor/investor.reducer';
// prettier-ignore
import contact, {
  ContactState
} from 'app/entities/contact/contact.reducer';
// prettier-ignore
import intent, {
  IntentState
} from 'app/entities/intent/intent.reducer';
// prettier-ignore
import compliance, {
  ComplianceState
} from 'app/entities/compliance/compliance.reducer';
// prettier-ignore
import region, {
  RegionState
} from 'app/entities/region/region.reducer';
// prettier-ignore
import country, {
  CountryState
} from 'app/entities/country/country.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly groupMessage: GroupMessageState;
  readonly message: MessageState;
  readonly organizationProfile: OrganizationProfileState;
  readonly entitySeekingFund: EntitySeekingFundState;
  readonly vendor: VendorState;
  readonly investor: InvestorState;
  readonly contact: ContactState;
  readonly intent: IntentState;
  readonly compliance: ComplianceState;
  readonly region: RegionState;
  readonly country: CountryState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  groupMessage,
  message,
  organizationProfile,
  entitySeekingFund,
  vendor,
  investor,
  contact,
  intent,
  compliance,
  region,
  country,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
