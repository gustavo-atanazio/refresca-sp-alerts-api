package com.refresca_sp.domain.alert.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "alerts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alert {

  @Id
  @Column(nullable = false, updatable = false)
  private String id;

  @Embedded
  private Address address;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private RiskLevel riskLevel;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String recommendation;

  @Column(nullable = false)
  private Boolean active = true;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @PrePersist
  private void prePersist() {
    this.id = UUID.randomUUID().toString();
    this.createdAt = LocalDateTime.now();
  }
}