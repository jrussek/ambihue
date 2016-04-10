package com.jrussek.ambilight;

import java.util.Map;

/**
 * Created by jrussek on 10.04.16.
 */
public class Edge {

  private Map<Integer, Pixel> pixelMap;

  public Edge(Map<Integer, Pixel> pixelMap) {
    this.pixelMap = pixelMap;
  }
}
