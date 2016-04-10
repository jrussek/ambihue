package com.jrussek.ambilight;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Created by jrussek on 10.04.16.
 */
public class AmbilightTopology {

  private int layers;
  private int left;
  private int top;
  private int right;
  private int bottom;

  public AmbilightTopology(int layers, int left, int top, int right, int bottom) {
    this.layers = layers;
    this.left = left;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
  }

  public int getLeftPixels() {
    return left;
  }

  public int getTopPixels() {
    return top;
  }

  public int getRightPixels() {
    return right;
  }

  public int getBottomPixels() {
    return bottom;
  }

  public int getEdgePixels(String name) {
    if(name.equals("left")) return getLeftPixels();
    if(name.equals("top")) return getTopPixels();
    if(name.equals("right")) return getRightPixels();
    if(name.equals("bottom")) return getBottomPixels();
    return 0;
  }

  public List<String> getLayers() {
    ImmutableList.Builder<String> builder = ImmutableList.builder();
    for (int i = 1; i <= layers; i++) {
      builder.add("layer" + i);
    }
    return builder.build();
  }

  public List<String> getEdges() {
    return ImmutableList.of("left", "top", "right", "bottom");
  }

  public String[] getEdgeNames() {
    return new String[]{"left", "top", "right", "bottom"};
  }
}
