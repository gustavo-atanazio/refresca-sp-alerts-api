package com.refresca_sp.domain.alert.service;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.refresca_sp.domain.alert.dto.AddressDTO;
import com.refresca_sp.domain.alert.dto.AlertRequestDTO;
import com.refresca_sp.domain.alert.dto.AlertResponseDTO;
import com.refresca_sp.domain.alert.entity.Address;
import com.refresca_sp.domain.alert.entity.Alert;
import com.refresca_sp.domain.alert.entity.RiskLevel;
import com.refresca_sp.domain.alert.repository.AlertRepository;
import com.refresca_sp.infra.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class AlertServiceTest {
  @Mock
  private AlertRepository alertRepository;

  @InjectMocks
  private AlertService alertService;

  private Alert buildAlert() {
    Alert alert = new Alert();
    alert.setId("test-id-123");
    alert.setAddress(new Address("01310-100", "Avenida Paulista", 1578));
    alert.setRiskLevel(RiskLevel.HIGH);
    alert.setRecommendation("Evite exposição ao sol.");
    alert.setActive(true);
    return alert;
  }

  private AlertRequestDTO buildRequest() {
    return new AlertRequestDTO(
      new AddressDTO("01310-100", "Avenida Paulista", 1578),
      RiskLevel.HIGH,
      "Evite exposição ao sol.",
      true
    );
  }

  @Test
  void create_shouldReturnAlertResponseDTO_whenValidRequest() {
    Alert alert = buildAlert();
    when(alertRepository.save(any(Alert.class))).thenReturn(alert);

    AlertResponseDTO response = alertService.create(buildRequest());

    assertNotNull(response);
    assertEquals("test-id-123", response.alertID());
    assertEquals(RiskLevel.HIGH, response.riskLevel());
    verify(alertRepository).save(any(Alert.class));
  }

  @Test
  void findByID_shouldReturnAlert_whenIDExists() {
    Alert alert = buildAlert();
    when(alertRepository.findById("test-id-123")).thenReturn(Optional.of(alert));

    AlertResponseDTO response = alertService.findByID("test-id-123");

    assertNotNull(response);
    assertEquals("test-id-123", response.alertID());
  }

  @Test
  void findByID_shouldThrowResourceNotFoundException_whenIDNotFound() {
    when(alertRepository.findById("invalid-id")).thenReturn(Optional.empty());

    assertThrows(
      ResourceNotFoundException.class,
      () -> alertService.findByID("invalid-id")
    );
  }

  @Test
  void delete_shouldSetActiveToFalse_whenIDExists() {
    Alert alert = buildAlert();
    when(alertRepository.findById("test-id-123")).thenReturn(Optional.of(alert));
    when(alertRepository.save(any(Alert.class))).thenReturn(alert);

    alertService.delete("test-id-123");

    assertFalse(alert.getActive());
    verify(alertRepository).save(alert);
  }

  @Test
  void update_shouldUpdateFields_whenIDExists() {
    Alert alert = buildAlert();
    when(alertRepository.findById("test-id-123")).thenReturn(Optional.of(alert));
    when(alertRepository.save(any(Alert.class))).thenReturn(alert);

    AlertRequestDTO updateRequest = new AlertRequestDTO(
      new AddressDTO("04038-001", "Rua Domingos de Morais", 342),
      RiskLevel.EXTREME,
      "Situação crítica.",
      true
    );

    AlertResponseDTO response = alertService.update("test-id-123", updateRequest);

    assertNotNull(response);
    assertEquals(RiskLevel.EXTREME, alert.getRiskLevel());
    assertEquals("Situação crítica.", alert.getRecommendation());
    assertEquals("04038-001", alert.getAddress().getCep());
    verify(alertRepository).save(alert);
  }
}