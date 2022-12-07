package com.tot.system.repository;

import com.tot.system.model.Security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityRepository extends JpaRepository<Security, Long> {
    Optional<Security> findBySecId(String secId);

    void deleteBySecId(String secId);
}
