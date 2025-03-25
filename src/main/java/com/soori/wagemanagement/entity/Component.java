package com.soori.wagemanagement.entity;

import jakarta.persistence.*;
import lombok.*;
//Will have the record of the component available after being placed.


@Entity
@Table(name = "components")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "component_id")
    private Long componentId;

    @Column(name = "component_name")
    private String componentName;
//    @Column(name = "rate")
//    private Double rate;

    @Column(name = "unit")
    private Integer unit;

    // This column will be used to join with ItemRegistration
    @ManyToOne
    @JoinColumn(name = "item_registration_id")
    private ItemRegistration itemRegistration;

}
