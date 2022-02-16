package com.a_smart_cookie.util.hashing;

import com.a_smart_cookie.exception.HashingException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;


/**
 * Util class for hashing password with PBKDF2 algorithm.
 * Uses salt for better security.
 *
 */
public final class PBKDF2Hash {
    private final static int HASH_LENGTH_BYTES = 64;
    private final static int SALT_LENGTH_BYTES = 16;

    private PBKDF2Hash() {
    }

    /**
     * Dto for returning Hash in pair with its generated salt.
     *
     */
    public static final class HashSaltPair {
        private final byte[] hash;
        private final byte[] salt;

        public HashSaltPair(byte[] hash, byte[] salt) {
            this.hash = hash;
            this.salt = salt;
        }

        public byte[] getHash() {
            return hash;
        }

        public byte[] getSalt() {
            return salt;
        }
    }

    /**
     * Provides with method to generate random salt.
     *
     */
    private static final class Salt {
        private static final SecureRandom secureRandom = new SecureRandom();

        private Salt() {}

        private static byte[] generateRandomSalt() {
            byte[] salt = new byte[SALT_LENGTH_BYTES];
            secureRandom.nextBytes(salt);
            return salt;
        }
    }

    /**
     * Hashes input with randomly created secure salt.
     *
     * @param input Value to be hashed.
     * @return Hash and generated salt.
     * @throws HashingException Exception while hashing.
     */
    public static HashSaltPair hash(String input) throws HashingException {
        return generateHashSaltPair(input, Salt.generateRandomSalt());
    }

    /**
     * Verifies whether input value is equal to expected one.
     * So method generates new input with salt, that used for expected value.
     * If input value in pair with that salt equals to expected value - hashes are equal.
     *
     * @param input Value to be checked for equality.
     * @param salt Salt of expectedOutput value.
     * @param expectedOutput Value to be compared with input value.
     * @return Whether hashes are equal.
     * @throws HashingException Exception while hashing.
     */
    public static boolean verifyHash(String input, byte[] salt, byte[] expectedOutput) throws HashingException {
        return Arrays.equals(expectedOutput, generateHashSaltPair(input, salt).getHash());
    }

    /**
     * Generates hash and salt pair.
     *
     * @param input Value to be hashed.
     * @param salt Value to be used as salt for hashing.
     * @return Hash and salt pair.
     * @throws HashingException Exception while hashing.
     */
    private static HashSaltPair generateHashSaltPair(String input, byte[] salt) throws HashingException {
        if (input == null) {
            throw new HashingException("Input value for hashing can't be null");
        }

        KeySpec spec = new PBEKeySpec(input.toCharArray(), salt, 65536, HASH_LENGTH_BYTES * 8);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return new HashSaltPair(factory.generateSecret(spec).getEncoded(), salt);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new HashingException("Exception occurred while hashing by " + PBKDF2Hash.class.getName(), e);
        }
    }
}
