package edu.brown.cs.student.main.common;

import java.util.Map;

public record BroadbandResponse (
    ResultInfo result, String message, Map<String, String[]> params) implements APIResponse {

    }


            // proxy - intermediary step between a database 
    // proxy modifies info from database and do whatever with it
    // before it reaches the user. 

    // for three other cases ; load/view/search you don't need a proxy 
    // for broadband, the user gets  into the endpoint - put parameter queries, endpoint

    //endpoint for broadband will need to call endpoint and pass in parameters, 
    // once you retrieve information, modify it and serialize into json
    
