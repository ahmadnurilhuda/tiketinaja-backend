package com.greenacademy.tiketinaja.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.greenacademy.tiketinaja.dto.request.EventRequest;
import com.greenacademy.tiketinaja.dto.request.EventRequestUpdate;
import com.greenacademy.tiketinaja.models.Event;
import com.greenacademy.tiketinaja.models.Organizer;
import com.greenacademy.tiketinaja.repositories.EventRepository;
import com.greenacademy.tiketinaja.utils.SlugUtil;

@Service
public class EventService {

    private final EventRepository eventRepo;
    private CityService cityService;
    private EventCategoryService eventCategoryService;

    public EventService(EventRepository eventRepo, CityService cityService, EventCategoryService eventCategoryService) {
        this.eventRepo = eventRepo;
        this.cityService = cityService;
        this.eventCategoryService = eventCategoryService;
    }

    public Page<Event> getAllByOrganizer(Pageable pageable, String title, String sort, Integer cityId,
            Integer eventCategoryId, Integer organizerId, Integer provinceId) {
        String sortBy = "createdAt";
        Sort.Direction direction = Sort.Direction.DESC;

        if (sort != null && sort.contains(",")) {
            String[] parts = sort.split(",");
            sortBy = parts[0];
            direction = parts[1].equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        }
        pageable = PageRequest.of(pageable.getPageNumber(), 5, Sort.by(direction, sortBy));
        return eventRepo.findAllByOrganizerId(provinceId, organizerId, title, eventCategoryId, cityId, pageable);
    }

    public Event create(EventRequest request, Organizer organizer) {
        Event event = new Event();
        event.setTitle(request.getTitle());
        String baseSlug = SlugUtil.toSlug(request.getTitle());
        event.setSlug(generateUniqueSlug(baseSlug));
        event.setDescription(request.getDescription());
        event.setRequirements(request.getRequirements());
        event.setStartDate(request.getStartDate());
        event.setEndDate(request.getEndDate());
        event.setVenueName(request.getVenueName());
        event.setVenueAddress(request.getVenueAddress());
        if (request.getPoster() != null && !request.getPoster().isEmpty()) {
            String posterUrl = savePoster(request.getPoster());
            event.setPosterUrl(posterUrl);
        }
        if (request.getVenueLayout() != null && !request.getVenueLayout().isEmpty()) {
            String venueLayoutUrl = saveVenueLayout(request.getVenueLayout());
            event.setVenueLayoutUrl(venueLayoutUrl);
        }
        event.setIsOnline(request.isOnline());
        event.setCity(cityService.get(request.getCityId()));
        event.setEventCategory(eventCategoryService.get(request.getEventCategoryId()));
        event.setOrganizer(organizer);
        return eventRepo.save(event);
    }

    private String generateUniqueSlug(String baseSlug) {
        String slug = baseSlug;
        int counter = 1;
        while (eventRepo.existsBySlug(slug)) {
            slug = baseSlug + "-" + counter;
            counter++;
        }
        return slug;
    }

    private String saveVenueLayout(MultipartFile venueLayout) {
        try {
            if (venueLayout == null || venueLayout.isEmpty()) {
                throw new IllegalArgumentException("Profile picture is required");
            }
            if (!venueLayout.getContentType().equals("image/jpeg")
                    && !venueLayout.getContentType().equals("image/png")
                    && !venueLayout.getContentType().equals("image/webp")) {
                throw new IllegalArgumentException("Only JPEG, PNG, and WebP images are allowed");
            }

            if (venueLayout.getSize() > 5 * 1024 * 1024) {
                throw new IllegalArgumentException("Image size must be less than 5MB");
            }

            String originalFileName = venueLayout.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + fileExtension;

            String filePath = "src/main/resources/static/uploads/events/venue-layout/" + newFileName;
            Path targetFile = Path.of(filePath);

            Files.copy(venueLayout.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);
            String savedPath = "/uploads/events/venue-layout/" + newFileName;
            return savedPath;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Failed to save profile picture: " + e.getMessage());
        }
    }

