package com.codechallenge.callapp.repository;

import com.codechallenge.callapp.data.CallLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallLogRepository extends JpaRepository<CallLog, Long> {
}
