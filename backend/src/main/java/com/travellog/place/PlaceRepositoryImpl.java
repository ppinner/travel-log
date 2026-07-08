package com.travellog.place;

import java.util.List;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class PlaceRepositoryImpl implements PlaceRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public PlaceRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Place> findWithinBounds(double swLat, double swLng, double neLat, double neLng) {
        GeoJsonPolygon boundingBox = new GeoJsonPolygon(
                new GeoJsonPoint(swLng, swLat),
                new GeoJsonPoint(neLng, swLat),
                new GeoJsonPoint(neLng, neLat),
                new GeoJsonPoint(swLng, neLat),
                new GeoJsonPoint(swLng, swLat));

        Query query = new Query(Criteria.where("location").within(boundingBox).and("active").is(true));
        return mongoTemplate.find(query, Place.class);
    }
}
