package com.travellog.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ratings {

    private int cost;
    private int authenticity;
    private int enjoyment;
    private int fitnessRequired;
}
