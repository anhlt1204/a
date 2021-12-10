package com.edso.checkage.model;

import com.edso.checkage.service.CheckService;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class Mom extends Thread {

    private Data data;
    private CountDownLatch latch;
    private String name;
    private Integer age;

    public Mom(Data data, CountDownLatch latch, String name, Integer age) {
        this.data = data;
        this.latch = latch;
        this.name = name;
        this.age = age;
    }

    @Override
    public void run() {
        boolean flag = false;
        log.info("m start " + name);
        try {
            Thread.sleep(5000);
            if (checkAge(readFile())) {
                flag = true;
                data.setCount(data.getCount() + 1);
                if (data.getCount() >= 2) {
                    latch.countDown();
                }
                latch.countDown();
            }
            if (!flag) {
                latch.countDown();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        log.info("m stop " + name);
    }

    private int readFile() throws IOException {
        int year = 0;
        FileReader frd;
        BufferedReader bufR = null;

        try {
            frd = new FileReader("src/main/resources/file/" + name +"mom.txt");
            bufR = new BufferedReader(frd);
            String line;
            while ((line = bufR.readLine()) != null) {
                year = Integer.parseInt(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufR != null) {
                    bufR.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return year;
    }

    private boolean checkAge(int year) {
        Calendar instance = Calendar.getInstance();
        int now = instance.get(Calendar.YEAR);
        return now - year == age;
    }
}