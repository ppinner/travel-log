package com.travellog.place;

import com.travellog.place.dto.CreatePlaceRequest;
import com.travellog.place.dto.PlaceResponse;
import com.travellog.place.dto.PlaceSummaryResponse;
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
@RequestMapping("/api/places")
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping
    public List<PlaceSummaryResponse> list(
            @RequestParam double swLat,
            @RequestParam double swLng,
            @RequestParam double neLat,
            @RequestParam double neLng) {
        return placeService.findWithinBounds(swLat, swLng, neLat, neLng).stream()
                .map(PlaceSummaryResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public PlaceResponse get(@PathVariable String id) {
        return PlaceResponse.from(placeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PlaceResponse> create(
            @Valid @RequestBody CreatePlaceRequest request, Authentication authentication) {
        Place place = placeService.create(request, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(PlaceResponse.from(place));
    }

    @PutMapping("/{id}")
    public PlaceResponse update(
            @PathVariable String id,
            @Valid @RequestBody CreatePlaceRequest request,
            Authentication authentication) {
        return PlaceResponse.from(placeService.update(id, request, authentication.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id, Authentication authentication) {
        placeService.delete(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
