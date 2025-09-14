package java_pro_spring.homework_6.api.dto;

import java_pro_spring.homework_6.domain.ProductType;

import java.math.BigDecimal;

public record ProductDto(Long id,
                         String accountNumber,
                         BigDecimal balance,
                         ProductType productType,
                         Long userId) {
}
