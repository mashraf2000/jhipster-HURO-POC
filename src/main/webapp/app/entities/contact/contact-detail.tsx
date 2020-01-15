import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './contact.reducer';
import { IContact } from 'app/shared/model/contact.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IContactDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ContactDetail = (props: IContactDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { contactEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="huroPocApp.contact.detail.title">Contact</Translate> [<b>{contactEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="firstName">
              <Translate contentKey="huroPocApp.contact.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{contactEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="huroPocApp.contact.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{contactEntity.lastName}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="huroPocApp.contact.address">Address</Translate>
            </span>
          </dt>
          <dd>{contactEntity.address}</dd>
          <dt>
            <span id="telephoneNumber">
              <Translate contentKey="huroPocApp.contact.telephoneNumber">Telephone Number</Translate>
            </span>
          </dt>
          <dd>{contactEntity.telephoneNumber}</dd>
          <dt>
            <span id="emailAddress">
              <Translate contentKey="huroPocApp.contact.emailAddress">Email Address</Translate>
            </span>
          </dt>
          <dd>{contactEntity.emailAddress}</dd>
          <dt>
            <span id="dateCreated">
              <Translate contentKey="huroPocApp.contact.dateCreated">Date Created</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={contactEntity.dateCreated} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="dateUpdated">
              <Translate contentKey="huroPocApp.contact.dateUpdated">Date Updated</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={contactEntity.dateUpdated} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <Translate contentKey="huroPocApp.contact.entitySeekingFund">Entity Seeking Fund</Translate>
          </dt>
          <dd>{contactEntity.entitySeekingFund ? contactEntity.entitySeekingFund.id : ''}</dd>
          <dt>
            <Translate contentKey="huroPocApp.contact.investor">Investor</Translate>
          </dt>
          <dd>{contactEntity.investor ? contactEntity.investor.id : ''}</dd>
          <dt>
            <Translate contentKey="huroPocApp.contact.vendor">Vendor</Translate>
          </dt>
          <dd>{contactEntity.vendor ? contactEntity.vendor.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/contact" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/contact/${contactEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ contact }: IRootState) => ({
  contactEntity: contact.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ContactDetail);
