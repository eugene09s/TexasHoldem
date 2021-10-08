package com.epam.poker.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LineHasher {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String ENCRYPTION_ALGORITHM = "SHA-256";
    private static final int ONE = 1;
    private static final String FORMAT_LINE = "%064x";
    private static final LineHasher instance = new LineHasher();

    public String hashingLine(String line) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ENCRYPTION_ALGORITHM);
            messageDigest.update(line.getBytes(StandardCharsets.UTF_8));
            byte[] digest = messageDigest.digest();
            return String.format(FORMAT_LINE, new BigInteger(ONE, digest));
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Hash algorithm error: " + e);
        }
        return null;
    }
}
