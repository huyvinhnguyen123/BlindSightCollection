package todo.advance.blindsight.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncrypt {
    
    /**
     * * BCrypt password encoder
     * 
     * @param password
     * @return
     */
    public static String bCryptpasswordEncoder(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
