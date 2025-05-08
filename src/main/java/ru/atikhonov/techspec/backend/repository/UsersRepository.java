package ru.atikhonov.techspec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.atikhonov.techspec.backend.model.Users;

import java.util.List;


public interface UsersRepository extends JpaRepository<Users, Long> {
}
