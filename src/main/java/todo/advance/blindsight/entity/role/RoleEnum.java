package todo.advance.blindsight.entity.role;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ADMIN(1, "admin"),
    OWNER(2, "owner"),
    LEADER(3, "leader"),
    EMPLOYEE(4, "employee"),
    MAINTAINER(5, "maintainer"),
    REPORTER(6, "reporter"),
    GUEST(7, "guest");

    private int code;
    private String role;

    RoleEnum(int code, String role) {
        this.code = code;
        this.role = role;
    }
}
