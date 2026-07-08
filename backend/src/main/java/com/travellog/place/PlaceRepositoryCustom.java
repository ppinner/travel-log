package com.travellog.place;

import java.util.List;

public interface PlaceRepositoryCustom {

    List<Place> findWithinBounds(double swLat, double swLng, double neLat, double neLng);
}
