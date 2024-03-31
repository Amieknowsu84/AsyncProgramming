package parallelstreams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static util.CommonUtil.*;
import static util.LoggerUtil.log;

public class ArrayListSpliteratorExample {

    public List<Integer> multiplyEachValue(ArrayList<Integer> inputList, int multiplyValue, boolean isParallel) {
        startTimer();

        Stream<Integer> integerStream = inputList.stream();

        if (isParallel)
            integerStream.parallel();

        List<Integer> resultList = integerStream
                .map((i) -> {
                    //log("inside map");
                    return i * multiplyValue;
                })
                .collect(Collectors.toList());
        timeTaken();
        log("Completed!");
        return resultList;
    }

    public List<Integer> multiplyEachValue(int n, int multiplyValue, boolean isParallel) {
        startTimer();

        IntStream integerStream = IntStream.range(0, n); // Stream of primitive int valued elements

        if (isParallel)
            integerStream.parallel();

        List<Integer> resultList = integerStream
                .map((i) -> i * multiplyValue)
                .boxed()
                .collect(Collectors.toList());
        timeTaken();
        stopWatchReset();
        log("Completed!");
        return resultList;
    }
}
