package com.savvyers.savvyersserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    @Value("${ELASTICSEARCH_HOST}")
    private String elasticsearchHost;

    @Value("${ELASTICSEARCH_USERNAME}")
    private String username;

    @Value("${ELASTICSEARCH_PASSWORD}")
    private String password;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(elasticsearchHost)
                .withBasicAuth(username, password)
                .build();
    }
}