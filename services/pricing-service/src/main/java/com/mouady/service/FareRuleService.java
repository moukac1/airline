package com.mouady.service;

import com.mouady.payload.request.FareRulesRequest;
import com.mouady.payload.response.FareRulesResponse;

import java.util.List;

public interface FareRuleService {
    FareRulesResponse createFareRules(FareRulesRequest request);
    FareRulesResponse getFareRulesById(Long id);
    FareRulesResponse getFareRulesByFareId(Long fareId);
    List<FareRulesResponse> getFareRulesByAirlineId(Long airlineId);
    FareRulesResponse updateFareRules(Long id, FareRulesRequest request);
    void deleteFareRules(Long id);
}
