package com.savvyers.savvyersserver.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product_info", schema = "savvyers")
public class ProductInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Size(max = 255)
    @Column(name = "prdkindstate")
    private String prdkindstate;

    @Lob
    @Column(name = "manufacture")
    private String manufacture;

    @Size(max = 255)
    @Column(name = "rnum")
    private String rnum;

    @Size(max = 255)
    @Column(name = "prdkind")
    private String prdkind;

    @Lob
    @Column(name = "rawmtrl")
    private String rawmtrl;

    @Size(max = 255)
    @Column(name = "prdlst_nm")
    private String prdlstNm;

    @Size(max = 255)
    @Column(name = "imgurl2")
    private String imgurl2;

    @Size(max = 255)
    @Column(name = "imgurl1")
    private String imgurl1;

    @Size(max = 255)
    @Column(name = "product_gb")
    private String productGb;

    @Size(max = 255)
    @Column(name = "report_no")
    private String reportNo;

    @Lob
    @Column(name = "allergy")
    private String allergy;

}