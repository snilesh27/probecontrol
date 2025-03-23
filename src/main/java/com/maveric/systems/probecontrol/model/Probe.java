package com.maveric.systems.probecontrol.model;

import java.util.ArrayList;
import java.util.List;
// Probe class represent movable Object which has properties like position , direction and tracking visitedPositions
public class Probe {

    private Position position;
    private Direction direction;
    private List<Position> visitedPositions = new ArrayList<>();

    public Probe(Position position, Direction direction) {
        this.position = position;
        this.direction = direction;
        visitedPositions.add(new Position(position)); // Add start position only once
    }

    public Position getPosition() { return position; }

    public Direction getDirection() { return direction; }

    public List<Position> getVisitedPositions() { return visitedPositions; }

    public void updatePosition(Position newPos) {
        this.position = newPos;
        visitedPositions.add(new Position(newPos)); // Always new copy
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
