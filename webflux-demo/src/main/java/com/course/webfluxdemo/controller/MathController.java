package com.course.webfluxdemo.controller;

import com.course.webfluxdemo.dto.Response;
import com.course.webfluxdemo.services.MathService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("math")
@RequiredArgsConstructor
public class MathController {

    private final MathService service;

    @GetMapping("square/{input}")
    public Response findSquere(@PathVariable int input) {
        return this.service.findSquare(input);
    }

    @GetMapping("table/{input}")
    public List<Response> multiplicationTable(@PathVariable int input) {
        return this.service.multiplicationTable(input);
    }

}
