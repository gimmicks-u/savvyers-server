package com.savvyers.savvyersserver.product.document;

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
    private String allergy;

    @Field(type = FieldType.Text, index = false)
    private String imgurl1;

    @Field(type = FieldType.Text, index = false)
    private String imgurl2;

    @Field(type = FieldType.Text)
    private String manufacture;

    @Field(type = FieldType.Text)
    private String prdkind;

    @Field(type = FieldType.Text)
    private String prdkindstate;

    @Field(type = FieldType.Text)
    private String prdlstNm;

    @Field(type = FieldType.Text)
    private String productGb;

    @Field(type = FieldType.Text)
    private String rawmtrl;

    @Field(type = FieldType.Text)
    private String reportNo;

    @Field(type = FieldType.Text)
    private String rnum;
}