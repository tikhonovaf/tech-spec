package ru.atikhonov.techspec.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
// import jakarta.validation.constraints.NotNull;

/**
 * Created by Tikhonov Arkadiy
 */
@Entity
@Getter
@Setter
public class Services {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "services_seq"
    )
    @SequenceGenerator(
            name = "services_seq",
            sequenceName = "services_seq",
            allocationSize = 1
    )
    Long id;

    /**
     * Наименование
     */
    @NotNull
    String name;

}
