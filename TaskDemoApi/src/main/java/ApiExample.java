import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import service.UserService;

import java.util.List;

public class ApiExample {
    public static void main(String[] args) {
        UserService userService = new UserService();
        userService.inWork();
        System.out.println(userService.getKey());
    }
}
