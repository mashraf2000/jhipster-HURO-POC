import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IRegion } from 'app/shared/model/region.model';
import { getEntities as getRegions } from 'app/entities/region/region.reducer';
import { ICountry } from 'app/shared/model/country.model';
import { getEntities as getCountries } from 'app/entities/country/country.reducer';
import { getEntity, updateEntity, createEntity, reset } from './compliance.reducer';
import { ICompliance } from 'app/shared/model/compliance.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IComplianceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ComplianceUpdate = (props: IComplianceUpdateProps) => {
  const [idsregion, setIdsregion] = useState([]);
  const [idscountry, setIdscountry] = useState([]);
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { complianceEntity, regions, countries, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/compliance' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getRegions();
    props.getCountries();
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
        ...complianceEntity,
        ...values,
        regions: mapIdList(values.regions),
        countries: mapIdList(values.countries)
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
          <h2 id="huroPocApp.compliance.home.createOrEditLabel">
            <Translate contentKey="huroPocApp.compliance.home.createOrEditLabel">Create or edit a Compliance</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : complianceEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="compliance-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="compliance-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="compliance-name">
                  <Translate contentKey="huroPocApp.compliance.name">Name</Translate>
                </Label>
                <AvField id="compliance-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="dateCreatedLabel" for="compliance-dateCreated">
                  <Translate contentKey="huroPocApp.compliance.dateCreated">Date Created</Translate>
                </Label>
                <AvInput
                  id="compliance-dateCreated"
                  type="datetime-local"
                  className="form-control"
                  name="dateCreated"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? null : convertDateTimeFromServer(props.complianceEntity.dateCreated)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="dateUpdatedLabel" for="compliance-dateUpdated">
                  <Translate contentKey="huroPocApp.compliance.dateUpdated">Date Updated</Translate>
                </Label>
                <AvInput
                  id="compliance-dateUpdated"
                  type="datetime-local"
                  className="form-control"
                  name="dateUpdated"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? null : convertDateTimeFromServer(props.complianceEntity.dateUpdated)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="compliance-region">
                  <Translate contentKey="huroPocApp.compliance.region">Region</Translate>
                </Label>
                <AvInput
                  id="compliance-region"
                  type="select"
                  multiple
                  className="form-control"
                  name="regions"
                  value={complianceEntity.regions && complianceEntity.regions.map(e => e.id)}
                >
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
              <AvGroup>
                <Label for="compliance-country">
                  <Translate contentKey="huroPocApp.compliance.country">Country</Translate>
                </Label>
                <AvInput
                  id="compliance-country"
                  type="select"
                  multiple
                  className="form-control"
                  name="countries"
                  value={complianceEntity.countries && complianceEntity.countries.map(e => e.id)}
                >
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
              <Button tag={Link} id="cancel-save" to="/compliance" replace color="info">
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
  regions: storeState.region.entities,
  countries: storeState.country.entities,
  complianceEntity: storeState.compliance.entity,
  loading: storeState.compliance.loading,
  updating: storeState.compliance.updating,
  updateSuccess: storeState.compliance.updateSuccess
});

const mapDispatchToProps = {
  getRegions,
  getCountries,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ComplianceUpdate);
