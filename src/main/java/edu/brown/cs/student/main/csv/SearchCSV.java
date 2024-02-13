package edu.brown.cs.student.main.csv;


import java.util.Map;
import java.util.Set;
import java.util.List; 
import java.util.ArrayList; 

public class SearchCSV implements InterfaceCSV{
    
    private final Set<CSVParser> parserSet; 

    public SearchCSV(Set<CSVParser> parserSet) {
        this.parserSet = parserSet;
    }

    public Object handle(Request request, Response response) {
        response.header("Label", "Label");
        // use a responseMap
        Map<String, String[]> params = request.queryMap().toMap();
        String query = request.queryParams("query");
        CSVParser csvParser = getParser(parserSet.iterator());
        List<List<String>> results = search(query, csvParser);

    }

    private List<List<String>> search(String query, CSVParser parser){

        List<List<String>> result = new ArrayList<>();
        List<List<String>> rows = parser.getRawResults();
        for (List<String> row : rows) {
            if (row.equals(query)){
                result.add(row);
            }
        }
    
        return result; 

    }


}
