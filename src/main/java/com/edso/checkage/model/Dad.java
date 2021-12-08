package com.edso.checkage.model;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class Dad extends Thread {

    private Data data;
    private CountDownLatch latch;
    private String name;
    private Integer age;

    public Dad(Data data, CountDownLatch latch, String name, Integer age) {
        this.data = data;
        this.latch = latch;
        this.name = name;
        this.age = age;
    }

    @Override
    public void run() {
        boolean flag = false;
        log.info("d start" + name);
        try {
            Thread.sleep(1000);
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
        log.info("d stop" + name);
    }

    private int readFile() throws IOException {
        int age = 0;
        FileReader frd = null;
        BufferedReader bufR = null;

        try {
            frd = new FileReader("src/main/resources/file/" + name + "dad.txt");
            bufR = new BufferedReader(frd);
            String line;
            while ((line = bufR.readLine()) != null) {
                age = Integer.parseInt(line);
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

        return age;
    }

    private boolean checkAge(int age) {
        return age == this.age;
    }
}

