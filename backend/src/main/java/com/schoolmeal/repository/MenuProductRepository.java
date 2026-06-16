package com.schoolmeal.repository;

import com.schoolmeal.entity.MenuProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MenuProductRepository extends JpaRepository<MenuProduct, Long> {
    List<MenuProduct> findByMenuId(Long menuId);
}
