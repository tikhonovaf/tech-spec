package ru.atikhonov.techspec.backend.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
// import jakarta.validation.constraints.NotNull;

/**
 * Created by Tikhonov Arkadiy
 */
@Entity
@Getter
@Setter
public class Subscriptions {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    Users user;

    @ManyToOne
    Services service;
}
