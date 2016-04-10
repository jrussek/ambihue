package com.jrussek.ambilight;

/**
 * Created by jrussek on 10.04.16.
 */
public class Pixel {

  private int r;
  private int g;
  private int b;

  public Pixel(int r, int g, int b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }

  public AmbilightStateBuilder Builder() {
    return new AmbilightStateBuilder();
  }
}
