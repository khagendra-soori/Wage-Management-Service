package com.soori.wagemanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Long detailId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "rate")
    private Double rate;

    // Use the correct field name "orderDetail" from ItemRegistration
    @OneToMany(mappedBy = "orderDetail", cascade = CascadeType.ALL)
    private List<ItemRegistration> items = new ArrayList<>();

}
