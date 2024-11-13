package com.group4.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "shipping_address_id", referencedColumnName = "address_id")
    private AddressEntity shippingAddress;

    @Column(name = "order_date", nullable = false)
    private Date orderDate;

    @Column(name = "receive_date", nullable = false)
    private Date receiveDate;

    @Column(name = "shipping_state", nullable = false)
    //Trạng thái giao hàng
    private String shippingStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", referencedColumnName = "payment_id")
    private PaymentEntity payment;

    @Column(name = "shipping_method", nullable = false)
    // Phương thức giao hàng
    private String shippingMethod;


    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    private String note;

    @PrePersist
    public void onCreate() {
        orderDate = new Date();
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineItemEntity> listLineItems;
}

