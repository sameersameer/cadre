package com.cadre.fuzzifying;

import com.cadre.contracts.ProfilerRequest;
import com.cadre.entities.FuzzyTime;

/**
 * Created by purushotham.m on 10/09/16.
 */
public class TimeFuzzifier {

    public static FuzzyTime fuzzify(ProfilerRequest request){
        return FuzzyTime.NIGHT;
    }
}
