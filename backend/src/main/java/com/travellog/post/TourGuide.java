package com.travellog.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourGuide {

    private String name;
    private ContactMethod contactMethod;
    private String contactValue;
    private String note;
}
