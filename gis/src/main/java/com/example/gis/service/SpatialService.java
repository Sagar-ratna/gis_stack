package com.example.gis.service;

import com.example.gis.dto.PlaceCreateRequest;
import com.example.gis.entity.Place;
import com.example.gis.geoMapper.GeoJsonMapper;
import com.example.gis.repositoy.PlaceRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Map;

@Service
public class SpatialService {
    private final PlaceRepository repository;
    private final GeoJsonMapper geoJsonMapper;
    private final GeometryFactory geometryFactory =
            new GeometryFactory(new PrecisionModel(), 4326);

    public SpatialService(PlaceRepository repository, GeoJsonMapper geoJsonMapper) {
        this.repository = repository;
        this.geoJsonMapper = geoJsonMapper;
    }

    @Transactional
    public Place createPlace(PlaceCreateRequest req) {
        Point point = geometryFactory.createPoint(
                new Coordinate(req.lon(), req.lat())
        );

        Place place = new Place();
        place.setName(req.name());
        place.setCategory(req.category());
        place.setGeo(point);

        return repository.save(place);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> findNearby(
            double lat,
            double lon,
            double radiusMeters
    ) {
        double bboxDegrees = radiusMeters / 111_000.0;
        var results = repository.findNearbyOptimized(
                lat, lon, radiusMeters, bboxDegrees
        );
        return geoJsonMapper.toFeatureCollection(results);
    }

    @Transactional(readOnly = true)
    public double calculateDistance(
            double lat1, double lon1,
            double lat2, double lon2
    ) {
        return repository.calculateDistance(lat1, lon1, lat2, lon2);
    }
}
