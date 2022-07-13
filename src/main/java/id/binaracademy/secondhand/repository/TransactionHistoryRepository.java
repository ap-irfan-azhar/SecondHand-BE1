package id.binaracademy.secondhand.repository;

import id.binaracademy.secondhand.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
}