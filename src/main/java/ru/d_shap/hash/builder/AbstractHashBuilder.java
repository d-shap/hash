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
package ru.d_shap.hash.builder;

import ru.d_shap.hash.Hash;
import ru.d_shap.hash.HashAlgorithms;
import ru.d_shap.hash.HashHelper;
import ru.d_shap.hash.SaltStoreType;

/**
 * Base class for all hash builder classes.
 *
 * @param <T> type for the chain call.
 * @author Dmitry Shapovalov
 */
abstract class AbstractHashBuilder<T extends AbstractHashBuilder> {

    private String _algorithm;

    private byte[] _storedHash;

    AbstractHashBuilder() {
        super();
        _algorithm = HashAlgorithms.MD5;
        _storedHash = null;
    }

    /**
     * Get the algorithm name.
     *
     * @return the algorithm name.
     */
    public final String getAlgorithm() {
        return _algorithm;
    }

    /**
     * Set the algorithm name.
     *
     * @param algorithm the algorithm name.
     * @return current object for the chain call.
     */
    @SuppressWarnings("unchecked")
    public final T setAlgorithm(final String algorithm) {
        _algorithm = algorithm;
        return (T) this;
    }

    /**
     * Get the hash bytes.
     *
     * @return the hash bytes.
     */
    public abstract byte[] getHash();

    /**
     * Get the stored hash.
     *
     * @return the stored hash.
     */
    public final byte[] getStoredHash() {
        if (_storedHash == null) {
            return null;
        } else {
            byte[] result = new byte[_storedHash.length];
            System.arraycopy(_storedHash, 0, result, 0, _storedHash.length);
            return result;
        }
    }

    /**
     * Set the stored hash.
     *
     * @param storedHash the stored hash.
     * @return current object for the chain call.
     */
    @SuppressWarnings("unchecked")
    public final T setStoredHash(final byte[] storedHash) {
        if (storedHash == null) {
            _storedHash = null;
        } else {
            _storedHash = new byte[storedHash.length];
            System.arraycopy(storedHash, 0, _storedHash, 0, storedHash.length);
        }
        return (T) this;
    }

    final int getStoredSaltLength(final int hashLength) {
        if (_storedHash == null) {
            throw new IllegalArgumentException("Stored hash is null");
        }
        return _storedHash.length - hashLength;
    }

    final boolean matches(final Hash hash) {
        if (hash == null) {
            throw new IllegalArgumentException("Hash is null");
        }
        return hash.matches(_storedHash);
    }

    final byte[] getHashFromStoredHash(final SaltStoreType saltStoreType, final int storedSaltLength) {
        return HashHelper.getHashBytes(_storedHash, saltStoreType, storedSaltLength);
    }

    final byte[] getSaltFromStoredHash(final SaltStoreType saltStoreType, final int storedSaltLength) {
        return HashHelper.getSaltBytes(_storedHash, saltStoreType, storedSaltLength);
    }

    /**
     * Check if the current hash is valid.
     *
     * @return true, if the current hash is valid.
     */
    public abstract boolean isHashValid();

}
