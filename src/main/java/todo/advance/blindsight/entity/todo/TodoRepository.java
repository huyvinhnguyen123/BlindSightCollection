package todo.advance.blindsight.entity.todo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<Todo, String> {
    @Query(value = "SELECT * FROM todo t WHERE t.sub_card_id = ?1", nativeQuery = true)
    Iterable<Todo> findTodoInSubCard(String todoId);
}
