package ru.atikhonov.techspec.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
// import jakarta.validation.constraints.NotNull;

/**
 * Created by Tikhonov Arkadiy
 */
@Entity
@Getter
@Setter
public class SubscriptionsView {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue
    Long id;

    Long user_id;
    String  userFirstName;
    String  userLastName;
    String  userSecondName;

    Long serviceId;
    String  serviceName;
}
