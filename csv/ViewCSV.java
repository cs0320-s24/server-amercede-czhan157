package csv;

public class ViewCSV {
    
}
// Mesh Network Community Coalition: "As a developer calling your web API, 
// I can make API requests to load, view, or search the contents of a CSV file by calling the `loadcsv`, `viewcsv` 
// or `searchcsv` endpoints. For `loadcsv`, I will provide the path of the CSV file to load (on the backend)." 

// Acceptance Criteria: The `loadcsv` API query for CSV data takes a file path. At most one 
// CSV dataset is loaded at any time, and using `viewcsv` or `searchcsv` CSV queries without a 
// CSV loaded must produce an error API response, but not halt the server. (See the API specification 
// your server must follow below.) The requirements for search include the ability to search by column
//  index, the ability to search by column header, as well as the ability to search across all columns. 
//  A command-line entry point is intended to start the server (i.e. clicking the green play button),
//  displaying minimal output, such as “Server started” and an instructional line. You will aim to achieve 
//  this through the use of a run script. Similarly to the CSV sprint, this is provided in the stencil code.
//   This ensures that interaction with the server’s functionalities is primarily through the API endpoints. 

// You should use your search code from CSV for this, possibly with modifications. 
// For CSCI 1340: incoming API requests may use the nested and/or/not boolean query pattern 
// from queries in the CSV sprint.
