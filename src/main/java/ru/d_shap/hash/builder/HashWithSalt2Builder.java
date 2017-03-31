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
import ru.d_shap.hash.WrongArgumentException;

/**
 * Hash with the stored salt and the fixed salt builder.
 *
 * @author Dmitry Shapovalov
 */
public abstract class HashWithSalt2Builder extends AbstractHashBuilder<HashWithSalt2Builder> {

    private byte[] _storedSalt;

    private byte[] _fixedSalt;

    private SaltOrder _saltOrder;

    private SaltStoreType _saltStoreType;

    HashWithSalt2Builder() {
        super();
        _storedSalt = null;
        _fixedSalt = null;
        _saltOrder = SaltOrder.STORED_SALT_FIRST;
        _saltStoreType = SaltStoreType.DO_NOT_STORE;
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
    public final HashWithSalt2Builder setStoredSalt(final byte[] storedSalt) {
        if (storedSalt == null) {
            _storedSalt = null;
        } else {
            _storedSalt = new byte[storedSalt.length];
            System.arraycopy(storedSalt, 0, _storedSalt, 0, storedSalt.length);
        }
        return this;
    }

    /**
     * Get the fixed salt.
     *
     * @return the fixed salt.
     */
    public final byte[] getFixedSalt() {
        if (_fixedSalt == null) {
            return null;
        } else {
            byte[] result = new byte[_fixedSalt.length];
            System.arraycopy(_fixedSalt, 0, result, 0, _fixedSalt.length);
            return result;
        }
    }

    /**
     * Set the fixed salt.
     *
     * @param fixedSalt the fixed salt.
     * @return current object for the chain call.
     */
    public final HashWithSalt2Builder setFixedSalt(final byte[] fixedSalt) {
        if (fixedSalt == null) {
            _fixedSalt = null;
        } else {
            _fixedSalt = new byte[fixedSalt.length];
            System.arraycopy(fixedSalt, 0, _fixedSalt, 0, fixedSalt.length);
        }
        return this;
    }

    final Hash addSalt(final Hash hash) {
        HashHelper.addSalt(hash, _storedSalt, _fixedSalt, _saltOrder);
        return hash;
    }

    final Hash addSalt(final Hash hash, final byte[] storedSalt) {
        HashHelper.addSalt(hash, storedSalt, _fixedSalt, _saltOrder);
        return hash;
    }

    final byte[] addSaltBytes(final Hash hash) {
        if (hash == null) {
            throw new WrongArgumentException("Hash is null");
        }
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
    public final HashWithSalt2Builder setSaltOrder(final SaltOrder saltOrder) {
        _saltOrder = saltOrder;
        return this;
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
    public final HashWithSalt2Builder setSaltStoreType(final SaltStoreType saltStoreType) {
        _saltStoreType = saltStoreType;
        return this;
    }

}
