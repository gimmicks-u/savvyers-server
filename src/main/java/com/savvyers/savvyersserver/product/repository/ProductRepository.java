package com.savvyers.savvyersserver.product.repository;

import com.savvyers.savvyersserver.product.document.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ElasticsearchRepository<ProductDocument, Long> {
}