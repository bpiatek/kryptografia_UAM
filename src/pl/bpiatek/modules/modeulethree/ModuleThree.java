package pl.bpiatek.modules.modeulethree;

import static java.math.BigInteger.*;
import static java.math.MathContext.DECIMAL128;
import static java.math.RoundingMode.CEILING;

import pl.bpiatek.modules.models.*;
import pl.bpiatek.modules.moduleone.ModuleOne;
import pl.bpiatek.modules.moduletwo.ModuleTwo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

/**
 * Created by Bartosz Piatek on 23/01/2021
 */
public class ModuleThree {

  private static final int U_100 = 100;
  private static final Random random = new Random();

  // ZAD 1. Zaimplementuj algorytm generowania kluczy kryptosystemu ElGamala na krzywej eliptycznej.
  public static KeyPair elgamalKeysGenerator(int numberOfBits) {
    // generuje liczbe pierwsza
    BigInteger prime = generateHugePrime(numberOfBits);
    // losuje krzywa
    EllipticCurve ellipticCurve = ModuleTwo.generateEllipticCurve(prime);
    // losuje punkt na krzywej
    Point pointp = ModuleTwo.randomPointOnEllipticCurve(ellipticCurve);

    // 1 < x < |podloga(p + 1 - 2 pierw p)|
    BigInteger maxX = max(prime);

    BigInteger x = new BigInteger(maxX.bitLength() - 1, random);
    // if 1 < randomInRange < max
    if (!(biggerThanOne(x) && lessThanMax(x, maxX))) {
      throw new RuntimeException("Number out of range");
    }

    Point pointq = multiplyEllipticCurvePoint(ellipticCurve, pointp, x);

    PublicKey publicKey = new PublicKey(ellipticCurve, pointp, pointq);
    PrivateKey privateKey = new PrivateKey(ellipticCurve, pointp, pointq, x);

    return new KeyPair(publicKey, privateKey);
  }

  // ZAD. 2 Zaimplementuj algorytm, który oblicza wielokrotność punktu na krzywej eliptycznej.
  public static Point multiplyEllipticCurvePoint(EllipticCurve ellipticCurve, Point point, BigInteger n) {
    Point q = point;
    Point r = new Point(null, null);

    // kiedy n > 0
    while (n.compareTo(ZERO) == 1) {
      // jesli n % 2 = 1
      if (n.mod(TWO).equals(ONE)) {
        r = ModuleTwo.addPoints(r, q, ellipticCurve);
        n = n.subtract(ONE);
      }

      q = ModuleTwo.addPoints(q, q, ellipticCurve);
      n = n.divide(TWO);
    }

    return r;
  }

  // ZAD. 3 Zaimplementuj algorytm, który koduje wiadomość na punkt na krzywej eliptycznej.
  public static Point encode(EllipticCurve ellipticCurve, BigInteger m) {
    BigInteger p = ellipticCurve.getP();
    BigInteger u = valueOf(U_100);
    int uu = U_100;
    BigInteger n = p.divide(m.add(TWO));
    if (!(n.compareTo(u) == 1)) {
      throw new RuntimeException("M is to big!");
    }

    for (int j = 0; j < uu; j++) {
      // x = m * u + j mod p
      BigInteger x = m.multiply(u).add(valueOf(j)).mod(p);
      // f = x3 + Ax + b mod p
      BigInteger f = x.pow(3).add(ellipticCurve.getA().multiply(x)).add(ellipticCurve.getB()).mod(p);
      if (ModuleOne.isSquareReminder(p, f)) {
        BigInteger y = ModuleOne.squareRootMod(p, f);
        return new Point(x, y);
      }
    }

    throw new RuntimeException("Could not encrypt message");
  }

  // ZAD. 4 Zaimplementuj algorytm szyfrowania ElGamala na krzywej eliptycznej.
  public static PointsPair encrypt(Point m, PublicKey publicKey) {
    EllipticCurve ellipticCurve = publicKey.getEllipticCurve();
    // 1 < x < |podloga(p + 1 - 2 pierw p)|
    BigInteger yMax = max(ellipticCurve.getP());

    BigInteger y = new BigInteger(yMax.bitLength(), random);
    // if 1 < randomInRange < max
    if (!(biggerThanOne(y) && lessThanMax(y, yMax))) {
      throw new RuntimeException("Number out of range");
    }

    Point c1 = multiplyEllipticCurvePoint(ellipticCurve, publicKey.getP(), y);
    Point yQ = multiplyEllipticCurvePoint(ellipticCurve, publicKey.getQ(), y);
    Point c2 = ModuleTwo.pPlusQ(m, yQ, ellipticCurve);

    return new PointsPair(c1, c2);
  }

  // ZAD. 5 Zaimplementuj algorytm deszyfrowania ElGamala na krzywej eliptycznej.
  public static Point decrypt(PointsPair pointsPair, PrivateKey privateKey) {
    final EllipticCurve ellipticCurve = privateKey.getEllipticCurve();
    // oblicz xC1
    Point xC1 = multiplyEllipticCurvePoint(ellipticCurve, pointsPair.getC1(), privateKey.getX());
    // wez punkt przeciwny
    Point xC1Negation = ModuleTwo.ellipticPointNegation(xC1, ellipticCurve);
    // dodaj do C2
    return ModuleTwo.pPlusQ(pointsPair.getC2(), xC1Negation, ellipticCurve);
  }

  // ZAD. 6 Zaimplementuj algorytm, który dekoduje punkt krzywej eliptycznej.
  public static BigInteger decode(Point m, EllipticCurve ellipticCurve) {

    return m.getX().mod(ellipticCurve.getP()).divide(valueOf(U_100));
  }

  private static boolean biggerThanOne(BigInteger number) {
    return number.compareTo(ONE) == 1;
  }

  private static boolean lessThanMax(BigInteger number, BigInteger max) {
    return number.compareTo(max) == -1;
  }

  // Losowanie liczby pierwszej o k bitach i takiej, że p % 4 == 3
  private static BigInteger generateHugePrime(int numberOfBits) {

    boolean found = false;
    BigInteger prime = ZERO;

    while (!found) {
      prime = BigInteger.probablePrime(numberOfBits, random);
      if (prime.mod(valueOf(4)).compareTo(valueOf(3)) == 0) {
        found = true;
      }
    }

    return prime;
  }

  // 1 < x < |podloga(p + 1 - 2 pierw p)|
  private static BigInteger max(BigInteger number) {
   return new BigDecimal(number).add(BigDecimal.ONE)
       .subtract(new BigDecimal("2").multiply(new BigDecimal(number).sqrt(DECIMAL128))
                     .setScale(0, CEILING))
       .toBigInteger();
  }
}
