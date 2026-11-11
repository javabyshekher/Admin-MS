package in.ashokit.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductDto {

    private Integer productId;
    private String name;
    private String description;
    private String title;
    private Double unitPrice;
    private String imageUrl;

    private Integer unitsStock;

    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;
}
