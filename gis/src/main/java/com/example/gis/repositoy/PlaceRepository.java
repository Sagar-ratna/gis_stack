package com.example.gis.repositoy;

import com.example.gis.dto.PlaceGeoView;
import com.example.gis.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PlaceRepository extends JpaRepository <Place,Long>{
    @Query(value = """
        SELECT
            id,
            name,
            category,
            ST_X(geom) AS lon,
            ST_Y(geom) AS lat,
            ST_Distance(
                geo,
                ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography
            ) AS distance
        FROM place
        WHERE geom && ST_Expand(
            ST_SetSRID(ST_MakePoint(:lon, :lat), 4326),
            :bbox
        )
        AND ST_DWithin(
            geo,
            ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography,
            :radius
        )
        ORDER BY distance
        """, nativeQuery = true)
    List<PlaceGeoView> findNearbyOptimized(
            double lat,
            double lon,
            double radius,
            double bbox
    );
    @Query(value = """
        SELECT ST_Distance(
            ST_SetSRID(ST_MakePoint(:lon1, :lat1), 4326)::geography,
            ST_SetSRID(ST_MakePoint(:lon2, :lat2), 4326)::geography
        )
        """, nativeQuery = true)
    Double calculateDistance(
            double lat1, double lon1,
            double lat2, double lon2
    );
}



