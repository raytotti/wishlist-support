package com.raytotti.wishlist.support.client.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateClientRequest {

    @NotBlank(message = "{Client.cpf.NotBlank}")
    @Size(max = 14, min = 14, message = "{Client.cpf.Size}")
    @CPF(message = "{Client.cpf.Pattern}")
    private String cpf;

    @NotBlank(message = "{Client.name.NotBlank}")
    @Size(min = 3, max = 256, message = "{Client.name.Size}")
    private String name;

}
