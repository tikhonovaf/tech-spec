package ru.atikhonov.techspec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.atikhonov.techspec.backend.model.Services;
import java.util.List;


public interface ServicesRepository extends JpaRepository<Services, Long> {
}
