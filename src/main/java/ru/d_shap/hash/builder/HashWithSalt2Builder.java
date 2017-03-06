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
package ru.d_shap.hash.builder;

import ru.d_shap.hash.Hash;
import ru.d_shap.hash.HashHelper;
import ru.d_shap.hash.SaltOrder;
import ru.d_shap.hash.SaltStoreType;

/**
 * Base class for all hash with the stored salt and the fixed salt builder classes.
 *
 * @param <T> type for the chain call.
 * @author Dmitry Shapovalov
 */
public abstract class HashWithSalt2Builder<T extends HashWithSalt2Builder> extends AbstractHashBuilder<T> {

    private byte[] _storedSalt;

    private byte[] _fixedSalt;

    private SaltOrder _saltOrder;

    private SaltStoreType _saltStoreType;

    private int _storedSaltLenght;

    HashWithSalt2Builder() {
        super();
        _storedSalt = null;
        _fixedSalt = null;
        _saltOrder = SaltOrder.STORED_SALT_FIRST;
        _saltStoreType = SaltStoreType.DO_NOT_STORE;
        _storedSaltLenght = 0;
    }

    /**
     * Get the stored salt.
     *
     * @return the stored salt.
     */
    public final byte[] getStoredSalt() {
        if (_storedSalt == null) {
            return null;
        } else {
            byte[] result = new byte[_storedSalt.length];
            System.arraycopy(_storedSalt, 0, result, 0, _storedSalt.length);
            return result;
        }
    }

    /**
     * Set the stored salt.
     *
     * @param storedSalt the stored salt.
     * @return current object for the chain call.
     */
    @SuppressWarnings("unchecked")
    public final T setStoredSalt(final byte[] storedSalt) {
        _storedSalt = storedSalt;
        return (T) this;
    }

    /**
     * Get the fixed salt.
     *
     * @return the fixed salt.
     */
    public final byte[] getFixedSalt() {
        return _fixedSalt;
    }

    /**
     * Set the fixed salt.
     *
     * @param fixedSalt the fixed salt.
     * @return current object for the chain call.
     */
    @SuppressWarnings("unchecked")
    public final T setFixedSalt(final byte[] fixedSalt) {
        _fixedSalt = fixedSalt;
        return (T) this;
    }

    final Hash addSalt(final Hash hash) {
        HashHelper.addSalt(hash, _storedSalt, _fixedSalt, _saltOrder);
        return hash;
    }

    final byte[] addSaltBytes(final Hash hash) {
        return HashHelper.addSaltBytes(hash.getBytes(), _storedSalt, _saltStoreType);
    }

    /**
     * Get the salt order.
     *
     * @return the salt order.
     */
    public final SaltOrder getSaltOrder() {
        return _saltOrder;
    }

    /**
     * Set the salt order.
     *
     * @param saltOrder the salt order.
     * @return current object for the chain call.
     */
    @SuppressWarnings("unchecked")
    public final T setSaltOrder(final SaltOrder saltOrder) {
        _saltOrder = saltOrder;
        return (T) this;
    }

    /**
     * Get the salt store type.
     *
     * @return the salt store type.
     */
    public final SaltStoreType getSaltStoreType() {
        return _saltStoreType;
    }

    /**
     * Set the salt store type.
     *
     * @param saltStoreType the salt store type.
     * @return current object for the chain call.
     */
    @SuppressWarnings("unchecked")
    public final T setSaltStoreType(final SaltStoreType saltStoreType) {
        _saltStoreType = saltStoreType;
        return (T) this;
    }

    /**
     * Get the stored salt length.
     *
     * @return the stored salt length.
     */
    public final int getStoredSaltLenght() {
        return _storedSaltLenght;
    }

    /**
     * Set the stored salt length.
     *
     * @param storedSaltLenght the stored salt length.
     * @return current object for the chain call.
     */
    @SuppressWarnings("unchecked")
    public final T setStoredSaltLenght(final int storedSaltLenght) {
        _storedSaltLenght = storedSaltLenght;
        return (T) this;
    }

}
