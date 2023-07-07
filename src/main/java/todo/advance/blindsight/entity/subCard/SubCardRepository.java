package todo.advance.blindsight.entity.subCard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SubCardRepository extends CrudRepository<SubCard, String> {
    @Query(value = "SELECT * FROM sub_card sc WHERE sc.card_id = ?1", nativeQuery = true)
    Iterable<SubCard> findSubCardInCard(String cardId);
}
