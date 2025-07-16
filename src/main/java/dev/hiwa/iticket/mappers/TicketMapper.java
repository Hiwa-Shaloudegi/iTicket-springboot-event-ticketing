package dev.hiwa.iticket.mappers;

import dev.hiwa.iticket.domain.dto.response.PurchaseTicketResponse;
import dev.hiwa.iticket.domain.dto.response.QrCodeResponse;
import dev.hiwa.iticket.domain.entities.Ticket;
import dev.hiwa.iticket.domain.enums.QrCodeStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {TicketTypeMapper.class, QrCodeMapper.class}
)
public interface TicketMapper {

    @Mapping(target = "qrCode", expression = "java(mapActiveQrCode(ticket))")
    PurchaseTicketResponse toPurchaseTicketResponse(Ticket ticket);


    default QrCodeResponse mapActiveQrCode(Ticket ticket) {
        return ticket
                .getQrCodes()
                .stream()
                .filter(qr -> qr.getStatus() == QrCodeStatus.ACTIVE)
                .findFirst()
                .map(qr -> new QrCodeResponse(qr.getId(), qr.getValue(), qr.getStatus()))
                .orElse(null);
    }
}
