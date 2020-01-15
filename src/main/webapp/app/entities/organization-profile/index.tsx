import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OrganizationProfile from './organization-profile';
import OrganizationProfileDetail from './organization-profile-detail';
import OrganizationProfileUpdate from './organization-profile-update';
import OrganizationProfileDeleteDialog from './organization-profile-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={OrganizationProfileDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OrganizationProfileUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OrganizationProfileUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OrganizationProfileDetail} />
      <ErrorBoundaryRoute path={match.url} component={OrganizationProfile} />
    </Switch>
  </>
);

export default Routes;
