package com.course.productservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.With;

@Data
@ToString
public class ProductDto {

    private String id;
    private String description;
    private Integer price;

}
