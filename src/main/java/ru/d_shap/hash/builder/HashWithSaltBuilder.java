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
import ru.d_shap.hash.SaltStoreType;
import ru.d_shap.hash.WrongArgumentException;

/**
 * Base class for all hash with the salt builder classes.
 *
 * @author Dmitry Shapovalov
 */
public abstract class HashWithSaltBuilder extends AbstractHashBuilder<HashWithSaltBuilder> {

    private byte[] _salt;

    private SaltStoreType _saltStoreType;

    HashWithSaltBuilder() {
        super();
        _salt = null;
        _saltStoreType = SaltStoreType.DO_NOT_STORE;
    }

    /**
     * Get the salt.
     *
     * @return the salt.
     */
    public final byte[] getSalt() {
        if (_salt == null) {
            return null;
        } else {
            byte[] result = new byte[_salt.length];
            System.arraycopy(_salt, 0, result, 0, _salt.length);
            return result;
        }
    }

    /**
     * Set the salt.
     *
     * @param salt the salt.
     * @return current object for the chain call.
     */
    public final HashWithSaltBuilder setSalt(final byte[] salt) {
        if (salt == null) {
            _salt = null;
        } else {
            _salt = new byte[salt.length];
            System.arraycopy(salt, 0, _salt, 0, salt.length);
        }
        return this;
    }

    final Hash addSalt(final Hash hash) {
        if (hash == null) {
            throw new WrongArgumentException("Hash is null");
        }
        hash.addSalt(_salt);
        return hash;
    }

    final byte[] addSaltBytes(final Hash hash) {
        if (hash == null) {
            throw new WrongArgumentException("Hash is null");
        }
        return HashHelper.addSaltBytes(hash.getBytes(), _salt, _saltStoreType);
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
    public final HashWithSaltBuilder setSaltStoreType(final SaltStoreType saltStoreType) {
        _saltStoreType = saltStoreType;
        return this;
    }

}
