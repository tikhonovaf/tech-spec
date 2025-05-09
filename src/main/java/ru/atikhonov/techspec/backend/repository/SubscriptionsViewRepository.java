package ru.atikhonov.techspec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.atikhonov.techspec.backend.model.SubscriptionsView;

import java.util.List;


public interface SubscriptionsViewRepository extends JpaRepository<SubscriptionsView, Long> {
    List<SubscriptionsView> findAllByUserId(Long userId);
}
