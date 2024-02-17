package edu.brown.cs.student.main.common;
/** Response for CSV-related function calls */
import java.util.Map;

public record CSVResponse(ResultInfo result, String message, Map<String, String[]> params) {}
