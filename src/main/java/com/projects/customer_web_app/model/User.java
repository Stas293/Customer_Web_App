package com.projects.customer_web_app.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

import static org.hibernate.annotations.CacheConcurrencyStrategy.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@DynamicUpdate
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_list")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @Embedded
    private PersonalInfo personalInfo;

    private String imagePath;

    @Embedded
    private Address address;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ToString.Exclude
    @Cache(usage = READ_WRITE)
    private List<Role> roles;

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public String getFullName() {
        return personalInfo.getFirstName() + " " + personalInfo.getLastName();
    }
}
