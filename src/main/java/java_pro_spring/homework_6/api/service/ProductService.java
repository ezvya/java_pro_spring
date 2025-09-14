package java_pro_spring.homework_6.api.service;

import java_pro_spring.homework_6.api.dto.ProductDto;
import java_pro_spring.homework_6.domain.Product;
import java_pro_spring.homework_6.repo.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<ProductDto> getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::mapProductToDto);
    }

    public Optional<List<ProductDto>> getProductsByUserId(Long userId) {
        List<ProductDto> products = productRepository.findByUserId(userId)
                .stream()
                .map(this::mapProductToDto)
                .collect(Collectors.toList());

        return products.isEmpty() ? Optional.empty() : Optional.of(products);
    }

    private ProductDto mapProductToDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getAccountNumber(),
                product.getBalance(),
                product.getProductType(),
                product.getUser().getId()
        );
    }
}
