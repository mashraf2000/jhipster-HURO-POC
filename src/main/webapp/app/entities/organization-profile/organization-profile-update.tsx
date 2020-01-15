import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './organization-profile.reducer';
import { IOrganizationProfile } from 'app/shared/model/organization-profile.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOrganizationProfileUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OrganizationProfileUpdate = (props: IOrganizationProfileUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { organizationProfileEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/organization-profile' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...organizationProfileEntity,
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
          <h2 id="huroPocApp.organizationProfile.home.createOrEditLabel">
            <Translate contentKey="huroPocApp.organizationProfile.home.createOrEditLabel">Create or edit a OrganizationProfile</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : organizationProfileEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="organization-profile-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="organization-profile-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="languageLabel" for="organization-profile-language">
                  <Translate contentKey="huroPocApp.organizationProfile.language">Language</Translate>
                </Label>
                <AvInput
                  id="organization-profile-language"
                  type="select"
                  className="form-control"
                  name="language"
                  value={(!isNew && organizationProfileEntity.language) || 'FRENCH'}
                >
                  <option value="FRENCH">{translate('huroPocApp.Language.FRENCH')}</option>
                  <option value="ENGLISH">{translate('huroPocApp.Language.ENGLISH')}</option>
                  <option value="SPANISH">{translate('huroPocApp.Language.SPANISH')}</option>
                  <option value="GERMAN">{translate('huroPocApp.Language.GERMAN')}</option>
                  <option value="CHINESE">{translate('huroPocApp.Language.CHINESE')}</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/organization-profile" replace color="info">
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
  organizationProfileEntity: storeState.organizationProfile.entity,
  loading: storeState.organizationProfile.loading,
  updating: storeState.organizationProfile.updating,
  updateSuccess: storeState.organizationProfile.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OrganizationProfileUpdate);
