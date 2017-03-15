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
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
        if (bytes == null) {
            throw new WrongArgumentException("Source byte array is null");
        }
        if (algorithm == null) {
            throw new WrongArgumentException("Algorithm is null");
        }
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
        if (str == null) {
            throw new WrongArgumentException("Source string is null");
        }
        if (encoding == null) {
            throw new WrongArgumentException("Source string encoding is null");
        }
        try {
            byte[] strBytes = str.getBytes(encoding);
            return getHash(strBytes, algorithm);
        } catch (UnsupportedEncodingException ex) {
            throw new WrongArgumentException("Wrong source string encoding: " + encoding, ex);
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
        if (stream == null) {
            throw new WrongArgumentException("Source stream is null");
        }
        if (algorithm == null) {
            throw new WrongArgumentException("Algorithm is null");
        }
        MessageDigest messageDigest = createMessageDigest(algorithm);
        updateMessageDigest(messageDigest, stream);
        return new Hash(messageDigest);
    }

    private static MessageDigest createMessageDigest(final String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException ex) {
            throw new WrongArgumentException("Wrong algorithm name: " + algorithm, ex);
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
            throw new RuntimeIOException(ex);
        }
    }

    /**
     * Add the specified salt to the specified hash in the specified order.
     *
     * @param hash       the hash object.
     * @param storedSalt the stored salt.
     * @param fixedSalt  the fixed salt.
     * @param saltOrder  the stored salt and the fixed salt order.
     */
    public static void addSalt(final Hash hash, final byte[] storedSalt, final byte[] fixedSalt, final SaltOrder saltOrder) {
        if (hash == null) {
            throw new WrongArgumentException("Hash is null");
        }
        if (storedSalt == null) {
            throw new WrongArgumentException("Stored salt is null");
        }
        if (fixedSalt == null) {
            throw new WrongArgumentException("Fixed salt is null");
        }
        if (saltOrder == null) {
            throw new WrongArgumentException("Salt order is null");
        }
        saltOrder.addSalt(hash, storedSalt, fixedSalt);
    }

    /**
     * Store the salt bytes in the result array of bytes along with the hash bytes.
     *
     * @param hash          the hash bytes.
     * @param salt          the salt bytes.
     * @param saltStoreType how to store the salt bytes.
     * @return the result array of bytes.
     */
    public static byte[] addSaltBytes(final byte[] hash, final byte[] salt, final SaltStoreType saltStoreType) {
        if (hash == null) {
            throw new WrongArgumentException("Hash byte array is null");
        }
        if (salt == null) {
            throw new WrongArgumentException("Salt byte array is null");
        }
        if (saltStoreType == null) {
            throw new WrongArgumentException("Salt store type is null");
        }
        return saltStoreType.addSaltBytes(hash, salt);
    }

    /**
     * Get the hash bytes from the the array of bytes.
     *
     * @param bytes         the array of bytes.
     * @param saltStoreType how the salt bytes are stored.
     * @param saltLength    the number of the salt bytes.
     * @return the hash bytes.
     */
    public static byte[] getHashBytes(final byte[] bytes, final SaltStoreType saltStoreType, final int saltLength) {
        if (bytes == null) {
            throw new WrongArgumentException("Byte array is null");
        }
        if (saltStoreType == null) {
            throw new WrongArgumentException("Salt store type is null");
        }
        if (saltLength < 0 || saltLength >= bytes.length) {
            throw new WrongArgumentException("Salt length is not within [0; " + bytes.length + ")");
        }
        return saltStoreType.getHashBytes(bytes, saltLength);
    }

    /**
     * Get the salt bytes from the the array of bytes.
     *
     * @param bytes         the array of bytes.
     * @param saltStoreType how the salt bytes are stored.
     * @param saltLength    the number of the salt bytes.
     * @return the salt bytes.
     */
    public static byte[] getSaltBytes(final byte[] bytes, final SaltStoreType saltStoreType, final int saltLength) {
        if (bytes == null) {
            throw new WrongArgumentException("Byte array is null");
        }
        if (saltStoreType == null) {
            throw new WrongArgumentException("Salt store type is null");
        }
        if (saltLength < 0 || saltLength >= bytes.length) {
            throw new WrongArgumentException("Salt length is not within [0; " + bytes.length + ")");
        }
        return saltStoreType.getSaltBytes(bytes, saltLength);
    }

}
