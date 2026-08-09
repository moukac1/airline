package com.mouady.mapper;

import com.mouady.model.Fare;
import com.mouady.model.FareRule;
import com.mouady.payload.request.FareRulesRequest;
import com.mouady.payload.response.FareRulesResponse;

public class FareRuleMapper {
    public static FareRule toEntity(FareRulesRequest request, Fare fare) {
        if (request == null) return null;
        return FareRule.builder()
                .ruleName(request.getRuleName())
                .fare(fare)
                .airlineId(request.getAirlineId())
                .isRefundable(request.getIsRefundable())
                .changeFee(request.getChangeFee())
                .cancellationFee(request.getCancellationFee())
                .refundDeadlineDays(request.getRefundDeadlineDays())
                .changeDeadlineHours(request.getChangeDeadlineHours())
                .isChangeable(request.getIsChangeable() != null ? request.getIsChangeable() : false)
                .build();
    }

    public static FareRulesResponse toResponse(FareRule fareRules) {
        if (fareRules == null) return null;
        return FareRulesResponse.builder()
                .id(fareRules.getId())
                .ruleName(fareRules.getRuleName())
                .fareId(fareRules.getFare() != null ? fareRules.getFare().getId() : null)
                .airlineId(fareRules.getAirlineId())
                .isRefundable(fareRules.getIsRefundable())
                .changeFee(fareRules.getChangeFee())
                .cancellationFee(fareRules.getCancellationFee())
                .refundDeadlineDays(fareRules.getRefundDeadlineDays())
                .changeDeadlineHours(fareRules.getChangeDeadlineHours())
                .isChangeable(fareRules.getIsChangeable())
                .createdAt(fareRules.getCreatedAt())
                .updatedAt(fareRules.getUpdatedAt())
                .build();
    }

    public static void updateEntity(FareRulesRequest request, FareRule existing) {
        if (request == null || existing == null) return;
        if (request.getRuleName() != null) existing.setRuleName(request.getRuleName());
        if (request.getAirlineId() != null) existing.setAirlineId(request.getAirlineId());
        if (request.getIsRefundable() != null) existing.setIsRefundable(request.getIsRefundable());
        if (request.getChangeFee() != null) existing.setChangeFee(request.getChangeFee());
        if (request.getCancellationFee() != null) existing.setCancellationFee(request.getCancellationFee());
        if (request.getRefundDeadlineDays() != null) existing.setRefundDeadlineDays(request.getRefundDeadlineDays());
        if (request.getChangeDeadlineHours() != null) existing.setChangeDeadlineHours(request.getChangeDeadlineHours());
        if (request.getIsChangeable() != null) existing.setIsChangeable(request.getIsChangeable());
    }
}
