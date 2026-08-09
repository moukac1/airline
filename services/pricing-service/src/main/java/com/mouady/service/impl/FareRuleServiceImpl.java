package com.mouady.service.impl;

import com.mouady.mapper.FareRuleMapper;
import com.mouady.model.Fare;
import com.mouady.model.FareRule;
import com.mouady.payload.request.FareRulesRequest;
import com.mouady.payload.response.FareRulesResponse;
import com.mouady.repository.FareRepository;
import com.mouady.repository.FareRuleRepository;
import com.mouady.service.FareRuleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FareRuleServiceImpl implements FareRuleService {

    private final FareRuleRepository fareRuleRepository;
    private final FareRepository fareRepository;

    @Override
    public FareRulesResponse createFareRules(FareRulesRequest request) {
        Fare fare = fareRepository.findById(request.getFareId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Fare not found with id: " + request.getFareId()));

        if (fareRuleRepository.existsByFareId(request.getFareId())) {
            throw new IllegalArgumentException(
                    "Fare rules already exist for fare id: " + request.getFareId());
        }

        FareRule fareRules = FareRuleMapper.toEntity(request, fare);
        FareRule saved = fareRuleRepository.save(fareRules);
        return FareRuleMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public FareRulesResponse getFareRulesById(Long id) {
        FareRule fareRules = fareRuleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Fare rules not found with id: " + id));
        return FareRuleMapper.toResponse(fareRules);
    }

    @Override
    @Transactional(readOnly = true)
    public FareRulesResponse getFareRulesByFareId(Long fareId) {
        FareRule fareRules = fareRuleRepository.findByFareId(fareId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Fare rules not found for fare id: " + fareId));
        return FareRuleMapper.toResponse(fareRules);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FareRulesResponse> getFareRulesByAirlineId(Long airlineId) {
        return fareRuleRepository.findByAirlineId(airlineId).stream()
                .map(FareRuleMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FareRulesResponse updateFareRules(Long id, FareRulesRequest request) {
        FareRule existing = fareRuleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Fare rules not found with id: " + id));

        FareRuleMapper.updateEntity(request, existing);
        FareRule saved = fareRuleRepository.save(existing);
        return FareRuleMapper.toResponse(saved);
    }

    @Override
    public void deleteFareRules(Long id) {
        FareRule fareRules = fareRuleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Fare rules not found with id: " + id));
        fareRuleRepository.delete(fareRules);
    }
}
