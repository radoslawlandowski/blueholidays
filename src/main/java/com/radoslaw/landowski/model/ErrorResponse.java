package com.radoslaw.landowski.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String description;
    private List<Problem> problems;

    public ErrorResponse() {
        this.problems = new ArrayList<>();
    }

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
