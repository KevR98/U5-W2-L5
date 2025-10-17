package U5_W2_L5.Config;

import com.cloudinary.Cloudinary;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
//    public Cloudinary uploader(@Value("${cloudinary.name}") String cloudName,
//                               @Value("${cloudinary.key}") String apiKey,
//                               @Value("${cloudinary.secret}") String apiSecret) {
//        Map<String, String> config = new HashMap<>();
//        config.put("cloud_name", cloudName);
//        config.put("api_key", apiKey);
//        config.put("api_secret", apiSecret);
//        return new Cloudinary(config);
//    }

    // mi da errore
}
