package com.crihexe.hiddenviewsmind;

import com.crihexe.hiddenviewsmind.publisher.ContentPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private final ContentPublisher cp;

    @Autowired
    public TestController(ContentPublisher cp) {
        this.cp = cp;
    }

    @GetMapping
    public String callTest() {
        cp.testPublishImagePost();
        return "done";
    }


}
