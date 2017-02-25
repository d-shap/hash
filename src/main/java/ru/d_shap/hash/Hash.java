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
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * Class to perform hash computations.
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
     * Add the salt to the current hash.
     *
     * @param salt the salt.
     */
    public void addSalt(final byte[] salt) {
        _messageDigest.reset();
        _messageDigest.update(_currentHash);
        _messageDigest.update(salt);
        _currentHash = _messageDigest.digest();
    }

    /**
     * Add the salt to the current hash.
     *
     * @param salt     the salt.
     * @param encoding the encoding of the salt.
     */
    public void addSalt(final String salt, final String encoding) {
        try {
            addSalt(salt.getBytes(encoding));
        } catch (IOException ex) {
            throw new HashException(ex);
        }
    }

    /**
     * Get the current hash bytes.
     *
     * @return the hash.
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
        return Arrays.equals(_currentHash, hash);
    }

}
