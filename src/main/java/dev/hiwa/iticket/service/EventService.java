package dev.hiwa.iticket.service;

import dev.hiwa.iticket.domain.dto.request.CreateEventRequest;
import dev.hiwa.iticket.domain.dto.response.CreateEventResponse;
import dev.hiwa.iticket.domain.dto.response.EventResponse;
import dev.hiwa.iticket.domain.entities.Event;
import dev.hiwa.iticket.exceptions.ResourceNotFoundException;
import dev.hiwa.iticket.mappers.EventMapper;
import dev.hiwa.iticket.repository.EventRepository;
import dev.hiwa.iticket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    private final UserRepository userRepository;

    @Transactional
    public CreateEventResponse createEvent(UUID organizerId, CreateEventRequest request) {
        var organizer = userRepository
                .findById(organizerId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", organizerId.toString()));
        var event = eventMapper.toEntity(request);

        event.setOrganizer(organizer);

        var savedEvent = eventRepository.save(event);

        return eventMapper.toCreateEventResponse(savedEvent);
    }

    @Transactional(readOnly = true)
    public Page<EventResponse> getAllEventsForOrganizer(UUID organizerId, Integer page, Integer size) {
        var pageRequest = PageRequest.of(page, size);

        userRepository
                .findById(organizerId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", organizerId.toString()));

        Page<Event> eventsPage =
                eventRepository.findAllWithTicketTypesByOrganizer_Id(organizerId, pageRequest);

        return eventsPage.map(eventMapper::toEventResponse);
    }
}
