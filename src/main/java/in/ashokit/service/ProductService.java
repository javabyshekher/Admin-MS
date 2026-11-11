package in.ashokit.service;

import in.ashokit.dto.ProductCategoryDto;
import in.ashokit.dto.ProductDto;
import in.ashokit.entity.ProductEntity;
import in.ashokit.mapper.ProductMapper;
import in.ashokit.repository.ProductCategoryRepository;
import in.ashokit.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductCategoryRepository categoryRepository;

    @Value("${images.upload-dir}")
    private String imagesUploadDir;

    public ProductDto createProduct(Integer categoryId, ProductDto productDto, MultipartFile productImage) throws Exception {
        UUID uuid = UUID.randomUUID();
        String originalFileName = uuid.toString() + "_" + productImage.getOriginalFilename();

        Path filePath = Paths.get(imagesUploadDir + originalFileName);

        // Create the directory if it doesn't exist
        if (!Files.exists(filePath)) {
            try {
                Files.createDirectories(filePath.getParent());
            } catch (IOException e) {
                throw new RuntimeException("Failed to save product image", e);
            }
        }
        Files.copy(productImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        productDto.setImageUrl(filePath.toString());

        ProductEntity entity = ProductMapper.toEntity(productDto);

        categoryRepository.findById(categoryId).ifPresent(category -> entity.setCategory(category));

        ProductEntity saved = repository.save(entity);

        return ProductMapper.toDto(saved);

    }

    public ProductDto updateProduct(Integer productId, ProductDto productDto, MultipartFile productImage) throws Exception {

        ProductEntity productEntity = repository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        UUID uuid = UUID.randomUUID();

        String originalFilename = uuid.toString() + "_" + productImage.getOriginalFilename();

        Path filePath = Paths.get(imagesUploadDir + originalFilename);

        // Create the directory if it doesn't exist
        if (!Files.exists(filePath)) {
            try {
                Files.createDirectories(filePath.getParent());
            } catch (IOException e) {
                throw new RuntimeException("Failed to save product image", e);
            }
        }

        // Save the file to the specified location
        Files.copy(productImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        productEntity.setImageUrl(filePath.toString());
        productEntity.setName(productDto.getName());
        productEntity.setDescription(productDto.getDescription());
        productEntity.setUnitsStock(productDto.getUnitsStock());
        // productEntity.setActive(productDto.isActive());
        productEntity.setUnitPrice(productDto.getUnitPrice());

        ProductEntity updatedEntity = repository.save(productEntity); // UPSERT
        return ProductMapper.toDto(updatedEntity);
    }

    public List<ProductDto> getAllProductsByCategoryId(Integer categoryId) {
        return repository.findByCategoryCategoryId(categoryId).stream().map(ProductMapper::toDto).toList();
    }

    public ProductDto getProductById(Integer productId) {
        ProductEntity productEntity = repository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        return ProductMapper.toDto(productEntity);
    }

    public List<ProductDto> getProductsByName(String productName) {
        return repository.findByNameContainingIgnoreCase(productName).stream().map(ProductMapper::toDto).toList();
    }

    public ProductDto deleteProduct(Integer productId) {

        ProductEntity productEntity = repository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        ProductEntity save = repository.save(productEntity);

        return ProductMapper.toDto(save);
    }
}
