package com.sritel.billingservice.controller;

import com.sritel.billingservice.dto.StripePaymentRequest;
import com.sritel.billingservice.entity.Bill;
import com.sritel.billingservice.service.BillingService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
public class BillingController {

    @Autowired
    private BillingService billingService;

    @GetMapping()
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Billing Service is up and running");
    }

    @GetMapping("/my-bills")
    public ResponseEntity<List<Bill>> getMyBills() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Bill> bills = billingService.getBillsForUser(username);
        return ResponseEntity.ok(bills);
    }

    @PostMapping("/pay")
    public ResponseEntity<String> payBill(@RequestBody StripePaymentRequest paymentRequest) throws StripeException {
        String paymentStatus = billingService.payForBill(paymentRequest);
        return ResponseEntity.ok(paymentStatus);
    }
}
