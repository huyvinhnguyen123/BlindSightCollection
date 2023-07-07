package todo.advance.blindsight.entity.subCard;

import java.time.Instant;
import java.time.ZoneId;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import todo.advance.blindsight.entity.card.Card;
import todo.advance.blindsight.entity.card.CardRepository;
import todo.advance.blindsight.entity.log.LogService;
import todo.advance.blindsight.util.custom.exception.NotFoundException;
import todo.advance.blindsight.util.log.CRUDLogger;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubCardService {
    private final SubCardRepository subCardRepository;
    private final CardRepository cardRepository;
    private final LogService logService;

    private static final String SUB_CARD_NAME = "Subcard title";
    private static final String SUB_CARD_DESC = "Subcard description";
    private static final String SUB_CARD_DUE_DATE = "Subcard due date";
//===================================================================================================================================
    // & Main Section
    /**
     * * find all subcard
     * 
     * @return
     */
    public Iterable<SubCard> findAllSubCardInCard(String cardId) {
        Iterable<SubCard> subCards = subCardRepository.findSubCardInCard(cardId);
        log.info(CRUDLogger.SELECT_ALL_SUCCESS("subcard"));
        return subCards;
    }
    
    /**
     * * find subcard by id
     * 
     * @param cardId
     * @param subCardId
     * @return
     */
    public SubCard findSubCardByIdInCard(String cardId,  String subCardId) {
        Card existingCard = cardRepository.findById(cardId).orElseThrow(() -> {
            log.error(CRUDLogger.SEARCH_ONE_FAIL(cardId));
            log.trace("id must be unique & same with one in database");
            logService.saveErrorLog("card with id does not exist: " + cardId);

            return new NotFoundException("Not found this card: " + cardId);
        });
        log.info(CRUDLogger.SELECT_ONE_SUCCESS("card"));

        SubCard existingSubCard = new SubCard();
        for(SubCard sc: existingCard.getSubCards()) {
            if(sc.getSubCardId().equals(subCardId)) {
                existingSubCard.setCard(existingCard);
                existingSubCard.setSubCardId(sc.getSubCardId());
                existingSubCard.setSubCardName(sc.getSubCardName());
                existingSubCard.setSubCardDescription(sc.getSubCardDescription());
                existingSubCard.setSubCardTimeUpload(sc.getSubCardTimeUpload());
                existingSubCard.setSubCardDueDate(sc.getSubCardDueDate());
                log.info(CRUDLogger.SELECT_ONE_SUCCESS("subcard"));
            } else {
                log.error(CRUDLogger.SEARCH_ONE_FAIL(cardId));
                log.trace("id must be unique & same with one in database");
                logService.saveErrorLog("subcard with id does not exist: " + subCardId);
            }
        }
        
        return existingSubCard;
    }

    /**
     * * add subcard in card
     * 
     * @param cardId
     * @param newSubCard
     * @return
     */
    public SubCard addSubCard(String cardId,  SubCard newSubCard) {
        Card existingCard = cardRepository.findById(cardId).orElseThrow(() -> {
            log.error(CRUDLogger.SEARCH_ONE_FAIL(cardId));
            log.trace("id must be unique & same with one in database");
            logService.saveErrorLog("card with id does not exist: " + cardId);

            return new NotFoundException("Not found this card: " + cardId);
        });
        log.info(CRUDLogger.SELECT_ONE_SUCCESS("card"));

        newSubCard.setCard(existingCard);

        newSubCard.setSubCardName(newSubCard.getSubCardName());
        if(newSubCard.getSubCardName().equals("") || newSubCard.getSubCardName() == null) {
            newSubCard.setSubCardName(SUB_CARD_NAME);
        }

        newSubCard.setSubCardDescription(newSubCard.getSubCardDescription());
        if(newSubCard.getSubCardDescription().equals("") || newSubCard.getSubCardDescription() == null) {
            newSubCard.setSubCardDescription(SUB_CARD_DESC);
        }

        newSubCard.setSubCardDueDate(newSubCard.getSubCardDueDate());
        if(newSubCard.getSubCardDueDate().equals("") || newSubCard.getSubCardDueDate() == null) {
            newSubCard.setSubCardDueDate(SUB_CARD_DUE_DATE);
        }

        subCardRepository.save(newSubCard);
        log.info(CRUDLogger.CREATE_SUCCESS("subcard"));

        return newSubCard;
    }

    /**
     * * update subcard in card
     * 
     * @param cardId
     * @param updateSubCard
     * @return
     */
    public Boolean updateSubCard(String cardId, SubCard updateSubCard) {
        SubCard existingSubCard = findSubCardByIdInCard(cardId, updateSubCard.getSubCardId());

        existingSubCard.setSubCardName(updateSubCard.getSubCardName());
        existingSubCard.setSubCardDescription(updateSubCard.getSubCardDescription());
        existingSubCard.setSubCardDueDate(updateSubCard.getSubCardDueDate());
        existingSubCard.setLastModifiedDate(Instant.now().atZone(ZoneId.systemDefault()));

        subCardRepository.save(existingSubCard);
        log.info(CRUDLogger.UPDATE_SUCCESS("subcard"));
        return true;
    }

    /**
     * * delete subcard in card
     * 
     * @param cardId
     * @param subCardId
     * @return
     */
    public Boolean deleteSubCard(String cardId, String subCardId) {
        SubCard existingSubCard = findSubCardByIdInCard(cardId, subCardId);
        subCardRepository.deleteById(existingSubCard.getSubCardId());
        log.info(CRUDLogger.DELETE_SUCCESS("subcard"));
        return true;
    }
}
