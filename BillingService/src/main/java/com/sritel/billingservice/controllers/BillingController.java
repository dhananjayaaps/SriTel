package com.sritel.billingservice.controllers;

import com.sritel.billingservice.dto.StripePaymentRequest;
import com.sritel.billingservice.entity.Bill;
import com.sritel.billingservice.exception.PaymentProcessingException;
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

    @GetMapping("/health")
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
    public ResponseEntity<String> payBill(@RequestBody StripePaymentRequest paymentRequest) {
        try {
            System.out.println(paymentRequest);
            System.out.println(paymentRequest.getAmount());
            String paymentStatus = billingService.payForBill(paymentRequest);
            System.out.println(paymentStatus);
            return ResponseEntity.ok(paymentStatus);
        } catch (StripeException e) {
            throw new PaymentProcessingException("Payment processing failed: " + e.getMessage());
        } catch (Exception e) {
            throw new PaymentProcessingException("An error occurred while processing the payment: " + e.getMessage());
        }
    }
}
