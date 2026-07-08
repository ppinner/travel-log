package com.travellog.tripplan;

import com.travellog.tripplan.dto.SavePlaceRequest;
import com.travellog.tripplan.dto.SavedPlaceResponse;
import com.travellog.tripplan.dto.UpdateSavedPlaceRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tripplan")
public class TripPlanController {

    private final TripPlanService tripPlanService;

    public TripPlanController(TripPlanService tripPlanService) {
        this.tripPlanService = tripPlanService;
    }

    @GetMapping
    public List<SavedPlaceResponse> list(
            @RequestParam(required = false) SavedPlaceStatus status, Authentication authentication) {
        return tripPlanService.list(authentication.getName(), status);
    }

    @PostMapping
    public ResponseEntity<SavedPlaceResponse> save(
            @Valid @RequestBody SavePlaceRequest request, Authentication authentication) {
        SavedPlaceResponse response = tripPlanService.save(request, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public SavedPlaceResponse update(
            @PathVariable String id,
            @Valid @RequestBody UpdateSavedPlaceRequest request,
            Authentication authentication) {
        return tripPlanService.update(id, request, authentication.getName());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id, Authentication authentication) {
        tripPlanService.delete(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
