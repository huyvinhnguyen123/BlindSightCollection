package todo.advance.blindsight.entity.card;

import java.time.Instant;
import java.time.ZoneId;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import todo.advance.blindsight.entity.log.LogService;
import todo.advance.blindsight.util.custom.exception.NotFoundException;
import todo.advance.blindsight.util.log.CRUDLogger;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final LogService logService;

    private static final String CARD_NAME = "Card Title";
    private static final String CARD_IMAGE = "Card Image";
//===================================================================================================================================
    // & Main Section
    /**
     * * find all cards
     * 
     * @return
     */
    public Iterable<Card> findAllCard() {
        Iterable<Card> cards = cardRepository.findAll();
        log.info(CRUDLogger.SELECT_ALL_SUCCESS("card"));
        return cards;
    }

    /**
     * * find card by id
     * 
     * @param cardId
     * @return
     */
    public Card findCardById(String cardId) {
        Card existingCard = cardRepository.findById(cardId).orElseThrow(() -> {
            log.error(CRUDLogger.SEARCH_ONE_FAIL(cardId));
            log.trace("id must be unique & same with one in database");
            logService.saveErrorLog("card with id does not exist: " + cardId);

            return new NotFoundException("Not found this card: " + cardId);
        });
        log.info(CRUDLogger.SELECT_ONE_SUCCESS("card"));
        return existingCard;
    }

    /**
     * * create new card
     * 
     * @param newCard
     * @return
     */
    public Card createCard(Card newCard) {
        newCard.setCardName(newCard.getCardName());
        if(newCard.getCardName().equals("") || newCard.getCardName() == null){
            newCard.setCardName(CARD_NAME);
        } else {
            newCard.setCardName(newCard.getCardName());
        }

        newCard.setCardImage(newCard.getCardImage());
        if(newCard.getCardImage().equals("") || newCard.getCardImage() == null){
            newCard.setCardImage(CARD_IMAGE);
        } else {
            newCard.setCardImage(newCard.getCardImage());
        }

        cardRepository.save(newCard);
        log.info(CRUDLogger.CREATE_SUCCESS("card"));

        return newCard;
    }

    /**
     * * update card
     * 
     * @param updatedCard
     * @return
     */
    public Boolean updateCard(Card updatedCard) {
        Card existingCard = findCardById(updatedCard.getCardId());
        
        existingCard.setCardName(updatedCard.getCardName());
        existingCard.setCardImage(updatedCard.getCardImage());
        existingCard.setLastModifiedDate(Instant.now().atZone(ZoneId.systemDefault()));

        cardRepository.save(existingCard);
        log.info(CRUDLogger.UPDATE_SUCCESS("card"));
        return true;
    }

    /**
     * * delete card
     * 
     * @param cardId
     * @return
     */
    public Boolean deleteCard(String cardId) {
        Card existingCard = findCardById(cardId);
        cardRepository.delete(existingCard);
        return true;
    }
}
