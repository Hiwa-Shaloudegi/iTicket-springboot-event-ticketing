package dev.hiwa.iticket.service;

import dev.hiwa.iticket.domain.dto.request.PurchaseTicketRequest;
import dev.hiwa.iticket.domain.dto.response.PurchaseTicketResponse;
import dev.hiwa.iticket.domain.dto.response.QrCodeResponse;
import dev.hiwa.iticket.domain.entities.Ticket;
import dev.hiwa.iticket.domain.entities.TicketType;
import dev.hiwa.iticket.domain.entities.User;
import dev.hiwa.iticket.domain.enums.TicketStatus;
import dev.hiwa.iticket.exceptions.ResourceNotFoundException;
import dev.hiwa.iticket.exceptions.TicketSoldOutException;
import dev.hiwa.iticket.mappers.TicketMapper;
import dev.hiwa.iticket.repository.TicketRepository;
import dev.hiwa.iticket.repository.TicketTypeRepository;
import dev.hiwa.iticket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final UserRepository userRepository;

    private final TicketMapper ticketMapper;


    private final QrCodeService qrCodeService;

    @Transactional
    public PurchaseTicketResponse purchaseTicket(UUID userId, PurchaseTicketRequest request) {

        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.toString()));

        TicketType ticketType = ticketTypeRepository
                .findByIdWithLock(request.getTicketTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("TicketType",
                                                                 "id",
                                                                 request.getTicketTypeId().toString()
                ));

        int purchasedTicketsCount = ticketRepository.countByTicketStatusAndTicketType_Id(
                TicketStatus.PURCHASED,
                request.getTicketTypeId()
        );
        if (ticketType.getTotalAvailable() - purchasedTicketsCount <= 0) {
            throw new TicketSoldOutException();
        }

        Ticket ticket = new Ticket();
        ticket.setBuyer(user);
        ticket.setTicketType(ticketType);
        ticket.setTicketStatus(TicketStatus.PURCHASED);

        Ticket savedTicket = ticketRepository.save(ticket);

        var qr = qrCodeService.generateQrCodeFor(savedTicket);
        savedTicket.getQrCodes().add(qr);

        return ticketMapper.toPurchaseTicketResponse(ticketRepository.save(savedTicket));
    }

}
