import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GroupMessage from './group-message';
import GroupMessageDetail from './group-message-detail';
import GroupMessageUpdate from './group-message-update';
import GroupMessageDeleteDialog from './group-message-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GroupMessageDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GroupMessageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GroupMessageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GroupMessageDetail} />
      <ErrorBoundaryRoute path={match.url} component={GroupMessage} />
    </Switch>
  </>
);

export default Routes;
