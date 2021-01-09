package pl.bpiatek.modules.models;

import java.math.BigInteger;

/**
 * Created by Bartosz Piatek on 09/01/2021
 */
public class EllipticCurve {

  private final BigInteger p;
  private final BigInteger A;
  private final BigInteger B;

  public EllipticCurve(BigInteger p, BigInteger a, BigInteger b) {
    this.p = p;
    this.A = a;
    this.B = b;
  }

  public BigInteger getP() {
    return p;
  }

  public BigInteger getA() {
    return A;
  }

  public BigInteger getB() {
    return B;
  }

  @Override
  public String toString() {
    return "EllipticCurve {\n" +
           "  p=" + p +
           ", \n  A=" + A +
           ", \n  B=" + B +
           "\n}";
  }
}
