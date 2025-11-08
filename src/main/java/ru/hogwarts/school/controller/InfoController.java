package ru.hogwarts.school.controller;

import org.hibernate.mapping.Array;
import org.hibernate.mapping.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@RestController
public class InfoController {
    @Value("${server.port}")
    String port;

    Logger logger = LoggerFactory.getLogger(InfoController.class);

    @GetMapping("/getPort")
    public ResponseEntity<String> getPort (){
        return ResponseEntity.ok(port);
    }

    @GetMapping("/calculate")
    public ResponseEntity<Long> calculate (){
        StopWatch stopWatch = new StopWatch("calculate");
        stopWatch.start();
        long sum = Stream.iterate(1L, a -> a + 1L).limit(1_000_000)
                .reduce(0L, Long::sum);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        return ResponseEntity.ok(sum);
    }

    @GetMapping("/calculateMyVariant")
    public ResponseEntity<Long> calculateMyVariant (){
        StopWatch stopWatch = new StopWatch("calculateMy");
        stopWatch.start();
        long sum = LongStream.rangeClosed(1,1000000).sum();
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        return ResponseEntity.ok(sum);
    }
}
