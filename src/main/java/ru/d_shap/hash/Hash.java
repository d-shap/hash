///////////////////////////////////////////////////////////////////////////////////////////////////
// Hash library provides facilities for the hash computations.
// Copyright (C) 2017 Dmitry Shapovalov.
//
// This file is part of hash library.
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

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * Class contains computed hash and provides methods to work with this hash.
 *
 * @author Dmitry Shapovalov
 */
public final class Hash {

    private final MessageDigest _messageDigest;

    private byte[] _currentHash;

    Hash(final MessageDigest messageDigest) {
        super();
        _messageDigest = messageDigest;
        _currentHash = _messageDigest.digest();
    }

    /**
     * Get the hash length.
     *
     * @return the hash length.
     */
    public int getLength() {
        return _currentHash.length;
    }

    /**
     * Add the salt to the current hash.
     *
     * @param salt the salt.
     * @return current object for the chain call.
     */
    public Hash addSalt(final byte[] salt) {
        if (salt == null) {
            throw new IllegalArgumentException("Salt byte array is null");
        }
        _messageDigest.update(_currentHash);
        _messageDigest.update(salt);
        _currentHash = _messageDigest.digest();
        return this;
    }

    /**
     * Add the salt to the current hash.
     *
     * @param salt the salt.
     * @return current object for the chain call.
     */
    public Hash addSalt(final CharSequence salt) {
        return addSalt(salt, DefaultEncoding.UTF8);
    }

    /**
     * Add the salt to the current hash.
     *
     * @param salt     the salt.
     * @param encoding an encoding of the salt.
     * @return current object for the chain call.
     */
    public Hash addSalt(final CharSequence salt, final String encoding) {
        if (salt == null) {
            throw new IllegalArgumentException("Salt char sequence is null");
        }
        if (encoding == null) {
            throw new IllegalArgumentException("Salt char sequence encoding is null");
        }
        try {
            byte[] saltBytes = salt.toString().getBytes(encoding);
            return addSalt(saltBytes);
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalArgumentException("Wrong salt char sequence encoding: " + encoding, ex);
        }
    }

    /**
     * Get the current hash bytes.
     *
     * @return the hash bytes.
     */
    public byte[] getBytes() {
        byte[] result = new byte[_currentHash.length];
        System.arraycopy(_currentHash, 0, result, 0, _currentHash.length);
        return result;
    }

    /**
     * Check if the current hash bytes are equal to the specified bytes.
     *
     * @param hash the specified bytes.
     * @return true if the current hash bytes are equal to the specified bytes.
     */
    public boolean matches(final byte[] hash) {
        if (hash == null) {
            throw new IllegalArgumentException("Hash byte array is null");
        }
        return Arrays.equals(_currentHash, hash);
    }

}
