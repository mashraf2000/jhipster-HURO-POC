import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './intent.reducer';
import { IIntent } from 'app/shared/model/intent.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IIntentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const IntentDetail = (props: IIntentDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { intentEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="huroPocApp.intent.detail.title">Intent</Translate> [<b>{intentEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="huroPocApp.intent.name">Name</Translate>
            </span>
          </dt>
          <dd>{intentEntity.name}</dd>
          <dt>
            <span id="expectedDateOfCompletion">
              <Translate contentKey="huroPocApp.intent.expectedDateOfCompletion">Expected Date Of Completion</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={intentEntity.expectedDateOfCompletion} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="fundingAmountDesired">
              <Translate contentKey="huroPocApp.intent.fundingAmountDesired">Funding Amount Desired</Translate>
            </span>
          </dt>
          <dd>{intentEntity.fundingAmountDesired}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="huroPocApp.intent.status">Status</Translate>
            </span>
          </dt>
          <dd>{intentEntity.status}</dd>
          <dt>
            <span id="dateCreated">
              <Translate contentKey="huroPocApp.intent.dateCreated">Date Created</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={intentEntity.dateCreated} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="dateUpdated">
              <Translate contentKey="huroPocApp.intent.dateUpdated">Date Updated</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={intentEntity.dateUpdated} type="date" format={APP_DATE_FORMAT} />
          </dd>
        </dl>
        <Button tag={Link} to="/intent" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/intent/${intentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ intent }: IRootState) => ({
  intentEntity: intent.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(IntentDetail);
