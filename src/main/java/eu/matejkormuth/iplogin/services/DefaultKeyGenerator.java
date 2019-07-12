/**
 * iplogin - Login to your Minecraft server seamlessly
 * Copyright (c) 2015, Matej Kormuth <http://www.github.com/dobrakmato>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package eu.matejkormuth.iplogin.services;

import eu.matejkormuth.iplogin.api.services.KeyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;

public class DefaultKeyGenerator implements KeyGenerator {

    private static final Logger log = LoggerFactory.getLogger(DefaultKeyGenerator.class);

    /**
     * Default alphabet used in keys generation.
     */
    private static final char[] DEFAULT_ALPHABET =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTVUWXYZ0123456789".toCharArray();

    private final int length;
    private final char[] alphabet;
    private final SecureRandom random;

    /**
     * Creates new key generator using default alphabet with default key length of 16 characters.
     */
    public DefaultKeyGenerator() {
        this(16, DEFAULT_ALPHABET);
    }

    /**
     * Creates new key generator using default alphabet with specified key length.
     *
     * @param length length of keys
     */
    public DefaultKeyGenerator(int length) {
        this(length, DEFAULT_ALPHABET);
    }

    /**
     * Creates new key generator using specified alphabet with specified key length.
     *
     * @param length   length of keys
     * @param alphabet alphabet for key generation
     */
    public DefaultKeyGenerator(int length, char[] alphabet) {
        this.length = length;
        this.alphabet = alphabet;
        random = new SecureRandom();
    }

    /**
     * Generates random key with properties set by this generator.
     *
     * @return next random key
     */
    @Override
    public String generate() {
        char[] key = new char[this.length];

        for (int i = 0; i < this.length; i++) {
            key[i] = alphabet[random.nextInt(this.length)];
        }

        return new String(key);
    }
}
