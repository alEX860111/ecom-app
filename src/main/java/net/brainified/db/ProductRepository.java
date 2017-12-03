package net.brainified.db;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface ProductRepository extends ReactiveSortingRepository<Product, String> {

}
