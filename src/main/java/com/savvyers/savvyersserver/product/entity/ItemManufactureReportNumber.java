package com.savvyers.savvyersserver.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "item_manufacture_report_numbers", schema = "savvyers", indexes = {
        @Index(name = "idx_product_id", columnList = "product_id"),
        @Index(name = "idx_report_no", columnList = "report_no")
})
public class ItemManufactureReportNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false, columnDefinition = "int UNSIGNED not null",
            foreignKey = @ForeignKey(name = "item_manufacture_report_numbers_ibfk_1"))
    private Product product;

    @Size(max = 100)
    @NotNull
    @Column(name = "report_no", nullable = false, length = 100)
    private String reportNo;
}