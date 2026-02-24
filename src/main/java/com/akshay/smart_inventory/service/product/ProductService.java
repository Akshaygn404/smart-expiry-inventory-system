package com.akshay.smart_inventory.service.product;

import com.akshay.smart_inventory.dto.request.ProductRequest;
import com.akshay.smart_inventory.dto.response.ProductResponse;
import com.akshay.smart_inventory.exception.DuplicateResourceException;
import com.akshay.smart_inventory.exception.ResourceNotFoundException;
import com.akshay.smart_inventory.model.Category;
import com.akshay.smart_inventory.model.Product;
import com.akshay.smart_inventory.repository.BatchRepository;
import com.akshay.smart_inventory.repository.CategoryRepository;
import com.akshay.smart_inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BatchRepository batchRepository;

    @Override
    public ProductResponse createProduct(ProductRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        if (productRepository.existsByNameAndCategoryId(
                request.getName(), request.getCategoryId())) {

            throw new DuplicateResourceException(
                    "Product with this name already exists in this category");
        }

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setReorderLevel(request.getReorderLevel());
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        return mapToResponse(savedProduct);
    }

    @Override
    public ProductResponse getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return mapToResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        boolean isNameChanged = !product.getName().equals(request.getName());
        boolean isCategoryChanged = !product.getCategory().getId().equals(request.getCategoryId());

        if ((isNameChanged || isCategoryChanged) &&
                productRepository.existsByNameAndCategoryId(
                        request.getName(), request.getCategoryId())) {

            throw new DuplicateResourceException(
                    "Product with this name already exists in this category");
        }

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setReorderLevel(request.getReorderLevel());
        product.setCategory(category);

        Product updatedProduct = productRepository.save(product);

        return mapToResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        productRepository.delete(product);
    }

    public List<ProductResponse> getLowStockProducts() {

        return productRepository.findAll()
                .stream()
                .filter(product -> {

                    Integer totalStock =
                            batchRepository.getTotalValidStockByProductId(product.getId());

                    return totalStock < product.getReorderLevel();
                })
                .map(this::mapToResponse)
                .toList();
    }


    private ProductResponse mapToResponse(Product product) {

        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setReorderLevel(product.getReorderLevel());
        response.setCategoryId(product.getCategory().getId());
        response.setCategoryName(product.getCategory().getName());

        return response;
    }
}