    private void deleteImageVenueLayout(String venueLayoutUrl) {
        String filePath = "src/main/resources/static/uploads" + venueLayoutUrl;
        Path targetPath = Path.of(filePath);
        try {
            Files.deleteIfExists(targetPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String savePoster(MultipartFile poster) {
        try {
            if (poster == null || poster.isEmpty()) {
                throw new IllegalArgumentException("Profile picture is required");
            }
            if (!poster.getContentType().equals("image/jpeg")
                    && !poster.getContentType().equals("image/png")
                    && !poster.getContentType().equals("image/webp")) {
                throw new IllegalArgumentException("Only JPEG, PNG, and WebP images are allowed");
            }

            if (poster.getSize() > 5 * 1024 * 1024) {
                throw new IllegalArgumentException("Image size must be less than 5MB");
            }

            String originalFileName = poster.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + fileExtension;

            String filePath = "src/main/resources/static/uploads/events/poster/" + newFileName;
            Path targetFile = Path.of(filePath);

            Files.copy(poster.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);
            String savedPath = "/uploads/events/poster/" + newFileName;
            return savedPath;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Failed to save profile picture: " + e.getMessage());
        }
    }

    private void deleteImagePoster(String posterUrl) {
        String filePath = "src/main/resources/static/uploads" + posterUrl;
        Path targetPath = Path.of(filePath);
        try {
            Files.deleteIfExists(targetPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Event delete(Integer id) {
        Event event = eventRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Event not found"));
        if (event.getPosterUrl() != null && !event.getPosterUrl().isEmpty()) {
            deleteImagePoster(event.getPosterUrl());
        }
        if (event.getVenueLayoutUrl() != null && !event.getVenueLayoutUrl().isEmpty()) {
            deleteImageVenueLayout(event.getVenueLayoutUrl());
        }
        eventRepo.delete(event);
        return event;
    }

    public Event update(EventRequestUpdate eventRequest, Integer id) {
        Event event = eventRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Event not found"));
        if (event != null) {
            event.setTitle(eventRequest.getTitle());
            String baseSlug = SlugUtil.toSlug(eventRequest.getTitle());
            event.setSlug(generateUniqueSlug(baseSlug));
            event.setDescription(eventRequest.getDescription());
            event.setStartDate(eventRequest.getStartDate());
            event.setEndDate(eventRequest.getEndDate());
            event.setIsOnline(eventRequest.isOnline());
            event.setCity(cityService.get(eventRequest.getCityId()));
            event.setEventCategory(eventCategoryService.get(eventRequest.getEventCategoryId()));
            event.setRequirements(eventRequest.getRequirements());
            event.setVenueName(eventRequest.getVenueName());
            event.setVenueAddress(eventRequest.getVenueAddress());
            if (eventRequest.getPoster() != null && !eventRequest.getPoster().isEmpty()) {
                if(event.getPosterUrl() != null && !event.getPosterUrl().isEmpty()){
                    deleteImagePoster(event.getPosterUrl());
                }
                String posterUrl = savePoster(eventRequest.getPoster());
                event.setPosterUrl(posterUrl);
            }
            if (eventRequest.getVenueLayout() != null && !eventRequest.getVenueLayout().isEmpty()) {
                if(event.getVenueLayoutUrl() != null && !event.getVenueLayoutUrl().isEmpty()){
                    deleteImageVenueLayout(event.getVenueLayoutUrl());
                }
                String venueLayoutUrl = saveVenueLayout(eventRequest.getVenueLayout());
                event.setVenueLayoutUrl(venueLayoutUrl);
            }
        }
        return eventRepo.save(event);
    }

    public Event getEvent(Integer id) {
        return eventRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Event not found"));
    }

    public Iterable <Event> getEventByOrganizer(Integer organizerId) {
        return eventRepo.findByOrganizerId(organizerId);
    }
}
