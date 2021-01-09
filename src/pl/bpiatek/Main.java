package pl.bpiatek;

import pl.bpiatek.modules.models.EllipticCurve;
import pl.bpiatek.modules.models.Point;
import pl.bpiatek.modules.moduletwo.ModuleTwo;

public class Main {

  public static void main(String[] args) {

    // ZAD. 1
    final EllipticCurve ellipticCurve = ModuleTwo.generateEllipticCurve(null);
    System.out.println(ellipticCurve);

    // ZAD. 2
    final Point pointOnCurve = ModuleTwo.randomPointOnEllipticCurve(ellipticCurve);
    System.out.println(pointOnCurve);

    // ZAD. 3
    final boolean ellipticCurvePoint = ModuleTwo.isEllipticCurvePoint(ellipticCurve, pointOnCurve);
    System.out.println(ellipticCurvePoint);

    // ZAD. 4
    final Point pointNegated = ModuleTwo.ellipticPointNegation(pointOnCurve, ellipticCurve);
    System.out.println(pointNegated);

    // ZAD. 5
    // P + P
    final Point pointPP = ModuleTwo.pPlusP(pointOnCurve, ellipticCurve);
    System.out.println(pointPP);

    // P + Q
    final Point pointQ = ModuleTwo.pPlusQ(pointPP, pointOnCurve, ellipticCurve);
    System.out.println(pointQ);
  }
}
