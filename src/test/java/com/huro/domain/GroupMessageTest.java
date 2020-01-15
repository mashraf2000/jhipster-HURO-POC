package com.huro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.huro.web.rest.TestUtil;

public class GroupMessageTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupMessage.class);
        GroupMessage groupMessage1 = new GroupMessage();
        groupMessage1.setId(1L);
        GroupMessage groupMessage2 = new GroupMessage();
        groupMessage2.setId(groupMessage1.getId());
        assertThat(groupMessage1).isEqualTo(groupMessage2);
        groupMessage2.setId(2L);
        assertThat(groupMessage1).isNotEqualTo(groupMessage2);
        groupMessage1.setId(null);
        assertThat(groupMessage1).isNotEqualTo(groupMessage2);
    }
}
