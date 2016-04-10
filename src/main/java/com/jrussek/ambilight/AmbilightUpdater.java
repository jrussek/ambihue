package com.jrussek.ambilight;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

import java.util.concurrent.ExecutionException;

/**
 * Created by jrussek on 10.04.16.
 */
public class AmbilightUpdater {

  Gson gson = new Gson();
  AsyncHttpClient httpClient;
  String tvUrl;
  AmbilightTopology topology;
  private AmbilightState tvState;

  public AmbilightUpdater(String url, AmbilightState state) {
    tvState = state;
    httpClient = new AsyncHttpClient();
    tvUrl = url;
  }

  public void fetchTopology() throws ExecutionException, InterruptedException {
    topology = httpClient.prepareGet(tvUrl + "/1/ambilight/topology").execute(
        new AsyncCompletionHandler<AmbilightTopology>() {
          @Override
          public AmbilightTopology onCompleted(Response response) throws Exception {
            return gson.fromJson(response.getResponseBody(), AmbilightTopology.class);
          }
        }).get();
  }

  public void fetchState() throws ExecutionException, InterruptedException {
    if (topology == null) {
      fetchTopology();
    }
    tvState = httpClient.prepareGet(tvUrl + "/1/ambilight/processed").execute(
        new AsyncCompletionHandler<AmbilightState>() {
          @Override
          public AmbilightState onCompleted(Response response) throws Exception {
            JsonParser parser = new JsonParser();
            JsonElement ambilightStateTree = parser.parse(response.getResponseBody());
            AmbilightStateBuilder builder = new AmbilightStateBuilder();

            JsonObject layers = ambilightStateTree.getAsJsonObject();
            for (String layerName : topology.getLayers()) {
              System.out.println("parsing layer " + layerName);
              builder.setLayer(layerName);
              JsonObject layer = layers.get(layerName).getAsJsonObject();

              for (String edgeName : topology.getEdgeNames()) {
                System.out.println("parsing edge " + edgeName);
                builder.setEdge(edgeName);
                JsonObject edge = layer.get(edgeName).getAsJsonObject();

                for (int i = 0; i < topology.getEdgePixels(edgeName); i++) {
                  JsonObject pixel = edge.get(Integer.toString(i)).getAsJsonObject();
                  int r = pixel.get("r").getAsInt();
                  int g = pixel.get("g").getAsInt();
                  int b = pixel.get("b").getAsInt();
                  builder.setPixel(i,r,g,b);
                }
              }
            }
            return builder.build();
          }
        }).get();
    tvUrl = tvUrl;
  }

  public AmbilightState getState() {
    return tvState;
  }
}
