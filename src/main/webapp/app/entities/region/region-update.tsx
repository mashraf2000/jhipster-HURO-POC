import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICountry } from 'app/shared/model/country.model';
import { getEntities as getCountries } from 'app/entities/country/country.reducer';
import { ICompliance } from 'app/shared/model/compliance.model';
import { getEntities as getCompliances } from 'app/entities/compliance/compliance.reducer';
import { getEntity, updateEntity, createEntity, reset } from './region.reducer';
import { IRegion } from 'app/shared/model/region.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRegionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RegionUpdate = (props: IRegionUpdateProps) => {
  const [countryId, setCountryId] = useState('0');
  const [complianceId, setComplianceId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { regionEntity, countries, compliances, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/region' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCountries();
    props.getCompliances();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...regionEntity,
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
          <h2 id="huroPocApp.region.home.createOrEditLabel">
            <Translate contentKey="huroPocApp.region.home.createOrEditLabel">Create or edit a Region</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : regionEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="region-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="region-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="regionNameLabel" for="region-regionName">
                  <Translate contentKey="huroPocApp.region.regionName">Region Name</Translate>
                </Label>
                <AvField id="region-regionName" type="text" name="regionName" />
              </AvGroup>
              <AvGroup>
                <Label for="region-country">
                  <Translate contentKey="huroPocApp.region.country">Country</Translate>
                </Label>
                <AvInput id="region-country" type="select" className="form-control" name="country.id">
                  <option value="" key="0" />
                  {countries
                    ? countries.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/region" replace color="info">
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
  countries: storeState.country.entities,
  compliances: storeState.compliance.entities,
  regionEntity: storeState.region.entity,
  loading: storeState.region.loading,
  updating: storeState.region.updating,
  updateSuccess: storeState.region.updateSuccess
});

const mapDispatchToProps = {
  getCountries,
  getCompliances,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RegionUpdate);
