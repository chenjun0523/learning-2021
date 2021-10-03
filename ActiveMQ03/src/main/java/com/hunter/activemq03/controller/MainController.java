package com.hunter.activemq03.controller;

import com.hunter.activemq03.response.ResponseUtil;
import com.hunter.activemq03.service.SenderService;
import com.hunter.activemq03.service.SenderServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {


/*    @Autowired
    private SenderService senderService;*/

    @Autowired
    private SenderServiceV2 senderServiceV2;

/*    @RequestMapping("/send")
    public String send() {

        senderService.sendStringQueue("user", "hello");

        return ResponseUtil.SUCCESS;
    }*/

    @RequestMapping("/send2")
    public String send2() {

        senderServiceV2.send("user", "hello");

        return ResponseUtil.SUCCESS;
    }

}
