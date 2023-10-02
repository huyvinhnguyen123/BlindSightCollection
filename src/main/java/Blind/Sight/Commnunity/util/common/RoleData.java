package Blind.Sight.Commnunity.util.common;

public enum RoleData {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String role;

    RoleData(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
