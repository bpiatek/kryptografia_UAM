package pl.bpiatek;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.valueOf;

import pl.bpiatek.modules.models.EllipticCurve;
import pl.bpiatek.modules.models.KeyPair;
import pl.bpiatek.modules.models.Point;
import pl.bpiatek.modules.models.PointsPair;
import pl.bpiatek.modules.modeulethree.ModuleThree;
import pl.bpiatek.modules.moduleone.ModuleOne;

import java.math.BigInteger;
import java.nio.channels.Pipe;

public class Main {

  public static void main(String[] args) {

    System.out.println("Generowanie pary kluczy...");
    KeyPair keyPair = ModuleThree.elgamalKeysGenerator(32);
    System.out.println(keyPair);

    BigInteger message = valueOf(100);
    System.out.println("MESSAGE: " + message);
    Point encodedMessage = ModuleThree.encode(keyPair.getPublicKey().getEllipticCurve(), message);
    System.out.println("ENCODED POINT: " + encodedMessage);

    PointsPair pointsPair = ModuleThree.encrypt(encodedMessage, keyPair.getPublicKey());
    System.out.println("ENCRYPTED POINTS PAIR" + pointsPair);

    Point decrypt = ModuleThree.decrypt(pointsPair, keyPair.getPrivateKey());
    System.out.println("DECRYPTED POINT: " + decrypt);

    BigInteger decode = ModuleThree.decode(decrypt, keyPair.getPublicKey().getEllipticCurve());
    System.out.println("DECODED: " + decode);
  }
}
