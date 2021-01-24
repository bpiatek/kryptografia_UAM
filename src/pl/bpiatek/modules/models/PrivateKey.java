package pl.bpiatek.modules.models;

import java.math.BigInteger;

/**
 * Created by Bartosz Piatek on 23/01/2021
 */
public class PrivateKey {
  private EllipticCurve ellipticCurve;
  private Point p;
  private Point q;
  private BigInteger x;

  public PrivateKey(EllipticCurve ellipticCurve, Point p, Point q, BigInteger x) {
    this.ellipticCurve = ellipticCurve;
    this.p = p;
    this.q = q;
    this.x = x;
  }

  public EllipticCurve getEllipticCurve() {
    return ellipticCurve;
  }

  public Point getP() {
    return p;
  }

  public Point getQ() {
    return q;
  }

  public BigInteger getX() {
    return x;
  }

  @Override
  public String toString() {
    return "PrivateKey {" +
           "\n  p=" + p +
           "\n  q=" + q +
           "\n  x=" + x +
           "\n  ellipticCurve = " + ellipticCurve +
           "\n}";
  }
}
