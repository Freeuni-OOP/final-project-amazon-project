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
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name="Buyer_ID", nullable = false)
    private Integer buyerId;

    @Column(name="Total_Amount", nullable = false)
    private double totalAmount;

    @Column(name="Order_Date", nullable = false)
    private LocalDateTime datetime;

    public Order(Integer buyerId,
                 double totalAmount,
                 LocalDateTime datetime){

        this.buyerId=buyerId;
        this.totalAmount=totalAmount;
        this.datetime=datetime;
    }
}
