///////////////////////////////////////////////////////////////////////////////////////////////////
// Hash library provides facilities for the hash computations.
// Copyright (C) 2017 Dmitry Shapovalov.
//
// This file is part of Hash library.
//
// Hash library is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// Hash library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.
///////////////////////////////////////////////////////////////////////////////////////////////////
package ru.d_shap.hash;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

/**
 * Class to perform hash computations.
 *
 * @author Dmitry Shapovalov
 */
public final class HashHelper {

    private static final int INPUT_STREAM_BUFFER_SIZE = 512;

    private HashHelper() {
        super();
    }

    /**
     * Create the hash object for the specified bytes.
     *
     * @param bytes     the specified bytes.
     * @param algorithm the hash algorithm.
     * @return the hash object.
     */
    public static Hash getHash(final byte[] bytes, final String algorithm) {
        MessageDigest messageDigest = createMessageDigest(algorithm);
        updateMessageDigest(messageDigest, bytes);
        return new Hash(messageDigest);
    }

    /**
     * Create the hash object for the specified string.
     *
     * @param str       the specified string.
     * @param encoding  the encoding of the string.
     * @param algorithm the hash algorithm.
     * @return the hash object.
     */
    public static Hash getHash(final String str, final String encoding, final String algorithm) {
        try {
            return getHash(str.getBytes(encoding), algorithm);
        } catch (IOException ex) {
            throw new HashException(ex);
        }
    }

    /**
     * Create the hash object for the specified stream of bytes.
     *
     * @param stream    the specified stream of bytes.
     * @param algorithm the hash algorithm.
     * @return the hash object.
     */
    public static Hash getHash(final InputStream stream, final String algorithm) {
        MessageDigest messageDigest = createMessageDigest(algorithm);
        updateMessageDigest(messageDigest, stream);
        return new Hash(messageDigest);
    }

    private static MessageDigest createMessageDigest(final String algorithm) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.reset();
            return messageDigest;
        } catch (GeneralSecurityException ex) {
            throw new HashException(ex);
        }
    }

    private static void updateMessageDigest(final MessageDigest messageDigest, final byte[] bytes) {
        messageDigest.update(bytes);
    }

    private static void updateMessageDigest(final MessageDigest messageDigest, final InputStream stream) {
        try {
            try {
                byte[] buffer = new byte[INPUT_STREAM_BUFFER_SIZE];
                int read;
                while (true) {
                    read = stream.read(buffer);
                    if (read <= 0) {
                        break;
                    }
                    messageDigest.update(buffer, 0, read);
                }
            } finally {
                stream.close();
            }
        } catch (IOException ex) {
            throw new HashException(ex);
        }
    }

    /**
     * Add the salt bytes to the result array of bytes before the hash bytes.
     *
     * @param hash the hash bytes.
     * @param salt the salt bytes.
     * @return the result array of bytes.
     */
    public static byte[] addSaltToTheBeginning(final byte[] hash, final byte[] salt) {
        byte[] result = new byte[hash.length + salt.length];
        System.arraycopy(salt, 0, result, 0, salt.length);
        System.arraycopy(hash, 0, result, salt.length, hash.length);
        return result;
    }

    /**
     * Add the salt bytes to the result array of bytes after the hash bytes.
     *
     * @param hash the hash bytes.
     * @param salt the salt bytes.
     * @return the result array of bytes.
     */
    public static byte[] addSaltToTheEnd(final byte[] hash, final byte[] salt) {
        byte[] result = new byte[hash.length + salt.length];
        System.arraycopy(hash, 0, result, 0, hash.length);
        System.arraycopy(salt, 0, result, hash.length, salt.length);
        return result;
    }

    /**
     * Get the salt bytes from the beginning of the array of bytes.
     *
     * @param bytes      the array of bytes.
     * @param saltLength the number of the salt bytes.
     * @return the salt bytes.
     */
    public static byte[] getSaltFromTheBeginning(final byte[] bytes, final int saltLength) {
        byte[] salt = new byte[saltLength];
        System.arraycopy(bytes, 0, salt, 0, saltLength);
        return salt;
    }

    /**
     * Get the salt bytes from the end of the array of bytes.
     *
     * @param bytes      the array of bytes.
     * @param saltLength the number of the salt bytes.
     * @return the salt bytes.
     */
    public static byte[] getSaltFromTheEnd(final byte[] bytes, final int saltLength) {
        byte[] salt = new byte[saltLength];
        System.arraycopy(bytes, bytes.length - saltLength, salt, 0, saltLength);
        return salt;
    }

    /**
     * Get the hash bytes from the beginning of the array of bytes.
     *
     * @param bytes      the array of bytes.
     * @param saltLength the number of the salt bytes.
     * @return the hash bytes.
     */
    public static byte[] getHashFromTheBeginning(final byte[] bytes, final int saltLength) {
        int hashLength = bytes.length - saltLength;
        byte[] hash = new byte[hashLength];
        System.arraycopy(bytes, 0, hash, 0, hashLength);
        return hash;
    }

    /**
     * Get the hash bytes from the end of the array of bytes.
     *
     * @param bytes      the array of bytes.
     * @param saltLength the number of the salt bytes.
     * @return the hash bytes.
     */
    public static byte[] getHashFromTheEnd(final byte[] bytes, final int saltLength) {
        int hashLength = bytes.length - saltLength;
        byte[] hash = new byte[hashLength];
        System.arraycopy(bytes, saltLength, hash, 0, hashLength);
        return hash;
    }

    static byte[] toByteArray(final int[] arr) {
        byte[] bytes = new byte[arr.length];
        for (int i = 0; i < arr.length; i++) {
            bytes[i] = (byte) arr[i];
        }
        return bytes;
    }

}
