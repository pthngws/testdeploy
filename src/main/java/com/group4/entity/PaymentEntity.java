package com.group4.entity;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentID;

    @Column(name = "transaction_id")
    private String transactionID;

    @Column(name = "payment_method",columnDefinition = "nvarchar(250)", nullable = false)
    private String paymentMethod;

    @Column(name = "payment_status", columnDefinition = "nvarchar(250)", nullable = false)
    private String paymentStatus;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @Column(nullable = false)
    private int total;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    @ToString.Exclude // Loại trừ khỏi toString() để tránh vòng lặp
    private OrderEntity order;
}
