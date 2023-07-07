package todo.advance.blindsight.entity.todo;

import java.time.Instant;
import java.time.ZoneId;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import todo.advance.blindsight.entity.log.LogService;
import todo.advance.blindsight.entity.subCard.SubCard;
import todo.advance.blindsight.entity.subCard.SubCardRepository;
import todo.advance.blindsight.util.custom.exception.NotFoundException;
import todo.advance.blindsight.util.generate.code.CodeGenerator;
import todo.advance.blindsight.util.log.CRUDLogger;

@Service
@Slf4j
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final SubCardRepository subCardRepository;
    private final LogService logService;
//===================================================================================================================================
    // & Main Section
    /**
     * * find all todo
     * 
     * @return
     */
    public Iterable<Todo> findAllTodo(String subCardId) {
        Iterable<Todo> todos = todoRepository.findTodoInSubCard(subCardId);
        log.info(CRUDLogger.SELECT_ALL_SUCCESS("todo"));
        return todos;
    }

    /**
     * * create todo
     * 
     * @param subCardId
     * @param newTodo
     * @return
     */
    @Transactional
    public Todo addTodo(String subCardId, Todo newTodo) {
        SubCard subCard = subCardRepository.findById(subCardId).orElseThrow(() -> {
            log.error(CRUDLogger.SEARCH_ONE_FAIL(subCardId));
            log.trace("id must be unique & same with one in database");
            logService.saveErrorLog("subcard with id does not exist: " + subCardId);

            return new NotFoundException("Not found this subcard: " + subCardId);
        });
        log.info(CRUDLogger.SELECT_ONE_SUCCESS("card"));

        newTodo.setSubCard(subCard);
        newTodo.setTodoId(CodeGenerator.generateRandomCodeWithoutSpecialCharacters(5));
        newTodo.setDone(false);
        todoRepository.save(newTodo);
        return newTodo;
    }

    /**
     * * update todo
     * 
     * @param todoId
     * @param updateTodo
     * @return
     */
    @Transactional
    public Boolean updateTodo(String todoId, Todo updateTodo) {
        Todo existingTodo = todoRepository.findById(todoId).orElseThrow(() -> {
            log.error(CRUDLogger.SEARCH_ONE_FAIL(todoId));
            log.trace("id must be unique & same with one in database");
            logService.saveErrorLog("todo with id does not exist: " + todoId);

            return new NotFoundException("Not found this todo: " + todoId);
        });
        log.info(CRUDLogger.SELECT_ONE_SUCCESS("todo"));

        existingTodo.setTodoDescription(updateTodo.getTodoDescription());
        existingTodo.setDone(updateTodo.isDone());
        existingTodo.setLastModifiedDate(Instant.now().atZone(ZoneId.systemDefault()));

        todoRepository.save(existingTodo);

        return true;
    }

    /**
     * * delete todo
     * 
     * @param todoId
     * @return
     */
    @Transactional
    public Boolean deleteTodo(String todoId) {;
        todoRepository.deleteById(todoId);
        log.info(CRUDLogger.DELETE_SUCCESS("todo"));
        return true;
    }
}
