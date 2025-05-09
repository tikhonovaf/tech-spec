package ru.atikhonov.techspec.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Список топовых сервисов
 */
@Entity
@Getter
@Setter
public class SubscriptionsTopView {

    /**
     * Идентификатор сервиса
     */
    @Id
    Long id;

    /**
     * Наименование сервиса
     */
    @NotNull
    String name;

    /**
     * Количество подписок
     */
    Long rate;

}
