package pl.bpiatek.modules.models;

/**
 * Created by Bartosz Piatek on 23/01/2021
 */
public class KeyPair {
  private PublicKey publicKey;
  private PrivateKey privateKey;

  public KeyPair(PublicKey publicKey, PrivateKey privateKey) {
    this.publicKey = publicKey;
    this.privateKey = privateKey;
  }

  public PublicKey getPublicKey() {
    return publicKey;
  }

  public PrivateKey getPrivateKey() {
    return privateKey;
  }

  @Override
  public String toString() {
    return "KeyPair {" +
           "\n  publicKey = " + publicKey +
           "\n  privateKey = " + privateKey +
           "\n}";
  }
}
