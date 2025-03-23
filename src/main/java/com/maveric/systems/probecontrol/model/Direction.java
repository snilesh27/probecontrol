package com.maveric.systems.probecontrol.model;

/*
* Enum defined for direction.
* NORTH= 0 [suppose initial value], EAST=1 , SOUTH=2 , WEST=3
* values for ordinal position index to identify direction
* %4 is wrapping it to final values between 0-3
* */
public enum Direction {
    NORTH, EAST, SOUTH, WEST;

    public Direction turnLeft() {
        return values()[(ordinal() + 3) % 4];
    }

    public Direction turnRight() {
        return values()[(ordinal() + 1) % 4];
    }
}
