package pl.bpiatek.modules.moduletwo;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static java.math.BigInteger.valueOf;
import static pl.bpiatek.modules.moduleone.ModuleOne.*;
import static pl.bpiatek.modules.moduletwo.ModuleTwoHelper.calculateDelta;
import static pl.bpiatek.modules.moduletwo.ModuleTwoHelper.functionExcerciseTwo;
import static pl.bpiatek.modules.moduletwo.ModuleTwoHelper.randomBigIntegerInRange;

import pl.bpiatek.modules.models.EllipticCurve;
import pl.bpiatek.modules.models.Point;
import pl.bpiatek.modules.moduleone.ModuleOne;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by Bartosz Piatek on 09/01/2021
 */
public class ModuleTwo {

  private ModuleTwo() {
  }

  /*
      1. Zaimplementuj algorytm (funkcję), która generuje losową krzywą eliptyczną nad Fp.
      Dane: p= 3 (mod 4) duża liczba pierwsza (ok. 300 bitów)
      Wynik:A, B∈Fp takie, że E:Y2=X3+AX+B jest krzywą nad F
  */
  public static EllipticCurve generateEllipticCurve(BigDecimal randPrimeFromUser) {
    System.out.println("ZADANIE 1:\n");
    // losuj liczbe około 300 bitów
    BigInteger random = ModuleOne.rand(300);
    BigInteger hugeRandomPrime = valueOf(4L).multiply(random).add(valueOf(3));
    hugeRandomPrime = new BigInteger("1241282861446847839170228926026828716450703184678734522350890097344181755440518117399239645529943");

    // jesli nie jest liczba pierwsza to jeszcze raz losuj
    if (!isPrime(hugeRandomPrime)) {
      System.out.println("Number: " + hugeRandomPrime + " IS NOT prime. Generate again.");
      generateEllipticCurve(null);
    }

    while (true) {
      // losujemy 2 liczby nalezace do mod hugeRandomPrime
      BigInteger A = randomBigIntegerInRange(ZERO, hugeRandomPrime.subtract(ONE));
      BigInteger B = randomBigIntegerInRange(ZERO, hugeRandomPrime.subtract(ONE));

      A = new BigInteger("251253937428096535381749615186704996827671824623713534194869720190371340712257034260791734664079");
      B = new BigInteger("468890980645387444649294984369242921655474302273793566616065070624554918380706937944353524200293");

      // obliczamy delte / wyroznik
      BigInteger delta = calculateDelta(hugeRandomPrime, A, B);

      // jesli delta == 0 to jeszcze raz
      if (delta.equals(ZERO)) {
        continue;
      }

      System.out.println("DELTA: " + delta);
      return new EllipticCurve(hugeRandomPrime, A, B);
    }
  }

  /*
      2. Zaimplementuj algorytm (funkcję), który znajduje losowy punkt na krzywej eliptycznej nad Fp.
      Dane:A, B, p= 3 (mod 4) takie, że E:Y2=X3+AX+B jest krzywą nad Fp
      Wynik:P= (x, y)∈E (Fp)
   */
  public static Point randomPointOnEllipticCurve(EllipticCurve ellipticCurve) {
    System.out.println("\nZadanie 2:\n");
    System.out.println("P: " + ellipticCurve.getP());

    while (true) {
      // losujemy X nalezace do krzywej
      BigInteger X = randomBigIntegerInRange(ZERO, ellipticCurve.getP().subtract(ONE));
      X = new BigInteger("586161351238789257385941437290446893879498726098291302221253657079196940069133555438529832611354");

      // obliczamy funkcje // X^3+AX+B % P
      final BigInteger fx = functionExcerciseTwo(
          X,
          ellipticCurve.getA(),
          ellipticCurve.getB(),
          ellipticCurve.getP()
      );

      // sprawdzamy czy Fx jest reszta kwadratowa
      if (isSquareReminder(ellipticCurve.getP(), fx)) {
        continue;
      }

      System.out.println("Fx: " + fx);

      // obliczamy y^2 f(x) mod p i pierwiastkujemy
      BigInteger Y = squareRootMod(ellipticCurve.getP(), fx.mod(ellipticCurve.getP()));

      return new Point(X, Y);
    }
  }

  // 3 - does point belong to elliptic curve
  public static boolean isEllipticCurvePoint(EllipticCurve ellipticCurve, Point point) {
    System.out.println("\nZadanie 3:\n");

    //czy lewa strona jest równa prawej? wzór y^2 = x^3 + ax + b mod p
    BigInteger left = point.getY()
        .pow(2)
        .mod(ellipticCurve.getP());

    BigInteger right = point.getX()
        .pow(3)
        .add(ellipticCurve.getA().multiply(point.getX()))
        .add(ellipticCurve.getB())
        .mod(ellipticCurve.getP());

    return left.equals(right);
  }

