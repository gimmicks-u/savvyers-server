package com.savvyers.savvyersserver.product.service;

import com.savvyers.savvyersserver.product.document.ProductDocument;
import com.savvyers.savvyersserver.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.stereotype.Service;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    public Page<ProductDocument> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public ProductDocument getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Page<ProductDocument> searchProducts(String keyword, Pageable pageable) {

        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.bool(b -> b
                        .should(s -> s.multiMatch(mm -> mm
                                .query(keyword)
                                .type(TextQueryType.CrossFields)
                                .operator(Operator.And)
                                .fields("food_nm^2", "mfr_nm")
                        ))
                        .should(s -> s.multiMatch(mm -> mm
                                .query(keyword)
                                .type(TextQueryType.CrossFields)
                                .operator(Operator.And)
                                .fields("food_nm.ngram^2", "mfr_nm.ngram")
                        ))
                        .minimumShouldMatch("1")
                ))
                .withPageable(pageable)
                .build();

        SearchHits<ProductDocument> hits = elasticsearchOperations.search(query, ProductDocument.class);
        return SearchHitSupport.searchPageFor(hits, pageable).map(SearchHit::getContent);
    }
}
