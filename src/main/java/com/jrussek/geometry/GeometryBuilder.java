package com.jrussek.geometry;

import com.google.common.collect.Lists;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.math.Vector2D;
import com.vividsolutions.jts.operation.distance.DistanceOp;
import com.vividsolutions.jts.util.GeometricShapeFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by jrussek on 10.04.16.
 */
public class GeometryBuilder {

  private GeometricShapeFactory gsf = new GeometricShapeFactory();
  private GeometryFactory gf = new GeometryFactory();
  private List<Point> pointList = Lists.newArrayList();
  private Point center;
  private Vector2D zeroDegreeVector;

  public GeometryBuilder(double resolution) {
    center = gf.createPoint(new Coordinate(resolution / 2, resolution / 2));
    zeroDegreeVector = new Vector2D(center.getCoordinate(), new Coordinate(0, resolution));
  }

  public Coordinate getCenter() {
    return center.getCoordinate();
  }

  public GeometryCollection getAllPoints() {
    return new GeometryCollection(pointList.toArray(new Point[0]), gf);
  }

  public Point addPoint(double x, double y) {
    Point point = gf.createPoint(new Coordinate(x, y));
    pointList.add(point);
    return point;
  }

  public List<Point> getPointsFromPosition(double x, double y, double openingAngle) {
    Vector2D _vector = new Vector2D(center.getCoordinate(), new Coordinate(x, y));
    double rotationAngle = _vector.angleTo(zeroDegreeVector);
    double theta = Math.toRadians(openingAngle / 2);

    Vector2D leftEdgeVector = zeroDegreeVector.rotate(-rotationAngle - theta);
    Vector2D rightEdgeVector = zeroDegreeVector.rotate(-rotationAngle + theta);

    Polygon area = gf.createPolygon(
        new Coordinate[]{
            center.getCoordinate(),
            new Coordinate(center.getX() + leftEdgeVector.getX(),
                           center.getY() + leftEdgeVector.getY()
            ),
            new Coordinate(x, y),
            new Coordinate(center.getX() + rightEdgeVector.getX(),
                           center.getY() + rightEdgeVector.getY()
            ), center.getCoordinate()}
    );

    List<Point> points = pointList.stream()
        .filter(area::contains)
        .collect(Collectors.toList());
    if (points.isEmpty()) { // angle is too narrow, let's find the two closest points
      return pointList
          .stream()
          .distinct()
          .collect(Collectors.toMap(point -> point, point -> DistanceOp.distance(area, point)))
          .entrySet().stream()
          .sorted(Map.Entry.comparingByValue())
          .map(Map.Entry::getKey)
          .limit(2)
          .collect(Collectors.toList());
    }
    return points;
  }
}
