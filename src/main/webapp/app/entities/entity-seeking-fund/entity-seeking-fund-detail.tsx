import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './entity-seeking-fund.reducer';
import { IEntitySeekingFund } from 'app/shared/model/entity-seeking-fund.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEntitySeekingFundDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EntitySeekingFundDetail = (props: IEntitySeekingFundDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { entitySeekingFundEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="huroPocApp.entitySeekingFund.detail.title">EntitySeekingFund</Translate> [
          <b>{entitySeekingFundEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="huroPocApp.entitySeekingFund.name">Name</Translate>
            </span>
          </dt>
          <dd>{entitySeekingFundEntity.name}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="huroPocApp.entitySeekingFund.address">Address</Translate>
            </span>
          </dt>
          <dd>{entitySeekingFundEntity.address}</dd>
          <dt>
            <span id="telephoneNumber">
              <Translate contentKey="huroPocApp.entitySeekingFund.telephoneNumber">Telephone Number</Translate>
            </span>
          </dt>
          <dd>{entitySeekingFundEntity.telephoneNumber}</dd>
          <dt>
            <span id="emailAddress">
              <Translate contentKey="huroPocApp.entitySeekingFund.emailAddress">Email Address</Translate>
            </span>
          </dt>
          <dd>{entitySeekingFundEntity.emailAddress}</dd>
          <dt>
            <span id="dateCreated">
              <Translate contentKey="huroPocApp.entitySeekingFund.dateCreated">Date Created</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={entitySeekingFundEntity.dateCreated} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="dateUpdated">
              <Translate contentKey="huroPocApp.entitySeekingFund.dateUpdated">Date Updated</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={entitySeekingFundEntity.dateUpdated} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <Translate contentKey="huroPocApp.entitySeekingFund.intent">Intent</Translate>
          </dt>
          <dd>
            {entitySeekingFundEntity.intents
              ? entitySeekingFundEntity.intents.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {i === entitySeekingFundEntity.intents.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="huroPocApp.entitySeekingFund.region">Region</Translate>
          </dt>
          <dd>{entitySeekingFundEntity.region ? entitySeekingFundEntity.region.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/entity-seeking-fund" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/entity-seeking-fund/${entitySeekingFundEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ entitySeekingFund }: IRootState) => ({
  entitySeekingFundEntity: entitySeekingFund.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EntitySeekingFundDetail);
