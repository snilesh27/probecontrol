# Submersible Probe Control API

## Overview
REST API to remotely control a submersible probe on a grid-based ocean floor.

## Features:
- Move **Forward (F)** / **Backward (B)**
- Turn **Left (L)** / **Right (R)**
- Avoid obstacles
- Wrap-around grid boundaries
- Keep track of visited positions

## API Endpoints
**Commands:**
- 'F': Move forward
- 'B': Move backward
- 'L': Turn left
- 'R': Turn right

--- 
probecontrol application can be build with command =  mvn clean install
application can be run with command = - mvn spring-boot:run
once application is up on console with message - Tomcat started on port 8080 (http) with context path '/'
run api's with postman :
1. Sample Request
2. POST API - http://localhost:8080/probe/commands
{
   "commands":"FFRFF"
}
O/P :
   Current Position: (1,2) Facing: EAST

2. GET API -  http://localhost:8080/probe/visited
   Sample o/p ::
[
   {
   "x": 0,
   "y": 0
   },
   {
   "x": 0,
   "y": 1
   },
   {
   "x": 0,
   "y": 2
   },
   {
   "x": 1,
   "y": 2
   }
]