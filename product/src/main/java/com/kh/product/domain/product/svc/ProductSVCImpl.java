package com.kh.product.domain.product.svc;

import com.kh.product.domain.product.dao.ProductDAO;
import com.kh.product.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductSVCImpl implements ProductSVC{

  private final ProductDAO productDAO;

  /** 등록
   * @param product
   * @return
   */
  @Override
  public Long save(Product product) {
    return productDAO.save(product);
  }

  /** 조회
   * @param pid
   * @return
   */
  @Override
  public Optional<Product> findById(Long pid) {
    return productDAO.findById(pid);
  }

  /** 수정
   * @param pid
   * @param product
   * @return
   */
  @Override
  public int update(Long pid, Product product) {
    return productDAO.update(pid, product);
  }

  /** 삭제
   * @param pid
   * @return
   */
  @Override
  public int delete(Long pid) {
    return productDAO.delete(pid);
  }

  /** 목록
   * @return
   */
  @Override
  public List<Product> listAll() {
    return productDAO.listAll();
  }
}
