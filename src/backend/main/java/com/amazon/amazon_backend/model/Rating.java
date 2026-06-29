package com.amazon.amazon_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="Ratings")
@NoArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Rating_ID")
    private Integer rating_Id;

    @ManyToOne
    @JoinColumn(name="User_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="Product_ID", nullable = false)
    private Product product;

    @Column(name="Stars")
    private Integer stars;

    @Column(name="Created_At")
    private LocalDateTime created_At;

    public Rating(User user,
                  Product product,
                  Integer stars,
                  LocalDateTime created_At
                  ){
        this.user=user;
        this.product=product;
        this.stars=stars;
        this.created_At=created_At;
    }

}
