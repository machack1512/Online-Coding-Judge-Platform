package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.PlayerActivityLogsTestSamples.*;
import static com.mycompany.myapp.domain.PlayerinfoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PlayerActivityLogsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlayerActivityLogs.class);
        PlayerActivityLogs playerActivityLogs1 = getPlayerActivityLogsSample1();
        PlayerActivityLogs playerActivityLogs2 = new PlayerActivityLogs();
        assertThat(playerActivityLogs1).isNotEqualTo(playerActivityLogs2);

        playerActivityLogs2.setId(playerActivityLogs1.getId());
        assertThat(playerActivityLogs1).isEqualTo(playerActivityLogs2);

        playerActivityLogs2 = getPlayerActivityLogsSample2();
        assertThat(playerActivityLogs1).isNotEqualTo(playerActivityLogs2);
    }

    @Test
    void onetomanyTest() {
        PlayerActivityLogs playerActivityLogs = getPlayerActivityLogsRandomSampleGenerator();
        Playerinfo playerinfoBack = getPlayerinfoRandomSampleGenerator();

        playerActivityLogs.addOnetomany(playerinfoBack);
        assertThat(playerActivityLogs.getOnetomanies()).containsOnly(playerinfoBack);
        assertThat(playerinfoBack.getPlayerActivityLogs()).isEqualTo(playerActivityLogs);

        playerActivityLogs.removeOnetomany(playerinfoBack);
        assertThat(playerActivityLogs.getOnetomanies()).doesNotContain(playerinfoBack);
        assertThat(playerinfoBack.getPlayerActivityLogs()).isNull();

        playerActivityLogs.onetomanies(new HashSet<>(Set.of(playerinfoBack)));
        assertThat(playerActivityLogs.getOnetomanies()).containsOnly(playerinfoBack);
        assertThat(playerinfoBack.getPlayerActivityLogs()).isEqualTo(playerActivityLogs);

        playerActivityLogs.setOnetomanies(new HashSet<>());
        assertThat(playerActivityLogs.getOnetomanies()).doesNotContain(playerinfoBack);
        assertThat(playerinfoBack.getPlayerActivityLogs()).isNull();
    }
}
