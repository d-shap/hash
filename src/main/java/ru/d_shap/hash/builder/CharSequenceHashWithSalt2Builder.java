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
import ru.d_shap.hash.HashHelper;
import ru.d_shap.hash.SaltStoreType;

/**
 * Hash builder for the string.
 *
 * @author Dmitry Shapovalov
 */
final class CharSequenceHashWithSalt2Builder extends HashWithSalt2Builder {

    private final CharSequence _charSequence;

    private final String _encoding;

    CharSequenceHashWithSalt2Builder(final CharSequence charSequence, final String encoding) {
        super();
        _charSequence = charSequence;
        _encoding = encoding;
    }

    @Override
    public byte[] getHash() {
        Hash hash = addSalt(HashHelper.getHash(_charSequence, _encoding, getAlgorithm()));
        return addSaltBytes(hash);
    }

    @Override
    public boolean isHashValid() {
        if (getSaltStoreType() == SaltStoreType.DO_NOT_STORE) {
            return matches(addSalt(HashHelper.getHash(_charSequence, _encoding, getAlgorithm())));
        } else {
            Hash hash = HashHelper.getHash(_charSequence, _encoding, getAlgorithm());
            int storedSaltLength = getStoredSaltLength(hash.getLength());
            byte[] storedHash = getHashFromStoredHash(getSaltStoreType(), storedSaltLength);
            byte[] storedSalt = getSaltFromStoredHash(getSaltStoreType(), storedSaltLength);
            return addSalt(hash, storedSalt).matches(storedHash);
        }
    }

}
