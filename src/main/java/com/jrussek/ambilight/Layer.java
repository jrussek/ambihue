package com.jrussek.ambilight;

import java.util.Map;

/**
 * Created by jrussek on 10.04.16.
 */
public class Layer {

  private Map<String, Edge> edgeMap;

  public Layer(Map<String, Edge> edgeMap) {
    this.edgeMap = edgeMap;
  }
}
