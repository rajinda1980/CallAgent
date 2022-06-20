package com.codechallenge.callapp.repository;

import com.codechallenge.callapp.data.TransactionLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionLocationRepository extends JpaRepository<TransactionLocation, Long> {
    TransactionLocation findByName(String name);
}
