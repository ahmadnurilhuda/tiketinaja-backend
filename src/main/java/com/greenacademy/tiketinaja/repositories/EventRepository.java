package com.greenacademy.tiketinaja.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.greenacademy.tiketinaja.dto.response.EventPublicResponse;
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

        @Query("""
                        SELECT new com.greenacademy.tiketinaja.dto.response.EventPublicResponse(
                            e.id,
                            e.title,
                            e.description,
                            e.slug,
                            e.requirements,
                            e.venueName,
                            o.name,
                            c.name,
                            ec.name,
                            e.venueAddress,
                            e.startDate,
                            e.endDate,
                            e.posterUrl,
                            e.venueLayoutUrl
                        )
                        FROM Event e
                        LEFT JOIN City c ON e.city.id = c.id
                        LEFT JOIN EventCategory ec ON e.eventCategory.id = ec.id
                        LEFT JOIN Organizer o ON e.organizer.id = o.id
                        WHERE e.slug = :slug
                        """)
        Optional<EventPublicResponse> findBySlug(String slug);

        @Query("""
                        SELECT new com.greenacademy.tiketinaja.dto.response.EventPublicResponse(
                            e.id,
                            e.title,
                            e.description,
                            e.slug,
                            e.requirements,
                            e.venueName,
                            o.name,
                            c.name,
                            ec.name,
                            e.venueAddress,
                            e.startDate,
                            e.endDate,
                            e.posterUrl,
                            e.venueLayoutUrl
                        )
                        FROM Event e
                        LEFT JOIN City c ON e.city.id = c.id
                        LEFT JOIN Organizer o ON e.organizer.id = o.id
                        LEFT JOIN EventCategory ec ON e.eventCategory.id = ec.id
                        WHERE (:provinceId IS NULL OR c.province.id = :provinceId)
                        AND (:title IS NULL OR e.title LIKE %:title%)
                        AND (:eventCategoryId IS NULL OR e.eventCategory.id = :eventCategoryId)
                        AND (:cityId IS NULL OR c.id = :cityId)
                        """)
        Page<EventPublicResponse> findAllPublicEvents(
                        @Param("provinceId") Integer provinceId,
                        @Param("title") String title,
                        @Param("eventCategoryId") Integer eventCategoryId,
                        @Param("cityId") Integer cityId,
                        Pageable pageable);

}
