package com.refresca_sp.domain.alert.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.refresca_sp.domain.alert.entity.Alert;

public interface AlertRepository extends JpaRepository<Alert, String> {

  Page<Alert> findByActive(Boolean active, Pageable pageable);
}