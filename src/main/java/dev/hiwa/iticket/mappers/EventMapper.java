package dev.hiwa.iticket.mappers;

import dev.hiwa.iticket.domain.dto.request.CreateEventRequest;
import dev.hiwa.iticket.domain.dto.request.UpdateEventRequest;
import dev.hiwa.iticket.domain.dto.response.CreateEventResponse;
import dev.hiwa.iticket.domain.dto.response.EventResponse;
import dev.hiwa.iticket.domain.dto.response.UpdateEventResponse;
import dev.hiwa.iticket.domain.entities.Event;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {TicketTypeMapper.class}
)
public interface EventMapper {


    Event toEntity(CreateEventRequest createEventRequest);

    Event toEntity(UpdateEventRequest updateEventRequest);

    CreateEventResponse toCreateEventResponse(Event event);

    UpdateEventResponse toUpdateEventResponse(Event event);

    EventResponse toEventResponse(Event event);

    void update(@MappingTarget Event event, UpdateEventRequest request);
}
