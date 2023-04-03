package com.kh.product.domain.product.dao;

import com.kh.product.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductDAOImpl implements ProductDAO {

  private final NamedParameterJdbcTemplate template;

  /**
   * 등록
   *
   * @param product
   * @return
   */
  @Override
  public Long save(Product product) {

    StringBuffer sql = new StringBuffer();
    sql.append(" insert into product(pid, pname, quantity, price) ");
    sql.append(" values(product_pid_seq.nextval, :pname, :quantity, :price) ");

    SqlParameterSource param = new BeanPropertySqlParameterSource(product);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(sql.toString(), param, keyHolder, new String[]{"pid"});

    long pid = keyHolder.getKey().longValue();

    return pid;
  }

  /**
   * 조회
   *
   * @param pid
   * @return
   */
  @Override
  public Optional<Product> findById(Long pid) {
    StringBuffer sql = new StringBuffer();
    sql.append(" select pid, pname, quantity, price ");
    sql.append(" from product ");
    sql.append(" where pid = :id ");

    try {
      Map<String, Long> param = Map.of("id", pid);

      Product product = template.queryForObject(
        sql.toString(),
        param,
        BeanPropertyRowMapper.newInstance(Product.class)
      );
      return Optional.of(product);

    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  /**
   * 수정
   *
   * @param pid
   * @param product
   * @return
   */
  @Override
  public int update(Long pid, Product product) {
    StringBuffer sql = new StringBuffer();
    sql.append(" update product ");
    sql.append(" set pname = :pname, ");
    sql.append(" quantity = :quantity, ");
    sql.append(" price = :price ");
    sql.append(" where pid = :id ");

    SqlParameterSource param = new MapSqlParameterSource()
      .addValue("pname", product.getPname())
      .addValue("quantity", product.getQuantity())
      .addValue("price", product.getPrice())
      .addValue("id", pid);
    return template.update(sql.toString(), param);
  }

  /**
   * 삭제
   *
   * @param pid
   * @return
   */
  @Override
  public int delete(Long pid) {
    StringBuffer sql = new StringBuffer();
    sql.append(" delete from product where pid = :id ");
    return template.update(sql.toString(), Map.of("id", pid));
  }

  /**
   * 목록
   *
   * @return
   */
  @Override
  public List<Product> listAll() {
    StringBuffer sql = new StringBuffer();
    sql.append(" select pid, pname, quantity, price ");
    sql.append(" from product ");

    List<Product> list = template.query(
      sql.toString(),
      BeanPropertyRowMapper.newInstance(Product.class)
    );

    return list;
  }
}
