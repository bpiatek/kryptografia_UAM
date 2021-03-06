package pl.bpiatek.modules.moduleone;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TWO;
import static java.math.BigInteger.ZERO;
import static java.math.RoundingMode.FLOOR;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

/**
 * Created by Bartosz Piatek on 09/01/2021
 */
public class ModuleOne {

  private ModuleOne() {
  }

  // 1. Zaimplementuj funkcję, która generuje losowy element zbioru Zn.
  public static BigInteger rand(int numBits) {
    if (numBits <= 0) {
      return ZERO;
    }
    Random random = new Random();
    return new BigInteger(numBits, random);
  }

  // 2. Zaimplementuj funkcję obliczania odwrotności w grupie Φ(n). Wykorzystaj Rozszerzony Algorytm Euklidesa.
  public static BigInteger modInv(BigInteger n, BigInteger element) {
    BigInteger A = n;
    BigInteger B = element.mod(n);
    BigInteger U = BigInteger.ZERO;
    BigInteger V = BigInteger.ONE;

    while(!B.equals(BigInteger.ZERO)) {
      BigInteger q = A.divide(B);
      BigInteger A_copy = A;
      A = B;
      B = A_copy.subtract(q.multiply(B));
      BigInteger U_copy = U;
      U = V;
      V = U_copy.subtract(q.multiply(V));
    }

//    System.out.println(String.format("modInv n=%d element=%d eq %d", n, element, U.mod(n)));
    return U.mod(n);
  }

  // 3. Zaimplementuj funkcję efektywnego potęgowania w zbiorze Zn. Wykorzystaj algorytm iterowanego podnoszenia do kwadratu.
  public static BigInteger fastPowerModulo(BigInteger n, BigInteger element, BigInteger exponent) {
    BigInteger y = ONE;
    String bits = exponent.toString(2);
    for (Character bit : bits.toCharArray()) {
      y = y.pow(2).mod(n);
      if (bit == '1') {
        y = y.multiply(element).mod(n);
      }
    }

    return y;
  }

  // 4. Kryterium Eulera
  public static boolean isSquareReminder(BigInteger p, BigInteger element) {
    if(!isPrime(p)) {
      throw new RuntimeException("Not a prime");
    }
    BigInteger powerModulo = fastPowerModulo(p, element, p.subtract(ONE).divide(TWO));

    final boolean equals = powerModulo.compareTo(ONE) == 0;
    return equals;
  }


  // 5. 5. Zaimplementuj algorytm (funkcję), który oblicza pierwiastek kwadratowy w ciele Z∗p,
  // gdzie p ≡ 3 (mod 4) jest liczbą pierwszą. Wykorzystaj twierdzenie Eulera.
  public static BigInteger squareRootMod(BigInteger p, BigInteger element) {
    if (!isPrime(p)) {
      throw new RuntimeException("p must be prime");
    }
    BigInteger exponent = p.add(ONE).divide(BigInteger.valueOf(4));
    return fastPowerModulo(p, element, exponent);
  }

  //# Zadanie 6 - Wykorzystaj test pierwszości
  public static boolean isPrime(BigInteger b) {
    return b.isProbablePrime(100);
  }
}