  /*
      4. Zaimplementuj algorytm (funkcję), który oblicza punkt przeciwny do danego punktu.
      Dane: P=(x, y)∈E(Fp)
      Wynik:−P= (x,−y)∈E(Fp)
   */
  public static Point ellipticPointNegation(Point point, EllipticCurve ellipticCurve) {
    System.out.println("\nZADANIE 4:\n");
    return new Point(point.getX(), point.getY().negate().mod(ellipticCurve.getP()));
  }

  /*
      5. Zaimplementuj algorytm (funkcję), która oblicza P⊕Q sumę punktów krzywej eliptycznych. Zaimplementuj wszystkie przypadki.
      Dane: P= (x1, y1), Q= (x2, y2)∈E(Fp) oraz A, B, p= 3 (mod 4) takie, że E:Y2=X3+AX+B jest krzywą nad Fp
      Wynik:R= (x3, y3)∈E(Fp) taki, że R=P⊕Q.
   */
  // P + P
  public static Point pPlusP(Point pp, EllipticCurve ellipticCurve) {
    System.out.println("\nZADANIE 5: P + P\n");

    // obliczamy lambde λ = (3x1^2 + A)(2y1)^−1 mod p
    BigInteger firstBraces = valueOf(3).multiply(pp.getX().pow(2)).add(ellipticCurve.getA());
    BigInteger secondBraces = modInv(
        new BigDecimal(ellipticCurve.getP()),
        new BigDecimal(valueOf(2).multiply(pp.getY()))
    );

    BigInteger lambda = firstBraces.multiply(secondBraces).mod(ellipticCurve.getP());

    // x3 = λ^2 − 2x1 (mod p)
    BigInteger x3 = lambda.pow(2).subtract(valueOf(2).multiply(pp.getX())).mod(ellipticCurve.getP());

    // y3 = λ(x1 − x3) − y1 (mod p)
    BigInteger y3 = lambda.multiply(pp.getX().subtract(x3)).subtract(pp.getY()).mod(ellipticCurve.getP());

    return new Point(x3, y3);
  }

  // P + Q
  public static Point pPlusQ(Point pp, Point q, EllipticCurve ellipticCurve) {
    System.out.println("\nP + Q\n");

    // obliczamy lambde λ = (y2 − y1)(x2 − x1)^−1 (mod p)
    BigInteger firstBraces = q.getY().subtract(pp.getY());
    BigInteger secondBraces = modInv(
        new BigDecimal(ellipticCurve.getP()),
        new BigDecimal(q.getX().subtract(pp.getX()))
    );

    BigInteger lambda = firstBraces.multiply(secondBraces).mod(ellipticCurve.getP());

    // x3 = λ^2 − x1 − x2 (mod p)
    BigInteger x3 = lambda.pow(2).subtract(pp.getX()).subtract(q.getX()).mod(ellipticCurve.getP());

    // y3 = λ(x1 − x3) − y1 (mod p)
    BigInteger y3 = lambda.multiply(pp.getX().subtract(x3)).subtract(pp.getY()).mod(ellipticCurve.getP());

    return new Point(x3, y3);
  }
}

/*
************************************ WYNIK *************************
*
ZADANIE 1:

DELTA: 1235871594970807399078663401777967260663713982281852853102537632553578197762204865050873723820440
EllipticCurve {
  p=1241282861446847839170228926026828716450703184678734522350890097344181755440518117399239645529943,
  A=251253937428096535381749615186704996827671824623713534194869720190371340712257034260791734664079,
  B=468890980645387444649294984369242921655474302273793566616065070624554918380706937944353524200293
}

Zadanie 2:

P: 1241282861446847839170228926026828716450703184678734522350890097344181755440518117399239645529943
Fx: 939278965290377032944572765487704469144741146065755443707751440989400440948953261013624428552919
Point {
  X=586161351238789257385941437290446893879498726098291302221253657079196940069133555438529832611354
  Y=1156580549852279693265006155780305457792386297629688203574066863960942522017275757181344594268816
}

Zadanie 3:

true

ZADANIE 4:

Point {
  X=586161351238789257385941437290446893879498726098291302221253657079196940069133555438529832611354
  Y=84702311594568145905222770246523258658316887049046318776823233383239233423242360217895051261127
}

ZADANIE 5: P + P

Point {
  X=370228494009168351721842919930425190103631897314844985066413561905312740621887869937532869291448
  Y=643296330160511565382047279714471697071867496320037506299554868554940670142541755147508759924278
}

P + Q

Point {
  X=59647576204021668451168831264814211439828472392404176181766906292223353611986671208647849163219
  Y=82167055580010464650818319707093963138270225417821459149941820889873847364517858963434894277123
}
 */
