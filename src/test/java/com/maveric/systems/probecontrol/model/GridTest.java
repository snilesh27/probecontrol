package com.maveric.systems.probecontrol.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GridTest {
    @Test
    void testIsInside() {
        Grid grid = new Grid(5, 5, List.of());
        assertTrue(grid.isInside(new Position(2, 2)));
        assertFalse(grid.isInside(new Position(6, 6)));
    }

    @Test
    void testObstacleDetection() {
        Grid grid = new Grid(5, 5, List.of(new Obstacle(2, 2)));
        assertTrue(grid.isObstacle(new Position(2, 2)));
    }
}
