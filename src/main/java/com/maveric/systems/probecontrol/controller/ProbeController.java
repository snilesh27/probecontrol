package com.maveric.systems.probecontrol.controller;

import com.maveric.systems.probecontrol.model.*;
import com.maveric.systems.probecontrol.service.ProbeMovementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/probe")
public class ProbeController {

    private final ProbeMovementService service;
    private final Probe probe;

    public ProbeController() {
        // grid size - 5 * 5 = 25 in size , from {0,0} , {0,1} ...{4,4} in total blocks and obstacle taken {2,2} place.
        Grid grid = new Grid(5, 5, List.of(new Obstacle(2, 2)));
        probe = new Probe(new Position(0, 0), Direction.NORTH);
        service = new ProbeMovementService(grid, probe);
    }

    @PostMapping("/commands")
    public String executeCommands(@RequestBody ProbeCommandRequest commands) {
        service.validateCommands(commands.getCommands());
        service.executeCommands(commands.getCommands());
        return "Current Position: (" + probe.getPosition().getX() + "," + probe.getPosition().getY() + ") Facing: " + probe.getDirection();
    }

    @GetMapping("/visited")
    public List<Position> getVisitedPositions() {
        return probe.getVisitedPositions();
    }
}
