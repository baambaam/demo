package com.example.demo.client;

import com.example.demo.model.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.example.demo.client.util.WebHelper.buildTimeoutConnector;

@Component
public class PartClient extends AbstractClient {
    private final WebClient webClient;

    @Autowired
    public PartClient() {
        this.webClient = WebClient.builder()
                .baseUrl("http://apisandbox.ct8.pl/part.php")
                .clientConnector(buildTimeoutConnector())
                .build();
    }

    public Mono<Part> getPart(Long productId) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                .pathSegment()
                .build(productId)) // I know that it's not necessary
                .exchange()
                .flatMap(response -> handleResponse(response, Part.class));
    }
}
