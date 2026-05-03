package com.example.saasguard.controller;

import com.example.saasguard.model.Subscription;
import com.example.saasguard.manager.SubscriptionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionManager subscriptionManager;

    /**
     * GET /subscriptions
     * Returns all subscriptions
     */
    @GetMapping
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionManager.getAllSubscriptions();
        return ResponseEntity.ok(subscriptions);
    }

    /**
     * GET /subscriptions/{id}
     * Returns a subscription by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Subscription> getSubscriptionById(@PathVariable int id) {
        Subscription subscription = subscriptionManager.getSubscriptionById(id);
        if (subscription == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(subscription);
    }

    /**
     * POST /subscriptions
     * Adds a new subscription
     * Request body: { "name": "...", "monthlyCost": ... }
     */
    @PostMapping
    public ResponseEntity<Map<String, String>> addSubscription(@RequestBody Map<String, Object> request) {
        try {
            String name = (String) request.get("name");
            Double monthlyCost = ((Number) request.get("monthlyCost")).doubleValue();
            int id = ((Number) request.get("id")).intValue();

            Subscription sub = new Subscription(name, monthlyCost);
            sub.setId(id);
            subscriptionManager.addSubscription(sub);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Subscription created successfully", "id", String.valueOf(id)));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Failed to create subscription: " + e.getMessage()));
        }
    }

    /**
     * PUT /subscriptions/{id}
     * Updates an existing subscription
     * Request body: { "name": "...", "monthlyCost": ... }
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateSubscription(
            @PathVariable int id,
            @RequestBody Map<String, Object> request) {
        try {
            Subscription existingSub = subscriptionManager.getSubscriptionById(id);
            if (existingSub == null) {
                return ResponseEntity.notFound().build();
            }

            String name = (String) request.get("name");
            Double monthlyCost = ((Number) request.get("monthlyCost")).doubleValue();

            existingSub.setName(name);
            existingSub.setMonthlyCost(monthlyCost);

            subscriptionManager.updateSubscription(existingSub);

            return ResponseEntity.ok(Map.of("message", "Subscription updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Failed to update subscription: " + e.getMessage()));
        }
    }

    /**
     * DELETE /subscriptions/{id}
     * Deletes a subscription
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteSubscription(@PathVariable int id) {
        try {
            Subscription subscription = subscriptionManager.getSubscriptionById(id);
            if (subscription == null) {
                return ResponseEntity.notFound().build();
            }

            subscriptionManager.deleteSubscription(id);

            return ResponseEntity.ok(Map.of("message", "Subscription deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Failed to delete subscription: " + e.getMessage()));
        }
    }

    /**
     * GET /subscriptions/total
     * Returns the total monthly cost of all subscriptions
     */
    @GetMapping("/total/cost")
    public ResponseEntity<Map<String, Object>> getTotalMonthlyCost() {
        double total = subscriptionManager.getTotalMonthlyCost();
        return ResponseEntity.ok(Map.of(
                "totalMonthlyCost", total,
                "totalYearlyCost", total * 12
        ));
    }
}

