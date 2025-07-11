package dev.hiwa.iticket.mappers;

import dev.hiwa.iticket.domain.dto.request.CreateEventRequest;
import dev.hiwa.iticket.domain.dto.response.CreateEventResponse;
import dev.hiwa.iticket.domain.entities.Event;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",  unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {TicketTypeMapper.class})
public interface EventMapper {


    Event toEntity(CreateEventRequest createEventRequest);

    CreateEventResponse toEventResponse(Event event);
}
