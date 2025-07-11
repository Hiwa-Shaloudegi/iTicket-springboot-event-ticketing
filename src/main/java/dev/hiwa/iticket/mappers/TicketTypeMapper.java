package dev.hiwa.iticket.mappers;

import dev.hiwa.iticket.domain.dto.request.CreateTicketTypeRequest;
import dev.hiwa.iticket.domain.entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketTypeMapper {

    TicketType toEntity(CreateTicketTypeRequest createTicketTypeRequest);

}
