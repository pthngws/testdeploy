package com.group4.repository;

import com.group4.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT o FROM OrderEntity o WHERE o.customer.userID = :customerId")
    List<OrderEntity> findByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT o FROM OrderEntity o WHERE o.orderId = :keyword")
    List<OrderEntity> findByOrderId(@Param("keyword") String keyword);

    @Query("SELECT o FROM OrderEntity o WHERE o.orderId = :keyword AND o.shippingStatus = :status")
    List<OrderEntity> findByOrderIdAndStatus(@Param("keyword") String keyword, @Param("status") String status);

    @Query("SELECT o FROM OrderEntity o WHERE o.shippingStatus = :status")
    List<OrderEntity> findByStatus(@Param("status") String status);
    @Query("SELECT o FROM OrderEntity o WHERE o.customer.userID = :userID")
    List<OrderEntity> findOrdersByUserID(Long userID);

    @Query("SELECT o FROM OrderEntity o WHERE o.shippingStatus = 'true' AND o.payment.paymentDate BETWEEN :startDate AND :endDate")
    List<OrderEntity> findOrdersWithShippingStatusAndReceiveDate(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
