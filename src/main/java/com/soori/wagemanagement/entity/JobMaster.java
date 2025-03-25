package com.soori.wagemanagement.entity;

import com.soori.wagemanagement.utils.OrderIdGenerator;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.apachecommons.CommonsLog;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "job_masters")
@Builder

public class JobMaster {
    @Id
    @GenericGenerator(name = "job_master_id", strategy = "com.soori.wagemanagement.utils.JobMasterIdGenerator")
    @GeneratedValue(generator = "job_master_id")
    @Column(name = "job_master_id", updatable = false, nullable = false)
    private String jobMasterId;

//    @GenericGenerator(name = "order_id", strategy = "com.soori.wagemanagement.utils.OrderIdGenerator")
    @Column(name = "order_id", updatable = false, nullable = false, unique = true)
    private String orderId;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "address")
    private String address;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_price")
    private Double totalPrice;


    @ManyToMany
    @JoinTable(
            name = "order_items",
            joinColumns = @JoinColumn(name = "detail_id"),
            inverseJoinColumns = @JoinColumn(name = "item_registration_id")
    )
    private List<ItemRegistration> items = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (this.orderId == null) {
            // Simplified order ID generation that doesn't need database access
            String prefix = "ORDER00";
            int randomSuffix = 100 + (int)(Math.random() * 900); // Random number between 100-999
            this.orderId = prefix + randomSuffix;
        }
    }
}
