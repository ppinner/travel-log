package com.travellog.place;

import com.travellog.common.exception.ForbiddenException;
import com.travellog.common.exception.ResourceNotFoundException;
import com.travellog.place.dto.CreatePlaceRequest;
import java.time.Instant;
import java.util.List;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public List<Place> findWithinBounds(double swLat, double swLng, double neLat, double neLng) {
        return placeRepository.findWithinBounds(swLat, swLng, neLat, neLng);
    }

    public Place findById(String id) {
        return placeRepository.findById(id)
                .filter(Place::isActive)
                .orElseThrow(() -> new ResourceNotFoundException("Place not found"));
    }

    public Place create(CreatePlaceRequest request, String userId) {
        Instant now = Instant.now();
        Place place = Place.builder()
                .name(request.name())
                .description(request.description())
                .category(request.category())
                .country(request.country())
                .city(request.city())
                .location(new GeoJsonPoint(request.lng(), request.lat()))
                .createdBy(userId)
                .ratingSummary(RatingSummary.empty())
                .active(true)
                .createdAt(now)
                .updatedAt(now)
                .build();
        return placeRepository.save(place);
    }

    public Place update(String id, CreatePlaceRequest request, String userId) {
        Place place = findById(id);
        assertOwner(place, userId);

        place.setName(request.name());
        place.setDescription(request.description());
        place.setCategory(request.category());
        place.setCountry(request.country());
        place.setCity(request.city());
        place.setLocation(new GeoJsonPoint(request.lng(), request.lat()));
        place.setUpdatedAt(Instant.now());
        return placeRepository.save(place);
    }

    public void delete(String id, String userId) {
        Place place = findById(id);
        assertOwner(place, userId);
        place.setActive(false);
        place.setUpdatedAt(Instant.now());
        placeRepository.save(place);
    }

    public void updateRatingSummary(String placeId, RatingSummary summary) {
        Place place = findById(placeId);
        place.setRatingSummary(summary);
        placeRepository.save(place);
    }

    private void assertOwner(Place place, String userId) {
        if (!place.getCreatedBy().equals(userId)) {
            throw new ForbiddenException("You do not own this place");
        }
    }
}
