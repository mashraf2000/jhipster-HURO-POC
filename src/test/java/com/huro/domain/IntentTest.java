package com.huro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.huro.web.rest.TestUtil;

public class IntentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Intent.class);
        Intent intent1 = new Intent();
        intent1.setId(1L);
        Intent intent2 = new Intent();
        intent2.setId(intent1.getId());
        assertThat(intent1).isEqualTo(intent2);
        intent2.setId(2L);
        assertThat(intent1).isNotEqualTo(intent2);
        intent1.setId(null);
        assertThat(intent1).isNotEqualTo(intent2);
    }
}
