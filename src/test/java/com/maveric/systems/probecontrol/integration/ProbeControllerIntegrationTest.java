package com.maveric.systems.probecontrol.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maveric.systems.probecontrol.model.*;
import org.junit.jupiter.api.Test;

import com.maveric.systems.probecontrol.service.ProbeMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProbeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // Add this in your test class

    @TestConfiguration
    static class TestProbeConfig {

        @Bean
        public Grid testGrid() {
            // Define a 5x5 grid with no obstacles for testing
            return new Grid(5, 5, List.of());
        }

        @Bean
        public Probe testProbe() {
            return new Probe(new Position(0, 0), Direction.NORTH);
        }

        @Bean
        public ProbeMovementService probeMovementService(Grid grid, Probe probe) {
            return new ProbeMovementService(grid, probe);
        }
    }

    @Test
    void testExecuteValidCommands() throws Exception {
        ProbeCommandRequest probeCommandRequest = new ProbeCommandRequest();
        probeCommandRequest.setCommands("FFRBBL");
        mockMvc.perform(post("/probe/commands")
                       .content(objectMapper.writeValueAsString(probeCommandRequest)) // Proper JSON body
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Current Position: (3,2) Facing: NORTH")));
    }

    @Test
    void testExecuteInvalidCommands() throws Exception {
        ProbeCommandRequest probeCommandRequest = new ProbeCommandRequest();
        probeCommandRequest.setCommands("FXLR"); // Invalid X
        mockMvc.perform(post("/probe/commands")
                        .content(objectMapper.writeValueAsString(probeCommandRequest)) // Proper JSON body
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Invalid command sequence")));
    }

    @Test
    void testVisitedPositions() throws Exception {
        ProbeCommandRequest probeCommandRequest = new ProbeCommandRequest();
        probeCommandRequest.setCommands("F");
        // Move probe first
        mockMvc.perform(post("/probe/commands")
                        .content(objectMapper.writeValueAsString(probeCommandRequest)) // Proper JSON body
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Check visited positions
        mockMvc.perform(get("/probe/visited"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"x\":0")))
                .andExpect(content().string(containsString("\"y\":1")));

    }
}
