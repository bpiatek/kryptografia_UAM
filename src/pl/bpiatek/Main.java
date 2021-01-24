package pl.bpiatek;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.valueOf;

import pl.bpiatek.modules.models.KeyPair;
import pl.bpiatek.modules.models.Point;
import pl.bpiatek.modules.models.PointsPair;
import pl.bpiatek.modules.modeulethree.ModuleThree;

import java.math.BigInteger;

public class Main {

  public static void main(String[] args) {

    System.out.println("Generowanie pary kluczy...");
    KeyPair keyPair = ModuleThree.elgamalKeysGenerator(300);
    System.out.println(keyPair);

    Point m = ModuleThree.encode(keyPair.getPrivateKey().getEllipticCurve(), valueOf(1421412));
    System.out.println("\n\nMESSAGE: " + m);

//    PointsPair pointsPair = ModuleThree.encrypt(keyPair.getPrivateKey().getP(), keyPair.getPublicKey());
//    System.out.println("\n\n\n" + pointsPair);
//
//    Point decrypt = ModuleThree.decrypt(pointsPair, keyPair.getPrivateKey());
//    System.out.println("\n\n\nDecrypted: " + decrypt);
//
//    BigInteger decode = ModuleThree.decode(decrypt);
//    System.out.println("\n\n\nDECODED: " + decode);
  }
}
