import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EntitySeekingFund from './entity-seeking-fund';
import EntitySeekingFundDetail from './entity-seeking-fund-detail';
import EntitySeekingFundUpdate from './entity-seeking-fund-update';
import EntitySeekingFundDeleteDialog from './entity-seeking-fund-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EntitySeekingFundDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EntitySeekingFundUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EntitySeekingFundUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EntitySeekingFundDetail} />
      <ErrorBoundaryRoute path={match.url} component={EntitySeekingFund} />
    </Switch>
  </>
);

export default Routes;
