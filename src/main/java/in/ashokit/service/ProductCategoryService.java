package in.ashokit.service;

import in.ashokit.dto.ProductCategoryDto;
import in.ashokit.entity.ProductCategoryEntity;
import in.ashokit.mapper.ProductCategoryMapper;
import in.ashokit.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository repository;

    public ProductCategoryDto createProductCategory(ProductCategoryDto productCategoryDto) {
        ProductCategoryEntity entity = ProductCategoryMapper.toEntity(productCategoryDto);
        ProductCategoryEntity saved = repository.save(entity);
        return ProductCategoryMapper.toDto(saved);
    }

    public List<ProductCategoryDto> getAllProductCategories() {
        List<ProductCategoryEntity> entities = repository.findAll();
        return entities.stream()
                .map(ProductCategoryMapper::toDto).toList();
    }

    public ProductCategoryDto getProductCategoryById(Integer categoryId) {
        ProductCategoryEntity productCategoryEntity = repository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Product category not found with id: " + categoryId));
        return ProductCategoryMapper.toDto(productCategoryEntity);
    }

    public ProductCategoryDto getProductCategoryByName(String categoryName) {
        ProductCategoryEntity byCategoryName = repository.findByCategoryName(categoryName);
        if(byCategoryName!=null) {
            return ProductCategoryMapper.toDto(byCategoryName);
        }
        return null;
    }

    public ProductCategoryDto updateProductCategory(Integer categoryId, ProductCategoryDto dto) {
        ProductCategoryEntity entity = repository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Product category not found with id : " + categoryId));
        entity.setCategoryName(dto.getCategoryName());
        entity.setCategoryDescription(dto.getCategoryDescription());
        ProductCategoryEntity saved = repository.save(entity);
        return ProductCategoryMapper.toDto(saved);
    }

    public ProductCategoryDto deleteProductCategory(Integer categoryId) {
        ProductCategoryEntity entity = repository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Product category not found with id : " + categoryId));
        repository.delete(entity);
        return ProductCategoryMapper.toDto(entity);
    }
}
