package edu.brown.cs.student.main.csv;

public class Broadband {
//    ["NAME","S2802_C03_022E","state","county"]
    private String NAME;
    private double S2802_C03_022E;
    private String state;
    private String county;

    @Override
    public String toString() {
        return this.NAME + " with " + this.S2802_C03_022E + " broadband percentage.";
    }

        // proxy - intermediary step between a database 
    // proxy modifies info from database and do whatever with it
    // before it reaches the user. 

    // for three other cases ; load/view/search you don't need a proxy 
    // for broadband, the user gets  into the endpoint - put parameter queries, endpoint

    //endpoint for broadband will need to call endpoint and pass in parameters, 
    // once you retrieve information, modify it and serialize into json
    
}
    // proxy - intermediary step between a database
    // proxy modifies info from database and do whatever with it
    // before it reaches the user.

    // for three other cases ; load/view/search you don't need a proxy
    // for broadband, the user gets  into the endpoint - put parameter queries, endpoint

    //endpoint for broadband will need to call endpoint and pass in parameters,
    // once you retrieve information, modify it and serialize into json


