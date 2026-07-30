package com.example.gridjsdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrameworkDemoController {

    @GetMapping({"/demos/react", "/demos/react/"})
    public String react() {
        return "forward:/demos/react/index.html";
    }

    @GetMapping({"/demos/vue", "/demos/vue/"})
    public String vue() {
        return "forward:/demos/vue/index.html";
    }

    @GetMapping({"/demos/angular", "/demos/angular/"})
    public String angular() {
        return "forward:/demos/angular/index.html";
    }

    @GetMapping({"/demos/npm", "/demos/npm/"})
    public String npm() {
        return "forward:/demos/npm/index.html";
    }

    @GetMapping({
            "/demos/react/content/img/updating.gif",
            "/demos/vue/content/img/updating.gif",
            "/demos/angular/content/img/updating.gif",
            "/demos/npm/content/img/updating.gif"
    })
    public String updatingGif() {
        return "forward:/content/img/updating.gif";
    }

    @GetMapping({
            "/demos/react/content/img/loading.gif",
            "/demos/vue/content/img/loading.gif",
            "/demos/angular/content/img/loading.gif",
            "/demos/npm/content/img/loading.gif"
    })
    public String loadingGif() {
        return "forward:/content/img/loading.gif";
    }
}
