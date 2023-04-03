package com.kh.product.web.restDTO;

import lombok.Data;

@Data
public class SaveRest {
  private String pname;
  private Long quantity;
  private Long price;
}
