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

    @Column(name = "rate")
    private Double rate;

    @ManyToMany
    @JoinTable(
            name = "item_components",
            joinColumns = @JoinColumn(name = "item_registeration_id"),
            inverseJoinColumns = @JoinColumn(name = "component_id")
    )
    private List<Component> components = new ArrayList<>();

}
