package com.refresca_sp.domain.alert.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressDTO(
  @NotBlank
  String cep,

  @NotBlank
  String streetName,

  @NotNull
  Integer number
) {}