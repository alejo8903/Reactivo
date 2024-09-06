package bancolombia.com.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class DemoController {

    @GetMapping("/api")
    public DemoEntidad getEcho() {
        return new DemoEntidad("Hello World!");
    }
}
