import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './compliance.reducer';
import { ICompliance } from 'app/shared/model/compliance.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IComplianceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ComplianceDetail = (props: IComplianceDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { complianceEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="huroPocApp.compliance.detail.title">Compliance</Translate> [<b>{complianceEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="huroPocApp.compliance.name">Name</Translate>
            </span>
          </dt>
          <dd>{complianceEntity.name}</dd>
          <dt>
            <span id="dateCreated">
              <Translate contentKey="huroPocApp.compliance.dateCreated">Date Created</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={complianceEntity.dateCreated} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="dateUpdated">
              <Translate contentKey="huroPocApp.compliance.dateUpdated">Date Updated</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={complianceEntity.dateUpdated} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <Translate contentKey="huroPocApp.compliance.region">Region</Translate>
          </dt>
          <dd>
            {complianceEntity.regions
              ? complianceEntity.regions.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {i === complianceEntity.regions.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="huroPocApp.compliance.country">Country</Translate>
          </dt>
          <dd>
            {complianceEntity.countries
              ? complianceEntity.countries.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {i === complianceEntity.countries.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/compliance" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/compliance/${complianceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ compliance }: IRootState) => ({
  complianceEntity: compliance.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ComplianceDetail);
