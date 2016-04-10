package com.jrussek;

import com.jrussek.ambilight.AmbilightState;
import com.jrussek.ambilight.AmbilightUpdater;

import java.util.concurrent.ExecutionException;

/**
 * Created by jrussek on 10.04.16.
 */
public class Main {
  public static void main(String [ ] args) {
    AmbilightState tvState = new AmbilightState();
    AmbilightUpdater tvUpdater = new AmbilightUpdater("http://192.168.2.3:1925", tvState);
    try {
      tvUpdater.fetchTopology();
      tvUpdater.fetchState();
      AmbilightState state = tvUpdater.getState();
      tvState = state;
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
