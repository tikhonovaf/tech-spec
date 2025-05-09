package ru.atikhonov.techspec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.atikhonov.techspec.backend.model.Subscriptions;
import ru.atikhonov.techspec.backend.model.Users;

import java.util.List;


public interface SubscriptionsRepository extends JpaRepository<Subscriptions, Long> {

    public List<Subscriptions> findByUser(Users user);

    @Query(value = " " +
            " select *\n" +
            "    FROM subscriptions\n" +
            "    WHERE user_id = :userId  AND service_id = :serviceId",
            nativeQuery = true)
    public List<Subscriptions> findByUserIdAndServiceId(
            @Param("userId") Long userId,
            @Param("serviceId") Long serviceId
            );

}
