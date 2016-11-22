package com.bluexiii.jwh.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String username = (userDetails == null) ? "guest" : userDetails.getUsername();
        model.addAttribute("username", username);
        //return "redirect:/login";
        return "home";
    }
}