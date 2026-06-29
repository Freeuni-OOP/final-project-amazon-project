package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.Order;
import com.amazon.amazon_backend.model.Transaction;
import com.amazon.amazon_backend.model.TransactionStatus;
import com.amazon.amazon_backend.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.lang.Math.max;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TransactionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TransactionRepository transactionRepository;

    private User buyer, seller;
    private Order order;
    private int userCounter = 0;

    @BeforeEach
    public void setUp(){
        User user1 = createNthUser(++userCounter);
        User user2 = createNthUser(++userCounter);

        buyer = entityManager.persist(user1);
        seller = entityManager.persist(user2);

        order = entityManager.persist(new Order(user1, new BigDecimal(5), LocalDateTime.now()));
    }

    @Test
    public void testFindByBuyerIdOrSellerId() {
        User userBoth = entityManager.persist(createNthUser(++userCounter));
        Order order1 = entityManager.persist(new Order(buyer, new BigDecimal(2), LocalDateTime.now()));
        Order order2 = entityManager.persist(new Order(userBoth, new BigDecimal(6), LocalDateTime.now()));

        Transaction tran1 = new Transaction(order1, buyer, userBoth, BigDecimal.TEN, TransactionStatus.PENDING);
        entityManager.persist(tran1);

        Transaction tran2 = new Transaction(order2, userBoth, seller, BigDecimal.ONE, TransactionStatus.PENDING);
        entityManager.persist(tran2);

        entityManager.flush();

        List<Transaction> result = transactionRepository.findByBuyerIdOrSellerId(userBoth.getId(), userBoth.getId());

        assertEquals(2, result.size());
    }

    @Test
    public void testFindByBuyerId() {
        Transaction t = new Transaction(order, buyer, seller, BigDecimal.TEN, TransactionStatus.PENDING);
        entityManager.persist(t);
        entityManager.flush();

        List<Transaction> purchases = transactionRepository.findByBuyerId(buyer.getId());

        assertFalse(purchases.isEmpty());
        assertEquals(buyer.getId(), purchases.get(0).getBuyer().getId());
    }

    @Test
    public void testFindBySellerId() {
        Transaction t = new Transaction(order, buyer, seller, BigDecimal.TEN, TransactionStatus.PENDING);
        entityManager.persist(t);
        entityManager.flush();

        List<Transaction> sales = transactionRepository.findBySellerId(seller.getId());

        assertFalse(sales.isEmpty());
        assertEquals(seller.getId(), sales.get(0).getSeller().getId());
    }

    private User createNthUser(Integer n){
        User user = new User();
        user.setUsername("user" + n);
        user.setEmail("user" + n + "@gmail.com");
        user.setPassEncrypted("password" + n);
        user.setBalance(BigDecimal.TEN.add(BigDecimal.valueOf(n)));
        int year = max(2026, 2000 + (2*n-1));
        int month = (1 + 2*n) % 12 + 1;
        int day = (10 + 2*n) % 28 + 1;
        user.setBirthDate(Date.valueOf(LocalDate.of(year, month, day)));
        return user;
    }

}