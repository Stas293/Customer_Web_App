package com.projects.customer_web_app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.DynamicUpdate;

import static org.hibernate.annotations.CacheConcurrencyStrategy.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@DynamicUpdate
@Setter
@ToString
@Cache(usage = NONSTRICT_READ_WRITE)
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;
}
