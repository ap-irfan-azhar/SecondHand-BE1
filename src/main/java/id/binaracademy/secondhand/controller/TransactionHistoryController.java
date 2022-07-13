package id.binaracademy.secondhand.controller;

import id.binaracademy.secondhand.dto.TransactionHistoryDto;
import id.binaracademy.secondhand.entity.TransactionHistory;
import id.binaracademy.secondhand.service.interfaces.TransactionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/transaction-history")
public class TransactionHistoryController {
    @Autowired
    private TransactionHistoryService transactionHistoryService;

    @PostMapping("/save")
    public TransactionHistory saveTransac(TransactionHistoryDto transactionHistoryDto) {
        return transactionHistoryService.saveTransac(transactionHistoryDto);
    }

    @GetMapping("/{id}")
    public TransactionHistory findTransactionHistoryById(@PathVariable Long id) throws ParseException {
        return transactionHistoryService.findTransactionHistoryById(id);
    }

}
