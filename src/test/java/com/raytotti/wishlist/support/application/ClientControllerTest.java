package com.raytotti.wishlist.support.application;

import com.raytotti.wishlist.support.client.application.ClientController;
import com.raytotti.wishlist.support.client.application.ClientResponse;
import com.raytotti.wishlist.support.client.application.CreateClientRequest;
import com.raytotti.wishlist.support.client.domain.Client;
import com.raytotti.wishlist.support.client.domain.ClientRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.openMocks;

class ClientControllerTest {

    private final String CPF = "123.456.789-09";
    private final String NAME = "Ray Toti Felix de Araujo";

    @Mock
    private ClientRepository repository;
    private ClientController clientController;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        clientController = new ClientController(repository);
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.setContextPath("/api/v1/clients");
        ServletRequestAttributes attrs = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(attrs);
    }

    @Test
    public void create() {
        CreateClientRequest createClientRequest = new CreateClientRequest(CPF, NAME);
        Client client = Client.of(createClientRequest);
        doReturn(false).when(repository).existsByCpf(CPF);
        doReturn(client).when(repository).save(client);

        ResponseEntity<ClientResponse> response = clientController.create(createClientRequest);

        assertNotNull(response.getHeaders().get("location"));
        assertEquals(CPF, Objects.requireNonNull(response.getBody()).getCpf());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void deleteById() {
        ObjectId clientId = ObjectId.get();
        ResponseEntity<Void> response = clientController.delete(clientId.toHexString());

        assertNotNull(response);
        assertNull(response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

    }

    @Test
    public void existsById() {

    }


}