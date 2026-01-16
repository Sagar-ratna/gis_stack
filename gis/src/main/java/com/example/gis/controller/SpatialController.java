package com.example.gis.controller;

import com.example.gis.dto.PlaceCreateRequest;
import com.example.gis.entity.Place;
import com.example.gis.service.SpatialService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/spatial")
@CrossOrigin
public class SpatialController {

    private final SpatialService service;

    public SpatialController(SpatialService service) {
        this.service = service;
    }

    @PostMapping("/places")
    public Place createPlace(
            @RequestBody PlaceCreateRequest request
    ) {
        return service.createPlace(request);
    }

    @GetMapping("/nearby")
    public Map<String, Object> nearby(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(defaultValue = "5000") double radius
    ) {
        return service.findNearby(lat, lon, radius);
    }

    @GetMapping("/distance")
    public double distance(
            @RequestParam double lat1,
            @RequestParam double lon1,
            @RequestParam double lat2,
            @RequestParam double lon2
    ) {
        return service.calculateDistance(lat1, lon1, lat2, lon2);
    }
}
