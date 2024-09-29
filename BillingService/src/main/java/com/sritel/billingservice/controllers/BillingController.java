package com.sritel.billingservice.controllers;

import com.sritel.billingservice.dto.JwtHelper;
import com.sritel.billingservice.dto.StripePaymentRequest;
import com.sritel.billingservice.entity.Bill;
import com.sritel.billingservice.service.BillingService;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @GetMapping()
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Billing Service is up and running");
    }

    @GetMapping("/my-bills")
    public ResponseEntity<List<Bill>> getMyBills(HttpServletRequest request) {
        String token = null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("jwtToken")) {
                token = cookie.getValue();
            }
        }
        JwtHelper jwtHelper = new JwtHelper();
        String username = jwtHelper.getUsernameFromToken(token);
        System.out.println("username: " + username);
        List<Bill> bills = billingService.getBillsForUser(username);
        return ResponseEntity.ok(bills);
    }
    @PostMapping("/add-bill")
    public ResponseEntity<Bill> addBill(@RequestBody Bill bill) {
        Bill savedBill = billingService.addBill(bill);
        return ResponseEntity.ok(savedBill);
    }

    @PutMapping("/update-amount")
    public ResponseEntity<Bill> updateBill(@RequestBody Bill bill) {
        Bill updatedBill = billingService.updateBill(bill);
        return ResponseEntity.ok(updatedBill);
    }

    @PostMapping("/pay")
    public ResponseEntity<String> payBill(@RequestBody StripePaymentRequest paymentRequest) throws StripeException {
        String paymentStatus = billingService.payForBill(paymentRequest);
        return ResponseEntity.ok(paymentStatus);
    }
}
