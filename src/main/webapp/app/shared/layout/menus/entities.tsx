import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name={translate('global.menu.entities.main')} id="entity-menu">
    <MenuItem icon="asterisk" to="/group-message">
      <Translate contentKey="global.menu.entities.groupMessage" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/message">
      <Translate contentKey="global.menu.entities.message" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/organization-profile">
      <Translate contentKey="global.menu.entities.organizationProfile" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity-seeking-fund">
      <Translate contentKey="global.menu.entities.entitySeekingFund" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/vendor">
      <Translate contentKey="global.menu.entities.vendor" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/investor">
      <Translate contentKey="global.menu.entities.investor" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/contact">
      <Translate contentKey="global.menu.entities.contact" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/intent">
      <Translate contentKey="global.menu.entities.intent" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/compliance">
      <Translate contentKey="global.menu.entities.compliance" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/region">
      <Translate contentKey="global.menu.entities.region" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/country">
      <Translate contentKey="global.menu.entities.country" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
