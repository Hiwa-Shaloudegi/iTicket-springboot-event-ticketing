package dev.hiwa.iticket.controller;

import dev.hiwa.iticket.domain.dto.request.CreateEventRequest;
import dev.hiwa.iticket.domain.dto.response.CreateEventResponse;
import dev.hiwa.iticket.service.EventService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/events")
@SecurityRequirement(name = "Keycloak")
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<CreateEventResponse> createEvent(
            @AuthenticationPrincipal Jwt jwt, @Valid @RequestBody CreateEventRequest request
    ) {

        UUID userId = UUID.fromString(jwt.getSubject());

        var createdEvent = eventService.createEvent(userId, request);

        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);

    }
}
