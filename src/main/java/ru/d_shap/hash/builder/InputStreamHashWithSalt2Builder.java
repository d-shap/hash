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

import java.io.InputStream;

import ru.d_shap.hash.Hash;
import ru.d_shap.hash.HashHelper;
import ru.d_shap.hash.SaltStoreType;

/**
 * Hash builder for the string.
 *
 * @author Dmitry Shapovalov
 */
final class InputStreamHashWithSalt2Builder extends HashWithSalt2Builder {

    private final InputStream _stream;

    InputStreamHashWithSalt2Builder(final InputStream stream) {
        super();
        _stream = stream;
    }

    @Override
    public byte[] getHash() {
        Hash hashObj = addSalt(HashHelper.getHash(_stream, getAlgorithm()));
        return addSaltBytes(hashObj);
    }

    @Override
    public boolean isHashValid() {
        if (getSaltStoreType() == SaltStoreType.DO_NOT_STORE) {
            return matches(addSalt(HashHelper.getHash(_stream, getAlgorithm())));
        } else {
            byte[] storedHash = getHashFromStoredHash(getSaltStoreType(), getStoredSaltLenght());
            byte[] storedSalt = getSaltFromStoredHash(getSaltStoreType(), getStoredSaltLenght());
            return addSalt(HashHelper.getHash(_stream, getAlgorithm()), storedSalt).matches(storedHash);
        }
    }

}
