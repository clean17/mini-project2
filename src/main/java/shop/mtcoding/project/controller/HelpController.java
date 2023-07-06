package shop.mtcoding.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelpController {


    @GetMapping("/help")
    public String help() {
        return "help/help";
    }

    @GetMapping("/help/inquiryForm")
    public String inquiryForm() {
        return "help/inquiryForm";
    }

    @GetMapping("/help/user01Form")
    public String user01Form() {
        return "help/user01Form";
    }

    @GetMapping("/help/user02Form")
    public String user02Form() {
        return "help/user02Form";
    }

    @GetMapping("/help/user03Form")
    public String user03Form() {
        return "help/user03Form";
    }

    @GetMapping("/help/user04Form")
    public String user04Form() {
        return "help/user04Form";
    }

    @GetMapping("/help/user05Form")
    public String user05Form() {
        return "help/user05Form";
    }

    @GetMapping("/help/job01Form")
    public String job01Form() {
        return "help/job01Form";
    }

    @GetMapping("/help/job02Form")
    public String job02Form() {
        return "help/job02Form";
    }

    @GetMapping("/help/job03Form")
    public String job03Form() {
        return "help/job03Form";
    }

    @GetMapping("/help/job04Form")
    public String job04Form() {
        return "help/job04Form";
    }

    @GetMapping("/help/job05Form")
    public String job05Form() {
        return "help/job05Form";
    }
}
