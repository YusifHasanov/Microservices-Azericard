package org.azerciard.paymentservice.repository;

import jakarta.transaction.Transactional;
import org.azerciard.paymentservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    List<Payment> findAllByUserId(String userId);
}
