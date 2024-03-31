package apiclient;

import domain.github.GitHubPosition;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static util.CommonUtil.startTimer;
import static util.CommonUtil.timeTaken;

public class GitHubJobsClient {

    private final WebClient webClient;

    public GitHubJobsClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<GitHubPosition> invokeGithubJobsApi_withPageNumber(int pageNum, String description) {

        String uri = UriComponentsBuilder.fromUriString("/positions.json")
                .queryParam("description", description)
                .queryParam("page", pageNum)
                .buildAndExpand()
                .toUriString();
        System.out.println("uri  : " + uri);

        return webClient.get().uri(uri)
                .retrieve()
                .bodyToFlux(GitHubPosition.class)
                .collectList()
                .block();


    }

    public List<GitHubPosition> invokeGithubJobsApi_withMultiplePageNumbers_CF(List<Integer> pageList, String description) {
        startTimer();

        List<CompletableFuture<List<GitHubPosition>>> gitHubPositions = pageList.stream()
                .map(pageNum -> CompletableFuture.supplyAsync(() -> invokeGithubJobsApi_withPageNumber(pageNum, description)))
                .collect(Collectors.toList());

        CompletableFuture<Void> cfAllOf = CompletableFuture.allOf(gitHubPositions.toArray(new CompletableFuture[gitHubPositions.size()]));

        List<GitHubPosition> gitHubPositionsList = cfAllOf
                .thenApply(v -> gitHubPositions.stream().map(CompletableFuture::join)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList()))
                .join();
        timeTaken();

        return gitHubPositionsList;


    }

    public List<GitHubPosition> invokeGithubJobsApi_withPageNumber(List<Integer> pageList, String description) {
        startTimer();

        List<GitHubPosition> gitHubPositionsList = pageList.stream()
                .map(pageNum -> invokeGithubJobsApi_withPageNumber(pageNum, description))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        timeTaken();
        return gitHubPositionsList;
    }


}
