package com.projects.customer_web_app.repository;

import com.projects.customer_web_app.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CitiesRepository extends JpaRepository<City, Long> {
    List<City> findAllByNameContainingIgnoreCase(String name);
}
