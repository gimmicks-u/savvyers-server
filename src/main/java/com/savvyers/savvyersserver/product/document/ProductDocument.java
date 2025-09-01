package com.savvyers.savvyersserver.product.document;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "products")
public class ProductDocument {

    @Id
    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Keyword, name = "food_cd")
    private String foodCd;

    @Field(type = FieldType.Text, analyzer = "standard", name = "food_nm")
    private String foodNm;

    @Field(type = FieldType.Text, name = "food_lv3_cd")
    private String foodLv3Cd;

    @Field(type = FieldType.Keyword, name = "food_lv3_nm")
    private String foodLv3Nm;

    @Field(type = FieldType.Text, name = "food_lv4_cd")
    private String foodLv4Cd;

    @Field(type = FieldType.Keyword, name = "food_lv4_nm")
    private String foodLv4Nm;

    @Field(type = FieldType.Text, name = "food_lv5_cd")
    private String foodLv5Cd;

    @Field(type = FieldType.Keyword, name = "food_lv5_nm")
    private String foodLv5Nm;

    @Field(type = FieldType.Text, name = "food_lv6_cd")
    private String foodLv6Cd;

    @Field(type = FieldType.Keyword, name = "food_lv6_nm")
    private String foodLv6Nm;

    @Field(type = FieldType.Text, name = "food_lv7_cd")
    private String foodLv7Cd;

    @Field(type = FieldType.Keyword, name = "food_lv7_nm")
    private String foodLv7Nm;

    @Field(type = FieldType.Float)
    private Float enerc;

    @Field(type = FieldType.Float)
    private Float prot;

    @Field(type = FieldType.Float)
    private Float fatce;

    @Field(type = FieldType.Float)
    private Float ash;

    @Field(type = FieldType.Float)
    private Float chocdf;

    @Field(type = FieldType.Float)
    private Float fibtg;

    @Field(type = FieldType.Float)
    private Float ca;

    @Field(type = FieldType.Float)
    private Float fe;

    @Field(type = FieldType.Float)
    private Float vitc;

    @Field(type = FieldType.Float)
    private Float thia;

    @Field(type = FieldType.Float)
    private Float ribf;

    @Field(type = FieldType.Float)
    private Float nia;

    @Field(type = FieldType.Float)
    private Float retol;

    @Field(type = FieldType.Float, name = "vita_rae")
    private Float vitaRae;

    @Field(type = FieldType.Float)
    private Float vitd;

    @Field(type = FieldType.Float)
    private Float k;

    @Field(type = FieldType.Float)
    private Float nat;

    @Field(type = FieldType.Float)
    private Float p;

    @Field(type = FieldType.Float)
    private Float water;

    @Field(type = FieldType.Float)
    private Float fasat;

    @Field(type = FieldType.Float)
    private Float fatrn;

    @Field(type = FieldType.Float)
    private Float chole;

    @Field(type = FieldType.Float)
    private Float sugar;

    @Field(type = FieldType.Float)
    private Float cartb;

    @Field(type = FieldType.Text, name = "serv_size")
    private String servSize;

    @Field(type = FieldType.Text, name = "nut_con_srtr_qua")
    private String nutConSrtrQua;

    @Field(type = FieldType.Text, name = "food_size")
    private String foodSize;

    @Field(type = FieldType.Text, name = "food_origin_cd")
    private String foodOriginCd;

    @Field(type = FieldType.Text, name = "food_origin_nm")
    private String foodOriginNm;

    @Field(type = FieldType.Text, name = "coo_cd")
    private String cooCd;

    @Field(type = FieldType.Text, name = "coo_nm")
    private String cooNm;

    @Field(type = FieldType.Text, name = "mfr_nm")
    private String mfrNm;

    @Field(type = FieldType.Text, name = "dist_nm")
    private String distNm;

    @Field(type = FieldType.Text, name = "impt_nm")
    private String imptNm;

    @Field(type = FieldType.Text, name = "impt_yn")
    private String imptYn;

    @Field(type = FieldType.Text, name = "src_cd")
    private String srcCd;

    @Field(type = FieldType.Text, name = "src_nm")
    private String srcNm;

    @Field(type = FieldType.Text, name = "type_nm")
    private String typeNm;

    @Field(type = FieldType.Text, name = "data_cd")
    private String dataCd;

    @Field(type = FieldType.Text, name = "data_prod_cd")
    private String dataProdCd;

    @Field(type = FieldType.Text, name = "data_prod_nm")
    private String dataProdNm;

    @Field(type = FieldType.Text, name = "item_mnftr_rpt_no")
    private String itemMnftrRptNo;

    @Field(type = FieldType.Keyword, name = "report_no")
    private String reportNo;

    @Field(type = FieldType.Text, name = "crt_ymd")
    private String crtYmd;

    @Field(type = FieldType.Text, name = "crtr_ymd")
    private String crtrYmd;

    @Field(type = FieldType.Object, name = "haccp_data")
    private HaccpData haccpData;
}