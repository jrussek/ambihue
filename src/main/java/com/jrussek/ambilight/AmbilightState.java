package com.jrussek.ambilight;

import java.util.Map;

/**
 * Created by jrussek on 10.04.16.
 */
public class AmbilightState {
  private Map<String, Layer> layerMap;

  public Map<String, Layer> getLayerMap() {
    return layerMap;
  }

  public AmbilightState setLayerMap(
      Map<String, Layer> layerMap) {
    this.layerMap = layerMap;
    return this;
  }

}
