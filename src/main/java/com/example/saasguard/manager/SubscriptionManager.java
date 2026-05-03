package com.example.saasguard.manager;

import com.example.saasguard.model.Subscription;
import com.example.saasguard.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class SubscriptionManager {
    private int size = 0;

    @Autowired
    private SubscriptionRepository repo;

    public void addSubscription(Subscription sub) {
        repo.save(sub);
        size++;
    }

    public List<Subscription> getAllSubscriptions() {
        return repo.findAll();
    }

    public void seeAllSubscriptions() {
        repo.findAll().forEach(System.out::println);
    }

    public void updateSubscription(Subscription sub) {
        repo.update(sub);
    }

    public void deleteSubscription(int id) {
        repo.delete(id);
        size--;
    }

    public Subscription getSubscriptionById(int id) {
        return repo.findById(id);
    }

    public int getSize() {
        return size;
    }

    public double getTotalMonthlyCost() {
        double totalMonthlyCost = 0;
        for (Subscription s : repo.findAll()) {
            totalMonthlyCost += s.getMonthlyCost();
        }
        return totalMonthlyCost;
    }

    public List<Subscription> getInactiveSubscriptions(int days) {
        List<Subscription> inactiveSub = new ArrayList<>();

        LocalDateTime today = LocalDateTime.now();

        for (Subscription s : repo.findAll()) {
            if (s.getLastLogin() != null &&
                    s.getLastLogin().isBefore(today.minusDays(days))) {
                inactiveSub.add(s);
            }
        }
        return inactiveSub;
    }

    public Subscription getMostExpensiveSubscription() {
        List<Subscription> list = repo.findAll();
        if (list.isEmpty()) return null;

        Subscription max = list.get(0);

        for (Subscription s : list) {
            if (s.getMonthlyCost() > max.getMonthlyCost()) {
                max = s;
            }
        }
        return max;
    }

    public List<Subscription> getCostSortedDescending() {
        List<Subscription> sorted = new ArrayList<>(repo.findAll());
        sorted.sort((a, b) -> Double.compare(b.getMonthlyCost(), a.getMonthlyCost()));
        return sorted;
    }
}

