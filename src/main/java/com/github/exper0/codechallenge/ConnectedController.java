package com.github.exper0.codechallenge;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "API to check connectivity between two cities in pre-loaded map")
public class ConnectedController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectedController.class);
    private static final String YES = "yes";
    private static final String NO = "no";
    private static final String FILENAME = "city.txt";
    private final RoadMap roadMap;

    @Autowired
    public ConnectedController(RoadMapLoader roadMapLoader) {
        this.roadMap = roadMapLoader.load(FILENAME);
    }

    @ApiOperation(value = "Checks connectivity between two given cities")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "'yes' if it's possible to get from origin city to destination, 'no' otherwise"),
            @ApiResponse(code = 400, message = "either origin or destination is not set")
    })
    @GetMapping(value = "/connected", produces = "text/plain")
    public String connected(@RequestParam(name = "origin") @ApiParam(value = "Origin city") String origin,
                            @RequestParam(name = "destination") @ApiParam(value = "Destination city") String destination) {
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
