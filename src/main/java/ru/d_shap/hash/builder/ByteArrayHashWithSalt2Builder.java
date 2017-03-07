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

/**
 * Hash builder for the byte array.
 *
 * @author Dmitry Shapovalov
 */
final class ByteArrayHashWithSalt2Builder extends HashWithSalt2Builder<ByteArrayHashWithSalt2Builder> {

    private final byte[] _bytes;

    ByteArrayHashWithSalt2Builder(final byte[] bytes) {
        super();
        _bytes = bytes;
    }

    @Override
    public byte[] getHash() {
        Hash hashObj = addSalt(HashHelper.getHash(_bytes, getAlgorithm()));
        return addSaltBytes(hashObj);
    }

    @Override
    public boolean isHashValid() {
        if (getSaltStoreType() == SaltStoreType.DO_NOT_STORE) {
            return matches(addSalt(HashHelper.getHash(_bytes, getAlgorithm())));
        } else {
            byte[] storedHash = getHashFromStoredHash(getSaltStoreType(), getStoredSaltLenght());
            byte[] storedSalt = getSaltFromStoredHash(getSaltStoreType(), getStoredSaltLenght());
            return addSalt(HashHelper.getHash(_bytes, getAlgorithm()), storedSalt).matches(storedHash);
        }
    }

}
