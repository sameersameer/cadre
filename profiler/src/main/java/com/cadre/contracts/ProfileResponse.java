package com.cadre.contracts;

import com.cadre.entities.TrafficSeverity;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by purushotham.m on 10/09/16.
 */
@AllArgsConstructor
@Getter
public class ProfileResponse {
    TrafficSeverity trafficSeverity;
    double distance;
}
