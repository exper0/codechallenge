package com.github.exper0.codechallenge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConnectedController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectedController.class);
    private static final String YES = "yes";
    private static final String NO = "no";
    private static final String FILENAME = "city.txt";
    private final RoadMap roadMap;

    @Autowired
    public ConnectedController(RoadMapLoader roadMapLoader) throws Exception {
        this.roadMap = roadMapLoader.load(FILENAME);
    }

    @GetMapping("/connected")
    public String connected(@RequestParam(name = "origin") String origin,
                            @RequestParam(name = "destination") String destination) {
        if (!StringUtils.hasText(origin) || !StringUtils.hasText(destination)) {
            return NO;
        }
        try {
            return roadMap.isConnected(origin.trim().toLowerCase(), destination.trim().toLowerCase()) ? YES : NO;
        } catch (CityNotFoundException e) {
            LOGGER.warn("city '{}' is not found", e.getCity());
            return NO;
        }
    }
}
