package com.maveric.systems.probecontrol.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PositionTest {

    @Test
    void testPositionEquality() {
        Position p1 = new Position(1, 2);
        Position p2 = new Position(1, 2);
        assertEquals(p1, p2);
    }

    @Test
    void testHashCode() {
        Position p1 = new Position(1, 2);
        Position p2 = new Position(1, 2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }
}