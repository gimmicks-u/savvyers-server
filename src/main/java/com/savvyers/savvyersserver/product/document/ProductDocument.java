package com.savvyers.savvyersserver.product.document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.MultiField;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.Setting;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "products_v3")
@Setting(settingPath = "elasticsearch/products-settings.json")
public class ProductDocument {

    @Id
    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Keyword, name = "food_cd")
    @JsonIgnore
    private String foodCd;

    @MultiField(
            mainField = @Field(type = FieldType.Text, name = "food_nm", analyzer = "korean_nori", searchAnalyzer = "korean_nori"),
            otherFields = {
                    @InnerField(suffix = "ngram", type = FieldType.Text, analyzer = "korean_ngram", searchAnalyzer = "korean_ngram"),
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String foodNm;

    @Field(type = FieldType.Text, name = "food_lv3_cd")
    @JsonIgnore
    private String foodLv3Cd;

    @Field(type = FieldType.Keyword, name = "food_lv3_nm")
    @JsonIgnore
    private String foodLv3Nm;

    @Field(type = FieldType.Text, name = "food_lv4_cd")
    @JsonIgnore
    private String foodLv4Cd;

    @Field(type = FieldType.Keyword, name = "food_lv4_nm")
    @JsonIgnore
    private String foodLv4Nm;

    @Field(type = FieldType.Text, name = "food_lv5_cd")
    @JsonIgnore
    private String foodLv5Cd;

    @Field(type = FieldType.Keyword, name = "food_lv5_nm")
    @JsonIgnore
    private String foodLv5Nm;

    @Field(type = FieldType.Text, name = "food_lv6_cd")
    @JsonIgnore
    private String foodLv6Cd;

    @Field(type = FieldType.Keyword, name = "food_lv6_nm")
    @JsonIgnore
    private String foodLv6Nm;

    @Field(type = FieldType.Text, name = "food_lv7_cd")
    @JsonIgnore
    private String foodLv7Cd;

    @Field(type = FieldType.Keyword, name = "food_lv7_nm")
    @JsonIgnore
    private String foodLv7Nm;

    @Field(type = FieldType.Float)
    private Float enerc;

    @Field(type = FieldType.Float)
    private Float prot;

    @Field(type = FieldType.Float)
    private Float fatce;

    @Field(type = FieldType.Float)
    @JsonIgnore
    private Float ash;

    @Field(type = FieldType.Float)
    private Float chocdf;

    @Field(type = FieldType.Float)
    @JsonIgnore
    private Float fibtg;

    @Field(type = FieldType.Float)
    @JsonIgnore
    private Float ca;

    @Field(type = FieldType.Float)
    @JsonIgnore
    private Float fe;

    @Field(type = FieldType.Float)
    @JsonIgnore
    private Float vitc;

    @Field(type = FieldType.Float)
    @JsonIgnore
    private Float thia;

    @Field(type = FieldType.Float)
    @JsonIgnore
    private Float ribf;

    @Field(type = FieldType.Float)
    @JsonIgnore
    private Float nia;

    @Field(type = FieldType.Float)
    @JsonIgnore
    private Float retol;

    @Field(type = FieldType.Float, name = "vita_rae")
    @JsonIgnore
    private Float vitaRae;

    @Field(type = FieldType.Float)
    @JsonIgnore
    private Float vitd;

    @Field(type = FieldType.Float)
    @JsonIgnore
    private Float k;

    @Field(type = FieldType.Float)
    private Float nat;

    @Field(type = FieldType.Float)
    @JsonIgnore
    private Float p;

    @Field(type = FieldType.Float)
    @JsonIgnore
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
    @JsonIgnore
    private Float cartb;

    @Field(type = FieldType.Text, name = "serv_size")
    @JsonIgnore
    private String servSize;

    @Field(type = FieldType.Text, name = "nut_con_srtr_qua")
    private String nutConSrtrQua;

    @Field(type = FieldType.Text, name = "food_size")
    @JsonIgnore
    private String foodSize;

    @Field(type = FieldType.Text, name = "food_origin_cd")
    @JsonIgnore
    private String foodOriginCd;

    @Field(type = FieldType.Text, name = "food_origin_nm")
    @JsonIgnore
    private String foodOriginNm;

    @Field(type = FieldType.Text, name = "coo_cd")
    @JsonIgnore
    private String cooCd;

    @Field(type = FieldType.Text, name = "coo_nm")
    @JsonIgnore
    private String cooNm;

    @MultiField(
            mainField = @Field(type = FieldType.Text, name = "mfr_nm", analyzer = "korean_nori", searchAnalyzer = "korean_nori"),
            otherFields = {
                    @InnerField(suffix = "ngram", type = FieldType.Text, analyzer = "korean_ngram", searchAnalyzer = "korean_ngram"),
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String mfrNm;

    @Field(type = FieldType.Text, name = "dist_nm")
    @JsonIgnore
    private String distNm;

    @Field(type = FieldType.Text, name = "impt_nm")
    @JsonIgnore
    private String imptNm;

    @Field(type = FieldType.Text, name = "impt_yn")
    @JsonIgnore
    private String imptYn;

    @Field(type = FieldType.Text, name = "src_cd")
    @JsonIgnore
    private String srcCd;

    @Field(type = FieldType.Text, name = "src_nm")
    @JsonIgnore
    private String srcNm;

    @Field(type = FieldType.Text, name = "type_nm")
    @JsonIgnore
    private String typeNm;

    @Field(type = FieldType.Text, name = "data_cd")
    @JsonIgnore
    private String dataCd;

    @Field(type = FieldType.Text, name = "data_prod_cd")
    @JsonIgnore
    private String dataProdCd;

    @Field(type = FieldType.Text, name = "data_prod_nm")
    @JsonIgnore
    private String dataProdNm;

    @Field(type = FieldType.Text, name = "item_mnftr_rpt_no")
    @JsonIgnore
    private String itemMnftrRptNo;

    @Field(type = FieldType.Keyword, name = "report_no")
    @JsonIgnore
    private String reportNo;

    @Field(type = FieldType.Text, name = "crt_ymd")
    @JsonIgnore
    private String crtYmd;

    @Field(type = FieldType.Text, name = "crtr_ymd")
    @JsonIgnore
    private String crtrYmd;

    @Field(type = FieldType.Object, name = "haccp_data")
    private HaccpData haccpData;
}