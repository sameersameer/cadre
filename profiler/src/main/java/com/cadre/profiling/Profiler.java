package com.cadre.profiling;

import com.cadre.contracts.ProfileResponse;
import com.cadre.contracts.ProfilerRequest;
import com.cadre.entities.FuzzifiedProfileRequest;
import com.cadre.entities.TrafficSeverity;
import com.cadre.fuzzifying.LocationFuzzifier;
import com.cadre.fuzzifying.TimeFuzzifier;
import lombok.NoArgsConstructor;

/**
 * Created by purushotham.m on 10/09/16.
 */
@NoArgsConstructor
public class Profiler {

    ProfileResponse run(ProfilerRequest request){
        profileSeverity(getFuzzifiedProfileRequest(request));
        return null;
    }

    FuzzifiedProfileRequest getFuzzifiedProfileRequest(ProfilerRequest profilerRequest){
        return new FuzzifiedProfileRequest(LocationFuzzifier.fuzzify(profilerRequest),TimeFuzzifier.fuzzify(profilerRequest),profilerRequest.getServerity());
    }

    TrafficSeverity profileSeverity(FuzzifiedProfileRequest request){
        return request.getSeverity();
    }
}
