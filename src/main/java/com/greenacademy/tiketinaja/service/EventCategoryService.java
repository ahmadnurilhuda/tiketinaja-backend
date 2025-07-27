package com.greenacademy.tiketinaja.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greenacademy.tiketinaja.dto.request.EventCategoryRequest;
import com.greenacademy.tiketinaja.exception.ResourceAlreadyExistsException;
import com.greenacademy.tiketinaja.exception.ResourceNotFoundException;
import com.greenacademy.tiketinaja.models.EventCategory;
import com.greenacademy.tiketinaja.repositories.EventCategoryRepository;

@Service
public class EventCategoryService {

    private EventCategoryRepository eventCategoryRepo;

    public EventCategoryService(EventCategoryRepository eventCategoryRepo) {
        this.eventCategoryRepo = eventCategoryRepo;
    }

    public EventCategory get(Integer id) {
        return eventCategoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event Category not found"));
    }

    public Page<EventCategory> getAll(Pageable pageable, String name, String sort) {
        String sortBy = "createdAt";
        Sort.Direction direction = Sort.Direction.DESC;
        if (sort != null && sort.contains(",")) {
            String[] parts = sort.split(",");
            sortBy = parts[0];
            direction = parts[1].equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        }
        pageable = PageRequest.of(pageable.getPageNumber(), 5, Sort.by(direction, sortBy));
        return eventCategoryRepo.findAllBySearch(pageable, name);
    }
    public Iterable<EventCategory> getAll() {
        return eventCategoryRepo.findAll();
    }

    public EventCategory create (EventCategoryRequest eventCategoryRequest) {
        if(eventCategoryRepo.existsByName(eventCategoryRequest.getName())) {
            throw new ResourceAlreadyExistsException("Event Category with name " + eventCategoryRequest.getName() + " already exists");
        }
        EventCategory eventCategory = new EventCategory();
        eventCategory.setName(eventCategoryRequest.getName());
        eventCategory.setDescription(eventCategoryRequest.getDescription());
        return eventCategoryRepo.save(eventCategory);
    }
    public EventCategory delete (Integer id) {
        EventCategory eventCategory = eventCategoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event Category not found"));
        eventCategoryRepo.delete(eventCategory);
        return eventCategory;
    }

    public EventCategory update (Integer id, EventCategoryRequest eventCategoryRequest) {
        EventCategory eventCategory = eventCategoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event Category not found"));
        eventCategory.setName(eventCategoryRequest.getName());
        eventCategory.setDescription(eventCategoryRequest.getDescription());
        return eventCategoryRepo.save(eventCategory);
    }



}
