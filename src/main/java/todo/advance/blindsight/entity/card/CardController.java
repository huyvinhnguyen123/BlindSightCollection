package todo.advance.blindsight.entity.card;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import todo.advance.blindsight.util.log.CRUDLogger;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v2/cards")
public class CardController {
    private final CardService cardService;
//===================================================================================================================================
    // & USER SECTION
    /**
     * * find all card
     * 
     * @return
     */
    @GetMapping("")
    public ResponseEntity<Iterable<Card>> findAllCard() {
        log.info(CRUDLogger.REQUEST_SELECT("Card"));
        Iterable<Card> findAllCard = cardService.findAllCard();
        return new ResponseEntity<>(findAllCard, HttpStatus.FOUND);
    }

    /**
     * * find card by id
     * 
     * @param cardId
     * @return
     */
    @GetMapping("/{cardId}/card")
    public ResponseEntity<Card> findCardById(@PathVariable String cardId) {
        log.info(CRUDLogger.REQUEST_SELECT("Card"));
        Card findCardById = cardService.findCardById(cardId);
        return new ResponseEntity<>(findCardById, HttpStatus.FOUND);
    }

    /**
     * * request create card
     * 
     * @param card
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<Card> createCard(@RequestBody Card card) {
        log.info(CRUDLogger.REQUEST_CREATE("Card"));
        Card createCard = cardService.createCard(card);
        return new ResponseEntity<>(createCard, HttpStatus.CREATED);
    }

    /**
     * * request update card
     * 
     * @param card
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<Boolean> updateCard(@RequestBody Card card) {
        log.info(CRUDLogger.REQUEST_UPDATE("Card"));
        boolean isUpdateCard = cardService.updateCard(card);
        return new ResponseEntity<>(isUpdateCard, HttpStatus.CREATED);
    }

    /**
     * * request delete card
     * 
     * @param cardId
     * @return
     */
    @DeleteMapping("{cardId}/delete")
    public ResponseEntity<Boolean> deleteCard(@PathVariable String cardId) {
        log.info(CRUDLogger.REQUEST_DELETE("Card"));
        boolean isDeleteCard = cardService.deleteCard(cardId);
        return new ResponseEntity<>(isDeleteCard, HttpStatus.NO_CONTENT);
    }
}
