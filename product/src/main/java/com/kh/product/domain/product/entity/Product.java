package com.kh.product.domain.product.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

  private Long pid;
  private String pname;
  private Long quantity;
  private Long price;

}
