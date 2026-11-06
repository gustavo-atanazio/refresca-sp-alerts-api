package com.refresca_sp.domain.alert.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.refresca_sp.domain.alert.dto.AlertRequestDTO;
import com.refresca_sp.domain.alert.dto.AlertResponseDTO;
import com.refresca_sp.domain.alert.entity.Address;
import com.refresca_sp.domain.alert.entity.Alert;
import com.refresca_sp.domain.alert.repository.AlertRepository;
import com.refresca_sp.infra.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class AlertService {
  private final AlertRepository alertRepository;

  public AlertResponseDTO create(AlertRequestDTO request) {
    Alert alert = new Alert();

    alert.setAddress(new Address(
      request.address().cep(),
      request.address().streetName(),
      request.address().number()
    ));
    alert.setRiskLevel(request.riskLevel());
    alert.setRecommendation(request.recommendation());
    alert.setActive(request.active() != null ? request.active() : true);

    Alert saved = alertRepository.save(alert);

    return AlertResponseDTO.from(saved);
  }

  public Page<AlertResponseDTO> findAll(Boolean active, Pageable pageable) {
    if (active != null) {
      return alertRepository.findByActive(active, pageable)
        .map(AlertResponseDTO::from)
      ;
    }

    return alertRepository.findAll(pageable)
      .map(AlertResponseDTO::from)
    ;
  }

  public AlertResponseDTO findByID(String id) {
    Alert alert = alertRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Alerta não encontrado com ID: " + id))
    ;

    return AlertResponseDTO.from(alert);
  }

  public AlertResponseDTO update(String id, AlertRequestDTO request) {
    Alert alert = alertRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Alerta não encontrado com ID: " + id))
    ;

    alert.setAddress(new Address(
      request.address().cep(),
      request.address().streetName(),
      request.address().number()
    ));
    alert.setRiskLevel(request.riskLevel());
    alert.setRecommendation(request.recommendation());

    if (request.active() != null) alert.setActive(request.active());

    Alert saved = alertRepository.save(alert);
    return AlertResponseDTO.from(saved);
  }

  public void delete(String id) {
    Alert alert = alertRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Alerta não encontrado com ID: " + id))
    ;

    alert.setActive(false);

    alertRepository.save(alert);
  }
}