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
import { getEntity, updateEntity, createEntity, reset } from './investor.reducer';
import { IInvestor } from 'app/shared/model/investor.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IInvestorUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InvestorUpdate = (props: IInvestorUpdateProps) => {
  const [idsintent, setIdsintent] = useState([]);
  const [regionId, setRegionId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { investorEntity, intents, regions, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/investor' + props.location.search);
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
        ...investorEntity,
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
          <h2 id="huroPocApp.investor.home.createOrEditLabel">
            <Translate contentKey="huroPocApp.investor.home.createOrEditLabel">Create or edit a Investor</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : investorEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="investor-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="investor-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="investor-name">
                  <Translate contentKey="huroPocApp.investor.name">Name</Translate>
                </Label>
                <AvField id="investor-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="addressLabel" for="investor-address">
                  <Translate contentKey="huroPocApp.investor.address">Address</Translate>
                </Label>
                <AvField id="investor-address" type="text" name="address" />
              </AvGroup>
              <AvGroup>
                <Label id="telephoneNumberLabel" for="investor-telephoneNumber">
                  <Translate contentKey="huroPocApp.investor.telephoneNumber">Telephone Number</Translate>
                </Label>
                <AvField id="investor-telephoneNumber" type="text" name="telephoneNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="emailAddressLabel" for="investor-emailAddress">
                  <Translate contentKey="huroPocApp.investor.emailAddress">Email Address</Translate>
                </Label>
                <AvField id="investor-emailAddress" type="text" name="emailAddress" />
              </AvGroup>
              <AvGroup>
                <Label id="dateCreatedLabel" for="investor-dateCreated">
                  <Translate contentKey="huroPocApp.investor.dateCreated">Date Created</Translate>
                </Label>
                <AvInput
                  id="investor-dateCreated"
                  type="datetime-local"
                  className="form-control"
                  name="dateCreated"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? null : convertDateTimeFromServer(props.investorEntity.dateCreated)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="dateUpdatedLabel" for="investor-dateUpdated">
                  <Translate contentKey="huroPocApp.investor.dateUpdated">Date Updated</Translate>
                </Label>
                <AvInput
                  id="investor-dateUpdated"
                  type="datetime-local"
                  className="form-control"
                  name="dateUpdated"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? null : convertDateTimeFromServer(props.investorEntity.dateUpdated)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="investor-intent">
                  <Translate contentKey="huroPocApp.investor.intent">Intent</Translate>
                </Label>
                <AvInput
                  id="investor-intent"
                  type="select"
                  multiple
                  className="form-control"
                  name="intents"
                  value={investorEntity.intents && investorEntity.intents.map(e => e.id)}
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
                <Label for="investor-region">
                  <Translate contentKey="huroPocApp.investor.region">Region</Translate>
                </Label>
                <AvInput id="investor-region" type="select" className="form-control" name="region.id">
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
              <Button tag={Link} id="cancel-save" to="/investor" replace color="info">
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
  investorEntity: storeState.investor.entity,
  loading: storeState.investor.loading,
  updating: storeState.investor.updating,
  updateSuccess: storeState.investor.updateSuccess
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

export default connect(mapStateToProps, mapDispatchToProps)(InvestorUpdate);
