package com.savvyers.savvyersserver.product.document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HaccpData {

    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Text)
    @JsonIgnore
    private String allergy;

    @Field(type = FieldType.Text, index = false)
    private String imgurl1;

    @Field(type = FieldType.Text, index = false)
    private String imgurl2;

    // TODO: Haccp 이미지 URL 문제 해결 후 제거 필요
    public String getImgurl1() {
        return null;
    }

    public String getImgurl2() {
        return null;
    }

    @Field(type = FieldType.Text)
    private String manufacture;

    @Field(type = FieldType.Text)
    @JsonIgnore
    private String prdkind;

    @Field(type = FieldType.Text)
    @JsonIgnore
    private String prdkindstate;

    @Field(type = FieldType.Text, name = "prdlst_nm")
    private String prdlstNm;

    @Field(type = FieldType.Text)
    @JsonIgnore
    private String productGb;

    @Field(type = FieldType.Text)
    @JsonIgnore
    private String rawmtrl;

    @Field(type = FieldType.Text)
    @JsonIgnore
    private String reportNo;

    @Field(type = FieldType.Text)
    @JsonIgnore
    private String rnum;
}