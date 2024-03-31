package parallelstreams;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static util.CommonUtil.*;
import static util.LoggerUtil.log;


public class LinkedListSpliteratorExample {

    public List<Integer> multiplyEachValue(LinkedList<Integer> inputList, int multiplyValue, boolean isParallel) {
        startTimer();

        Stream<Integer> integerStream = inputList.stream();


        if (isParallel)
            integerStream.parallel();

        List<Integer> resultList = integerStream
                .map((i) -> i * multiplyValue)
                .collect(Collectors.toList());
        timeTaken();
        stopWatchReset();
        log("Completed!");
        return resultList;
    }

}
