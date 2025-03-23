package com.maveric.systems.probecontrol.service;

import com.maveric.systems.probecontrol.exception.InvalidCommandException;
import com.maveric.systems.probecontrol.model.Position;
import com.maveric.systems.probecontrol.model.Direction;
import com.maveric.systems.probecontrol.model.Grid;
import com.maveric.systems.probecontrol.model.Obstacle;
import com.maveric.systems.probecontrol.model.Probe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProbeMovementServiceTest {

    private Grid grid;
    private ProbeMovementService service;
    private Probe probe;

    @BeforeEach
    void setUp() {
        grid = new Grid(5, 5, List.of(new Obstacle(2, 2)));
        probe = new Probe(new Position(0, 0), Direction.NORTH);
        service = new ProbeMovementService(grid, probe);
    }

    @Test
    void testMoveForwardWithoutObstacles() {
        service.executeCommands("F");
        assertEquals(0, probe.getPosition().getX());
        assertEquals(1, probe.getPosition().getY());
    }

    @Test
    void testTurnLeft() {
        service.executeCommands("L");
        assertEquals(Direction.WEST, probe.getDirection());
    }

    @Test
    void testTurnRight() {
        service.executeCommands("R");
        assertEquals(Direction.EAST, probe.getDirection());
    }

    @Test
    void testMoveBackward() {
        service.executeCommands("B");
        assertEquals(0, probe.getPosition().getX());
        assertEquals(4, probe.getPosition().getY()); // wraps around grid
    }

    @Test
    void testAvoidObstacle() {
        service.executeCommands("FFRFF"); // Would hit (2,2)
        assertEquals(new Position(1,2), probe.getPosition());
    }

    @Test
    void testStayWithinGridBoundaries() {
        service.executeCommands("LLLLFFFFFFFFFF");
        assertEquals(0, probe.getPosition().getX());
        assertEquals(0, probe.getPosition().getY());
    }

    @Test
    void testVisitedCoordinatesSummary() {
        service.executeCommands("FFRFF");
        List<Position> visited = probe.getVisitedPositions();
        assertEquals(4, visited.size());
        assertTrue(visited.contains(new Position(0, 1)));
        assertTrue(visited.contains(new Position(1, 2)));
    }

    @Test
    void testInvalidCommandShouldThrowException() {
        Probe probe = new Probe(new Position(0, 0), Direction.NORTH);
        Grid grid = new Grid(5, 5, List.of());
        ProbeMovementService service = new ProbeMovementService(grid, probe);

        Exception exception = assertThrows(InvalidCommandException.class, () -> {
            service.validateCommands("FXR"); // 'X' is invalid
        });

        assertEquals("Invalid command sequence: Only F, B, L, R are allowed.", exception.getMessage());
    }
}
