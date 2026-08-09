package com.mouady.controller;


import com.mouady.payload.request.FareRulesRequest;
import com.mouady.payload.response.FareRulesResponse;
import com.mouady.service.FareRuleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fare-rules")
@RequiredArgsConstructor
public class FareRuleController {

    private final FareRuleService fareRuleService;

    @PostMapping
    public ResponseEntity<FareRulesResponse> createFareRules(
            @Valid @RequestBody FareRulesRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(fareRuleService.createFareRules(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FareRulesResponse> getFareRulesById(@PathVariable Long id) {
        return ResponseEntity.ok(fareRuleService.getFareRulesById(id));
    }

    @GetMapping("/fare/{fareId}")
    public ResponseEntity<FareRulesResponse> getFareRulesByFareId(
            @PathVariable Long fareId) {
        return ResponseEntity.ok(fareRuleService.getFareRulesByFareId(fareId));
    }

    @GetMapping("/airline/{airlineId}")
    public ResponseEntity<List<FareRulesResponse>> getFareRulesByAirlineId(
            @PathVariable Long airlineId) {
        return ResponseEntity.ok(fareRuleService.getFareRulesByAirlineId(airlineId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FareRulesResponse> updateFareRules(
            @PathVariable Long id,
            @Valid @RequestBody FareRulesRequest request) {
        return ResponseEntity.ok(fareRuleService.updateFareRules(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFareRules(@PathVariable Long id) {
        fareRuleService.deleteFareRules(id);
        System.out.println("deleted successfully with id: " + id);
        return ResponseEntity.noContent().build();
    }
}

