package com.greenacademy.tiketinaja.models;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    private Organizer organizer;

    @ManyToOne
    @JoinColumn(name = "event_category_id", nullable = false)
    private EventCategory eventCategory;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String requirements;

    @Column(nullable = false, name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(nullable = false, name = "venue_name")
    private String venueName;

    @Column(columnDefinition = "TEXT", name = "venue_address")
    private String venueAddress;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column(nullable = false, name = "poster_url")
    private String posterUrl;

    @Column(name = "venue_layout_url")
    private String venueLayoutUrl;

    @Column(nullable = false, name = "is_online", columnDefinition = "boolean default false")
    private boolean isOnline;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

    public Event() {
    }

    public Event(Integer id, Organizer organizer, EventCategory eventCategory, String title, String slug,
            String description, String requirements, Instant startDate, Instant endDate, String venueName,
            String venueAddress, City city, String posterUrl, String venueLayoutUrl, boolean isOnline) {
        this.id = id;
        this.organizer = organizer;
        this.eventCategory = eventCategory;
        this.title = title;
        this.slug = slug;
        this.description = description;
        this.requirements = requirements;
        this.startDate = startDate;
        this.endDate = endDate;
        this.venueName = venueName;
        this.venueAddress = venueAddress;
        this.city = city;
        this.posterUrl = posterUrl;
        this.venueLayoutUrl = venueLayoutUrl;
        this.isOnline = isOnline;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    public EventCategory getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenueAddress() {
        return venueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getVenueLayoutUrl() {
        return venueLayoutUrl;
    }

    public void setVenueLayoutUrl(String mapUrl) {
        this.venueLayoutUrl = mapUrl;
    }

    public boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}

// Table Events {
// id int [pk, increment]
// organizer_id int [not null, ref: > Organizers.id]
// event_category_id int [not null, ref: > EventCategories.id]
// title varchar(255) [not null]
// slug varchar(255) [not null]
// description text
// requirements text [not null]
// start_date timestamp [not null]
// end_date timestamp
// venue_name varchar(255) [not null]
// venue_address text
// city_id int [not null, ref: > Cities.id]
// poster_url varchar(255) [not null]

// map_url varchar(255)
// is_online boolean [not null]
// created_at timestamp [default: `now()`]

// note: 'Setiap event dibuat oleh seorang organizer (pengguna dengan peran
// organizer)'
// }