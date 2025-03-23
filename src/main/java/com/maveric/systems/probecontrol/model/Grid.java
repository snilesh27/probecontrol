package com.maveric.systems.probecontrol.model;

import java.util.List;

// Grid class is defined for rectifying boundries of surface  e.g : 5*5 = then total positions can be 25 with x,y axis
public class Grid {


    private final int width;
    private final int height;

    // keeping record of obstacles values
    private final List<Obstacle> obstacles;

    public Grid(int width, int height, List<Obstacle> obstacles) {
        this.width = width;
        this.height = height;
        this.obstacles = obstacles;
    }

    public boolean isInside(Position position) {
        return position.getX() >= 0 && position.getX() < width &&
                position.getY() >= 0 && position.getY() < height;
    }

    public boolean isObstacle(Position position) {
        return obstacles.stream().anyMatch(o -> o.equals(position));
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

}
