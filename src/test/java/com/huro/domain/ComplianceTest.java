package com.huro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.huro.web.rest.TestUtil;

public class ComplianceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Compliance.class);
        Compliance compliance1 = new Compliance();
        compliance1.setId(1L);
        Compliance compliance2 = new Compliance();
        compliance2.setId(compliance1.getId());
        assertThat(compliance1).isEqualTo(compliance2);
        compliance2.setId(2L);
        assertThat(compliance1).isNotEqualTo(compliance2);
        compliance1.setId(null);
        assertThat(compliance1).isNotEqualTo(compliance2);
    }
}
