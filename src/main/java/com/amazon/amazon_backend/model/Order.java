package com.amazon.amazon_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="Orders")
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Order_ID")
    private Long orderId;

    @Column(name="Buyer_ID")
    private Long buyerId;

    @Column(name="Total_Amount")
    private double totalAmount;

    @Column(name="Order_Date")
    private LocalDateTime datetime;

    public Order(Long buyerId,
                 double totalAmount,
                 LocalDateTime datetime){

        this.buyerId=buyerId;
        this.totalAmount=totalAmount;
        this.datetime=datetime;
    }
}
