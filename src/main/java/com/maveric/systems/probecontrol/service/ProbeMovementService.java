package com.maveric.systems.probecontrol.service;

import com.maveric.systems.probecontrol.exception.InvalidCommandException;
import com.maveric.systems.probecontrol.model.Grid;
import com.maveric.systems.probecontrol.model.Position;
import com.maveric.systems.probecontrol.model.Probe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// service class having logic for executeCommands and move function
public class ProbeMovementService {

        private final Grid grid;
        private final Probe probe;

        public ProbeMovementService(Grid grid, Probe probe) {
            this.grid = grid;
            this.probe = probe;
        }

        // defined switch function with interpret FBLR - and set directions
        public void executeCommands(String commands) {
            for (char command : commands.toCharArray()) {
                switch (command) {
                    case 'F' -> move(true);
                    case 'B' -> move(false);
                    case 'L' -> probe.setDirection(probe.getDirection().turnLeft());
                    case 'R' -> probe.setDirection(probe.getDirection().turnRight());
                }
            }
        }

        // identify the position value and update position accordingly
        private void move(boolean forward) {
            Position current = probe.getPosition();
            Position next = new Position(current);

            int move = forward ? 1 : -1;
            switch (probe.getDirection()) {
                case NORTH -> next.setY((current.getY() + move + grid.getHeight()) % grid.getHeight());
                case SOUTH -> next.setY((current.getY() - move + grid.getHeight()) % grid.getHeight());
                case EAST  -> next.setX((current.getX() + move + grid.getWidth()) % grid.getWidth());
                case WEST  -> next.setX((current.getX() - move + grid.getWidth()) % grid.getWidth());
            }
            // new position can be updated in case its not having obstacle position
            if (!grid.isObstacle(next)) {
                probe.updatePosition(next);
            }
        }

    // this method can be validate commands sent valid one
    public void validateCommands(String commands) {
        if (!commands.matches("[FBLR]*")) {
            throw new InvalidCommandException("Invalid command sequence: Only F, B, L, R are allowed.");
        }
    }
}
