package com.soori.wagemanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "item_registrations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_registration_id")
    private Long itemRegistrationId;

    @Column(name = "item_name")
    private String itemName;

    @OneToMany(mappedBy = "itemRegistration",cascade = {CascadeType.PERSIST, CascadeType.MERGE},
    orphanRemoval = true)
    private List<Component> components = new ArrayList<>();

    // This is the correct mapping back to OrderDetail
    @ManyToOne
    @JoinColumn(name = "detail_id") // This will create a foreign key column in the item_registrations table
    private OrderDetail orderDetail;

}
