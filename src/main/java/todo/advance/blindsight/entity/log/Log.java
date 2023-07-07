package todo.advance.blindsight.entity.log;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import todo.advance.blindsight.util.generate.date.DateGenerator;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="logs") // specify the collection name in MongoDB
public class Log {
    @Id
    private String id = "LOG-" + UUID.randomUUID().toString();
    private String message;
    private String level;

    // @Indexed(name="userInteract", unique = true)
    // private String username;

    @Indexed(name = "logTimeIndex", expireAfterSeconds = 3600) // automatically delete record in 1 hour
    private Date logTime = Date.from(Instant.now());
    private String localTime = DateGenerator.getCurrentDateDetails();
}
