package com.sritel.billingservice.repository;

import com.sritel.billingservice.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findByUsername(String username);

    Optional<Bill> findByIdAndUsername(Long id, String username);
}
