package com.refresca_sp.domain.alert.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

  @Column(name = "cep", nullable = false)
  private String cep;

  @Column(name = "street_name", nullable = false)
  private String streetName;

  @Column(name = "number", nullable = false)
  private Integer number;
}