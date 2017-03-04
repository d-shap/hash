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
 * Enum defines how the salt is stored along with the hash.
 *
 * @author Dmitry Shapovalov
 */
public enum SaltStoreType {

    DO_NOT_STORE {
        @Override
        public byte[] addSaltBytes(final byte[] hash, final byte[] salt) {
            byte[] result = new byte[hash.length];
            System.arraycopy(hash, 0, result, 0, hash.length);
            return result;
        }

        @Override
        public byte[] getSaltBytes(final byte[] bytes, final int saltLength) {
            int length = bytes.length;
            return new byte[bytes.length - length];
        }

        @Override
        public byte[] getHashBytes(final byte[] bytes, final int saltLength) {
            byte[] result = new byte[bytes.length];
            System.arraycopy(bytes, 0, result, 0, bytes.length);
            return result;
        }
    },

    AT_THE_BEGINNING {
        @Override
        public byte[] addSaltBytes(final byte[] hash, final byte[] salt) {
            byte[] result = new byte[hash.length + salt.length];
            System.arraycopy(salt, 0, result, 0, salt.length);
            System.arraycopy(hash, 0, result, salt.length, hash.length);
            return result;
        }

        @Override
        public byte[] getSaltBytes(final byte[] bytes, final int saltLength) {
            byte[] salt = new byte[saltLength];
            System.arraycopy(bytes, 0, salt, 0, saltLength);
            return salt;
        }

        @Override
        public byte[] getHashBytes(final byte[] bytes, final int saltLength) {
            int hashLength = bytes.length - saltLength;
            byte[] hash = new byte[hashLength];
            System.arraycopy(bytes, saltLength, hash, 0, hashLength);
            return hash;
        }
    },

    AT_THE_END {
        @Override
        public byte[] addSaltBytes(final byte[] hash, final byte[] salt) {
            byte[] result = new byte[hash.length + salt.length];
            System.arraycopy(hash, 0, result, 0, hash.length);
            System.arraycopy(salt, 0, result, hash.length, salt.length);
            return result;
        }

        @Override
        public byte[] getSaltBytes(final byte[] bytes, final int saltLength) {
            byte[] salt = new byte[saltLength];
            System.arraycopy(bytes, bytes.length - saltLength, salt, 0, saltLength);
            return salt;
        }

        @Override
        public byte[] getHashBytes(final byte[] bytes, final int saltLength) {
            int hashLength = bytes.length - saltLength;
            byte[] hash = new byte[hashLength];
            System.arraycopy(bytes, 0, hash, 0, hashLength);
            return hash;
        }
    };

    /**
     * Add the salt bytes to the result array of bytes.
     *
     * @param hash the hash bytes.
     * @param salt the salt bytes.
     * @return the result array of bytes.
     */
    public abstract byte[] addSaltBytes(byte[] hash, byte[] salt);

    /**
     * Get the salt bytes from the array of bytes.
     *
     * @param bytes      the array of bytes.
     * @param saltLength the salt length.
     * @return the salt bytes.
     */
    public abstract byte[] getSaltBytes(byte[] bytes, int saltLength);

    /**
     * Get the hash bytes from the array of bytes.
     *
     * @param bytes      the array of bytes.
     * @param saltLength the salt length.
     * @return the hash bytes.
     */
    public abstract byte[] getHashBytes(byte[] bytes, int saltLength);

}
