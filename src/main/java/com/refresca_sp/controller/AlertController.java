package com.refresca_sp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.refresca_sp.domain.alert.dto.AlertRequestDTO;
import com.refresca_sp.domain.alert.dto.AlertResponseDTO;
import com.refresca_sp.domain.alert.service.AlertService;

@RestController
@RequestMapping("/api/v1/alerts")
@RequiredArgsConstructor
public class AlertController {

  private final AlertService alertService;

  @Operation(summary = "Cria um novo alerta de temperatura")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Alerta criado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
  })
  @PostMapping
  public ResponseEntity<AlertResponseDTO> create(
    @RequestBody @Valid AlertRequestDTO request
  ) {
    return ResponseEntity.status(HttpStatus.CREATED)
      .body(alertService.create(request))
    ;
  }

  @Operation(summary = "Lista todos os alertas")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
  })
  @GetMapping
  public ResponseEntity<Page<AlertResponseDTO>> findAll(
    @RequestParam(required = false) Boolean active,
    @PageableDefault(size = 10) Pageable pageable
  ) {
    return ResponseEntity.ok(alertService.findAll(active, pageable));
  }

  @Operation(summary = "Busca alerta por ID")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Alerta encontrado"),
    @ApiResponse(responseCode = "404", description = "Alerta não encontrado")
  })
  @GetMapping("/{id}")
  public ResponseEntity<AlertResponseDTO> findByID(@PathVariable String id) {
    return ResponseEntity.ok(alertService.findByID(id));
  }

  @Operation(summary = "Atualiza um alerta")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Alerta atualizado com sucesso"),
    @ApiResponse(responseCode = "404", description = "Alerta não encontrado")
  })
  @PutMapping("/{id}")
  public ResponseEntity<AlertResponseDTO> update(
    @PathVariable String id,
    @RequestBody @Valid AlertRequestDTO request
  ) {
    return ResponseEntity.ok(alertService.update(id, request));
  }

  @Operation(summary = "Remove (soft delete) um alerta")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Alerta removido com sucesso"),
    @ApiResponse(responseCode = "404", description = "Alerta não encontrado")
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    alertService.delete(id);
    return ResponseEntity.noContent().build();
  }
}