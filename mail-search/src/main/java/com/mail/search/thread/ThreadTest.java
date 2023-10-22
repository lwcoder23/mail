package com.mail.search.thread;

import java.util.concurrent.*;

public class ThreadTest {

    public static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        /**
         *  CompletableFuture
         *  1. 创建异步任务  CompletableFuture.supplyAsync() --> 带有返回值的方法
         *                CompletableFuture.runAsync()    --> 没有返回值的方法
         *  2.
         */

        System.out.println("main start");

        /*CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            System.out.println("currentThread getId: " + Thread.currentThread().getId());
            System.out.println("res: " + (10 / 2));
        }, executorService);*/

        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {   // 带有返回值的方法
            System.out.println("currentThread getId: " + Thread.currentThread().getId());
            return 10 / 0;
        }, executorService).whenComplete((res, ex) -> {
            // 虽然能得到异常信息，但是没办法修改返回数据
            System.out.println("异步任务完成，结果是" + res + "; 异常是：" + ex);
        }).exceptionally(throwable -> {
            // 可以 感知 异常，并返回默认处理结果
            return 10;
        });
        // Integer integer = completableFuture.get();

        // 使用 handle 方法来做任务完成后的调用（处理）
        CompletableFuture<Integer> Future = CompletableFuture.supplyAsync(() -> {   // 带有返回值的方法
            System.out.println("currentThread getId: " + Thread.currentThread().getId());
            return 10 / 0;
        }, executorService).handle((res, ex) -> {
            if (res != null) {
                return res * 10;
            }
            return 0;
        });

        /**
         *  线程串行化
         *  1. thenRun(Async) -> 直接执行下一个任务，不需要上一个任务的返回值，也不需要返回当前任务的值
         *  2. thenAccept(Async) -> 接收上一个任务的返回结果，本任务不返回结果
         *  3. thenApply(Async) -> 接收上一个任务的返回结果，并返回本次任务的结果
         */
        // 1 thenRun
        CompletableFuture<Void> completable = CompletableFuture.supplyAsync(() -> {
            // 任务一（当前任务，以异步方式进行）
            System.out.println("currentThread getId: " + Thread.currentThread().getId());
            return 10 / 5;
        }, executorService).thenRunAsync(() -> {
            // 任务二（任务一完成以后的下一个任务，也是以异步的方式进行）
            System.out.println("任务2启动");
        }, executorService);

        // 2 thenAccept
        CompletableFuture<Void> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("currentThread getId: " + Thread.currentThread().getId());
            return 10 / 5;
        }, executorService).thenAcceptAsync(res -> System.out.println(res + "任务二启动"), executorService);

        // 3 thenApply
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("currentThread getId: " + Thread.currentThread().getId());
            return 10 / 5;
        }, executorService).thenApplyAsync(res -> {
            System.out.println("任务二启动");
            return "thread-2 res";
        }, executorService);

        /**
         *  多任务组合，两个都完成后做下一个
         *  1. runAfterBoth(Async) 直接执行方法三
         *  2. thenAcceptBoth(Async) 获取前两个方法的返回值，不返回当前方法的值
         *  3. thenCombine(Async) 获取前两个方法的返回值，并且返回当前的方法的结果
         */
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("currentThread getId: " + Thread.currentThread().getId());
            System.out.println("任务二结束");
            return 10 / 5 + "";
        }, executorService);

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("currentThread getId: " + Thread.currentThread().getId());
            System.out.println("任务三结束");
            return "test2";
        }, executorService);

        // 任务二和任务三都结束时 ⬇
        // 1 runAfterBothAsync
        future2.runAfterBothAsync(future3, () -> {
            System.out.println("任务四");
        }, executorService);

        // 2 thenAcceptBothAsync
        future2.thenAcceptBothAsync(future3, (res1, res2) -> {
            System.out.println("任务四" + "res1 : res2" + res1 + ":" + res2);
        }, executorService);

        // 3 thenCombineAsync
        CompletableFuture<String> combineAsync = future2.thenCombineAsync(future3, (res1, res2) -> {
            System.out.println("任务四" + "res1 : res2" + res1 + ":" + res2);
            return "res3";
        }, executorService);

        /**
         *  多任务组合，完成两个中的一个时做任务三
         *  1. runAfterEither(Async) 完成一个任务后直接执行任务三
         *  2. acceptEither(Async) 完成一个后获取其结果，！不！ 返回任务三的结果，然后执行任务三，要注意，关联的两个方法的返回值类型应一直
         *  3. applyToEither(Async) 完成一个后获取其结果，返回任务三的结果，然后执行任务三，要注意，关联的两个方法的返回值类型应一直
         */
        // 1 runAfterEither
        future2.runAfterEitherAsync(future3, () -> {
            System.out.println("已完成其中一个，这是任务三");
        },executorService);

        // 2 acceptEither
        future2.acceptEitherAsync(future3, (res) -> {
            System.out.println("已完成其中一个，这是任务三");
        },executorService);

        // 3 applyToEitherAsync
        CompletableFuture<String> applyToEitherAsync = future2.applyToEitherAsync(future3, (res) -> {
            System.out.println("已完成其中一个，这是任务三");
            return "res3";
        }, executorService);

        /**
         *  多任务组合，多个任务（两个以上）组合
         *  1. CompletableFuture.allOf(...) 所有任务都要完成时
         *  2. CompletableFuture.anyOf(...) 只要完成一个就可
         */
        // 1 CompletableFuture.allOf
        CompletableFuture<Void> allOf = CompletableFuture.allOf(future1, future2, future3);
        allOf.get();

        // 2 CompletableFuture.anyOf
        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(future1, future2, future3);

        System.out.println("main end");

    }

    public void thread() throws ExecutionException, InterruptedException {
        System.out.println("main function start ===");

        /**
         *  1. 继承 Thread
         *      Thread01 thread01 = new Thread01();
         *      thread01.start(); //启动线程
         *  2. 实现 Runnable 接口。 配合 FutureTask 并传入一个对象，也可以获取到返回值
         *      Runnable01 runnable01 = new Runnable01();
         *      new Thread(runnable01).start();
         *  3. 实现 Callable 接口 + FeatureTask （可以拿到异步调用的返回结果，可以处理异常）
         *      FutureTask<Integer> futureTask = new FutureTask<>(new Callable01());
         *      new Thread(futureTask).start();
         *      // FutureTask 的 get 方法 作用：等待被调用的整个线程执行完成，获取返回结果
         *      Integer integer = futureTask.get();
         *      System.out.println(integer);
         *  4. 线程池 （我们在大并发环境中所采用的方案，因为线程的开启和销毁是非常消耗资源的；本系统中将所有的异步任务都交给线程池执行！）
         *      在线程池里直接提交任务
         */

        // 当前系统在初始化时创建一两个线程池 ExecutorService，本机器环境的所有异步任务都将由该线程池内的线程来完成，性能稳定（！！！），不会因为线程资源耗尽而宕机
        ExecutorService executorService = Executors.newFixedThreadPool(10); // 最快的创建线程池的方法

        // 原始方法，有七大参数
        /**
         * @param corePoolSize 核心线程数，会一直保持 the number of threads to keep in the pool, even
         *        if they are idle, unless {@code allowCoreThreadTimeOut} is set
         * @param maximumPoolSize the maximum number of threads to allow in the
         *        pool  控制资源，最大数量，再大的并发也是这个量
         * @param keepAliveTime 到存货时间后释放非核心线程 when the number of threads is greater than
         *        the core, this is the maximum time that excess idle threads
         *        will wait for new tasks before terminating.
         * @param unit 指定时间单位 the time unit for the {@code keepAliveTime} argument
         * @param workQueue （BlockingQueue<Runnable> workQueue）阻塞队列，超出处理能力的任务将被阻塞，空闲的线程将在队列中接取任务
         *        the queue to use for holding tasks before they are executed.
         *        This queue will hold only the {@code Runnable} tasks submitted by the {@code execute} method.
         * @param threadFactory the factory to use when the executor creates a ！！new thread！！如何创建一个新线程
         * @param handler the handler to use when execution is blocked
         *        because the thread bounds and queue capacities are reached
         *        RejectedExecutionHandler handler --> 拒绝策略，如果阻塞队列已满，如何处理该任务
         *
         *
         * 工作顺序：
         *          1. 创建线程池，准备好 corePoolSize 数量的核心线程，准备接收任务
         *              1.1 当接收到超过核心线程数的任务时，将任务放到阻塞队列 workQueue 中
         *              1.2 当阻塞队列也满时，开启新线程；所有开启的最大线程数为 maximumPoolSize
         *              1.3 当 达到最大线程数 并且 阻塞队列也满 时，启用 拒绝策略 RejectedExecutionHandler handler
         *              1.4 当线程出现空闲时，超过存活时间 keepAliveTime 的线程将被销毁
         *
         *  eg: 一个线程池 core: 7, max: 20, queue: 50, 100个并发进来时怎么执行（分配）
         *                -> 7个立即执行，50 个进队列， 再创建 13 个线程执行 13个任务， 剩下的得不到空闲线程执行的，未进入阻塞队列的就使用拒绝策略执行
         *                   如果不想抛弃任务，可以使用 callerRunsPolicy 策略，调用 Runnable 对象的 run 方法顺序执行，或者 DisCardOldestPolicy 抛弃较久的任务
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10,
                50,
                30,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(10000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()); // 直接丢弃的拒绝策略

        // Executors.newCachedThreadPool(); 带有缓存的线程池，core=0，max较大，可以灵活回收闲暇线程
        Executors.newFixedThreadPool(10); // 定大小的线程池 core=max，都不可回收
        Executors.newScheduledThreadPool(10); // 定时任务线程池
        Executors.newSingleThreadExecutor(); // core = max = 1，单线程的线程池，从队列中获取任务逐一执行

        executorService.execute(new Runnable01()); // 无返回值
        executorService.submit(new Callable01());

        System.out.println("main function end ===");

    }

    public static class Callable01 implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            System.out.println("currentThread getId: " + Thread.currentThread().getId());
            return 1 + 1;
        }
    }

    public static class Thread01 extends Thread {

        public void run() {
            System.out.println("currentThread getId: " + Thread.currentThread().getId());
            System.out.println("res: " + (10 / 2));
        }

    }

    public static class Runnable01 implements Runnable {

        @Override
        public void run() {
            System.out.println("currentThread getId: " + Thread.currentThread().getId());
            System.out.println("res: " + (10 / 2));
        }
    }

}
