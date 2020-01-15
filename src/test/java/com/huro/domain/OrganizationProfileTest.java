package com.huro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.huro.web.rest.TestUtil;

public class OrganizationProfileTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationProfile.class);
        OrganizationProfile organizationProfile1 = new OrganizationProfile();
        organizationProfile1.setId(1L);
        OrganizationProfile organizationProfile2 = new OrganizationProfile();
        organizationProfile2.setId(organizationProfile1.getId());
        assertThat(organizationProfile1).isEqualTo(organizationProfile2);
        organizationProfile2.setId(2L);
        assertThat(organizationProfile1).isNotEqualTo(organizationProfile2);
        organizationProfile1.setId(null);
        assertThat(organizationProfile1).isNotEqualTo(organizationProfile2);
    }
}
