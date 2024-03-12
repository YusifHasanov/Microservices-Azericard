package org.azerciard.cardservice.repository;

import jakarta.transaction.Transactional;
import org.azerciard.cardservice.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CardRepository extends JpaRepository<Card, String> {

    Boolean existsByCardNumberAndCvv(String cardNumber,String cvv);
    List<Card> findAllByUserId(String userId);
    Optional<Card> findByCardNumberAndCvv(String cardNumber,String cvv);

    List<Card> findAllByUserIdAndIdIn(String userId, List<String> cardIds);
}
