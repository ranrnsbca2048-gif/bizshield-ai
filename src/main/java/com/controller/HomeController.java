package com.bizshield_ai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    @ResponseBody
    public String home() {
        return """
                <html>
                    <head>
                        <title>BizShield AI</title>
                    </head>
                    <body>
                        <h1>Welcome to BizShield AI!</h1>
                        <p>Your business management and security platform.</p>
                    </body>
                </html>
                """;
    }
}