package com.greenacademy.tiketinaja.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.greenacademy.tiketinaja.models.Event;

public interface EventRepository extends CrudRepository<Event, Integer> {

    boolean existsBySlug(String slug);

    Iterable<Event> findByOrganizerId(Integer organizerId);

    @Query("""
            SELECT e FROM Event e
            LEFT JOIN e.city c
            LEFT JOIN c.province p
            WHERE e.organizer.id = :organizerId
            AND (:provinceId IS NULL OR p.id = :provinceId)
            AND (:title IS NULL OR e.title LIKE %:title%)
            AND (:eventCategoryId IS NULL OR e.eventCategory.id = :eventCategoryId)
            AND (:cityId IS NULL OR c.id = :cityId)
            """)
    Page<Event> findAllByOrganizerId(Integer provinceId, Integer organizerId, String title, Integer eventCategoryId,
            Integer cityId, Pageable pageable);


}
