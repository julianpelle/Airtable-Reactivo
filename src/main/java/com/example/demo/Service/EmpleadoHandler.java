package com.example.demo.Service;

import com.example.demo.DTO.EmpleadoFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class EmpleadoHandler {

    @Autowired
    private EmpleadoAirtableService service;

    public Mono<ServerResponse> getAll(ServerRequest req) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll(), EmpleadoFields.class);
    }

    public Mono<ServerResponse> getById(ServerRequest req) {
        long id = Long.parseLong(req.pathVariable("id"));
        return service.findByAutonumberId(id)
                .flatMap(emp -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(emp))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(EmpleadoFields.class)
                .flatMap(service::create)
                .flatMap(emp -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(emp));
    }

    public Flux<Map<String,Object>> ping(WebClient client,
                                         String baseId, String tableId, String viewId) {
        return client.get()
                .uri(uri -> uri.path("/" + baseId + "/" + tableId)
                        .queryParam("view", viewId) // opcional
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String,Object>>() {})
                .flatMapMany(m -> Flux.just(m)); // sólo para ver que responde
    }
}