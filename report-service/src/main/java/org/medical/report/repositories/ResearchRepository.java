package org.medical.report.repositories;

import org.medical.report.model.Research;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ResearchRepository extends JpaRepository<Research, Long> {
    @Query("SELECT r FROM Research r WHERE r.researchType=:researchType " +
            "AND r.date BETWEEN :startDate AND :endDate")
    List<Research> findByParams(@Param("researchType") String researchType,
                                @Param("startDate") Date startDate, @Param("endDate")Date endDate);

    @Query("SELECT COUNT (r) FROM Research r WHERE r.researchType=:researchType " +
            "AND r.date BETWEEN :startDate AND :endDate AND r.researchZone=:researchZone")
    Integer findCountByParams(@Param("researchType") String researchType,
                                @Param("startDate") Date startDate,
                              @Param("endDate")Date endDate, @Param("researchZone")String researchZone);
}
