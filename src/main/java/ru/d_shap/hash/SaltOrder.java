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

/**
 * Enum defines, how stored salt and fixed salt are processed.
 *
 * @author Dmitry Shapovalov
 */
public enum SaltOrder {

    STORED_SALT_FIRST {
        @Override
        public void addSalt(final Hash hash, final byte[] storedSalt, final byte[] fixedSalt) {
            hash.addSalt(storedSalt).addSalt(fixedSalt);
        }
    },

    FIXED_SALT_FIRST {
        @Override
        public void addSalt(final Hash hash, final byte[] storedSalt, final byte[] fixedSalt) {
            hash.addSalt(fixedSalt).addSalt(storedSalt);
        }
    };

    /**
     * Add the salt to the hash in the specified order.
     *
     * @param hash       the hash.
     * @param storedSalt the stored salt.
     * @param fixedSalt  the fixed salt.
     */
    public abstract void addSalt(Hash hash, byte[] storedSalt, byte[] fixedSalt);

}
