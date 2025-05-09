package ru.atikhonov.techspec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.atikhonov.techspec.backend.model.SubscriptionsTopView;

import java.util.List;


public interface SubscriptionsTopViewRepository extends JpaRepository<SubscriptionsTopView, Long> {
}
