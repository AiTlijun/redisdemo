package com.niu.top.redisdemo;

import java.util.concurrent.*;

/**
 * @author hongwei
 * @date 2018/10/24 11:03
 */
public class ThreadImplementation {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadClassImp threadClassImp = new ThreadClassImp();
        Thread threadA = new Thread(threadClassImp);
        threadA.start();

        new Thread(new ThreadRunableImp()).start();
        String result = "false";
        FutureTask<String> futureTask = new FutureTask<String>(new ThreadRunableImp(),result);
        FutureTask<String> futureTask2 = new FutureTask<String>(new ThreadRunableImp2());
        new Thread(futureTask).start();
        new Thread(futureTask2).start();
        //System.out.println("futureTask:"+futureTask.get().toString());
        //System.out.println("futureTask2:"+futureTask2.get().toString());

        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(futureTask);
        service.submit(futureTask2);
        service.submit(futureTask2);
        System.out.println("futureTask:"+futureTask.get().toString());
        System.out.println("futureTask2:"+futureTask2.get().toString());
    }
}
class ThreadClassImp extends Thread{
    @Override
    public void run() {
        System.out.println("继承Thread类创建线程。");
    }
}

class ThreadRunableImp implements Runnable{

    @Override
    public void run() {
        System.out.println("implements Runnable创建线程。");
    }
}

class ThreadRunableImp2 implements Callable {


    @Override
    public Object call() throws Exception {
        return "implements Callable创建线程。";
    }
}