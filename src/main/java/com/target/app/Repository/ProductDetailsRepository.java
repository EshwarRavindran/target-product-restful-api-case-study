package com.target.app.Repository;

import com.target.app.Model.ProductDetailsData;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailsRepository extends CassandraRepository<ProductDetailsData, Integer> {
}
