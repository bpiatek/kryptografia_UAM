package pl.bpiatek.modules.models;

import java.math.BigInteger;

/**
 * Created by Bartosz Piatek on 09/01/2021
 */
public class Point {

  private final BigInteger X;
  private final BigInteger Y;

  public Point(BigInteger x, BigInteger y) {
    X = x;
    Y = y;
  }

  public BigInteger getX() {
    return X;
  }

  public BigInteger getY() {
    return Y;
  }

  @Override
  public String toString() {
    return "Point {" +
           "\n  X=" + X +
           "\n  Y=" + Y +
           "\n}";
  }
}
