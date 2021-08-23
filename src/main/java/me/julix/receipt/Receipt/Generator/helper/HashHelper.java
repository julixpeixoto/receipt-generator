package me.julix.receipt.Receipt.Generator.helper;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashHelper {
    public static String getHashMd5(String value) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        BigInteger hash = new BigInteger(1, md.digest(value.getBytes()));
        return hash.toString(16);
    }
}
