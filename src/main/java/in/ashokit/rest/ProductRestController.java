package in.ashokit.rest;

import in.ashokit.dto.ApiResponse;
import in.ashokit.dto.ProductDto;
import in.ashokit.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class ProductRestController {

    @Autowired
    private ProductService productService;

    @PostMapping("/product")
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(@RequestParam("categoryId") Integer categoryId,
                                                                 @RequestParam("product") String productJson,
                                                                 @RequestParam("productImage") MultipartFile productImage)
            throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        ProductDto productDto = mapper.readValue(productJson, ProductDto.class);
        ProductDto createdProduct = productService.createProduct(categoryId, productDto, productImage);

        ApiResponse<ProductDto> response = new ApiResponse<>();
        if (createdProduct != null) {
            response.setStatusCode(201);
            response.setMessage("Product created successfully");
            response.setData(createdProduct);
            return ResponseEntity.status(201).body(response);
        } else {
            response.setStatusCode(400);
            response.setMessage("Failed to create product");
            response.setData(null);
            return ResponseEntity.status(400).body(response);
        }
    }

    @PutMapping("/product")
    public ResponseEntity<ApiResponse<ProductDto>> updateProduct(@RequestParam("productId") Integer productId,
                                                                 @RequestParam("product") String productJson,
                                                                 @RequestParam("productImage") MultipartFile productImage)
            throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        ProductDto productDto = mapper.readValue(productJson, ProductDto.class);
        ProductDto createdProduct = productService.updateProduct(productId, productDto, productImage);

        ApiResponse<ProductDto> response = new ApiResponse<>();
        if (createdProduct != null) {
            response.setStatusCode(200);
            response.setMessage("Product Updated successfully");
            response.setData(createdProduct);
            return ResponseEntity.status(200).body(response);
        } else {
            response.setStatusCode(400);
            response.setMessage("Failed to update product");
            response.setData(null);
            return ResponseEntity.status(400).body(response);
        }
    }

    @GetMapping("/products/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getProductsByCategoryId(@PathVariable Integer categoryId){

        List<ProductDto> allProductsByCategoryId = productService.getAllProductsByCategoryId(categoryId);
        ApiResponse<List<ProductDto>> response = new ApiResponse<>();

        if (!allProductsByCategoryId.isEmpty()) {
            response.setStatusCode(200);
            response.setMessage("Products fetched successfully");
            response.setData(allProductsByCategoryId);
            return ResponseEntity.status(200).body(response);
        } else {
            response.setStatusCode(400);
            response.setMessage("Failed to fetch products");
            response.setData(null);
            return ResponseEntity.status(400).body(response);
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<ProductDto>> getProductById(@PathVariable Integer productId){

        ProductDto productById = productService.getProductById(productId);

        ApiResponse<ProductDto> response = new ApiResponse<>();

        if (productById!=null) {
            response.setStatusCode(200);
            response.setMessage("Product fetched successfully");
            response.setData(productById);
            return ResponseEntity.status(200).body(response);
        } else {
            response.setStatusCode(400);
            response.setMessage("Failed to fetch product");
            response.setData(null);
            return ResponseEntity.status(400).body(response);
        }
    }

    @GetMapping("/product/by-name/{name}")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getProductsByName(@PathVariable String name){

        List<ProductDto> productsByName = productService.getProductsByName(name);

        ApiResponse<List<ProductDto>> response = new ApiResponse<>();

        if (!productsByName.isEmpty()) {
            response.setStatusCode(200);
            response.setMessage("Products fetched successfully");
            response.setData(productsByName);
            return ResponseEntity.status(200).body(response);
        } else {
            response.setStatusCode(400);
            response.setMessage("Failed to fetch products");
            response.setData(null);
            return ResponseEntity.status(400).body(response);
        }
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<ProductDto>> deleteProduct(@PathVariable("productId") Integer productId)throws Exception {

        ProductDto deletedProduct = productService.deleteProduct(productId);

        ApiResponse<ProductDto> response = new ApiResponse<>();

        if (deletedProduct != null) {
            response.setStatusCode(200);
            response.setMessage("Product deleted successfully");
            response.setData(deletedProduct);
            return ResponseEntity.status(200).body(response);
        } else {
            response.setStatusCode(400);
            response.setMessage("Failed to delete product");
            response.setData(null);
            return ResponseEntity.status(400).body(response);
        }
    }
}
