import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './investor.reducer';
import { IInvestor } from 'app/shared/model/investor.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IInvestorDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InvestorDetail = (props: IInvestorDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { investorEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="huroPocApp.investor.detail.title">Investor</Translate> [<b>{investorEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="huroPocApp.investor.name">Name</Translate>
            </span>
          </dt>
          <dd>{investorEntity.name}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="huroPocApp.investor.address">Address</Translate>
            </span>
          </dt>
          <dd>{investorEntity.address}</dd>
          <dt>
            <span id="telephoneNumber">
              <Translate contentKey="huroPocApp.investor.telephoneNumber">Telephone Number</Translate>
            </span>
          </dt>
          <dd>{investorEntity.telephoneNumber}</dd>
          <dt>
            <span id="emailAddress">
              <Translate contentKey="huroPocApp.investor.emailAddress">Email Address</Translate>
            </span>
          </dt>
          <dd>{investorEntity.emailAddress}</dd>
          <dt>
            <span id="dateCreated">
              <Translate contentKey="huroPocApp.investor.dateCreated">Date Created</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={investorEntity.dateCreated} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="dateUpdated">
              <Translate contentKey="huroPocApp.investor.dateUpdated">Date Updated</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={investorEntity.dateUpdated} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <Translate contentKey="huroPocApp.investor.intent">Intent</Translate>
          </dt>
          <dd>
            {investorEntity.intents
              ? investorEntity.intents.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {i === investorEntity.intents.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="huroPocApp.investor.region">Region</Translate>
          </dt>
          <dd>{investorEntity.region ? investorEntity.region.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/investor" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/investor/${investorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ investor }: IRootState) => ({
  investorEntity: investor.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InvestorDetail);
