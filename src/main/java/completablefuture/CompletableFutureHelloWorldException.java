package completablefuture;

import service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

import static util.CommonUtil.*;
import static util.LoggerUtil.log;

public class CompletableFutureHelloWorldException {

    private HelloWorldService hws;

    public CompletableFutureHelloWorldException(HelloWorldService helloWorldService) {
        this.hws = helloWorldService;
    }

    public String helloWorld_3_async_calls_handle() {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> this.hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> this.hws.world());
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " HI CompletableFuture!";
        });

        String hw = hello
                .handle((result, e) -> { // this gets invoked for both success and failure
                    log("result is : " + result);
                    if (e != null) {
                        log("Exception is : " + e.getMessage());
                        return "";
                    }
                    return result;

                })
                .thenCombine(world, (h, w) -> h + w) // (first,second)
                .handle((result, e) -> { // this gets invoked for both success and failure
                    log("result is : " + result);
                    if (e != null) {
                        log("Exception Handle after world : " + e.getMessage());
                        return "";
                    }
                    return result;
                })
                .thenCombine(hiCompletableFuture, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)

                .join();

        timeTaken();

        return hw;
    }

    public String helloWorld_3_async_calls_exceptionally() {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> this.hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> this.hws.world());
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " HI CompletableFuture!";
        });

        String hw = hello
                .exceptionally((e) -> { // this gets invoked for both success and failure
                    log("Exception is : " + e.getMessage());
                    return "";
                })
                .thenCombine(world, (h, w) -> h + w) // (first,second)
                .exceptionally((e) -> { // this gets invoked for both success and failure
                    log("Exception Handle after world : " + e.getMessage());
                    return "";
                })
                .thenCombine(hiCompletableFuture, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)

                .join();

        timeTaken();

        return hw;
    }


    public String helloWorld_3_async_whenComplete() {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> this.hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> this.hws.world());
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " HI CompletableFuture!";
        });

        String hw = hello
                .whenComplete((result, e) -> { // this gets invoked for both success and failure
                    log("result is : " + result);
                    if (e != null) {
                        log("Exception is : " + e.getMessage());
                    }
                })
                .thenCombine(world, (h, w) -> h + w) // (first,second)
                .whenComplete((result, e) -> { // this gets invoked for both success and failure
                    log("result is : " + result);
                    if (e != null) {
                        log("Exception Handle after world : " + e.getMessage());
                    }
                })
                .exceptionally((e) -> { // this gets invoked for both success and failure
                    log("Exception Handle after world : " + e.getMessage());
                    return "";
                })
                .thenCombine(hiCompletableFuture, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)

                .join();

        timeTaken();
        return hw;
    }

}
