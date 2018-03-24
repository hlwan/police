package com.task.police.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping({"/index","/"})
    public String index(){
        return "redirect:/dashboard/account/list";
    }

}
