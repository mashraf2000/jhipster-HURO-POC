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
import { getEntity, updateEntity, createEntity, reset } from './entity-seeking-fund.reducer';
import { IEntitySeekingFund } from 'app/shared/model/entity-seeking-fund.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEntitySeekingFundUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EntitySeekingFundUpdate = (props: IEntitySeekingFundUpdateProps) => {
  const [idsintent, setIdsintent] = useState([]);
  const [regionId, setRegionId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { entitySeekingFundEntity, intents, regions, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/entity-seeking-fund' + props.location.search);
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
        ...entitySeekingFundEntity,
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
          <h2 id="huroPocApp.entitySeekingFund.home.createOrEditLabel">
            <Translate contentKey="huroPocApp.entitySeekingFund.home.createOrEditLabel">Create or edit a EntitySeekingFund</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : entitySeekingFundEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="entity-seeking-fund-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="entity-seeking-fund-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="entity-seeking-fund-name">
                  <Translate contentKey="huroPocApp.entitySeekingFund.name">Name</Translate>
                </Label>
                <AvField id="entity-seeking-fund-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="addressLabel" for="entity-seeking-fund-address">
                  <Translate contentKey="huroPocApp.entitySeekingFund.address">Address</Translate>
                </Label>
                <AvField id="entity-seeking-fund-address" type="text" name="address" />
              </AvGroup>
              <AvGroup>
                <Label id="telephoneNumberLabel" for="entity-seeking-fund-telephoneNumber">
                  <Translate contentKey="huroPocApp.entitySeekingFund.telephoneNumber">Telephone Number</Translate>
                </Label>
                <AvField id="entity-seeking-fund-telephoneNumber" type="text" name="telephoneNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="emailAddressLabel" for="entity-seeking-fund-emailAddress">
                  <Translate contentKey="huroPocApp.entitySeekingFund.emailAddress">Email Address</Translate>
                </Label>
                <AvField id="entity-seeking-fund-emailAddress" type="text" name="emailAddress" />
              </AvGroup>
              <AvGroup>
                <Label id="dateCreatedLabel" for="entity-seeking-fund-dateCreated">
                  <Translate contentKey="huroPocApp.entitySeekingFund.dateCreated">Date Created</Translate>
                </Label>
                <AvInput
                  id="entity-seeking-fund-dateCreated"
                  type="datetime-local"
                  className="form-control"
                  name="dateCreated"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? null : convertDateTimeFromServer(props.entitySeekingFundEntity.dateCreated)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="dateUpdatedLabel" for="entity-seeking-fund-dateUpdated">
                  <Translate contentKey="huroPocApp.entitySeekingFund.dateUpdated">Date Updated</Translate>
                </Label>
                <AvInput
                  id="entity-seeking-fund-dateUpdated"
                  type="datetime-local"
                  className="form-control"
                  name="dateUpdated"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? null : convertDateTimeFromServer(props.entitySeekingFundEntity.dateUpdated)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="entity-seeking-fund-intent">
                  <Translate contentKey="huroPocApp.entitySeekingFund.intent">Intent</Translate>
                </Label>
                <AvInput
                  id="entity-seeking-fund-intent"
                  type="select"
                  multiple
                  className="form-control"
                  name="intents"
                  value={entitySeekingFundEntity.intents && entitySeekingFundEntity.intents.map(e => e.id)}
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
                <Label for="entity-seeking-fund-region">
                  <Translate contentKey="huroPocApp.entitySeekingFund.region">Region</Translate>
                </Label>
                <AvInput id="entity-seeking-fund-region" type="select" className="form-control" name="region.id">
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
              <Button tag={Link} id="cancel-save" to="/entity-seeking-fund" replace color="info">
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
  entitySeekingFundEntity: storeState.entitySeekingFund.entity,
  loading: storeState.entitySeekingFund.loading,
  updating: storeState.entitySeekingFund.updating,
  updateSuccess: storeState.entitySeekingFund.updateSuccess
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

export default connect(mapStateToProps, mapDispatchToProps)(EntitySeekingFundUpdate);
