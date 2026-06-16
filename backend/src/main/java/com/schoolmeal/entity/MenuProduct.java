package com.schoolmeal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "menu_products", uniqueConstraints = @UniqueConstraint(columnNames = {"menu_id", "product_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;
}
