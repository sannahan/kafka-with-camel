package org.acme;

import org.apache.camel.builder.RouteBuilder;

public class MyRouteBuilder extends RouteBuilder {
    @Override
    public void configure() {

        from("kafka:{{kafka.topic.name}}?seekTo=beginning")
                .process(exchange -> {
                    Movie movie = exchange.getMessage().getBody(Movie.class);
                    exchange.getMessage().setHeader("title", movie.getTitle());
                    exchange.getMessage().setHeader("year", movie.getYear());
                })
                .log("Received movie is: ${header.title} and its year is: ${header.year}");

    }
}
