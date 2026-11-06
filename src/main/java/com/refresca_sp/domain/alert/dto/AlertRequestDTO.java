package com.refresca_sp.domain.alert.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.refresca_sp.domain.alert.entity.RiskLevel;

public record AlertRequestDTO(
  @NotNull
  @Valid
  AddressDTO address,

  @NotNull
  RiskLevel riskLevel,

  @NotBlank
  String recommendation,

  Boolean active
) {}