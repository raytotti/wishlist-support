package com.raytotti.wishlist.support.product.application;

import com.raytotti.wishlist.support.product.domain.Product;
import com.raytotti.wishlist.support.product.domain.ProductRepository;
import com.raytotti.wishlist.support.product.exception.ProductExistsException;
import com.raytotti.wishlist.support.product.exception.ProductNotFoundException;
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
public class ProductController {

    private final ProductRepository repository;

    @PostMapping
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

    @GetMapping(path = "/")
    public ResponseEntity<List<Product>> findAll() {
        log.info("ProductController -> findAll: Solicitado a consulta de todos os produtos.");

        List<Product> all = repository.findAll();

        log.info("ProductController -> findAll: Produtos encontrados: {}", all);
        return ResponseEntity.ok(all);
    }

}
