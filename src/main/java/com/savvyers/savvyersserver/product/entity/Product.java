package com.savvyers.savvyersserver.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "products", schema = "savvyers")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Size(max = 50)
    @Column(name = "food_cd", length = 50)
    private String foodCd;

    @Size(max = 255)
    @Column(name = "item_mnftr_rpt_no")
    private String itemMnftrRptNo;

    @Size(max = 255)
    @Column(name = "food_nm")
    private String foodNm;

    @Size(max = 50)
    @Column(name = "data_cd", length = 50)
    private String dataCd;

    @Size(max = 50)
    @Column(name = "type_nm", length = 50)
    private String typeNm;

    @Size(max = 50)
    @Column(name = "food_origin_cd", length = 50)
    private String foodOriginCd;

    @Size(max = 50)
    @Column(name = "food_origin_nm", length = 50)
    private String foodOriginNm;

    @Size(max = 50)
    @Column(name = "food_lv3_cd", length = 50)
    private String foodLv3Cd;

    @Size(max = 50)
    @Column(name = "food_lv3_nm", length = 50)
    private String foodLv3Nm;

    @Size(max = 50)
    @Column(name = "food_lv4_cd", length = 50)
    private String foodLv4Cd;

    @Size(max = 50)
    @Column(name = "food_lv4_nm", length = 50)
    private String foodLv4Nm;

    @Size(max = 50)
    @Column(name = "food_lv5_cd", length = 50)
    private String foodLv5Cd;

    @Size(max = 50)
    @Column(name = "food_lv5_nm", length = 50)
    private String foodLv5Nm;

    @Size(max = 50)
    @Column(name = "food_lv6_cd", length = 50)
    private String foodLv6Cd;

    @Size(max = 50)
    @Column(name = "food_lv6_nm", length = 50)
    private String foodLv6Nm;

    @Size(max = 50)
    @Column(name = "food_lv7_cd", length = 50)
    private String foodLv7Cd;

    @Size(max = 50)
    @Column(name = "food_lv7_nm", length = 50)
    private String foodLv7Nm;

    @Size(max = 50)
    @Column(name = "nut_con_srtr_qua", length = 50)
    private String nutConSrtrQua;

    @Column(name = "enerc", precision = 10, scale = 2)
    private BigDecimal enerc;

    @Column(name = "water", precision = 10, scale = 2)
    private BigDecimal water;

    @Column(name = "prot", precision = 10, scale = 2)
    private BigDecimal prot;

    @Column(name = "fatce", precision = 10, scale = 2)
    private BigDecimal fatce;

    @Column(name = "ash", precision = 10, scale = 2)
    private BigDecimal ash;

    @Column(name = "chocdf", precision = 10, scale = 2)
    private BigDecimal chocdf;

    @Column(name = "sugar", precision = 10, scale = 2)
    private BigDecimal sugar;

    @Column(name = "fibtg", precision = 10, scale = 2)
    private BigDecimal fibtg;

    @Column(name = "ca", precision = 10, scale = 2)
    private BigDecimal ca;

    @Column(name = "fe", precision = 10, scale = 2)
    private BigDecimal fe;

    @Column(name = "p", precision = 10, scale = 2)
    private BigDecimal p;

    @Column(name = "k", precision = 10, scale = 2)
    private BigDecimal k;

    @Column(name = "nat", precision = 10, scale = 2)
    private BigDecimal nat;

    @Column(name = "vita_rae", precision = 10, scale = 2)
    private BigDecimal vitaRae;

    @Column(name = "retol", precision = 10, scale = 2)
    private BigDecimal retol;

    @Column(name = "cartb", precision = 10, scale = 2)
    private BigDecimal cartb;

    @Column(name = "thia", precision = 10, scale = 2)
    private BigDecimal thia;

    @Column(name = "ribf", precision = 10, scale = 2)
    private BigDecimal ribf;

    @Column(name = "nia", precision = 10, scale = 2)
    private BigDecimal nia;

    @Column(name = "vitc", precision = 10, scale = 2)
    private BigDecimal vitc;

    @Column(name = "vitd", precision = 10, scale = 2)
    private BigDecimal vitd;

    @Column(name = "chole", precision = 10, scale = 2)
    private BigDecimal chole;

    @Column(name = "fasat", precision = 10, scale = 2)
    private BigDecimal fasat;

    @Column(name = "fatrn", precision = 10, scale = 2)
    private BigDecimal fatrn;

    @Size(max = 10)
    @Column(name = "src_cd", length = 10)
    private String srcCd;

    @Size(max = 50)
    @Column(name = "src_nm", length = 50)
    private String srcNm;

    @Size(max = 255)
    @Column(name = "serv_size")
    private String servSize;

    @Size(max = 50)
    @Column(name = "food_size", length = 50)
    private String foodSize;

    @Size(max = 255)
    @Column(name = "mfr_nm")
    private String mfrNm;

    @Size(max = 50)
    @Column(name = "impt_nm", length = 50)
    private String imptNm;

    @Size(max = 50)
    @Column(name = "dist_nm", length = 50)
    private String distNm;

    @Size(max = 50)
    @Column(name = "impt_yn", length = 50)
    private String imptYn;

    @Size(max = 50)
    @Column(name = "coo_cd", length = 50)
    private String cooCd;

    @Size(max = 50)
    @Column(name = "coo_nm", length = 50)
    private String cooNm;

    @Size(max = 50)
    @Column(name = "data_prod_cd", length = 50)
    private String dataProdCd;

    @Size(max = 50)
    @Column(name = "data_prod_nm", length = 50)
    private String dataProdNm;

    @Column(name = "crt_ymd")
    private Instant crtYmd;

    @Column(name = "crtr_ymd")
    private Instant crtrYmd;

    @Size(max = 50)
    @Column(name = "instt_code", length = 50)
    private String insttCode;

    @Size(max = 50)
    @Column(name = "instt_nm", length = 50)
    private String insttNm;

}