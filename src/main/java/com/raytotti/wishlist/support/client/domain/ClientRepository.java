package com.raytotti.wishlist.support.client.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends MongoRepository<Client, String> {
    boolean existsByCpf(String cpf);
}
