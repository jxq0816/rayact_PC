package com.bra.common.time;

import com.bra.modules.cms.entity.Activity;
import com.bra.modules.cms.entity.PostMain;
import com.bra.modules.cms.service.ActivityService;
import com.bra.modules.cms.service.PostMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by DDT on 2016/5/30.
 */
@Service
@Lazy(false)
public class OutOnTime {
    @Autowired
    private ActivityService activityService;
    @Autowired
    private PostMainService postMainService;
    public static long num =0 ;
    public static int index =0;
    @Scheduled(cron = "0 0/30 * * * ?")
    public void job1() {
        System.out.println("活动结束定时计算任务开始。"+new Date().toString());
        Activity activity = new Activity();
        activity.setIsAvaliable("1");
        List<Activity> list = activityService.findList(activity);
        if(list!=null&&list.size()>0)
        for(Activity activity1:list){
            Date end = activity1.getEndDate();
            if(new Date().after(end)){
                activity1.setIsAvaliable("0");
                activityService.save(activity1);
            }
        }
        System.out.println("活动结束定时计算任务结束。"+new Date().toString());
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
    }
    //计算帖子的权重
    @Scheduled(cron = "0 0/30 * * * ?")
    public void job2(){
        System.out.println("帖子权重定时计算任务开始。"+new Date().toString());
        PostMain p = new PostMain();
        p.getSqlMap().put("dsf"," and a.order_num > 0 ");
        List<PostMain> ps = postMainService.findList(p);
        for(PostMain postMain:ps){
            int order = postMain.getOrderNum();
            int now = order-1 > 0 ? order-1:0;
            postMain.setOrderNum(now);
            postMainService.save(postMain);
        }
        System.out.println("帖子权重定时计算任务结束。"+new Date().toString());
    }
}

class MyThread extends Thread {
    private static int t = 0;
    public void run(){
        t++;
        if(OutOnTime.index%4==0){

        }else{
            for(int i=0;i<100;i++){
                for(int j=0;j<100;j++){
                    OutOnTime.num++;
                }
            }
        }
        System.out.println("-------"+t+"-------"+OutOnTime.index+"---"+OutOnTime.num+"---"+new Date());
    }
}