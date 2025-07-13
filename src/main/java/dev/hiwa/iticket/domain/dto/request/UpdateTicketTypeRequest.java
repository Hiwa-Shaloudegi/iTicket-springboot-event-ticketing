package dev.hiwa.iticket.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTicketTypeRequest {

    @NotNull
    private UUID id;

    @NotBlank
    private String name;
    private String description;

    @NotNull
    @PositiveOrZero(message = "Price must be zero or greater")
    private Double price;

    @NotNull
    private Integer totalAvailable;

}
