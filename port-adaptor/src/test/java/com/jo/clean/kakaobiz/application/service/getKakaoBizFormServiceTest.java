package com.jo.clean.kakaobiz.application.service;

import com.jo.clean.common.MultiHttpClientConnThread;
import com.jo.clean.kakaobiz.domain.BizForm;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
class getKakaoBizFormServiceTest {
    String kakaoURL = "";

    @Test
    @DisplayName("WebClient를 통한 Non-blocking 호출")
    void concurrentCallTestWithWebClient() {
        List<Integer> pageList = IntStream.rangeClosed(0, 5)
                .boxed()
                .collect(Collectors.toList());

        Flux<BizForm> bizFormFlux = fetchBizForm(pageList);
        Optional<List<BizForm>> block = bizFormFlux.collectList().blockOptional();

        block.ifPresent(bizForms -> bizForms.forEach(it -> log.info("it => {}", it)));
    }

    private Flux<BizForm> fetchBizForm(List<Integer> pageList) {
        return Flux.fromIterable(pageList)
                .flatMap(this::getBizFormMono);
    }

    private Mono<BizForm> getBizFormMono(int startPage) {
        WebClient webClient = WebClient.builder().build();

        return webClient.get()
                .uri(kakaoURL, startPage)
                .header("X-BIZFORM-API-KEY", "")
                .retrieve()
                .bodyToMono(BizForm.class);
    }
}