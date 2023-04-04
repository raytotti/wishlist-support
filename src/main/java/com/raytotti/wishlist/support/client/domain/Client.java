package com.raytotti.wishlist.support.client.domain;

import com.raytotti.wishlist.support.client.application.CreateClientRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Getter
@Document
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Client {

    @Id
    private String id;

    @NotNull
    @Indexed(unique = true)
    private String cpf;

    @NotNull
    private String name;

    public static Client of(@NotNull final CreateClientRequest request) {
        return new Client(
                null,
                Objects.requireNonNull(request.getCpf()),
                Objects.requireNonNull(request.getName())
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(cpf, client.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }
}
