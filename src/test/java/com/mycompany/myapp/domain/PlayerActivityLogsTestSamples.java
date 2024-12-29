package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerActivityLogsTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static PlayerActivityLogs getPlayerActivityLogsSample1() {
        return new PlayerActivityLogs().id("id1").playerId("playerId1").action("action1").beforeBalance(2F).afterBalance(2F);
    }

    public static PlayerActivityLogs getPlayerActivityLogsSample2() {
        return new PlayerActivityLogs().id("id2").playerId("playerId2").action("action2").beforeBalance(2F).afterBalance(2F);
    }

    public static PlayerActivityLogs getPlayerActivityLogsRandomSampleGenerator() {
        return new PlayerActivityLogs()
            .id(UUID.randomUUID().toString())
            .playerId(UUID.randomUUID().toString())
            .action(UUID.randomUUID().toString())
            .beforeBalance((float) intCount.incrementAndGet())
            .afterBalance((float) intCount.incrementAndGet());
    }
}
