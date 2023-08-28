package com.fiap.burger.controller.controller;

import com.fiap.burger.controller.adapter.api.ProductController;
import com.fiap.burger.entity.product.Category;
import com.fiap.burger.entity.product.Product;
import com.fiap.burger.usecase.adapter.gateway.ProductGateway;
import com.fiap.burger.usecase.adapter.usecase.ProductUseCase;
import com.fiap.burger.usecase.misc.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultProductController implements ProductController {
    @Autowired
    private ProductUseCase useCase;
    @Autowired
    private ProductGateway gateway;
    @Override
    public List<Product> list(Category category) {
        if (category == null) {
            return useCase
                    .findAll(gateway);
        } else {
            return useCase
                    .findAllBy(gateway, category);
        }
    }

    @Override
    public Product findById(Long productId) {
        var persistedProduct = useCase.findById(gateway, productId);
        if (persistedProduct == null) throw new ProductNotFoundException();
        return persistedProduct;
    }

    @Override
    public Product insert(Product product) {
        return useCase.insert(gateway, product);
    }

    @Override
    public Product update(Product product) {
        return useCase.update(gateway, product);
    }

    @Override
    public String deleteBy(Long productId) {
        useCase.deleteBy(gateway, productId);
        return "Product has been successfully deleted.";
    }
}
