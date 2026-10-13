package com.yeensight.core.infrastructure.rest;

import com.yeensight.core.application.InsightNotFoundException;
import com.yeensight.core.application.InsightService;
import com.yeensight.core.domain.model.Insight;
import com.yeensight.core.domain.model.InsightId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/insights")
public class InsightController {
    public record CreateInsightRequest(@NotBlank String title, @NotBlank String content) {}

    public record InsightResponse(UUID id, String title, String content) {
        static InsightResponse from(Insight i) {
            return new InsightResponse(i.id().value(), i.title(), i.content());
        }
    }

    private final InsightService service;

    public InsightController(InsightService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InsightResponse create(@Valid @RequestBody CreateInsightRequest request) {
        return InsightResponse.from(service.create(request.title(), request.content()));
    }

    @GetMapping("/{id}")
    public InsightResponse get(@PathVariable UUID id) {
        return InsightResponse.from(service.get(new InsightId(id)));
    }

    @ExceptionHandler(InsightNotFoundException.class)
    ResponseEntity<Void> notFound() {
        return ResponseEntity.notFound().build();
    }
}
