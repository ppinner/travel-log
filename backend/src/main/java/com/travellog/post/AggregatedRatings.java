package com.travellog.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AggregatedRatings {

    private int count;

    @Field("avgCost")
    private double avgCost;

    @Field("avgAuthenticity")
    private double avgAuthenticity;

    @Field("avgEnjoyment")
    private double avgEnjoyment;

    @Field("avgFitnessRequired")
    private double avgFitnessRequired;
}
