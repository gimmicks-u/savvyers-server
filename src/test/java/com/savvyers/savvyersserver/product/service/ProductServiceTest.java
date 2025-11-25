package com.savvyers.savvyersserver.product.service;

import com.savvyers.savvyersserver.product.document.ProductDocument;
import com.savvyers.savvyersserver.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.TotalHitsRelation;
import org.springframework.data.elasticsearch.core.query.Query;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("ProductService 단위 테스트")
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ElasticsearchOperations elasticsearchOperations;

    @InjectMocks
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
    @DisplayName("getProductById 메서드")
    class GetProductByIdTest {

        @Test
        @DisplayName("존재하는_상품_ID로_조회하면_상품을_반환한다")
        void 존재하는_상품_ID로_조회하면_상품을_반환한다() {
            // given
            Long productId = 1L;
            given(productRepository.findById(productId)).willReturn(Optional.of(테스트_상품));

            // when
            ProductDocument result = productService.getProductById(productId);

            // then
            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(productId);
            assertThat(result.getFoodNm()).isEqualTo("테스트 상품명");
            verify(productRepository, times(1)).findById(productId);
        }

        @Test
        @DisplayName("존재하지_않는_상품_ID로_조회하면_null을_반환한다")
        void 존재하지_않는_상품_ID로_조회하면_null을_반환한다() {
            // given
            Long productId = 999L;
            given(productRepository.findById(productId)).willReturn(Optional.empty());

            // when
            ProductDocument result = productService.getProductById(productId);

            // then
            assertThat(result).isNull();
            verify(productRepository, times(1)).findById(productId);
        }
    }

    @Nested
    @DisplayName("searchProducts 메서드")
    class SearchProductsTest {

        @Test
        @DisplayName("유효한_키워드로_검색하면_검색결과를_반환한다")
        void 유효한_키워드로_검색하면_검색결과를_반환한다() {
            // given
            String keyword = "테스트";
            Pageable pageable = PageRequest.of(0, 10);
            
            @SuppressWarnings("unchecked")
            SearchHit<ProductDocument> searchHit = mock(SearchHit.class);
            given(searchHit.getContent()).willReturn(테스트_상품);
            
            @SuppressWarnings("unchecked")
            SearchHits<ProductDocument> searchHits = mock(SearchHits.class);
            given(searchHits.getSearchHits()).willReturn(List.of(searchHit));
            given(searchHits.getTotalHits()).willReturn(1L);
            given(searchHits.getTotalHitsRelation()).willReturn(TotalHitsRelation.EQUAL_TO);
            given(searchHits.stream()).willReturn(List.of(searchHit).stream());
            
            given(elasticsearchOperations.search(any(Query.class), eq(ProductDocument.class))).willReturn(searchHits);

            // when
            var result = productService.searchProducts(keyword, pageable);

            // then
            assertThat(result).isNotNull();
            verify(elasticsearchOperations, times(1)).search(any(Query.class), eq(ProductDocument.class));
        }

        @Test
        @DisplayName("검색결과가_없으면_빈_페이지를_반환한다")
        void 검색결과가_없으면_빈_페이지를_반환한다() {
            // given
            String keyword = "존재하지않는상품";
            Pageable pageable = PageRequest.of(0, 10);
            
            @SuppressWarnings("unchecked")
            SearchHits<ProductDocument> emptySearchHits = mock(SearchHits.class);
            given(emptySearchHits.getSearchHits()).willReturn(List.of());
            given(emptySearchHits.getTotalHits()).willReturn(0L);
            given(emptySearchHits.getTotalHitsRelation()).willReturn(TotalHitsRelation.EQUAL_TO);
            given(emptySearchHits.stream()).willReturn(List.<SearchHit<ProductDocument>>of().stream());
            
            given(elasticsearchOperations.search(any(Query.class), eq(ProductDocument.class))).willReturn(emptySearchHits);

            // when
            var result = productService.searchProducts(keyword, pageable);

            // then
            assertThat(result).isNotNull();
            assertThat(result.getTotalElements()).isZero();
            verify(elasticsearchOperations, times(1)).search(any(Query.class), eq(ProductDocument.class));
        }

        @Test
        @DisplayName("페이지네이션이_적용된_검색결과를_반환한다")
        void 페이지네이션이_적용된_검색결과를_반환한다() {
            // given
            String keyword = "테스트";
            Pageable pageable = PageRequest.of(1, 5);
            
            @SuppressWarnings("unchecked")
            SearchHits<ProductDocument> searchHits = mock(SearchHits.class);
            given(searchHits.getSearchHits()).willReturn(List.of());
            given(searchHits.getTotalHits()).willReturn(10L);
            given(searchHits.getTotalHitsRelation()).willReturn(TotalHitsRelation.EQUAL_TO);
            given(searchHits.stream()).willReturn(List.<SearchHit<ProductDocument>>of().stream());
            
            given(elasticsearchOperations.search(any(Query.class), eq(ProductDocument.class))).willReturn(searchHits);

            // when
            var result = productService.searchProducts(keyword, pageable);

            // then
            assertThat(result).isNotNull();
            verify(elasticsearchOperations, times(1)).search(any(Query.class), eq(ProductDocument.class));
        }
    }
}

