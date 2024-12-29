package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerinfoTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Playerinfo getPlayerinfoSample1() {
        return new Playerinfo().id("id1").playerId("playerId1").playerName("playerName1").balance(1F);
    }

    public static Playerinfo getPlayerinfoSample2() {
        return new Playerinfo().id("id2").playerId("playerId2").playerName("playerName2").balance(2F);
    }

    public static Playerinfo getPlayerinfoRandomSampleGenerator() {
        return new Playerinfo()
            .id(UUID.randomUUID().toString())
            .playerId(UUID.randomUUID().toString())
            .playerName(UUID.randomUUID().toString())
            .balance((float) intCount.incrementAndGet());
    }
}
