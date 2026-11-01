
package com.bizshield.bizshield_ai.repository;

import com.bizshield.bizshield_ai.model.SecurityEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityEventRepository
        extends JpaRepository<SecurityEvent, Long> {
}