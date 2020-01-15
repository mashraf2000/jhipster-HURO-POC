package com.huro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.huro.web.rest.TestUtil;

public class EntitySeekingFundTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntitySeekingFund.class);
        EntitySeekingFund entitySeekingFund1 = new EntitySeekingFund();
        entitySeekingFund1.setId(1L);
        EntitySeekingFund entitySeekingFund2 = new EntitySeekingFund();
        entitySeekingFund2.setId(entitySeekingFund1.getId());
        assertThat(entitySeekingFund1).isEqualTo(entitySeekingFund2);
        entitySeekingFund2.setId(2L);
        assertThat(entitySeekingFund1).isNotEqualTo(entitySeekingFund2);
        entitySeekingFund1.setId(null);
        assertThat(entitySeekingFund1).isNotEqualTo(entitySeekingFund2);
    }
}
