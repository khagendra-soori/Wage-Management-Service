package com.soori.wagemanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.apachecommons.CommonsLog;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

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
    @GeneratedValue(generator = "job_master_id", strategy = GenerationType.IDENTITY)
    @Column(name = "job_master_id")
    private String jobMasterId;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "address")
    private String address;

    @OneToMany
    private List<Component> components;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "detail_id")
    private OrderDetail orderDetail;

}
