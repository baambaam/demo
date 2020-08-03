package com.example.demo.client;

import com.example.demo.exception.ClientResponseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;


@NoArgsConstructor
public abstract class AbstractClient {
    private ObjectMapper objectMapper;

    public AbstractClient(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    <T> Mono<T> handleResponse(ClientResponse response, Class<T> elementType) {
        return handleResponse(response, resp -> resp.bodyToMono(elementType));
    }

    <T> Mono<T> handleResponse(ClientResponse response, ParameterizedTypeReference<T> typeReference) {
        return handleResponse(response, resp -> resp.bodyToMono(typeReference));
    }

    <T> Flux<T> handleResponseList(ClientResponse response, Class<T> elementType) {
        return Mono.just(response)
                .filter(resp -> resp.statusCode().is2xxSuccessful())
                .switchIfEmpty(response.bodyToMono(String.class)
                        .map(message -> new ClientResponseException(response.statusCode(), message))
                        .flatMap(Mono::error))
                .flatMapMany(resp -> resp.bodyToFlux(elementType));
    }

    protected String normalize(String text) {
        return text.replaceAll("[^\\w.-]", "_");
    }

    private <R> Mono<R> handleResponse(ClientResponse response, Function<ClientResponse, Mono<R>> transformer) {
        return Mono.just(response)
                .filter(resp -> resp.statusCode().is2xxSuccessful())
                .switchIfEmpty(response.bodyToMono(String.class)
                        .map(message -> new ClientResponseException(response.statusCode(), message))
                        .flatMap(Mono::error))
                .flatMap(transformer);
    }

    protected  <T> MultiValueMap<String, String> buildParameters(T object) {
        if (this.objectMapper == null) {
            throw new UnsupportedOperationException("ObjectMapper not provided");
        }
        MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<>();

        Map<String, String> convertedMap = objectMapper.convertValue(object, new TypeReference<Map<String, String>>() {
        });
        convertedMap.entrySet().stream()
                .filter(entry -> Objects.nonNull(entry.getValue()))
                .forEach(entry -> parametersMap.add(entry.getKey(), entry.getValue()));


        return parametersMap;
    }
}
