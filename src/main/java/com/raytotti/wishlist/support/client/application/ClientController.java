package com.raytotti.wishlist.support.client.application;

import com.raytotti.wishlist.support.client.domain.Client;
import com.raytotti.wishlist.support.client.domain.ClientRepository;
import com.raytotti.wishlist.support.client.exception.ClientExistsException;
import com.raytotti.wishlist.support.client.exception.ClientNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/clients")
@Tag(name = "Client", description = "Client API Operations")
public class ClientController {

    private final ClientRepository repository;

    @PostMapping
    @Operation(summary = "Create a new client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Operation completed successfully."),
            @ApiResponse(responseCode = "400", description = "Failed to validate! Request Invalid.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Failed to validate! CPF already exists.", content = @Content),
            @ApiResponse(responseCode = "415", description = "Unsupported Content Type.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected system failure.", content = @Content)
    })
    public ResponseEntity<ClientResponse> create(@RequestBody @Valid CreateClientRequest request) {
        log.info("ClientController -> create: Solicitado a criação de um cliente: {}", request);

        if (repository.existsByCpf(request.getCpf())) {
            log.error("ClientController -> create: Já existe um cliente para o CPF: {}", request.getCpf());
            throw new ClientExistsException();
        }

        Client client = repository.save(Client.of(request));
        log.info("ClientController -> create: Cliente com id {} criado.", client.getId());

        ClientResponse response = ClientResponse.from(client);
        log.info("ClientController -> create: Cliente respondido {}", response);

        URI uri = fromCurrentRequest()
                .path("/")
                .path(response.getId())
                .build().toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Remove an informed clientId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Operation completed successfully."),
            @ApiResponse(responseCode = "400", description = "Failed to validate! Request Invalid.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Failed to validate! Client not found.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected system failure.", content = @Content)
    })
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("ClientController -> delete: Solicitado a remoção do cliente com id: {}", id);

        if (!repository.existsById(id)) {
            log.error("ClientController -> delete: Cliente com id {} não encontrado.", id);
            throw new ClientNotFoundException();
        }

        repository.deleteById(id);
        log.error("ClientController -> delete: Cliente com id {} removido.", id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Retrieve an informed clientId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation completed successfully."),
            @ApiResponse(responseCode = "400", description = "Failed to validate! Request Invalid.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Failed to validate! Client not found.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected system failure.", content = @Content)
    })
    public ResponseEntity<ClientResponse> findById(@PathVariable String id) {
        log.info("ClientController -> findById: Solicitado a busca de um cliente pelo id {}.", id);
        Optional<Client> client = repository.findById(id);

        ClientResponse clientResponse = ClientResponse.from(client.orElseThrow(() -> {
            log.error("ClientController -> findById: Cliente com o id {} não encontrado.", id);
            return new ClientNotFoundException();
        }));

        log.info("ClientController -> findById: Cliente encontrado. {}", clientResponse);
        return ResponseEntity.ok(clientResponse);
    }

    @GetMapping(path = "/{id}/exists")
    @Operation(summary = "Checks if the informed client exists.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation completed successfully."),
            @ApiResponse(responseCode = "400", description = "Failed to validate! Request Invalid.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Failed to validate! Client not found.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected system failure.", content = @Content)
    })
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        log.info("ClientController -> exists: Solicitado a verificação de existencia do cliente pelo id {}.", id);

        boolean exists = repository.existsById(id);

        if (exists) {
            log.info("ClientController -> exists: O client com id {} foi encontrado.", id);
            return ResponseEntity.ok(true);
        } else {
            log.info("ClientController -> exists: O client com id {} não foi encontrado.", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(summary = "Retrieve all clients.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation completed successfully."),
            @ApiResponse(responseCode = "400", description = "Failed to validate! Request Invalid.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected system failure.", content = @Content)
    })
    public ResponseEntity<List<Client>> findAll() {
        log.info("ClientController -> findAll: Solicitado a consulta de todos os clientes");

        List<Client> all = repository.findAll();

        log.info("ClientController -> findAll: Clientes encontrados: {}", all);
        return ResponseEntity.ok(all);

    }

}
