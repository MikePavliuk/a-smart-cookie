package com.a_smart_cookie.util.hashing;

import com.a_smart_cookie.exception.HashingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PBKDF2HashTesting {

    private final String input = "Test12345!";

    @Test
    void givenNull_whenHashing_thenThrowHashingException() {
        assertThrows(HashingException.class,
                () -> PBKDF2Hash.hash(null));
    }

    @Test
    void givenInputString_whenHashing_thenReturnHashWithLengthEqualsTo64Bytes() throws HashingException {
        assertEquals(64, PBKDF2Hash.hash(input).getHash().length);
    }

    @Test
    void givenSameSaltsButDifferentInputStrings_whenVerifyingHashesEquality_thenReturnFalse() throws HashingException {
        PBKDF2Hash.HashSaltPair hashSaltPair = PBKDF2Hash.hash(input);
        assertFalse(PBKDF2Hash.verifyHash(input.substring(0, input.length()-1), hashSaltPair.getSalt(), hashSaltPair.getHash()));
    }

    @Test
    void givenSameSaltsAndInputStrings_whenVerifyingHashesEquality_thenReturnTrue() throws HashingException {
        PBKDF2Hash.HashSaltPair hashSaltPair = PBKDF2Hash.hash(input);
        assertTrue(PBKDF2Hash.verifyHash(input, hashSaltPair.getSalt(), hashSaltPair.getHash()));
    }

}
