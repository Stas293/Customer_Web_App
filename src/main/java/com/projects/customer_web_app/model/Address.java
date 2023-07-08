package com.projects.customer_web_app.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@ToString
@Embeddable
public class Address {
    private String street;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private City city;
}