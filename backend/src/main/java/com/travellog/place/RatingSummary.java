package com.travellog.place;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingSummary {

    private int count;
    private double avgCost;
    private double avgAuthenticity;
    private double avgEnjoyment;
    private double avgFitnessRequired;

    public static RatingSummary empty() {
        return RatingSummary.builder().count(0).avgCost(0).avgAuthenticity(0).avgEnjoyment(0).avgFitnessRequired(0)
                .build();
    }
}
