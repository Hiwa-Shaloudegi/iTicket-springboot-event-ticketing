package dev.hiwa.iticket.service;

import dev.hiwa.iticket.domain.dto.request.CreateEventRequest;
import dev.hiwa.iticket.domain.dto.response.CreateEventResponse;
import dev.hiwa.iticket.exceptions.ResourceNotFoundException;
import dev.hiwa.iticket.mappers.EventMapper;
import dev.hiwa.iticket.repository.EventRepository;
import dev.hiwa.iticket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;

    @Transactional
    public CreateEventResponse createEvent(UUID organizerId, CreateEventRequest request) {
        var organizer = userRepository
                .findById(organizerId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", organizerId.toString()));
        var event = eventMapper.toEntity(request);

        event.setOrganizer(organizer);
        var savedEvent = eventRepository.save(event);

        return eventMapper.toEventResponse(savedEvent);
    }
}
