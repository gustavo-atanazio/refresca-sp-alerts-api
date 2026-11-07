package com.refresca_sp.controller;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.refresca_sp.domain.alert.dto.AddressDTO;
import com.refresca_sp.domain.alert.dto.AlertResponseDTO;
import com.refresca_sp.domain.alert.entity.RiskLevel;
import com.refresca_sp.domain.alert.service.AlertService;
import com.refresca_sp.infra.exception.ResourceNotFoundException;
import com.refresca_sp.infra.security.SecurityConfig;

@WebMvcTest(AlertController.class)
@Import(SecurityConfig.class)
class AlertControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private AlertService alertService;

  private AlertResponseDTO buildResponse() {
    return new AlertResponseDTO(
      "test-id-123",
      new AddressDTO("01310-100", "Avenida Paulista", 1578),
      RiskLevel.HIGH,
      "Evite exposição ao sol.",
      true
    );
  }

  @Test
  void createAlert_shouldReturn201_whenValidBody() throws Exception {
    when(alertService.create(any())).thenReturn(buildResponse());

    mockMvc.perform(post("/api/v1/alerts")
        .contentType(MediaType.APPLICATION_JSON)
        .content("""
          {
            "address": {
              "cep": "01310-100",
              "streetName": "Avenida Paulista",
              "number": 1578
            },
            "riskLevel": "HIGH",
            "recommendation": "Evite exposição ao sol.",
            "active": true
          }
          """))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.alertID").value("test-id-123"))
    ;
  }

  @Test
  void getAllAlerts_shouldReturn200() throws Exception {
    when(alertService.findAll(any(), any(Pageable.class)))
      .thenReturn(new PageImpl<>(List.of(buildResponse())))
    ;

    mockMvc.perform(get("/api/v1/alerts"))
      .andExpect(status().isOk())
    ;
  }

  @Test
  void getAlertByID_shouldReturn200_whenExists() throws Exception {
    when(alertService.findByID("test-id-123")).thenReturn(buildResponse());

    mockMvc.perform(get("/api/v1/alerts/test-id-123"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.alertID").value("test-id-123"))
    ;
  }

  @Test
  void getAlertByID_shouldReturn404_whenNotFound() throws Exception {
    when(alertService.findByID("invalid-id"))
      .thenThrow(new ResourceNotFoundException("Alerta não encontrado com ID: invalid-id"))
    ;

    mockMvc.perform(get("/api/v1/alerts/invalid-id"))
      .andExpect(status().isNotFound())
    ;
  }

  @Test
  void deleteAlert_shouldReturn204_whenExists() throws Exception {
    mockMvc.perform(delete("/api/v1/alerts/test-id-123"))
      .andExpect(status().isNoContent())
    ;
  }
}