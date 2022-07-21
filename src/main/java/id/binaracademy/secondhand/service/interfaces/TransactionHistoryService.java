package id.binaracademy.secondhand.service.interfaces;

import id.binaracademy.secondhand.dto.TransactionHistoryDto;
import id.binaracademy.secondhand.entity.TransactionHistory;

import java.text.ParseException;


public interface TransactionHistoryService {
    TransactionHistory saveTransac(TransactionHistoryDto transactionHistoryDto);
    TransactionHistory findTransactionHistoryById(Long id) throws ParseException;
}
