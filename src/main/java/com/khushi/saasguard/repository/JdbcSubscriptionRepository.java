package com.example.saasguard.repository;

import com.example.saasguard.model.Subscription;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcSubscriptionRepository implements SubscriptionRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcSubscriptionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Subscription> findAll() {
        String sql = "SELECT * FROM subscriptions";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Subscription(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDouble("monthly_cost"),
                rs.getTimestamp("last_login"),
                rs.getInt("usage_count")
        ));
    }

    @Override
    public void save(Subscription sub) {
        String sql = "INSERT INTO subscriptions (name, monthly_cost, usage_count, last_login) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                sub.getName(),
                sub.getMonthlyCost(),
                0,
                null
        );
    }

    @Override
    public void update(Subscription sub) {
        String sql = "UPDATE subscriptions SET name=?, monthly_cost=? WHERE id=?";
        jdbcTemplate.update(sql,
                sub.getName(),
                sub.getMonthlyCost(),
                sub.getId()
        );
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM subscriptions WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Subscription findById(int id) {
        String sql = "SELECT * FROM subscriptions WHERE id=?";
        List<Subscription> result = jdbcTemplate.query(sql, (rs, rowNum) -> new Subscription(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDouble("monthly_cost"),
                rs.getTimestamp("last_login"),
                rs.getInt("usage_count")
        ), id);
        return result.isEmpty() ? null : result.get(0);
    }
}

