package ru.atikhonov.techspec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.atikhonov.techspec.backend.model.Subscriptions;

import java.util.List;


public interface SubscriptionsRepository extends JpaRepository<Subscriptions, Long> {
}
