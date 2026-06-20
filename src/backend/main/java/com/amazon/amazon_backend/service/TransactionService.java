package com.amazon.amazon_backend.service;

import com.amazon.amazon_backend.dto.TransactionResponse;
import com.amazon.amazon_backend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.amazon.amazon_backend.utility.TransactionConverter.tranListToTranRespList;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository tranRepo;

    public List<TransactionResponse> getUserTransactions(Integer userId){
        return tranListToTranRespList(tranRepo.findTransactionByBuyerIdOrSellerId(userId, userId));
    }

    public List<TransactionResponse> getUserPurchaseTransactions(Integer userId){
        return tranListToTranRespList(tranRepo.findTransactionByBuyerId(userId));
    }

    public List<TransactionResponse> getUserSaleTransactions(Integer userId){
        return tranListToTranRespList(tranRepo.findTransactionBySellerId(userId));
    }

}
