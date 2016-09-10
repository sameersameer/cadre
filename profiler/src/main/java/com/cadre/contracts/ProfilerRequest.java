package com.cadre.contracts;

import com.cadre.entities.Location;
import com.cadre.entities.TrafficSeverity;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by purushotham.m on 10/09/16.
 */
@Getter
@AllArgsConstructor
public class ProfilerRequest {
    TrafficSeverity serverity;
    Location location;
    long epoch;
    double distance;
}
