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
import { getEntity, updateEntity, createEntity, reset } from './intent.reducer';
import { IIntent } from 'app/shared/model/intent.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IIntentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const IntentUpdate = (props: IIntentUpdateProps) => {
  const [entitySeekingFundId, setEntitySeekingFundId] = useState('0');
  const [investorId, setInvestorId] = useState('0');
  const [vendorId, setVendorId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { intentEntity, entitySeekingFunds, investors, vendors, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/intent' + props.location.search);
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
    values.expectedDateOfCompletion = convertDateTimeToServer(values.expectedDateOfCompletion);
    values.dateCreated = convertDateTimeToServer(values.dateCreated);
    values.dateUpdated = convertDateTimeToServer(values.dateUpdated);

    if (errors.length === 0) {
      const entity = {
        ...intentEntity,
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
          <h2 id="huroPocApp.intent.home.createOrEditLabel">
            <Translate contentKey="huroPocApp.intent.home.createOrEditLabel">Create or edit a Intent</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : intentEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="intent-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="intent-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="intent-name">
                  <Translate contentKey="huroPocApp.intent.name">Name</Translate>
                </Label>
                <AvField id="intent-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="expectedDateOfCompletionLabel" for="intent-expectedDateOfCompletion">
                  <Translate contentKey="huroPocApp.intent.expectedDateOfCompletion">Expected Date Of Completion</Translate>
                </Label>
                <AvInput
                  id="intent-expectedDateOfCompletion"
                  type="datetime-local"
                  className="form-control"
                  name="expectedDateOfCompletion"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? null : convertDateTimeFromServer(props.intentEntity.expectedDateOfCompletion)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="fundingAmountDesiredLabel" for="intent-fundingAmountDesired">
                  <Translate contentKey="huroPocApp.intent.fundingAmountDesired">Funding Amount Desired</Translate>
                </Label>
                <AvField id="intent-fundingAmountDesired" type="string" className="form-control" name="fundingAmountDesired" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="intent-status">
                  <Translate contentKey="huroPocApp.intent.status">Status</Translate>
                </Label>
                <AvInput
                  id="intent-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && intentEntity.status) || 'INQUIRY'}
                >
                  <option value="INQUIRY">{translate('huroPocApp.IntentStatus.INQUIRY')}</option>
                  <option value="NEGOTIATE">{translate('huroPocApp.IntentStatus.NEGOTIATE')}</option>
                  <option value="COMMIT">{translate('huroPocApp.IntentStatus.COMMIT')}</option>
                  <option value="FUNDPROCESSING">{translate('huroPocApp.IntentStatus.FUNDPROCESSING')}</option>
                  <option value="DELIVERY">{translate('huroPocApp.IntentStatus.DELIVERY')}</option>
                  <option value="INSTALL">{translate('huroPocApp.IntentStatus.INSTALL')}</option>
                  <option value="TRAIN">{translate('huroPocApp.IntentStatus.TRAIN')}</option>
                  <option value="GOLIVE">{translate('huroPocApp.IntentStatus.GOLIVE')}</option>
                  <option value="CLOSED">{translate('huroPocApp.IntentStatus.CLOSED')}</option>
                  <option value="CANCELLED">{translate('huroPocApp.IntentStatus.CANCELLED')}</option>
                  <option value="ABANDONED">{translate('huroPocApp.IntentStatus.ABANDONED')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="dateCreatedLabel" for="intent-dateCreated">
                  <Translate contentKey="huroPocApp.intent.dateCreated">Date Created</Translate>
                </Label>
                <AvInput
                  id="intent-dateCreated"
                  type="datetime-local"
                  className="form-control"
                  name="dateCreated"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? null : convertDateTimeFromServer(props.intentEntity.dateCreated)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="dateUpdatedLabel" for="intent-dateUpdated">
                  <Translate contentKey="huroPocApp.intent.dateUpdated">Date Updated</Translate>
                </Label>
                <AvInput
                  id="intent-dateUpdated"
                  type="datetime-local"
                  className="form-control"
                  name="dateUpdated"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? null : convertDateTimeFromServer(props.intentEntity.dateUpdated)}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/intent" replace color="info">
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
  intentEntity: storeState.intent.entity,
  loading: storeState.intent.loading,
  updating: storeState.intent.updating,
  updateSuccess: storeState.intent.updateSuccess
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

export default connect(mapStateToProps, mapDispatchToProps)(IntentUpdate);
