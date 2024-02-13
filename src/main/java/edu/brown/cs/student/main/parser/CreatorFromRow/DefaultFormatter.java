package edu.brown.cs.student.main.parser.CreatorFromRow;

import edu.brown.cs.student.main.parser.CreatorFromRow;

import java.util.List;

public class DefaultFormatter implements CreatorFromRow<List<String>> {
    public List<String> create(List<String> row) {
        return row;
    }