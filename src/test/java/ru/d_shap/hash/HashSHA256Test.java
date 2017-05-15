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
package ru.d_shap.hash;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;

/**
 * Tests for {@link HashAlgorithms}.
 *
 * @author Dmitry Shapovalov
 */
public final class HashSHA256Test {

    /**
     * Test class constructor.
     */
    public HashSHA256Test() {
        super();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void simpleHashTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] hash = HashHelper.getHash(original, HashAlgorithms.SHA256).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(-102, -119, -58, -116, 76, 94, 40, -72, -60, -91, 86, 118, 115, -44, 98, -1, -11, 21, -37, 70, 17, 111, -103, 0, 98, 77, 9, -60, 116, -11, -109, -5);
        Assertions.assertThat(HashHelper.getHash(original, HashAlgorithms.SHA256).matches(hash)).isTrue();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void saltAtTheBeginningTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] salt = new byte[]{48, 49, 50};
        byte[] hash = HashHelper.getHash(original, HashAlgorithms.SHA256).addSalt(salt).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(-75, 28, 110, 64, 24, -94, 82, 61, 36, 111, -88, -5, 57, -39, -50, -75, -119, -120, 44, 78, 66, 84, 90, 34, -40, 45, -114, -96, 77, 11, -113, 90);

        byte[] full = HashHelper.addSaltBytes(hash, salt, SaltStoreType.AT_THE_BEGINNING);
        Assertions.assertThat(full).containsExactlyInOrder(48, 49, 50, -75, 28, 110, 64, 24, -94, 82, 61, 36, 111, -88, -5, 57, -39, -50, -75, -119, -120, 44, 78, 66, 84, 90, 34, -40, 45, -114, -96, 77, 11, -113, 90);

        Assertions.assertThat(HashHelper.getHash(original, HashAlgorithms.SHA256).addSalt(HashHelper.getSaltBytes(full, SaltStoreType.AT_THE_BEGINNING, 3)).matches(HashHelper.getHashBytes(full, SaltStoreType.AT_THE_BEGINNING, 3))).isTrue();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void saltAtTheEndTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] salt = new byte[]{48, 49, 50};
        byte[] hash = HashHelper.getHash(original, HashAlgorithms.SHA256).addSalt(salt).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(-75, 28, 110, 64, 24, -94, 82, 61, 36, 111, -88, -5, 57, -39, -50, -75, -119, -120, 44, 78, 66, 84, 90, 34, -40, 45, -114, -96, 77, 11, -113, 90);

        byte[] full = HashHelper.addSaltBytes(hash, salt, SaltStoreType.AT_THE_END);
        Assertions.assertThat(full).containsExactlyInOrder(-75, 28, 110, 64, 24, -94, 82, 61, 36, 111, -88, -5, 57, -39, -50, -75, -119, -120, 44, 78, 66, 84, 90, 34, -40, 45, -114, -96, 77, 11, -113, 90, 48, 49, 50);

        Assertions.assertThat(HashHelper.getHash(original, HashAlgorithms.SHA256).addSalt(HashHelper.getSaltBytes(full, SaltStoreType.AT_THE_END, 3)).matches(HashHelper.getHashBytes(full, SaltStoreType.AT_THE_END, 3))).isTrue();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void salt2AtTheBeginningTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] salt1 = new byte[]{48, 49, 50};
        byte[] salt2 = new byte[]{101, 102, 103};
        byte[] hash = HashHelper.getHash(original, HashAlgorithms.SHA256).addSalt(salt1).addSalt(salt2).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(-123, -73, -41, -41, -14, -111, 3, -116, 56, -58, 121, 110, -111, 84, -89, -22, -44, 51, -49, -88, 118, -73, 78, -53, -31, 64, 117, -71, 10, 48, -113, 83);

        byte[] full = HashHelper.addSaltBytes(hash, salt1, SaltStoreType.AT_THE_BEGINNING);
        Assertions.assertThat(full).containsExactlyInOrder(48, 49, 50, -123, -73, -41, -41, -14, -111, 3, -116, 56, -58, 121, 110, -111, 84, -89, -22, -44, 51, -49, -88, 118, -73, 78, -53, -31, 64, 117, -71, 10, 48, -113, 83);

        Assertions.assertThat(HashHelper.getHash(original, HashAlgorithms.SHA256).addSalt(HashHelper.getSaltBytes(full, SaltStoreType.AT_THE_BEGINNING, 3)).addSalt(salt2).matches(HashHelper.getHashBytes(full, SaltStoreType.AT_THE_BEGINNING, 3))).isTrue();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void salt2AtTheEndTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] salt1 = new byte[]{48, 49, 50};
        byte[] salt2 = new byte[]{101, 102, 103};
        byte[] hash = HashHelper.getHash(original, HashAlgorithms.SHA256).addSalt(salt1).addSalt(salt2).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(-123, -73, -41, -41, -14, -111, 3, -116, 56, -58, 121, 110, -111, 84, -89, -22, -44, 51, -49, -88, 118, -73, 78, -53, -31, 64, 117, -71, 10, 48, -113, 83);

        byte[] full = HashHelper.addSaltBytes(hash, salt1, SaltStoreType.AT_THE_END);
        Assertions.assertThat(full).containsExactlyInOrder(-123, -73, -41, -41, -14, -111, 3, -116, 56, -58, 121, 110, -111, 84, -89, -22, -44, 51, -49, -88, 118, -73, 78, -53, -31, 64, 117, -71, 10, 48, -113, 83, 48, 49, 50);

        Assertions.assertThat(HashHelper.getHash(original, HashAlgorithms.SHA256).addSalt(HashHelper.getSaltBytes(full, SaltStoreType.AT_THE_END, 3)).addSalt(salt2).matches(HashHelper.getHashBytes(full, SaltStoreType.AT_THE_END, 3))).isTrue();
    }

}
