package in.ashokit.rest;

import in.ashokit.dto.ApiResponse;
import in.ashokit.dto.ProductCategoryDto;
import in.ashokit.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class ProductCategoryRestController {

    @Autowired
    private ProductCategoryService productCategoryService;


    @PostMapping("/product-category")
    public ResponseEntity<ApiResponse<ProductCategoryDto>> createProductCategory(@RequestBody ProductCategoryDto categoryDto){

        ApiResponse<ProductCategoryDto> response = new ApiResponse<>();

        ProductCategoryDto productCategory = productCategoryService.createProductCategory(categoryDto);

        if(productCategory!=null){
            response.setStatusCode(201);
            response.setData(productCategory);
            response.setMessage("Product Category Saved");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }else{
            response.setStatusCode(500);
            response.setData(null);
            response.setMessage("Product Category Saving Failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/product-categories")
    public ResponseEntity<ApiResponse<List<ProductCategoryDto>>> getAllProductCategories(){

        List<ProductCategoryDto> allProductCategories = productCategoryService.getAllProductCategories();

        ApiResponse<List<ProductCategoryDto>> response = new ApiResponse<>();

        response.setStatusCode(200);
        response.setMessage("Product Categories Retrieved Successfully");
        response.setData(allProductCategories);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product-category/{categoryId}")
    public ResponseEntity<ApiResponse<ProductCategoryDto>> getProductCategoryById(@PathVariable Integer categoryId){

        ProductCategoryDto productCategoryById = productCategoryService.getProductCategoryById(categoryId);
        ApiResponse<ProductCategoryDto> response = new ApiResponse<>();

        response.setMessage("Product Category Retrieved");
        response.setStatusCode(200);
        response.setData(productCategoryById);

        return ResponseEntity.ok(response);

    }

    @PutMapping("/product-category/{categoryId}")
    public ResponseEntity<ApiResponse<ProductCategoryDto>> updateCategory(@PathVariable Integer categoryId,
                                                                          @RequestBody ProductCategoryDto categoryDto){

        ProductCategoryDto updatedCategory = productCategoryService.updateProductCategory(categoryId, categoryDto);
        ApiResponse<ProductCategoryDto> response = new ApiResponse<>();

        response.setMessage("Product Category Updated");
        response.setStatusCode(200);
        response.setData(updatedCategory);

        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/product-category/{categoryId}")
    public ResponseEntity<ApiResponse<ProductCategoryDto>> deleteCategory(@PathVariable Integer categoryId){

        ProductCategoryDto updatedCategory = productCategoryService.deleteProductCategory(categoryId);
        ApiResponse<ProductCategoryDto> response = new ApiResponse<>();

        response.setMessage("Product Category Deleted");
        response.setStatusCode(200);
        response.setData(updatedCategory);

        return ResponseEntity.ok(response);

    }
}