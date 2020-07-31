package com.hlwd.controller;


import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

public class PlainController {
    public static final String SESSION_COOKIE_NAME = "JSESSIONID";
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    public PlainController() {
    }

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public HttpSession getSession() {
        return this.request.getSession();
    }

    public String getClientId() {
        Cookie[] cookie = this.request.getCookies();

        Cookie cook;
        for(int i = 0; i < cookie.length; ++i) {
            cook = cookie[i];
            if (cook.getName().equalsIgnoreCase("JSESSIONID") && cook != null && !"".equals(cook.getValue())) {
                return cook.getValue();
            }
        }

        String clientId = UUID.randomUUID().toString();
        cook = new Cookie("JSESSIONID", clientId);
        cook.setMaxAge(-1);
        this.response.addCookie(cook);
        return clientId;
    }
}
