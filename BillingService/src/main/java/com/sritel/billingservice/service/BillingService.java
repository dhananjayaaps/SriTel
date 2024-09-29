package com.sritel.billingservice.service;

import com.sritel.billingservice.dto.StripePaymentRequest;
import com.sritel.billingservice.entity.Bill;
import com.sritel.billingservice.repository.BillRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BillingService {

    @Autowired
    private BillRepository billRepository;

    @PostConstruct
    public void init() {
        // Set up the Stripe API key (sandbox)
        Stripe.apiKey = "sk_test_***********"; // Use your Stripe secret key
    }

    public List<Bill> getBillsForUser(String username) {
        return billRepository.findByUsername(username);
    }

    public String payForBill(StripePaymentRequest paymentRequest) throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", paymentRequest.getAmount());
        chargeParams.put("currency", "usd");
        chargeParams.put("source", paymentRequest.getToken()); // obtained from the frontend
        chargeParams.put("description", "Bill payment for " + paymentRequest.getDescription());

        Charge charge = Charge.create(chargeParams);
        return charge.getStatus();
    }

    public Bill addBill(Bill bill) {
        return billRepository.save(bill);
    }

    public Bill updateBill(Bill bill) {
        Bill updatedbill = billRepository.findById(bill.getId()).orElse(null);
        if (updatedbill == null) {
            Bill error =new Bill();
            error.setDescription("Bill not found");
            return error;
        }
        updatedbill.setAmount(updatedbill.getAmount()+bill.getAmount());
        return billRepository.save(updatedbill);
    }
}
