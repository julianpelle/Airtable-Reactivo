package com.example.demo.Service;

import com.example.demo.DTO.AirtableListResponse;
import com.example.demo.DTO.AirtableRecord;
import com.example.demo.DTO.EmpleadoFields;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@Service
public class EmpleadoAirtableService {

    private final WebClient client;
    private final String tableId;
    private final String baseId;

    public EmpleadoAirtableService(WebClient airtableWebClient,
                                   @Value("${airtable.tableEMPLEADO_ENTITY}") String tableId,
                                   @Value("${airtable.baseId}") String baseId) {
        this.client = airtableWebClient;
        this.tableId = tableId;
        this.baseId = baseId;
    }

    private String tablePath() {
        return "/" + baseId + "/" + tableId;
    }
    private String pathBase() {
        return "/" + baseId + "/" + tableId;
    }
    public Flux<EmpleadoFields> findAll() {
        return client.get()
                .uri(pathBase())                    // "/{baseId}/{tableId}"
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<AirtableListResponse<EmpleadoFields>>() {})
                .flatMapMany(resp -> Flux.fromIterable(resp.getRecords()))
                .map(AirtableRecord::getFields);    // ✅ ahora compila
    }

    public Mono<EmpleadoFields> findByAutonumberId(long id) {
        String formula = "{Id} = " + id;
        return client.get()
                .uri(uri -> uri.path(pathBase())
                        .queryParam("filterByFormula", formula)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<AirtableListResponse<EmpleadoFields>>() {})
                .flatMap(resp -> resp.getRecords() == null || resp.getRecords().isEmpty()
                        ? Mono.empty()
                        : Mono.just(resp.getRecords().get(0).getFields()));  // ✅ tipado
    }

    public Mono<EmpleadoFields> create(EmpleadoFields fields) {
        return client.post()
                .uri(pathBase())
                .bodyValue(java.util.Map.of("fields", fields))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<AirtableRecord<EmpleadoFields>>() {})
                .map(AirtableRecord::getFields);   // ✅
    }

    public Mono<EmpleadoFields> update(String recordId, EmpleadoFields fields) {
        return client.patch()
                .uri(pathBase() + "/" + recordId)
                .bodyValue(java.util.Map.of("fields", fields))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<AirtableRecord<EmpleadoFields>>() {})
                .map(AirtableRecord::getFields);   // ✅
    }

    public Mono<Void> delete(String recordId) {
        // Airtable responde {deleted: true, id: "..."}; descartamos el body
        return client.delete()
                .uri(pathBase() + "/" + recordId)
                .retrieve()
                .bodyToMono(String.class)
                .then();
    }
}