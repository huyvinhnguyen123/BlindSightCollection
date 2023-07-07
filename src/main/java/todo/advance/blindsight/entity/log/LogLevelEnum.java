package todo.advance.blindsight.entity.log;

import lombok.Getter;

@Getter
public enum LogLevelEnum {
    DEBUG(1, "DEBUG"),
    INFO(2, "INFO"),
    WARN(3, "WARN"),
    ERROR(4, "ERROR"),
    TRACE(5, "TRACE");

    private int code;
    private String level;

    LogLevelEnum(int code, String level) {
        this.code = code;
        this.level = level;
    }
}