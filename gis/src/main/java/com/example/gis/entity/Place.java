package com.example.gis.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String category;
    @Column(columnDefinition = "geography(Point,4326)", nullable = false)
    private org.locationtech.jts.geom.Point geo;
    @Column(columnDefinition = "geometry(Point,4326)", insertable = false, updatable = false)
    private Point geom;
}
