package com.group4.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "orders")
@Inheritance(strategy = InheritanceType.JOINED)
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    @JsonManagedReference
    private CustomerEntity customer;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shipping_address_id", referencedColumnName = "address_id")
    private AddressEntity shippingAddress;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "receive_date", nullable = false)
    private LocalDateTime  receiveDate;

    @Column(name = "shipping_state", nullable = false)
    //Trạng thái giao hàng
    private String shippingStatus;

    @Column(name = "shipping_method", nullable = false)
    // Phương thức giao hàng
    private String shippingMethod;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    private String note;

    @Column(name = "payment_tatus", nullable = false)
    //Trạng thái giao hàng
    private String paymentStatus;

    @PrePersist
    public void onCreate() {
        orderDate = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonBackReference
    private List<LineItemEntity> listLineItems;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", referencedColumnName = "payment_id")
    @ToString.Exclude
    @JsonManagedReference
    private PaymentEntity payment;

    // Phương thức tiện ích để lấy phương thức thanh toán
    public String getPaymentMethod() {
        return payment != null ? payment.getPaymentMethod() : "Chưa xác định";
    }
}

