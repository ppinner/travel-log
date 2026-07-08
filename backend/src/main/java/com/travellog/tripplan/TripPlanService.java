package com.travellog.tripplan;

import com.travellog.common.exception.ForbiddenException;
import com.travellog.common.exception.ResourceNotFoundException;
import com.travellog.place.Place;
import com.travellog.place.PlaceService;
import com.travellog.place.dto.PlaceSummaryResponse;
import com.travellog.tripplan.dto.SavePlaceRequest;
import com.travellog.tripplan.dto.SavedPlaceResponse;
import com.travellog.tripplan.dto.UpdateSavedPlaceRequest;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TripPlanService {

    private final SavedPlaceRepository savedPlaceRepository;
    private final PlaceService placeService;

    public TripPlanService(SavedPlaceRepository savedPlaceRepository, PlaceService placeService) {
        this.savedPlaceRepository = savedPlaceRepository;
        this.placeService = placeService;
    }

    public List<SavedPlaceResponse> list(String userId, SavedPlaceStatus status) {
        List<SavedPlace> savedPlaces = status == null
                ? savedPlaceRepository.findByUserIdAndActiveTrue(userId)
                : savedPlaceRepository.findByUserIdAndStatusAndActiveTrue(userId, status);

        return savedPlaces.stream().map(this::toResponse).toList();
    }

    public SavedPlaceResponse save(SavePlaceRequest request, String userId) {
        placeService.findById(request.placeId());

        Instant now = Instant.now();
        // Reuses (and reactivates) an existing soft-deleted document for this (userId, placeId)
        // pair rather than creating a new one, since the pair is uniquely indexed.
        SavedPlace savedPlace = savedPlaceRepository.findByUserIdAndPlaceId(userId, request.placeId())
                .orElseGet(() -> SavedPlace.builder()
                        .userId(userId)
                        .placeId(request.placeId())
                        .createdAt(now)
                        .build());

        savedPlace.setStatus(request.status());
        savedPlace.setNotes(request.notes());
        savedPlace.setTargetDate(request.targetDate());
        savedPlace.setVisitedDate(request.status() == SavedPlaceStatus.VISITED ? now : null);
        savedPlace.setActive(true);
        savedPlace.setUpdatedAt(now);

        return toResponse(savedPlaceRepository.save(savedPlace));
    }

    public SavedPlaceResponse update(String id, UpdateSavedPlaceRequest request, String userId) {
        SavedPlace savedPlace = findById(id);
        assertOwner(savedPlace, userId);

        boolean becomingVisited =
                request.status() == SavedPlaceStatus.VISITED && savedPlace.getStatus() != SavedPlaceStatus.VISITED;

        savedPlace.setStatus(request.status());
        savedPlace.setNotes(request.notes());
        savedPlace.setTargetDate(request.targetDate());
        if (becomingVisited) {
            savedPlace.setVisitedDate(Instant.now());
        } else if (request.status() != SavedPlaceStatus.VISITED) {
            savedPlace.setVisitedDate(null);
        }
        savedPlace.setUpdatedAt(Instant.now());

        return toResponse(savedPlaceRepository.save(savedPlace));
    }

    public void delete(String id, String userId) {
        SavedPlace savedPlace = findById(id);
        assertOwner(savedPlace, userId);
        savedPlace.setActive(false);
        savedPlace.setUpdatedAt(Instant.now());
        savedPlaceRepository.save(savedPlace);
    }

    private SavedPlace findById(String id) {
        return savedPlaceRepository.findById(id)
                .filter(SavedPlace::isActive)
                .orElseThrow(() -> new ResourceNotFoundException("Saved place not found"));
    }

    private void assertOwner(SavedPlace savedPlace, String userId) {
        if (!savedPlace.getUserId().equals(userId)) {
            throw new ForbiddenException("You do not own this saved place");
        }
    }

    private SavedPlaceResponse toResponse(SavedPlace savedPlace) {
        Place place = placeService.findById(savedPlace.getPlaceId());
        return SavedPlaceResponse.from(savedPlace, PlaceSummaryResponse.from(place));
    }
}
