package com.example.gis.dto;

public record PlaceCreateRequest(String name,
                                 String category,
                                 double lat,
                                 double lon) {

}
