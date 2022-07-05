package com.course.productservice.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.With;
import org.springframework.data.annotation.Id;

@Data
@ToString
public class Product {

    @Id
    private String id;
    private String description;
    private Integer price;

}
