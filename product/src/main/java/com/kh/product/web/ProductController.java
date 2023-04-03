package com.kh.product.web;

import com.kh.product.domain.product.entity.Product;
import com.kh.product.domain.product.svc.ProductSVC;
import com.kh.product.web.restDTO.SaveRest;
import com.kh.product.web.restDTO.UpdateRest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductSVC productSVC;

  //등록
  @PostMapping
  public RestResponse<Object> save(@RequestBody SaveRest saveRest) {
    RestResponse<Object> res = null;
    log.info("saveRest={}", saveRest);

    Product product = new Product();
    product.setPname(saveRest.getPname());
    product.setQuantity(saveRest.getQuantity());
    product.setPrice(saveRest.getPrice());

    Long pid = productSVC.save(product);
    product.setPid(pid);

    if (pid > 0) {
      res = RestResponse.createRestResponse("00", "성공", product);
    } else {
      res = RestResponse.createRestResponse("99", "실패", "서버오류");
    }
    return res;
  }

  //조회
  @GetMapping("/{id}")
  public RestResponse<Object> findById(
    @PathVariable("id") Long pid
  ) {
    RestResponse<Object> res = null;

    Optional<Product> findProduct = productSVC.findById(pid);
    res = RestResponse.createRestResponse("00", "성공", findProduct);
    return res;
  }

  //수정
  @PatchMapping("/{id}")
  public RestResponse<Object> update(
    @PathVariable("id") Long pid,
    @RequestBody UpdateRest updateRest
  ) {
    RestResponse<Object> res = null;

    Product product = new Product();
    product.setPname(updateRest.getPname());
    product.setQuantity(updateRest.getQuantity());
    product.setPrice(updateRest.getPrice());

    int updateRowCnt = productSVC.update(pid, product);
    updateRest.setPid(pid);

    if (updateRowCnt == 1) {
      res = RestResponse.createRestResponse("00", "성공", updateRest);
    } else {
      res = RestResponse.createRestResponse("99", "실패", "서버오류");
    }
    return res;
  }

  //삭제
  @DeleteMapping("/{id}")
  public RestResponse<Object> delete(
    @PathVariable("id") Long pid
    ) {
    RestResponse<Object> res = null;

    int deleteRowCnt = productSVC.delete(pid);
    if (deleteRowCnt == 1) {
      res = RestResponse.createRestResponse("00", "성공", null);
    } else {
      res = RestResponse.createRestResponse("99", "실패", "서버오류");
    }
    return res;
  }

  //목록
  @GetMapping
  public RestResponse<Object> listAll() {
    RestResponse<Object> res = null;
    List<Product> list = productSVC.listAll();
    if (list.size() > 0) {
      res = RestResponse.createRestResponse("00", "성공", list);
    } else {
      res = RestResponse.createRestResponse("01", "상품이 존재하지 않습니다.", null);
    }
    return res;
  }


}
