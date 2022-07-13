package id.binaracademy.secondhand.service.impl;

import id.binaracademy.secondhand.dto.TransactionHistoryDto;
import id.binaracademy.secondhand.entity.TransactionHistory;
import id.binaracademy.secondhand.repository.TransactionHistoryRepository;
import id.binaracademy.secondhand.service.interfaces.TransactionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

@Service
public class TransactionHistoryImpl implements TransactionHistoryService {
    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;
    @Override
    public TransactionHistory saveTransac(TransactionHistoryDto transactionHistoryDto) {
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setPrice(transactionHistoryDto.getPrice());
        transactionHistory.setBuyerId(transactionHistoryDto.getBuyerId());
        transactionHistory.setSellerId(transactionHistoryDto.getSellerId());
        transactionHistory.setProductId(transactionHistoryDto.getProductId());
        transactionHistory.setDateSold(transactionHistoryDto.getDateSold());
        return transactionHistoryRepository.save(transactionHistory);
    }

    @Override
    public TransactionHistory findTransactionHistoryById(Long id) throws ParseException {
        Optional<TransactionHistory> transactionHistory = transactionHistoryRepository.findById(id);
        if (!transactionHistory.isPresent()) {
            throw new IllegalArgumentException(
                    String.format("TransactionHistory with id %s not found", id)
            );
        }
        TransactionHistory transactionHistory1 = transactionHistory.get();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        Date dateSold = sdf.parse(transactionHistory1.getDateSold().toString());
        System.out.println(dateSold);
        transactionHistory1.setDateSold(dateSold);
        System.out.println(transactionHistory1.getDateSold());
        return transactionHistory1;
    }
}
