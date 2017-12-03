package net.brainified;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface ProductRepository extends ReactiveSortingRepository<Product, String> {

}
