package com.bra.common.time;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by DDT on 2016/5/30.
 */
@Service
@Lazy(false)
public class OutOnTime {
    public static long num =0 ;
    public static int index =0;
    @Scheduled(cron = "* 55 * * * ?")
    public void job1() {
        index++;
        System.out.println("*******************"+index+"---"+num+"---"+new Date());
//        MyThread t = new MyThread();
//        t.start();
//        if(OutOnTime.index%4==0){
//
//        }else{
//            for(int i=0;i<1000000000;i++){
//                for(int j=0;j<100;j++){
//                    OutOnTime.num++;
//                }
//            }
//        }
        System.out.println("out on time the"+index+"---"+num+"---"+new Date());
    }
}

class MyThread extends Thread {
    private static int t = 0;
    public void run(){
        t++;
        if(OutOnTime.index%4==0){

        }else{
            for(int i=0;i<1000000000;i++){
                for(int j=0;j<100;j++){
                    OutOnTime.num++;
                }
            }
        }
        System.out.println("-------"+t+"-------"+OutOnTime.index+"---"+OutOnTime.num+"---"+new Date());
    }
}