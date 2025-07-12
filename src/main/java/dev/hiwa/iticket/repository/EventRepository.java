package dev.hiwa.iticket.repository;

import dev.hiwa.iticket.domain.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    @EntityGraph(attributePaths = {"ticketTypes"})
    Page<Event> findAllWithTicketTypesByOrganizer_Id(UUID organizerId, Pageable pageable);
}