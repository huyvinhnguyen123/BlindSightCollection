package todo.advance.blindsight.entity.subCard;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import todo.advance.blindsight.util.log.CRUDLogger;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v2/cards")
public class SubCardController {
    private final SubCardService subCardService;
//===================================================================================================================================
    // & USer Section
    /**
     * * add sub card
     * 
     * @param cardId
     * @param subCard
     * @return
     */
    @PostMapping("/{cardId}/create")
    public ResponseEntity<SubCard> addSubCard(@PathVariable String cardId, @RequestBody SubCard subCard) {
        log.info(CRUDLogger.REQUEST_CREATE("subcard"));
        SubCard newSubCard = subCardService.addSubCard(cardId, subCard);
        return new ResponseEntity<>(newSubCard, HttpStatus.CREATED);
    }

    /**
     * * find all subcard in card
     * 
     * @param cardId
     * @return
     */
    @GetMapping("/{cardId}/subcards")
    public ResponseEntity<Iterable<SubCard>> findAllSubCard(@PathVariable String cardId) {
        log.info(CRUDLogger.REQUEST_SELECT("subcards"));
        Iterable<SubCard> subCards = subCardService.findAllSubCardInCard(cardId);
        return new ResponseEntity<>(subCards, HttpStatus.FOUND);
    }

    /**
     * * find subcard by id in card
     * 
     * @param cardId
     * @param subCardId
     * @return
     */
    @GetMapping("/{cardId}/subcards/subcard")
    public ResponseEntity<SubCard> findSubCardByIdInCard(@PathVariable String cardId, @RequestParam String subCardId) {
        log.info(CRUDLogger.REQUEST_SELECT("subcard"));
        SubCard subCard = subCardService.findSubCardByIdInCard(cardId, subCardId);
        return new ResponseEntity<>(subCard, HttpStatus.FOUND);
    }
    
    /**
     * * update subcard in card
     * 
     * @param cardId
     * @param subCard
     * @return
     */
    @PutMapping("/{cardId}/subcards/subcard/update")
    public ResponseEntity<Boolean> updateSubCard(@PathVariable String cardId, @RequestBody SubCard subCard) {
        log.info(CRUDLogger.REQUEST_UPDATE("subcard"));
        Boolean updateSubCard = subCardService.updateSubCard(cardId, subCard);
        return new ResponseEntity<>(updateSubCard, HttpStatus.CREATED);
    }

    /**
     * * delete subcard in card
     * 
     * @param cardId
     * @param subCardId
     * @return
     */
    @DeleteMapping("/{cardId}/subcards/subcard/delete")
    public ResponseEntity<Boolean> deleteSubCard(@PathVariable String cardId, @RequestParam String subCardId) {
        log.info(CRUDLogger.REQUEST_DELETE("subcard"));
        Boolean subCard = subCardService.deleteSubCard(cardId, subCardId);
        return new ResponseEntity<>(subCard, HttpStatus.NO_CONTENT);
    }
}
