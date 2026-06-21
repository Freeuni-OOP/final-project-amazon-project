package com.amazon.amazon_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Transactions")
@NoArgsConstructor
@Getter @Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Order_ID")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Buyer_ID")
    private User buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Seller_ID")
    private User seller;

    @Column(name = "Amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status = TransactionStatus.PENDING;

    @Column(name = "Created_At", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetails> items = new ArrayList<>();


    public Transaction(Order order, User buyer, User seller, BigDecimal totalAmount, TransactionStatus status){
        this.order = order;
        this.buyer = buyer;
        this.seller = seller;
        this.totalAmount = totalAmount;
        this.status = status;
    }


    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public void addItem(OrderDetails item) {
        items.add(item);
        item.setTransaction(this);
    }

}
