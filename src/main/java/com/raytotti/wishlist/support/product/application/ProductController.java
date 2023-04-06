package com.raytotti.wishlist.support.product.application;

import com.raytotti.wishlist.support.product.domain.Product;
import com.raytotti.wishlist.support.product.domain.ProductRepository;
import com.raytotti.wishlist.support.product.exception.ProductExistsException;
import com.raytotti.wishlist.support.product.exception.ProductNotFoundException;
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
@RequestMapping(path = "/api/v1/products")
@Tag(name = "Product", description = "Product API Operations")
public class ProductController {

    private final ProductRepository repository;

    @PostMapping
    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Operation completed successfully."),
            @ApiResponse(responseCode = "400", description = "Failed to validate! Request Invalid.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Failed to validate! Code already exists.", content = @Content),
            @ApiResponse(responseCode = "415", description = "Unsupported Content Type.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected system failure.", content = @Content)
    })
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid CreateProductRequest request) {
        log.info("ProductController -> create: Solicitado a criação de um produto: {}", request);

        boolean exists = repository.existsByCode(request.getCode());
        if (exists) {
            log.info("ProductController -> create: O produto com codigo {} já existe.", request.getCode());
            throw new ProductExistsException();
        }

        Product newProduct = Product.builder()
                .code(request.getCode())
                .description(request.getDescription())
                .thumbnail(request.getThumbnail())
                .price(request.getPrice())
                .category(request.getCategory())
                .brand(request.getBrand())
                .additionalInfo(request.getAdditionalInfo())
                .build();

        Product product = repository.save(newProduct);
        log.info("ProductController -> create: Produto com id {} criado.", product.getId());

        ProductResponse response = ProductResponse.from(product);
        log.info("ClientController -> create: Produto respondido {}", response);

        URI uri = fromCurrentRequest()
                .path("/")
                .path(response.getId())
                .build().toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Remove an informed product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Operation completed successfully."),
            @ApiResponse(responseCode = "400", description = "Failed to validate! Request Invalid.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Failed to validate! Product not found.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected system failure.", content = @Content)
    })
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("ProductController -> delete: Solicitado a remoção do produto com id: {}", id);

        if (!repository.existsById(id)) {
            log.error("ProductController -> delete: Produto com id {} não encontrado.", id);
            throw new ProductNotFoundException();
        }

        repository.deleteById(id);
        log.error("ProductController -> delete: Produto com id {} removido.", id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Retrieve an informed product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation completed successfully."),
            @ApiResponse(responseCode = "400", description = "Failed to validate! Request Invalid.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Failed to validate! Product not found.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected system failure.", content = @Content)
    })
    public ResponseEntity<ProductResponse> findById(@PathVariable String id) {
        log.info("ProductController -> findById: Solicitado a busca de um produto pelo id {}.", id);
        Optional<Product> product = repository.findById(id);

        ProductResponse productResponse = ProductResponse.from(product.orElseThrow(() -> {
            log.error("ProductController -> findById: Produto com o id {} não encontrado.", id);
            return new ProductNotFoundException();
        }));

        log.info("ProductController -> findById: Produto encontrado. {}", productResponse);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping
    @Operation(summary = "Retrieve all products.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation completed successfully."),
            @ApiResponse(responseCode = "400", description = "Failed to validate! Request Invalid.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected system failure.", content = @Content)
    })
    public ResponseEntity<List<Product>> findAll() {
        log.info("ProductController -> findAll: Solicitado a consulta de todos os produtos.");

        List<Product> all = repository.findAll();

        log.info("ProductController -> findAll: Produtos encontrados: {}", all);
        return ResponseEntity.ok(all);
    }

}
