package ru.atikhonov.techspec.backend.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

/**
 * Created by Tikhonov Arkadiy
 */
@Entity
@Getter
@Setter
public class Users {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "users_seq"
    )
    @SequenceGenerator(
            name = "users_seq",
            sequenceName = "users_seq",
            allocationSize = 1
    )
    Long id;

    /**
     * имя
     */
    String firstName;

    /**
     * Фамилия
     */
    String lastName;

    /**
     * Отчество
     */
    String secondName;

    /**
     * email
     */
    String email;

    /**
     * phone
     */
    String phone;

    /**
     * telegram
     */
    String telegram;

}
