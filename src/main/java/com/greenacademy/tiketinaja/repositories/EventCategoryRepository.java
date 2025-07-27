package com.greenacademy.tiketinaja.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.greenacademy.tiketinaja.models.EventCategory;

public interface EventCategoryRepository extends CrudRepository<EventCategory, Integer> {

    @Query("SELECT ec FROM EventCategory ec WHERE (:name IS NULL OR ec.name LIKE %:name%)")
    Page<EventCategory> findAllBySearch(Pageable pageable, @Param("name") String name);
    Optional<EventCategory> findById(Integer id);

    boolean existsByName(String name);
    
}
