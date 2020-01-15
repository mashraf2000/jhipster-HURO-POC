import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GroupMessage from './group-message';
import Message from './message';
import OrganizationProfile from './organization-profile';
import EntitySeekingFund from './entity-seeking-fund';
import Vendor from './vendor';
import Investor from './investor';
import Contact from './contact';
import Intent from './intent';
import Compliance from './compliance';
import Region from './region';
import Country from './country';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}group-message`} component={GroupMessage} />
      <ErrorBoundaryRoute path={`${match.url}message`} component={Message} />
      <ErrorBoundaryRoute path={`${match.url}organization-profile`} component={OrganizationProfile} />
      <ErrorBoundaryRoute path={`${match.url}entity-seeking-fund`} component={EntitySeekingFund} />
      <ErrorBoundaryRoute path={`${match.url}vendor`} component={Vendor} />
      <ErrorBoundaryRoute path={`${match.url}investor`} component={Investor} />
      <ErrorBoundaryRoute path={`${match.url}contact`} component={Contact} />
      <ErrorBoundaryRoute path={`${match.url}intent`} component={Intent} />
      <ErrorBoundaryRoute path={`${match.url}compliance`} component={Compliance} />
      <ErrorBoundaryRoute path={`${match.url}region`} component={Region} />
      <ErrorBoundaryRoute path={`${match.url}country`} component={Country} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
