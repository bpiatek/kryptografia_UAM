package pl.bpiatek.modules.models;

/**
 * Created by Bartosz Piatek on 23/01/2021
 */
public class PointsPair {
  private Point c1;
  private Point c2;

  public PointsPair(Point c1, Point c2) {
    this.c1 = c1;
    this.c2 = c2;
  }

  public Point getC1() {
    return c1;
  }

  public Point getC2() {
    return c2;
  }

  @Override
  public String toString() {
    return "PointsPair {" +
           "\n  c1=" + c1 +
           "\n  c2=" + c2 +
           "\n}";
  }
}
