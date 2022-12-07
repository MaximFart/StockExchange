package com.tot.system.repository;

import com.tot.system.model.History;
import com.tot.system.model.Security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

}
