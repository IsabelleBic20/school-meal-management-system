package com.schoolmeal.service;

import com.schoolmeal.dto.MenuDTO;
import com.schoolmeal.dto.MenuProductDTO;
import com.schoolmeal.dto.PageDTO;
import com.schoolmeal.entity.Menu;
import com.schoolmeal.entity.MenuProduct;
import com.schoolmeal.entity.Product;
import com.schoolmeal.exception.EntityNotFoundException;
import com.schoolmeal.mapper.MenuMapper;
import com.schoolmeal.mapper.MenuProductMapper;
import com.schoolmeal.repository.MenuProductRepository;
import com.schoolmeal.repository.MenuRepository;
import com.schoolmeal.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuProductRepository menuProductRepository;
    private final ProductRepository productRepository;
    private final MenuMapper menuMapper;
    private final MenuProductMapper menuProductMapper;

    public PageDTO<MenuDTO> getAllMenus(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Menu> menuPage = menuRepository.findAll(pageable);

        return PageDTO.<MenuDTO>builder()
                .content(menuPage.stream()
                        .map(menuMapper::toDTO)
                        .collect(Collectors.toList()))
                .totalPages(menuPage.getTotalPages())
                .totalElements(menuPage.getTotalElements())
                .currentPage(page)
                .pageSize(size)
                .build();
    }

    public MenuDTO getMenuById(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Menu not found with id: " + id));
        return menuMapper.toDTO(menu);
    }

    public MenuDTO createMenu(MenuDTO menuDTO) {
        Menu menu = new Menu();
        menu.setName(menuDTO.getName());
        menu.setDate(menuDTO.getDate());

        Menu savedMenu = menuRepository.save(menu);

        if (menuDTO.getProducts() != null && !menuDTO.getProducts().isEmpty()) {
            for (MenuProductDTO productDTO : menuDTO.getProducts()) {
                Product product = productRepository.findById(productDTO.getProductId())
                        .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productDTO.getProductId()));

                MenuProduct menuProduct = MenuProduct.builder()
                        .menu(savedMenu)
                        .product(product)
                        .quantity(productDTO.getQuantity())
                        .build();

                menuProductRepository.save(menuProduct);
            }
        }

        return menuMapper.toDTO(menuRepository.findById(savedMenu.getId()).orElseThrow());
    }

    public MenuDTO updateMenu(Long id, MenuDTO menuDTO) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Menu not found with id: " + id));

        menu.setName(menuDTO.getName());
        menu.setDate(menuDTO.getDate());

        Menu updatedMenu = menuRepository.save(menu);

        menuProductRepository.deleteAll(menuProductRepository.findByMenuId(id));

        if (menuDTO.getProducts() != null && !menuDTO.getProducts().isEmpty()) {
            for (MenuProductDTO productDTO : menuDTO.getProducts()) {
                Product product = productRepository.findById(productDTO.getProductId())
                        .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productDTO.getProductId()));

                MenuProduct menuProduct = MenuProduct.builder()
                        .menu(updatedMenu)
                        .product(product)
                        .quantity(productDTO.getQuantity())
                        .build();

                menuProductRepository.save(menuProduct);
            }
        }

        return menuMapper.toDTO(menuRepository.findById(updatedMenu.getId()).orElseThrow());
    }

    public void deleteMenu(Long id) {
        if (!menuRepository.existsById(id)) {
            throw new EntityNotFoundException("Menu not found with id: " + id);
        }
        menuRepository.deleteById(id);
    }
}
