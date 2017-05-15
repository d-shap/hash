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
public final class HashSHA1Test {

    /**
     * Test class constructor.
     */
    public HashSHA1Test() {
        super();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void simpleHashTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] hash = HashHelper.getHash(original, HashAlgorithms.SHA1).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(-24, -102, -43, -87, 99, 28, 62, -3, -34, -41, -29, -20, -50, 121, -76, -48, -2, -36, -31, -65);
        Assertions.assertThat(HashHelper.getHash(original, HashAlgorithms.SHA1).matches(hash)).isTrue();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void saltAtTheBeginningTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] salt = new byte[]{48, 49, 50};
        byte[] hash = HashHelper.getHash(original, HashAlgorithms.SHA1).addSalt(salt).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(-102, -36, 88, -126, 84, 108, 33, -114, 76, -79, -93, -86, 10, 43, -98, 37, 50, 73, -84, 103);

        byte[] full = HashHelper.addSaltBytes(hash, salt, SaltStoreType.AT_THE_BEGINNING);
        Assertions.assertThat(full).containsExactlyInOrder(48, 49, 50, -102, -36, 88, -126, 84, 108, 33, -114, 76, -79, -93, -86, 10, 43, -98, 37, 50, 73, -84, 103);

        Assertions.assertThat(HashHelper.getHash(original, HashAlgorithms.SHA1).addSalt(HashHelper.getSaltBytes(full, SaltStoreType.AT_THE_BEGINNING, 3)).matches(HashHelper.getHashBytes(full, SaltStoreType.AT_THE_BEGINNING, 3))).isTrue();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void saltAtTheEndTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] salt = new byte[]{48, 49, 50};
        byte[] hash = HashHelper.getHash(original, HashAlgorithms.SHA1).addSalt(salt).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(-102, -36, 88, -126, 84, 108, 33, -114, 76, -79, -93, -86, 10, 43, -98, 37, 50, 73, -84, 103);

        byte[] full = HashHelper.addSaltBytes(hash, salt, SaltStoreType.AT_THE_END);
        Assertions.assertThat(full).containsExactlyInOrder(-102, -36, 88, -126, 84, 108, 33, -114, 76, -79, -93, -86, 10, 43, -98, 37, 50, 73, -84, 103, 48, 49, 50);

        Assertions.assertThat(HashHelper.getHash(original, HashAlgorithms.SHA1).addSalt(HashHelper.getSaltBytes(full, SaltStoreType.AT_THE_END, 3)).matches(HashHelper.getHashBytes(full, SaltStoreType.AT_THE_END, 3))).isTrue();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void salt2AtTheBeginningTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] salt1 = new byte[]{48, 49, 50};
        byte[] salt2 = new byte[]{101, 102, 103};
        byte[] hash = HashHelper.getHash(original, HashAlgorithms.SHA1).addSalt(salt1).addSalt(salt2).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(127, 60, -10, 73, -39, -104, -15, -30, -104, 99, -68, 58, -97, 8, 16, -17, -53, 69, -110, -4);

        byte[] full = HashHelper.addSaltBytes(hash, salt1, SaltStoreType.AT_THE_BEGINNING);
        Assertions.assertThat(full).containsExactlyInOrder(48, 49, 50, 127, 60, -10, 73, -39, -104, -15, -30, -104, 99, -68, 58, -97, 8, 16, -17, -53, 69, -110, -4);

        Assertions.assertThat(HashHelper.getHash(original, HashAlgorithms.SHA1).addSalt(HashHelper.getSaltBytes(full, SaltStoreType.AT_THE_BEGINNING, 3)).addSalt(salt2).matches(HashHelper.getHashBytes(full, SaltStoreType.AT_THE_BEGINNING, 3))).isTrue();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void salt2AtTheEndTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] salt1 = new byte[]{48, 49, 50};
        byte[] salt2 = new byte[]{101, 102, 103};
        byte[] hash = HashHelper.getHash(original, HashAlgorithms.SHA1).addSalt(salt1).addSalt(salt2).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(127, 60, -10, 73, -39, -104, -15, -30, -104, 99, -68, 58, -97, 8, 16, -17, -53, 69, -110, -4);

        byte[] full = HashHelper.addSaltBytes(hash, salt1, SaltStoreType.AT_THE_END);
        Assertions.assertThat(full).containsExactlyInOrder(127, 60, -10, 73, -39, -104, -15, -30, -104, 99, -68, 58, -97, 8, 16, -17, -53, 69, -110, -4, 48, 49, 50);

        Assertions.assertThat(HashHelper.getHash(original, HashAlgorithms.SHA1).addSalt(HashHelper.getSaltBytes(full, SaltStoreType.AT_THE_END, 3)).addSalt(salt2).matches(HashHelper.getHashBytes(full, SaltStoreType.AT_THE_END, 3))).isTrue();
    }

}
