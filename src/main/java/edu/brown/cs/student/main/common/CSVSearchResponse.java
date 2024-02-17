package edu.brown.cs.student.main.common;

import java.util.List;
import java.util.Map;

public record CSVSearchResponse(
    ResultInfo result, List<List<String>> data, Map<String, String[]> params) {}
