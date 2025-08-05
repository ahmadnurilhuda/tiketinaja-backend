package com.greenacademy.tiketinaja.service;

import org.springframework.stereotype.Service;
import com.greenacademy.tiketinaja.repositories.TicketTypeRepository;
import com.greenacademy.tiketinaja.dto.request.TicketTypeRequest;
import com.greenacademy.tiketinaja.models.Event;
import com.greenacademy.tiketinaja.models.TicketType;


@Service
public class TicketTypeService {

    private final TicketTypeRepository ticketTypeRepo;
    private final EventService eventService;

    public TicketTypeService(TicketTypeRepository ticketTypeRepo, EventService eventService) {
        this.ticketTypeRepo = ticketTypeRepo;
        this.eventService = eventService;
    }

    public TicketType create(TicketTypeRequest request) {
        TicketType ticketType = new TicketType();
        ticketType.setEvent(eventService.getEvent(request.getEventId()));
        ticketType.setName(request.getName());
        ticketType.setDescription(request.getDescription());
        ticketType.setQuantity(request.getQuantity());
        ticketType.setPrice(request.getPrice());
        ticketType.setStartDate(request.getStartDate());
        ticketType.setEndDate(request.getEndDate());
        return ticketTypeRepo.save(ticketType);
    }

    public TicketType update(TicketTypeRequest request, Integer id, Integer organizerId) {
        TicketType ticketType = ticketTypeRepo.findById(id).orElseThrow(()-> new IllegalArgumentException("TicketType not found"));
        Event event = eventService.getEvent(request.getEventId());
        if (event.getOrganizer().getId() != organizerId) {
            throw new IllegalArgumentException("You are not the organizer of this event");
        }
        ticketType.setEvent(eventService.getEvent(request.getEventId()));
        ticketType.setName(request.getName());
        ticketType.setDescription(request.getDescription());
        ticketType.setQuantity(request.getQuantity());
        ticketType.setPrice(request.getPrice());
        ticketType.setStartDate(request.getStartDate());
        ticketType.setEndDate(request.getEndDate());
        return ticketTypeRepo.save(ticketType);
    }

    public TicketType delete (Integer id, Integer organizerId) {
        TicketType ticketType = ticketTypeRepo.findById(id).orElseThrow(()-> new IllegalArgumentException("TicketType not found"));
        Event event = eventService.getEvent(ticketType.getEvent().getId());
        if (event.getOrganizer().getId() != organizerId) {
            throw new IllegalArgumentException("You are not the organizer of this event");
        }
        ticketTypeRepo.delete(ticketType);
        return ticketType;
    }

    public TicketType getTicketType(Integer id, Iterable<Event> events) {
        TicketType ticketType = ticketTypeRepo.findById(id).orElseThrow(()-> new IllegalArgumentException("TicketType not found"));
        for (Event event : events) {
            if (event.getId() == ticketType.getEvent().getId()) {
                return ticketType;
            }
        }
        throw new IllegalArgumentException("TicketType not found");
    }

    public Iterable<TicketType> getAllTicketType(Integer eventId) {
        Event event = eventService.getEvent(eventId);
        return ticketTypeRepo.findAllByEventId(event.getId());
    } 

    public TicketType getTicketType(Integer id) {
        return ticketTypeRepo.findById(id).orElseThrow(()-> new IllegalArgumentException("TicketType not found"));
    }

    public TicketType getTicketTypeByOrderItem(Integer id) {
        return ticketTypeRepo.findByOrderItemId(id).orElseThrow(()-> new IllegalArgumentException("TicketType not found"));
    }
}