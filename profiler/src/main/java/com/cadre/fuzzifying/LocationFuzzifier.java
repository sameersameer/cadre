package com.cadre.fuzzifying;

import com.cadre.contracts.ProfilerRequest;
import com.cadre.entities.FuzzyLocation;
import com.cadre.entities.Location;

/**
 * Created by purushotham.m on 10/09/16.
 */
public class LocationFuzzifier {

    public static FuzzyLocation fuzzify(ProfilerRequest profilerRequest){
        //Mocked
        return FuzzyLocation.COMMERCIAL;
    }
}
