package com.jrussek.ambilight;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jrussek on 10.04.16.
 */
public class AmbilightStateBuilder {

  Map<String, Layer> layers = new HashMap<>();

  String currentLayerName = null;
  Map<String, Edge> currentLayer = null;

  String currentEdgeName;
  Map<Integer, Pixel> currentEdge;

  Integer currentPixelIndex;
  Pixel currentPixel = null;

  public AmbilightState build() {
    layers.put(currentLayerName, new Layer(currentLayer));
    return new AmbilightState().setLayerMap(layers);
  }

  public AmbilightStateBuilder setLayer(String name) {
    if (currentLayerName != null && !currentLayerName.equals(name) && currentLayer != null) {
      layers.put(currentLayerName, new Layer(currentLayer));
    }
    currentLayerName = name;
    currentLayer = new HashMap<>();
    return this;
  }

  public AmbilightStateBuilder setEdge(String name) {
    if (currentEdgeName != null && !currentEdgeName.equals(name) && currentEdge != null) {
      currentLayer.put(currentEdgeName, new Edge(currentEdge));
    }
    currentEdgeName = name;
    currentEdge = new HashMap<>();
    return this;
  }

  public AmbilightStateBuilder setPixel(int id, int r, int g, int b) {
    currentPixelIndex = id;
    currentEdge.put(id, new Pixel(r, g, b));
    return this;
  }
}
