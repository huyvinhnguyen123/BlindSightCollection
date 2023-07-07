package todo.advance.blindsight.entity.todo;

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
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v2/cards")
public class TodoController {
    private final TodoService todoService;

    @GetMapping("/subcard-{subCardId}/todos")
    public ResponseEntity<Iterable<Todo>> findAllTodo(@PathVariable String subCardId) {
        log.info(CRUDLogger.REQUEST_SELECT("todos"));
        return new ResponseEntity<>(todoService.findAllTodo(subCardId), HttpStatus.FOUND);
    }

    @PostMapping("/subcard-{subCardId}/todos/create")
    public ResponseEntity<Todo> createTodo(@PathVariable String subCardId, @RequestBody Todo newTodo) {
        log.info(CRUDLogger.REQUEST_CREATE("todo"));
        return new ResponseEntity<>(todoService.addTodo(subCardId, newTodo), HttpStatus.CREATED);
    }

    @PutMapping("/subcard/todos/update")
    public ResponseEntity<Boolean> updateTodo(@RequestParam String todoId, @RequestBody Todo newTodo) {
        log.info(CRUDLogger.REQUEST_UPDATE("todo"));
        return new ResponseEntity<>(todoService.updateTodo(todoId, newTodo), HttpStatus.CREATED);
    }

    @DeleteMapping("/subcard/todos/delete")
    public ResponseEntity<Boolean> deleteTodo(@RequestParam String todoId) {
        log.info(CRUDLogger.REQUEST_UPDATE("todo"));
        return new ResponseEntity<>(todoService.deleteTodo(todoId), HttpStatus.NO_CONTENT);
    }
}
