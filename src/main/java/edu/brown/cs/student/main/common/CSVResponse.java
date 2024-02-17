package edu.brown.cs.student.main.common;

import java.util.Map;

public record CSVResponse(ResultInfo result, String message, Map<String, String[]> params) {}
