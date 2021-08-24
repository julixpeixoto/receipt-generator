package me.julix.receipt.Receipt.Generator.helper;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class HashHelper {
    public static String getRandomHashMd5() {
        MessageDigest md;
        try {
            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            String generatedString = new String(array, Charset.forName("UTF-8"));
            md = MessageDigest.getInstance("MD5");
            BigInteger hash = new BigInteger(1, md.digest(generatedString.getBytes()));
            return hash.toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
