package todo.advance.blindsight.util.custom.exception;

public class NotFoundException extends org.springframework.security.core.AuthenticationException  {

    public NotFoundException(String msg) {
        super(msg);
    }
  
}
