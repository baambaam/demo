package com.example.demo.client.util;

import io.netty.channel.ChannelOption;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import reactor.netty.http.client.HttpClient;

public class WebHelper {

    public static ClientHttpConnector buildTimeoutConnector() {
        HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(client ->
                        client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000));

        return new ReactorClientHttpConnector(httpClient);
    }
}
