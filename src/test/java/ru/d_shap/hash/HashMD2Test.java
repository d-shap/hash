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

import org.junit.Test;

import ru.d_shap.assertions.Assertions;

/**
 * Tests for {@link HashAlgorithms}.
 *
 * @author Dmitry Shapovalov
 */
public final class HashMD2Test {

    /**
     * Test class constructor.
     */
    public HashMD2Test() {
        super();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void simpleHashTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] hash = HashHelper.getHash(original, HashAlgorithms.MD2).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(-59, -127, -64, 108, 42, 88, -110, 7, -124, -75, 51, 51, 75, 20, -46, -62);
        Assertions.assertThat(HashHelper.getHash(original, HashAlgorithms.MD2).matches(hash)).isTrue();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void saltAtTheBeginningTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] salt = new byte[]{48, 49, 50};
        byte[] hash = HashHelper.getHash(original, HashAlgorithms.MD2).addSalt(salt).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(-63, -128, 38, 111, -50, -103, 6, -22, 15, 45, 80, -10, -69, -31, 21, 51);

        byte[] full = HashHelper.addSaltBytes(hash, salt, SaltStoreType.AT_THE_BEGINNING);
        Assertions.assertThat(full).containsExactlyInOrder(48, 49, 50, -63, -128, 38, 111, -50, -103, 6, -22, 15, 45, 80, -10, -69, -31, 21, 51);

        Assertions.assertThat(HashHelper.getHash(original, HashAlgorithms.MD2).addSalt(HashHelper.getSaltBytes(full, SaltStoreType.AT_THE_BEGINNING, 3)).matches(HashHelper.getHashBytes(full, SaltStoreType.AT_THE_BEGINNING, 3))).isTrue();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void saltAtTheEndTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] salt = new byte[]{48, 49, 50};
        byte[] hash = HashHelper.getHash(original, HashAlgorithms.MD2).addSalt(salt).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(-63, -128, 38, 111, -50, -103, 6, -22, 15, 45, 80, -10, -69, -31, 21, 51);

        byte[] full = HashHelper.addSaltBytes(hash, salt, SaltStoreType.AT_THE_END);
        Assertions.assertThat(full).containsExactlyInOrder(-63, -128, 38, 111, -50, -103, 6, -22, 15, 45, 80, -10, -69, -31, 21, 51, 48, 49, 50);

        Assertions.assertThat(HashHelper.getHash(original, HashAlgorithms.MD2).addSalt(HashHelper.getSaltBytes(full, SaltStoreType.AT_THE_END, 3)).matches(HashHelper.getHashBytes(full, SaltStoreType.AT_THE_END, 3))).isTrue();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void salt2AtTheBeginningTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] salt1 = new byte[]{48, 49, 50};
        byte[] salt2 = new byte[]{101, 102, 103};
        byte[] hash = HashHelper.getHash(original, HashAlgorithms.MD2).addSalt(salt1).addSalt(salt2).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(-4, 29, -31, 65, 53, 82, -46, -120, -49, 122, 48, 5, 13, -62, -101, 83);

        byte[] full = HashHelper.addSaltBytes(hash, salt1, SaltStoreType.AT_THE_BEGINNING);
        Assertions.assertThat(full).containsExactlyInOrder(48, 49, 50, -4, 29, -31, 65, 53, 82, -46, -120, -49, 122, 48, 5, 13, -62, -101, 83);

        Assertions.assertThat(HashHelper.getHash(original, HashAlgorithms.MD2).addSalt(HashHelper.getSaltBytes(full, SaltStoreType.AT_THE_BEGINNING, 3)).addSalt(salt2).matches(HashHelper.getHashBytes(full, SaltStoreType.AT_THE_BEGINNING, 3))).isTrue();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void salt2AtTheEndTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] salt1 = new byte[]{48, 49, 50};
        byte[] salt2 = new byte[]{101, 102, 103};
        byte[] hash = HashHelper.getHash(original, HashAlgorithms.MD2).addSalt(salt1).addSalt(salt2).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(-4, 29, -31, 65, 53, 82, -46, -120, -49, 122, 48, 5, 13, -62, -101, 83);

        byte[] full = HashHelper.addSaltBytes(hash, salt1, SaltStoreType.AT_THE_END);
        Assertions.assertThat(full).containsExactlyInOrder(-4, 29, -31, 65, 53, 82, -46, -120, -49, 122, 48, 5, 13, -62, -101, 83, 48, 49, 50);

        Assertions.assertThat(HashHelper.getHash(original, HashAlgorithms.MD2).addSalt(HashHelper.getSaltBytes(full, SaltStoreType.AT_THE_END, 3)).addSalt(salt2).matches(HashHelper.getHashBytes(full, SaltStoreType.AT_THE_END, 3))).isTrue();
    }

}
