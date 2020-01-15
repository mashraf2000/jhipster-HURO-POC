import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Compliance from './compliance';
import ComplianceDetail from './compliance-detail';
import ComplianceUpdate from './compliance-update';
import ComplianceDeleteDialog from './compliance-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ComplianceDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ComplianceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ComplianceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ComplianceDetail} />
      <ErrorBoundaryRoute path={match.url} component={Compliance} />
    </Switch>
  </>
);

export default Routes;
