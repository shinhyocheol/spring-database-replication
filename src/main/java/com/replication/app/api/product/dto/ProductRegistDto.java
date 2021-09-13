package com.replication.app.api.product.dto;

import com.replication.app.api.product.domain.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductRegistDto {

    private String title;

    private String content;

    public Product toEntity() {
        Product build = Product.builder()
                .title(title)
                .content(content)
                .build();

        return build;
    }

}
