package com.jrussek.geometry;

import com.vividsolutions.jts.geom.Point;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created by jrussek on 10.04.16.
 */
public class GeometryBuilderTest {


  // Let's assume we have 4x6 pixel on the TV, we space them out by 100
  // the top left pixel is placed at X,Y where X = Center.X - (5*100/2) = 500 - 250
  // and Y = Center.Y - (3*100/2) = 500 - 150
  List<Point> tvPoints;
  double resolution = 100;


  double numTop = 6;
  double numLeft = 4;
  double numRight = 4;
  double numBottom = 6;
  double space = 4; // space them by 10
  private GeometryBuilder geometryBuilder;
  private double centerX;
  private double centerY;
  private double topLeftX;
  private double topLeftY;


  @BeforeClass
  public void beforeClass() {
    geometryBuilder = new GeometryBuilder(resolution);
    centerX = geometryBuilder.getCenter().x;
    centerY = geometryBuilder.getCenter().y;
    topLeftX = centerX - ((numTop - 1) * space / 2);
    topLeftY = centerY + ((numLeft - 1) * space / 2);
    // build top and bottom edge
    for (int i = 0; i < numTop; i++) {
      double x = topLeftX + i * space;
      double y = topLeftY + space/2;
      geometryBuilder.addPoint(x, y);
      y = topLeftY - (numLeft - 1) * space - space / 2;
      geometryBuilder.addPoint(x, y);
    }

    // build left and right edge

    for (int i = 0; i < numLeft; i++) {
      double x = topLeftX - space/2;
      double y = topLeftY - i * space;
      geometryBuilder.addPoint(x, y);
      x = topLeftX + (numTop - 1) * space + space / 2;
      geometryBuilder.addPoint(x, y);
    }

    System.out.println(geometryBuilder.getAllPoints().toText());
  }

  @Test
  public void testCreateGeometry() throws Exception {
    assert(geometryBuilder.getCenter().x == (double) resolution / 2);
    assert(geometryBuilder.getCenter().y == (double) resolution / 2);
  }

  @Test
  public void testGetPointsFromPosition() throws Exception {
    assertEquals(geometryBuilder.getPointsFromPosition(50, 100, 90).size(), 4);
    assertEquals(geometryBuilder.getPointsFromPosition(50, 100, 180).size(), 10);
    assertEquals(geometryBuilder.getPointsFromPosition(50, 100, 0).size(), 2);
  }
}