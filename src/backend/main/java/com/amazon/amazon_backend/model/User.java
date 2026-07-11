package com.amazon.amazon_backend.model;

import com.amazon.amazon_backend.utility.PassEncryption;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class User{
    private static final BigDecimal DEFAULT_BALANCE = BigDecimal.valueOf(1000.00);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Email", nullable = false, length = 150)
    private String email;

    @Builder.Default
    @Column(name = "Balance", nullable = false)
    private BigDecimal balance = DEFAULT_BALANCE;

    @Column(name = "Gender", length = 10)
    private String gender;

    @Column(name = "Birth_Date", nullable = false)
    private Date birthDate;

    @Column(name = "User_Name", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "Pass_Encrypted", nullable = false, length = 500)
    private String passEncrypted;

    @OneToMany(mappedBy = "user")
    private List<CartItem> cartItems;

    public User(String username, String email, String password, String gender, Date birthDate) {
        this.username = username;
        this.email = email;
        this.passEncrypted = PassEncryption.hashPassword(password);
        this.gender = gender;
        this.birthDate = birthDate;
        this.balance = DEFAULT_BALANCE;
    }

}
