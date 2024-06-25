package org.acme;

import org.apache.camel.builder.RouteBuilder;
import org.acme.Movie;

public class MyRouteBuilder extends RouteBuilder {
    @Override
    public void configure() {

        from("scheduler://foo?delay=60000")
                .process(exchange -> {
                    Movie movie = new Movie();
                    movie.setTitle("The Matrix");
                    movie.setYear(1999);
                    exchange.getMessage().setBody(movie);
                })
                .log("Sending movie to Kafka")
                .to("kafka:movies");
    }
}
