package com.maveric.systems.probecontrol.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProbeTest {
    @Test
    void testInitialPositionAndDirection() {
        Probe probe = new Probe(new Position(0, 0), Direction.NORTH);
        assertEquals(0, probe.getPosition().getX());
        assertEquals(Direction.NORTH, probe.getDirection());
    }
}
