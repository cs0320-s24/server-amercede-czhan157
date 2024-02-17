// package edu.brown.cs.student.main.datasource;
//
// import com.google.common.cache.CacheBuilder;
// import com.google.common.cache.CacheLoader;
// import com.google.common.cache.LoadingCache;
// import edu.brown.cs.student.main.common.APIException;
// import java.io.IOException;
// import java.net.URISyntaxException;
// import java.util.List;
// import java.util.concurrent.ExecutionException;
// import java.util.concurrent.TimeUnit;
//
// public class CachedBroadband {
//  private final LoadingCache<String, List<List<String>>> cache;
//
//  public CachedBroadband() {
//    this.cache =
//        CacheBuilder.newBuilder()
//            .maximumSize(100)
//            .expireAfterWrite(1, TimeUnit.HOURS) // Cache for 1 hour
//            .build(
//                new CacheLoader<String, List<List<String>>>() {
//                  @Override
//                  public List<List<String>> load(String key) throws Exception {
//                    return fetchBroadbandPercent(key);
//                  }
//                });
//  }
//
//  public List<List<String>> getBroadbandPercent(String stateNum, String countyNum)
//      throws APIException, ExecutionException {
//    String cacheKey = stateNum + "-" + countyNum;
//    return cache.get(cacheKey);
//  }
//
//  private List<List<String>> fetchBroadbandPercent(String cacheKey)
//      throws IOException, URISyntaxException, InterruptedException {
//    String[] keys = cacheKey.split("-");
//    if (keys.length != 2) {
//      throw new IllegalArgumentException("Invalid cache key format");
//    }
//    String stateNum = keys[0];
//    String countyNum = keys[1];
//
//    // Perform API request and deserialization
//    BroadbandPercent broadbandPercent = new BroadbandPercent(stateNum, countyNum);
//    return broadbandPercent.getBroadbandPercent();
//  }
// }
