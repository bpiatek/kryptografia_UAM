package pl.bpiatek.modules.moduletwo;

import static java.math.BigInteger.valueOf;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by Bartosz Piatek on 09/01/2021
 */
class ModuleTwoHelper {

  private ModuleTwoHelper() {
  }

  // ******************************* ZAD 1 *******************************
  // 4 A^3 + 27 B^2 mod p
  static BigInteger calculateDelta(BigInteger hugeRandomPrime, BigInteger A, BigInteger B) {
    final BigInteger a = A.pow(3);
    final BigInteger left = valueOf(4).multiply(a);

    BigInteger b = B.pow(2);
    final BigInteger right = valueOf(27).multiply(b);

    final BigInteger add = left.add(right);

    return add.mod(hugeRandomPrime);
  }

  static BigInteger randomBigIntegerInRange(BigInteger min, BigInteger max) {
    BigInteger number = max.subtract(min);
    Random randNum = new Random();
    int length = max.bitLength();
    BigInteger result = new BigInteger(length, randNum);

    if (result.compareTo(min) < 0) {
      result = result.add(min);
    }

    if (result.compareTo(number) >= 0) {
      result = result.mod(number).add(min);
    }

    return result;
  }

  // ******************************* ZAD 2 *******************************
  // X3+AX+B % P
  public static BigInteger functionExcerciseTwo(BigInteger X, BigInteger A, BigInteger B, BigInteger hugePrime) {
    BigInteger xPow3 = X.pow(3);
    BigInteger aMulX = A.multiply(X);
    BigInteger addition = xPow3.add(aMulX).add(B);

    return addition.mod(hugePrime);
  }
}
