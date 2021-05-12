package com.target.app.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;

/**
 * Cassandra DB configuration class
 */
@Configuration
public class CassandraConfiguration extends AbstractCassandraConfiguration {

    @Value("${spring.data.cassandra.keyspace}")
    private String keySpace;


    @Override
    protected String getKeyspaceName() {
        return keySpace;
    }


}
