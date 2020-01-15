import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IIntent } from 'app/shared/model/intent.model';
import { getEntities as getIntents } from 'app/entities/intent/intent.reducer';
import { IRegion } from 'app/shared/model/region.model';
import { getEntities as getRegions } from 'app/entities/region/region.reducer';
import { getEntity, updateEntity, createEntity, reset } from './vendor.reducer';
import { IVendor } from 'app/shared/model/vendor.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IVendorUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const VendorUpdate = (props: IVendorUpdateProps) => {
  const [idsintent, setIdsintent] = useState([]);
  const [regionId, setRegionId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { vendorEntity, intents, regions, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/vendor' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getIntents();
    props.getRegions();
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
        ...vendorEntity,
        ...values,
        intents: mapIdList(values.intents)
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
          <h2 id="huroPocApp.vendor.home.createOrEditLabel">
            <Translate contentKey="huroPocApp.vendor.home.createOrEditLabel">Create or edit a Vendor</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : vendorEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="vendor-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="vendor-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="vendor-name">
                  <Translate contentKey="huroPocApp.vendor.name">Name</Translate>
                </Label>
                <AvField id="vendor-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="addressLabel" for="vendor-address">
                  <Translate contentKey="huroPocApp.vendor.address">Address</Translate>
                </Label>
                <AvField id="vendor-address" type="text" name="address" />
              </AvGroup>
              <AvGroup>
                <Label id="telephoneNumberLabel" for="vendor-telephoneNumber">
                  <Translate contentKey="huroPocApp.vendor.telephoneNumber">Telephone Number</Translate>
                </Label>
                <AvField id="vendor-telephoneNumber" type="text" name="telephoneNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="emailAddressLabel" for="vendor-emailAddress">
                  <Translate contentKey="huroPocApp.vendor.emailAddress">Email Address</Translate>
                </Label>
                <AvField id="vendor-emailAddress" type="text" name="emailAddress" />
              </AvGroup>
              <AvGroup>
                <Label id="dateCreatedLabel" for="vendor-dateCreated">
                  <Translate contentKey="huroPocApp.vendor.dateCreated">Date Created</Translate>
                </Label>
                <AvInput
                  id="vendor-dateCreated"
                  type="datetime-local"
                  className="form-control"
                  name="dateCreated"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? null : convertDateTimeFromServer(props.vendorEntity.dateCreated)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="dateUpdatedLabel" for="vendor-dateUpdated">
                  <Translate contentKey="huroPocApp.vendor.dateUpdated">Date Updated</Translate>
                </Label>
                <AvInput
                  id="vendor-dateUpdated"
                  type="datetime-local"
                  className="form-control"
                  name="dateUpdated"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? null : convertDateTimeFromServer(props.vendorEntity.dateUpdated)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="vendor-intent">
                  <Translate contentKey="huroPocApp.vendor.intent">Intent</Translate>
                </Label>
                <AvInput
                  id="vendor-intent"
                  type="select"
                  multiple
                  className="form-control"
                  name="intents"
                  value={vendorEntity.intents && vendorEntity.intents.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {intents
                    ? intents.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="vendor-region">
                  <Translate contentKey="huroPocApp.vendor.region">Region</Translate>
                </Label>
                <AvInput id="vendor-region" type="select" className="form-control" name="region.id">
                  <option value="" key="0" />
                  {regions
                    ? regions.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/vendor" replace color="info">
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
  intents: storeState.intent.entities,
  regions: storeState.region.entities,
  vendorEntity: storeState.vendor.entity,
  loading: storeState.vendor.loading,
  updating: storeState.vendor.updating,
  updateSuccess: storeState.vendor.updateSuccess
});

const mapDispatchToProps = {
  getIntents,
  getRegions,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VendorUpdate);
