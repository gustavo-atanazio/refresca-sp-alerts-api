package com.refresca_sp.domain.alert.dto;

import com.refresca_sp.domain.alert.entity.Alert;
import com.refresca_sp.domain.alert.entity.RiskLevel;

public record AlertResponseDTO(
  String alertID,
  AddressDTO address,
  RiskLevel riskLevel,
  String recommendation,
  Boolean active
) {

  public static AlertResponseDTO from(Alert alert) {
    AddressDTO addressDTO = new AddressDTO(
      alert.getAddress().getCep(),
      alert.getAddress().getStreetName(),
      alert.getAddress().getNumber()
    );
    return new AlertResponseDTO(
      alert.getId(),
      addressDTO,
      alert.getRiskLevel(),
      alert.getRecommendation(),
      alert.getActive()
    );
  }
}