package com.radoslaw.landowski.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * A base class returned for non-successful requests, ie: validation errors, server error etc.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String description;

    @Builder.Default
    private List<Problem> problems = new ArrayList<>();

    public void addProblem(String value, String details) {
        this.problems.add(new Problem(value, details));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Problem {
        private String value;
        private String details;
    }
}
