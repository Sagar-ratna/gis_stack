package com.example.gis.geoMapper;

import com.example.gis.dto.PlaceGeoView;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class GeoJsonMapper {

    public Map<String, Object> toFeatureCollection(
            List<PlaceGeoView> places
    ) {
        List<Map<String, Object>> features = places.stream().map(p ->
                Map.of(
                        "type", "Feature",
                        "geometry", Map.of(
                                "type", "Point",
                                "coordinates", List.of(p.getLon(), p.getLat())
                        ),
                        "properties", Map.of(
                                "id", p.getId(),
                                "name", p.getName(),
                                "category", p.getCategory(),
                                "distance_m",
                                Math.round(p.getDistance())
                        )
                )
        ).toList();

        return Map.of(
                "type", "FeatureCollection",
                "features", features
        );
    }
}
