package com.travellog.tripplan;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SavedPlaceRepository extends MongoRepository<SavedPlace, String> {

    List<SavedPlace> findByUserIdAndActiveTrue(String userId);

    List<SavedPlace> findByUserIdAndStatusAndActiveTrue(String userId, SavedPlaceStatus status);

    // Deliberately not filtered by active: saving a place the user previously removed should
    // reactivate the existing document (see TripPlanService.save) rather than violate the
    // unique (userId, placeId) index by creating a second one.
    Optional<SavedPlace> findByUserIdAndPlaceId(String userId, String placeId);
}
