package faang.school.accountservice.client;

import faang.school.accountservice.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${user-service.host}:${user-service.port}")
public interface UserServiceClient {
    @GetMapping("/{userId}")
    User getUser(@PathVariable("userId") long userId);
}
