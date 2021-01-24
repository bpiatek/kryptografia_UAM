package pl.bpiatek.modules.models;

/**
 * Created by Bartosz Piatek on 23/01/2021
 */
public class PublicKey {
  private EllipticCurve ellipticCurve;
  private Point p;
  private Point q;

  public PublicKey(EllipticCurve ellipticCurve, Point p, Point q) {
    this.ellipticCurve = ellipticCurve;
    this.p = p;
    this.q = q;
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

  @Override
  public String toString() {
    return "PublicKey {" +
           "\n  p=" + p +
           "\n  q=" + q +
           "\n  ellipticCurve = " + ellipticCurve +
           "\n}";
  }
}
