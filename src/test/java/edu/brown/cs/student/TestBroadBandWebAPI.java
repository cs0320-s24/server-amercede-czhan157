package edu.brown.cs.student;

//
// public class TestBroadBandWebAPI {
//    @Test
//    public void testWebAPIIntegrationTestForCountiesWithSpaces() throws URISyntaxException,
// IOException, InterruptedException {
//        HttpRequest buildBroadbandApiRequest =
//                HttpRequest.newBuilder()
//                        .uri(new
// URI("http://localhost:3232/census?statename=Alabama&countyname=Morgan%20County"))
//                        .GET()
//                        .build();
//
//        // This builds the client so it can receive and send a response
//        HttpResponse<String> sentBroadbandApiResponse =
//                HttpClient.newBuilder()
//                        .build()
//                        .send(buildBroadbandApiRequest, HttpResponse.BodyHandlers.ofString());
//
//        assertEquals("{\"broadband\":[\"Morgan County,
// Alabama\",\"75.2\",\"01\",\"103\"],\"type\":\"success\"}", sentBroadbandApiResponse.body());
//    }
//
//    @Test
//    public void testWrongPrtThrowsConnectException(){
//
//        assertThrows(
//                ConnectException.class, () -> {
//                    HttpRequest buildBroadbandApiRequest =
//                            HttpRequest.newBuilder()
//                                    .uri(new
// URI("http://localhost:323/census?statename=Alabama&countyname=hello"))
//                                    .GET()
//                                    .build();
//                    HttpResponse<String> sentBroadbandApiResponse =
//                            HttpClient.newBuilder()
//                                    .build()
//                                    .send(buildBroadbandApiRequest,
// HttpResponse.BodyHandlers.ofString());
//
//                });
//
//    }
//
//    @Test
//    public void testWrongParameterThrows500InternalServiceError() throws URISyntaxException,
// IOException, InterruptedException {
//        HttpRequest buildBroadbandApiRequest =
//                HttpRequest.newBuilder()
//                        .uri(new
// URI("http://localhost:3232/census?statename=Alabama&countyname=hello"))
//                        .GET()
//                        .build();
//        HttpResponse<String> sentBroadbandApiResponse =
//                HttpClient.newBuilder()
//                        .build()
//                        .send(buildBroadbandApiRequest, HttpResponse.BodyHandlers.ofString());
//        assertEquals("<html><body><h2>500 Internal Server Error</h2></body></html>",
// sentBroadbandApiResponse.body());
//
//    }
//    @Test
//    public void testEmptyParameterThrows500InternalServiceError() throws URISyntaxException,
// IOException, InterruptedException {
//        HttpRequest buildBroadbandApiRequest =
//                HttpRequest.newBuilder()
//                        .uri(new
// URI("http://localhost:3232/census?statename=Alabama&countyname="))
//                        .GET()
//                        .build();
//        HttpResponse<String> sentBroadbandApiResponse =
//                HttpClient.newBuilder()
//                        .build()
//                        .send(buildBroadbandApiRequest, HttpResponse.BodyHandlers.ofString());
//        assertEquals("<html><body><h2>500 Internal Server Error</h2></body></html>",
// sentBroadbandApiResponse.body());
//
//    }
// }
