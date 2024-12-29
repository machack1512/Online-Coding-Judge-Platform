package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.PlayerActivityLogsTestSamples.*;
import static com.mycompany.myapp.domain.PlayerinfoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlayerinfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Playerinfo.class);
        Playerinfo playerinfo1 = getPlayerinfoSample1();
        Playerinfo playerinfo2 = new Playerinfo();
        assertThat(playerinfo1).isNotEqualTo(playerinfo2);

        playerinfo2.setId(playerinfo1.getId());
        assertThat(playerinfo1).isEqualTo(playerinfo2);

        playerinfo2 = getPlayerinfoSample2();
        assertThat(playerinfo1).isNotEqualTo(playerinfo2);
    }

    @Test
    void playerActivityLogsTest() {
        Playerinfo playerinfo = getPlayerinfoRandomSampleGenerator();
        PlayerActivityLogs playerActivityLogsBack = getPlayerActivityLogsRandomSampleGenerator();

        playerinfo.setPlayerActivityLogs(playerActivityLogsBack);
        assertThat(playerinfo.getPlayerActivityLogs()).isEqualTo(playerActivityLogsBack);

        playerinfo.playerActivityLogs(null);
        assertThat(playerinfo.getPlayerActivityLogs()).isNull();
    }
}
