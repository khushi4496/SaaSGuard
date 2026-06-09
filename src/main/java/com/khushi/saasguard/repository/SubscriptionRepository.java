package com.khushi.saasguard.repository;

import com.khushi.saasguard.model.Subscription;
import java.util.List;

public interface SubscriptionRepository {
    void save(Subscription sub);
    List<Subscription> findAll();
    void update(Subscription sub);
    void delete(int id);
    Subscription findById(int id);
}

