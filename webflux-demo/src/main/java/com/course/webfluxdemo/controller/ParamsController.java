package com.course.webfluxdemo.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("jobs")
public class ParamsController {


    @GetMapping("search")
    public Flux<Integer> searchJobs(@RequestParam("count") int count,
                                    @RequestParam("page") int page) {

        log.info("count: {}, page: {}", count, page);
        return Flux.just(count, page);
    }

}
