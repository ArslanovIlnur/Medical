package org.medical.researches.repositories;

import org.medical.researches.model.Research;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResearchRepository extends JpaRepository<Research, Long> {
    List<Research> findByPatientId(Integer patientId);
}
