package dev.hiwa.iticket.controller;

import dev.hiwa.iticket.domain.dto.request.PurchaseTicketRequest;
import dev.hiwa.iticket.domain.dto.response.PurchaseTicketResponse;
import dev.hiwa.iticket.domain.dto.response.TicketResponse;
import dev.hiwa.iticket.service.TicketService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tickets")
@SecurityRequirement(name = "Keycloak")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/purchase")
    public ResponseEntity<PurchaseTicketResponse> purchaseTicket(
            @AuthenticationPrincipal Jwt jwt, @Valid @RequestBody PurchaseTicketRequest request
    ) {
        UUID userId = UUID.fromString(jwt.getSubject());

        return ResponseEntity.ok(ticketService.purchaseTicket(userId, request));
    }

    @GetMapping
    public ResponseEntity<Page<TicketResponse>> getAllUserTickets(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "25") Integer size
    ) {
        UUID userId = UUID.fromString(jwt.getSubject());

        return ResponseEntity.ok(ticketService.getAllUserTickets(userId, page, size));
    }
}
