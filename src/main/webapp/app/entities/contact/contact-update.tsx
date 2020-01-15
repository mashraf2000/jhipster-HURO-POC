import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEntitySeekingFund } from 'app/shared/model/entity-seeking-fund.model';
import { getEntities as getEntitySeekingFunds } from 'app/entities/entity-seeking-fund/entity-seeking-fund.reducer';
import { IInvestor } from 'app/shared/model/investor.model';
import { getEntities as getInvestors } from 'app/entities/investor/investor.reducer';
import { IVendor } from 'app/shared/model/vendor.model';
import { getEntities as getVendors } from 'app/entities/vendor/vendor.reducer';
import { getEntity, updateEntity, createEntity, reset } from './contact.reducer';
import { IContact } from 'app/shared/model/contact.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IContactUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ContactUpdate = (props: IContactUpdateProps) => {
  const [entitySeekingFundId, setEntitySeekingFundId] = useState('0');
  const [investorId, setInvestorId] = useState('0');
  const [vendorId, setVendorId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { contactEntity, entitySeekingFunds, investors, vendors, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/contact' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getEntitySeekingFunds();
    props.getInvestors();
    props.getVendors();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.dateCreated = convertDateTimeToServer(values.dateCreated);
    values.dateUpdated = convertDateTimeToServer(values.dateUpdated);

    if (errors.length === 0) {
      const entity = {
        ...contactEntity,
        ...values
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="huroPocApp.contact.home.createOrEditLabel">
            <Translate contentKey="huroPocApp.contact.home.createOrEditLabel">Create or edit a Contact</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : contactEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="contact-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="contact-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="firstNameLabel" for="contact-firstName">
                  <Translate contentKey="huroPocApp.contact.firstName">First Name</Translate>
                </Label>
                <AvField id="contact-firstName" type="text" name="firstName" />
              </AvGroup>
              <AvGroup>
                <Label id="lastNameLabel" for="contact-lastName">
                  <Translate contentKey="huroPocApp.contact.lastName">Last Name</Translate>
                </Label>
                <AvField id="contact-lastName" type="text" name="lastName" />
              </AvGroup>
              <AvGroup>
                <Label id="addressLabel" for="contact-address">
                  <Translate contentKey="huroPocApp.contact.address">Address</Translate>
                </Label>
                <AvField id="contact-address" type="text" name="address" />
              </AvGroup>
              <AvGroup>
                <Label id="telephoneNumberLabel" for="contact-telephoneNumber">
                  <Translate contentKey="huroPocApp.contact.telephoneNumber">Telephone Number</Translate>
                </Label>
                <AvField id="contact-telephoneNumber" type="text" name="telephoneNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="emailAddressLabel" for="contact-emailAddress">
                  <Translate contentKey="huroPocApp.contact.emailAddress">Email Address</Translate>
                </Label>
                <AvField id="contact-emailAddress" type="text" name="emailAddress" />
              </AvGroup>
              <AvGroup>
                <Label id="dateCreatedLabel" for="contact-dateCreated">
                  <Translate contentKey="huroPocApp.contact.dateCreated">Date Created</Translate>
                </Label>
                <AvInput
                  id="contact-dateCreated"
                  type="datetime-local"
                  className="form-control"
                  name="dateCreated"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? null : convertDateTimeFromServer(props.contactEntity.dateCreated)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="dateUpdatedLabel" for="contact-dateUpdated">
                  <Translate contentKey="huroPocApp.contact.dateUpdated">Date Updated</Translate>
                </Label>
                <AvInput
                  id="contact-dateUpdated"
                  type="datetime-local"
                  className="form-control"
                  name="dateUpdated"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? null : convertDateTimeFromServer(props.contactEntity.dateUpdated)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="contact-entitySeekingFund">
                  <Translate contentKey="huroPocApp.contact.entitySeekingFund">Entity Seeking Fund</Translate>
                </Label>
                <AvInput id="contact-entitySeekingFund" type="select" className="form-control" name="entitySeekingFund.id">
                  <option value="" key="0" />
                  {entitySeekingFunds
                    ? entitySeekingFunds.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="contact-investor">
                  <Translate contentKey="huroPocApp.contact.investor">Investor</Translate>
                </Label>
                <AvInput id="contact-investor" type="select" className="form-control" name="investor.id">
                  <option value="" key="0" />
                  {investors
                    ? investors.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="contact-vendor">
                  <Translate contentKey="huroPocApp.contact.vendor">Vendor</Translate>
                </Label>
                <AvInput id="contact-vendor" type="select" className="form-control" name="vendor.id">
                  <option value="" key="0" />
                  {vendors
                    ? vendors.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/contact" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  entitySeekingFunds: storeState.entitySeekingFund.entities,
  investors: storeState.investor.entities,
  vendors: storeState.vendor.entities,
  contactEntity: storeState.contact.entity,
  loading: storeState.contact.loading,
  updating: storeState.contact.updating,
  updateSuccess: storeState.contact.updateSuccess
});

const mapDispatchToProps = {
  getEntitySeekingFunds,
  getInvestors,
  getVendors,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ContactUpdate);
