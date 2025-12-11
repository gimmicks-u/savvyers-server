package com.savvyers.savvyersserver.product.document;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * History
 * - Haccp fresh 시스템 변경으로 이미지에 접근할 수 없는 issue 발생
 * - 이미지 URL 임시 null 반환 처리
 * TODO: Haccp 시스템 정상화 이후 이미지 URL 문제 해결
 */
@DisplayName("HaccpData 단위 테스트")
class HaccpDataTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Nested
    @DisplayName("이미지 URL 임시 null 반환 테스트")
    class ImageUrlNullTest {
        @Test
        @DisplayName("imgurl1_getter는_항상_null을_반환한다")
        void imgurl1_getter는_항상_null을_반환한다() {
            // given
            HaccpData haccpData = new HaccpData();
            haccpData.setImgurl1("http://example.com/image1.jpg");

            // when
            String result = haccpData.getImgurl1();

            // then
            assertThat(result).isNull();
        }

        @Test
        @DisplayName("imgurl2_getter는_항상_null을_반환한다")
        void imgurl2_getter는_항상_null을_반환한다() {
            // given
            HaccpData haccpData = new HaccpData();
            haccpData.setImgurl2("http://example.com/image2.jpg");

            // when
            String result = haccpData.getImgurl2();

            // then
            assertThat(result).isNull();
        }

        @Test
        @DisplayName("생성자로_이미지URL을_설정해도_getter는_null을_반환한다")
        void 생성자로_이미지URL을_설정해도_getter는_null을_반환한다() {
            // given
            HaccpData haccpData = new HaccpData(
                    1L,
                    "알러지정보",
                    "http://example.com/image1.jpg",
                    "http://example.com/image2.jpg",
                    "제조사",
                    "품목종류",
                    "품목상태",
                    "제품명",
                    "제품구분",
                    "원재료",
                    "신고번호",
                    "일련번호"
            );

            // when & then
            assertThat(haccpData.getImgurl1()).isNull();
            assertThat(haccpData.getImgurl2()).isNull();
        }

        @Test
        @DisplayName("JSON_직렬화시_이미지URL은_null로_반환된다")
        void JSON_직렬화시_이미지URL은_null로_반환된다() throws Exception {
            // given
            HaccpData haccpData = new HaccpData();
            haccpData.setId(1L);
            haccpData.setImgurl1("http://example.com/image1.jpg");
            haccpData.setImgurl2("http://example.com/image2.jpg");
            haccpData.setManufacture("테스트제조사");
            haccpData.setPrdlstNm("테스트제품");

            // when
            String json = objectMapper.writeValueAsString(haccpData);

            // then
            assertThat(json).contains("\"imgurl1\":null");
            assertThat(json).contains("\"imgurl2\":null");
            assertThat(json).contains("\"manufacture\":\"테스트제조사\"");
            assertThat(json).contains("\"prdlstNm\":\"테스트제품\"");
        }

        @Test
        @DisplayName("다른_필드들은_정상적으로_값을_반환한다")
        void 다른_필드들은_정상적으로_값을_반환한다() {
            // given
            HaccpData haccpData = new HaccpData();
            haccpData.setId(1L);
            haccpData.setManufacture("테스트제조사");
            haccpData.setPrdlstNm("테스트제품");

            // when & then
            assertThat(haccpData.getId()).isEqualTo(1L);
            assertThat(haccpData.getManufacture()).isEqualTo("테스트제조사");
            assertThat(haccpData.getPrdlstNm()).isEqualTo("테스트제품");
        }
    }
}

