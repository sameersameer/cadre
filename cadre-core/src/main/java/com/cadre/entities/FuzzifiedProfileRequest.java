package com.cadre.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by purushotham.m on 10/09/16.
 */
@AllArgsConstructor
@Getter
public class FuzzifiedProfileRequest {
    FuzzyLocation location;
    FuzzyTime time;
    TrafficSeverity severity;
}
