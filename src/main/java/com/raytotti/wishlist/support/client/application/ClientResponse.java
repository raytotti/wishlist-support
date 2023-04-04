package com.raytotti.wishlist.support.client.application;

import com.raytotti.wishlist.support.client.domain.Client;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientResponse {

    private String id;
    private String cpf;
    private String name;

    public static ClientResponse from(Client client) {
        return new ClientResponse(client.getId(), client.getCpf(), client.getName());
    }
}
