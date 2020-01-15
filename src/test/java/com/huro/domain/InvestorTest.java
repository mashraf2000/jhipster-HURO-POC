package com.huro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.huro.web.rest.TestUtil;

public class InvestorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Investor.class);
        Investor investor1 = new Investor();
        investor1.setId(1L);
        Investor investor2 = new Investor();
        investor2.setId(investor1.getId());
        assertThat(investor1).isEqualTo(investor2);
        investor2.setId(2L);
        assertThat(investor1).isNotEqualTo(investor2);
        investor1.setId(null);
        assertThat(investor1).isNotEqualTo(investor2);
    }
}
