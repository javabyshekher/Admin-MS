package in.ashokit.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ProductCategoryDto {

    private Integer categoryId;
    private String categoryName;
    private String categoryDescription;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
