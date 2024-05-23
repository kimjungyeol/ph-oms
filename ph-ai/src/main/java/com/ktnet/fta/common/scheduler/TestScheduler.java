package com.ktnet.fta.common.scheduler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestScheduler {

    //@Scheduled(fixedDelay = 3000, initialDelay = 5000)
    public void testPrintRepeat1() throws InterruptedException {
        DateTimeFormatter dtf =  DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        System.out.println("스케쥴러 테스트(fixedDelay) " + LocalDateTime.now().format(dtf));
    }
}