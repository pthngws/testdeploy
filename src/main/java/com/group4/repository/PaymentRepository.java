package com.group4.repository;

import com.group4.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    @Query("SELECT SUM(p.total) FROM PaymentEntity p WHERE DATE(p.paymentDate) = :date")
    Double getRevenueByDay(@Param("date") LocalDate date);

    @Query("SELECT MONTH(p.paymentDate) as month, SUM(p.total) as revenue " +
            "FROM PaymentEntity p " +
            "WHERE YEAR(p.paymentDate) = :year " +
            "GROUP BY MONTH(p.paymentDate)")
    List<Map<String, Object>> getMonthlyRevenue(@Param("year") int year);
}
