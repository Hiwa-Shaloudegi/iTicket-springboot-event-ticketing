package dev.hiwa.iticket.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseTicketResponse {

    private UUID id;
    private PurchaseTicketTypeResponse ticketType;
    private QrCodeResponse qrCode;
}
