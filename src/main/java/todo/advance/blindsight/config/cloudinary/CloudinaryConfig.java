package todo.advance.blindsight.config.cloudinary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {
    private final String CLOUD_NAME = "djk3aom9c";
    private final String API_KEY = "172923496937475";
    private final String API_SECRET = "qj9pl_FlwJf9dA6wENmuOV_xo64";

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", CLOUD_NAME,
            "api_key", API_KEY,
            "api_secret", API_SECRET
        ));
    }
}
