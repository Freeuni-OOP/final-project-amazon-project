package com.amazon.amazon_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Email", nullable = false, length = 150)
    private String email;

    @Column(name = "Balance", nullable = false)
    private BigDecimal balance = BigDecimal.valueOf(1000.00);

    @Column(name = "Gender", length = 10)
    private String gender;

    @Column(name = "Birth_Date")
    private LocalDate birthDate;

    @Column(name = "UserName", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "Pass_Encrypted", nullable = false, length = 500)
    private String passEncrypted;

    @OneToOne(mappedBy = "user")
    private Cart cart;
}
