import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Investor from './investor';
import InvestorDetail from './investor-detail';
import InvestorUpdate from './investor-update';
import InvestorDeleteDialog from './investor-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={InvestorDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={InvestorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={InvestorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={InvestorDetail} />
      <ErrorBoundaryRoute path={match.url} component={Investor} />
    </Switch>
  </>
);

export default Routes;
