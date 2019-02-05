package com.acc.awssns;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value="/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @GetMapping(value="/send")
    public int getAllRecord()
    {
       return mailService.sendMail();
    }


}
