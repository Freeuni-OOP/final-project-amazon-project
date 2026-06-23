package com.amazon.amazon_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="Comment")
@NoArgsConstructor

public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Comment_ID")
    private Integer commentId;

    @Column(name = "Comment_STR", nullable = false, length = 500)
    private String commentStr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Product_ID", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "User_ID", nullable = false)
    private User user;
}