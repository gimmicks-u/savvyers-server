package com.savvyers.savvyersserver.product.controller;

import com.savvyers.savvyersserver.product.document.ProductDocument;
import com.savvyers.savvyersserver.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@DisplayName("ProductController 단위 테스트")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    private ProductDocument 테스트_상품;

    @BeforeEach
    void setUp() {
        테스트_상품 = new ProductDocument();
        테스트_상품.setId(1L);
        테스트_상품.setFoodNm("테스트 상품명");
        테스트_상품.setMfrNm("테스트 제조사");
        테스트_상품.setEnerc(100.0F);
        테스트_상품.setProt(10.0F);
        테스트_상품.setFatce(5.0F);
        테스트_상품.setChocdf(15.0F);
    }

    @Nested
    @DisplayName("GET /api/products/search")
    class SearchProductsTest {

        @Test
        @DisplayName("유효한_요청이_들어오면_검색결과를_반환한다")
        void 유효한_요청이_들어오면_검색결과를_반환한다() throws Exception {
            // given
            String keyword = "테스트";
            Page<ProductDocument> expectedPage = new PageImpl<>(
                    List.of(테스트_상품),
                    PageRequest.of(0, 10),
                    1
            );
            given(productService.searchProducts(eq(keyword), any(Pageable.class))).willReturn(expectedPage);

            // when & then
            mockMvc.perform(get("/api/products/search")
                            .param("keyword", keyword)
                            .param("page", "0")
                            .param("size", "10")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isArray())
                    .andExpect(jsonPath("$.content[0].id").value(1))
                    .andExpect(jsonPath("$.content[0].foodNm").value("테스트 상품명"))
                    .andExpect(jsonPath("$.totalElements").value(1));
        }

        @Test
        @DisplayName("검색결과가_없으면_빈_배열을_반환한다")
        void 검색결과가_없으면_빈_배열을_반환한다() throws Exception {
            // given
            String keyword = "존재하지않는상품";
            Page<ProductDocument> emptyPage = new PageImpl<>(
                    Collections.emptyList(),
                    PageRequest.of(0, 10),
                    0
            );
            given(productService.searchProducts(eq(keyword), any(Pageable.class))).willReturn(emptyPage);

            // when & then
            mockMvc.perform(get("/api/products/search")
                            .param("keyword", keyword)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isArray())
                    .andExpect(jsonPath("$.content").isEmpty())
                    .andExpect(jsonPath("$.totalElements").value(0));
        }

        @Test
        @DisplayName("키워드_없이_요청하면_400_에러를_반환한다")
        void 키워드_없이_요청하면_400_에러를_반환한다() throws Exception {
            // when & then
            mockMvc.perform(get("/api/products/search")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            verify(productService, never()).searchProducts(anyString(), any(Pageable.class));
        }

        @Test
        @DisplayName("빈_키워드로_요청하면_400_에러를_반환한다")
        void 빈_키워드로_요청하면_400_에러를_반환한다() throws Exception {
            // when & then
            mockMvc.perform(get("/api/products/search")
                            .param("keyword", "")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            verify(productService, never()).searchProducts(anyString(), any(Pageable.class));
        }

        @Test
        @DisplayName("공백_키워드로_요청하면_400_에러를_반환한다")
        void 공백_키워드로_요청하면_400_에러를_반환한다() throws Exception {
            // when & then
            mockMvc.perform(get("/api/products/search")
                            .param("keyword", "   ")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            verify(productService, never()).searchProducts(anyString(), any(Pageable.class));
        }

        @Test
        @DisplayName("페이지_번호_기본값은_0이다")
        void 페이지_번호_기본값은_0이다() throws Exception {
            // given
            String keyword = "테스트";
            Page<ProductDocument> expectedPage = new PageImpl<>(
                    List.of(테스트_상품),
                    PageRequest.of(0, 10),
                    1
            );
            given(productService.searchProducts(eq(keyword), any(Pageable.class))).willReturn(expectedPage);

            // when & then
            mockMvc.perform(get("/api/products/search")
                            .param("keyword", keyword)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.number").value(0));
        }

        @Test
        @DisplayName("페이지_크기_기본값은_10이다")
        void 페이지_크기_기본값은_10이다() throws Exception {
            // given
            String keyword = "테스트";
            Page<ProductDocument> expectedPage = new PageImpl<>(
                    List.of(테스트_상품),
                    PageRequest.of(0, 10),
                    1
            );
            given(productService.searchProducts(eq(keyword), any(Pageable.class))).willReturn(expectedPage);

            // when & then
            mockMvc.perform(get("/api/products/search")
                            .param("keyword", keyword)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size").value(10));
        }

        @Test
        @DisplayName("페이지_크기는_최대_24이다")
        void 페이지_크기는_최대_24이다() throws Exception {
            // when & then
            mockMvc.perform(get("/api/products/search")
                            .param("keyword", "테스트")
                            .param("size", "25")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            verify(productService, never()).searchProducts(anyString(), any(Pageable.class));
        }

        @Test
        @DisplayName("페이지_크기는_최소_1이다")
        void 페이지_크기는_최소_1이다() throws Exception {
            // when & then
            mockMvc.perform(get("/api/products/search")
                            .param("keyword", "테스트")
                            .param("size", "0")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            verify(productService, never()).searchProducts(anyString(), any(Pageable.class));
        }

        @Test
        @DisplayName("페이지_번호가_음수이면_400_에러를_반환한다")
        void 페이지_번호가_음수이면_400_에러를_반환한다() throws Exception {
            // when & then
            mockMvc.perform(get("/api/products/search")
                            .param("keyword", "테스트")
                            .param("page", "-1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            verify(productService, never()).searchProducts(anyString(), any(Pageable.class));
        }

        @Test
        @DisplayName("유효한_페이지네이션으로_요청하면_페이지_정보가_포함된_결과를_반환한다")
        void 유효한_페이지네이션으로_요청하면_페이지_정보가_포함된_결과를_반환한다() throws Exception {
            // given
            String keyword = "테스트";
            Page<ProductDocument> expectedPage = new PageImpl<>(
                    List.of(테스트_상품),
                    PageRequest.of(2, 5),
                    100
            );
            given(productService.searchProducts(eq(keyword), any(Pageable.class))).willReturn(expectedPage);

            // when & then
            mockMvc.perform(get("/api/products/search")
                            .param("keyword", keyword)
                            .param("page", "2")
                            .param("size", "5")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.number").value(2))
                    .andExpect(jsonPath("$.size").value(5))
                    .andExpect(jsonPath("$.totalElements").value(100))
                    .andExpect(jsonPath("$.totalPages").value(20));
        }
    }

    @Nested
    @DisplayName("GET /api/products/{id}")
    class GetProductByIdTest {

        @Test
        @DisplayName("존재하는_상품_ID로_조회하면_상품을_반환한다")
        void 존재하는_상품_ID로_조회하면_상품을_반환한다() throws Exception {
            // given
            Long productId = 1L;
            given(productService.getProductById(productId)).willReturn(테스트_상품);

            // when & then
            mockMvc.perform(get("/api/products/{id}", productId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.foodNm").value("테스트 상품명"))
                    .andExpect(jsonPath("$.mfrNm").value("테스트 제조사"))
                    .andExpect(jsonPath("$.enerc").value(100.0))
                    .andExpect(jsonPath("$.prot").value(10.0))
                    .andExpect(jsonPath("$.fatce").value(5.0))
                    .andExpect(jsonPath("$.chocdf").value(15.0));
        }

        @Test
        @DisplayName("존재하지_않는_상품_ID로_조회하면_404_에러를_반환한다")
        void 존재하지_않는_상품_ID로_조회하면_404_에러를_반환한다() throws Exception {
            // given
            Long productId = 999L;
            given(productService.getProductById(productId)).willReturn(null);

            // when & then
            mockMvc.perform(get("/api/products/{id}", productId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("상품_ID가_양수가_아니면_ConstraintViolationException이_발생한다")
        void 상품_ID가_양수가_아니면_ConstraintViolationException이_발생한다() {
            // when & then
            assertThat(org.junit.jupiter.api.Assertions.assertThrows(
                    jakarta.servlet.ServletException.class,
                    () -> mockMvc.perform(get("/api/products/{id}", 0)
                            .contentType(MediaType.APPLICATION_JSON))
            ).getCause()).isInstanceOf(jakarta.validation.ConstraintViolationException.class);

            verify(productService, never()).getProductById(any());
        }

        @Test
        @DisplayName("상품_ID가_음수이면_ConstraintViolationException이_발생한다")
        void 상품_ID가_음수이면_ConstraintViolationException이_발생한다() {
            // when & then
            assertThat(org.junit.jupiter.api.Assertions.assertThrows(
                    jakarta.servlet.ServletException.class,
                    () -> mockMvc.perform(get("/api/products/{id}", -1)
                            .contentType(MediaType.APPLICATION_JSON))
            ).getCause()).isInstanceOf(jakarta.validation.ConstraintViolationException.class);

            verify(productService, never()).getProductById(any());
        }

        @Test
        @DisplayName("상품_조회_시_영양정보가_포함되어_반환된다")
        void 상품_조회_시_영양정보가_포함되어_반환된다() throws Exception {
            // given
            Long productId = 1L;
            테스트_상품.setNat(500.0F);
            테스트_상품.setFasat(3.0F);
            테스트_상품.setFatrn(0.5F);
            테스트_상품.setChole(10.0F);
            테스트_상품.setSugar(8.0F);
            given(productService.getProductById(productId)).willReturn(테스트_상품);

            // when & then
            mockMvc.perform(get("/api/products/{id}", productId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.nat").value(500.0))
                    .andExpect(jsonPath("$.fasat").value(3.0))
                    .andExpect(jsonPath("$.fatrn").value(0.5))
                    .andExpect(jsonPath("$.chole").value(10.0))
                    .andExpect(jsonPath("$.sugar").value(8.0));
        }
    }
}

