package todo.advance.blindsight.entity.mail;

import java.util.List;

import lombok.Data;

@Data
public class EmailDetail {
    private String recipient;
	private String msgBody;
	private String subject;
	private String attachment;

    private List<String> recipients;
}
