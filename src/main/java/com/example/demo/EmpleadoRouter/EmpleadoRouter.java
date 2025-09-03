package com.example.demo.EmpleadoRouter;

import com.example.demo.Service.EmpleadoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class EmpleadoRouter {

    @Bean
    public RouterFunction<ServerResponse> empleadoRoutes(EmpleadoHandler handler) {
        return RouterFunctions
                .route(GET("/empleados").and(accept(MediaType.APPLICATION_JSON)), handler::getAll)
                .andRoute(GET("/empleados/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::getById)
                .andRoute(POST("/empleados").and(accept(MediaType.APPLICATION_JSON)), handler::create);
    }
}