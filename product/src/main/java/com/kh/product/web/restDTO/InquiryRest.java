package com.kh.product.web.restDTO;

import lombok.Data;

@Data
public class InquiryRest {
  private Long pid;
  private String pname;
  private Long quantity;
  private Long price;
}
