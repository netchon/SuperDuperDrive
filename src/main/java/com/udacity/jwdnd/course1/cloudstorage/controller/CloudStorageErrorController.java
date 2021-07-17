package com.udacity.jwdnd.course1.cloudstorage.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;


@Controller
public class CloudStorageErrorController implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(CloudStorageErrorController.class);

    @Override
    public String getErrorPath() {
        logger.info("setting up the path for error pages");
        return "/error";
    }

    @GetMapping("/error")
    public String handleError(HttpServletRequest httpServletRequest){

        Object status = httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(status != null){

            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "404";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "500";
            }
        }
        return "error";
    }

}
