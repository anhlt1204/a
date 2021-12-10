package com.edso.checkage.service;

import com.edso.checkage.model.Dad;
import com.edso.checkage.model.Data;
import com.edso.checkage.model.Mom;
import com.edso.checkage.model.Ubnd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
public class CheckService {

    public boolean runCheck(String name, Integer age) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);
        Data data = new Data();

        Dad dad = new Dad(data, latch, name, age);
        Mom mom = new Mom(data, latch, name, age);
        Ubnd ubnd = new Ubnd(data, latch, name, age);

        dad.start();
        mom.start();
        ubnd.start();

        latch.await();

        boolean checkAge = false;

        if (data.getCount() >= 2)
            checkAge = true;

        data.setCount(0);

        write(name, checkAge);
        log.info("Check success");
        return checkAge;
    }

    public static void write(String name, boolean check) {
        log.info("Write start");
        try {
            FileWriter fw = new FileWriter(name + "result.txt");
            fw.write(String.valueOf(check));
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        log.info("Write success");
    }
}
