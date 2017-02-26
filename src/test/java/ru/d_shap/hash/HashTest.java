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
 * Tests for {@link Hash}.
 *
 * @author Dmitry Shapovalov
 */
public final class HashTest {

    /**
     * Test class constructor.
     */
    public HashTest() {
        super();
    }

    /**
     * {@link Hash} class test.
     */
    @Test
    public void getHashLengthTest() {
        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD2).getLength()).isEqualTo(16);
        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD2).addSalt((byte) 1, (byte) 2, (byte) 3).getLength()).isEqualTo(16);

        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5).getLength()).isEqualTo(16);
        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5).addSalt((byte) 1, (byte) 2, (byte) 3).getLength()).isEqualTo(16);

        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.SHA1).getLength()).isEqualTo(20);
        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.SHA1).addSalt((byte) 1, (byte) 2, (byte) 3).getLength()).isEqualTo(20);

        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.SHA256).getLength()).isEqualTo(32);
        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.SHA256).addSalt((byte) 1, (byte) 2, (byte) 3).getLength()).isEqualTo(32);

        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.SHA384).getLength()).isEqualTo(48);
        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.SHA384).addSalt((byte) 1, (byte) 2, (byte) 3).getLength()).isEqualTo(48);

        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.SHA512).getLength()).isEqualTo(64);
        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.SHA512).addSalt((byte) 1, (byte) 2, (byte) 3).getLength()).isEqualTo(64);
    }

    /**
     * {@link Hash} class test.
     */
    @Test
    public void addByteArraySaltTest() {
        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5).getBytes()).containsExactlyInOrder(124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104);
        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5).addSalt((byte) 49, (byte) 50, (byte) 51).getBytes()).containsExactlyInOrder(18, -111, 64, 32, 30, -51, 42, -70, 95, -77, 69, -34, 100, 100, 27, -115);
        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5).addSalt((byte) 97, (byte) 98, (byte) 99).getBytes()).containsExactlyInOrder(97, 97, -128, 25, -70, -31, -76, -59, -121, 90, 86, -81, -84, 11, -124, -55);
    }

    /**
     * {@link Hash} class test.
     */
    @Test(expected = NullPointerException.class)
    public void addNullByteArraySaltFailTest() {
        HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5).addSalt((byte[]) null);
    }

    /**
     * {@link Hash} class test.
     */
    @Test
    public void addIntArraySaltTest() {
        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5).getBytes()).containsExactlyInOrder(124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104);
        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5).addSalt(49, 50, 51).getBytes()).containsExactlyInOrder(18, -111, 64, 32, 30, -51, 42, -70, 95, -77, 69, -34, 100, 100, 27, -115);
        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5).addSalt(97, 98, 99).getBytes()).containsExactlyInOrder(97, 97, -128, 25, -70, -31, -76, -59, -121, 90, 86, -81, -84, 11, -124, -55);
    }

    /**
     * {@link Hash} class test.
     */
    @Test(expected = NullPointerException.class)
    public void addNullIntArraySaltFailTest() {
        HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5).addSalt((int[]) null);
    }

    /**
     * {@link Hash} class test.
     */
    @Test
    public void addStringSaltTest() {
        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5).getBytes()).containsExactlyInOrder(124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104);
        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5).addSalt("123", "UTF-8").getBytes()).containsExactlyInOrder(18, -111, 64, 32, 30, -51, 42, -70, 95, -77, 69, -34, 100, 100, 27, -115);
        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5).addSalt("abc", "UTF-8").getBytes()).containsExactlyInOrder(97, 97, -128, 25, -70, -31, -76, -59, -121, 90, 86, -81, -84, 11, -124, -55);
    }

    /**
     * {@link Hash} class test.
     */
    @Test(expected = NullPointerException.class)
    public void addNullStringSaltFailTest() {
        HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5).addSalt(null, "UTF-8");
    }

    /**
     * {@link Hash} class test.
     */
    @Test(expected = NullPointerException.class)
    public void addNullEncodingStringSaltFailTest() {
        HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5).addSalt("123", null);
    }

    /**
     * {@link Hash} class test.
     */
    @Test(expected = HashException.class)
    public void addWrongEncodingStringSaltFailTest() {
        HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5).addSalt("123", "wrong encoding");
    }

    /**
     * {@link Hash} class test.
     */
    @Test
    public void getBytesUnchangedTest() {
        Hash hash = HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5);
        Assertions.assertThat(hash.getBytes()).containsExactlyInOrder(124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104);
        Assertions.assertThat(hash.getBytes()).isNotSameAs(hash.getBytes());
        Assertions.assertThat(hash.getBytes()).containsExactlyInOrder(hash.getBytes());

        byte[] bytes = hash.getBytes();
        bytes[0] = 0;
        bytes[1] = 0;
        Assertions.assertThat(hash.getBytes()).containsExactlyInOrder(124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104);
    }

    /**
     * {@link Hash} class test.
     */
    @Test
    public void matchesByteArrayTest() {
        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5).matches((byte) 124, (byte) -3, (byte) -48, (byte) 120, (byte) -119, (byte) -77, (byte) 41, (byte) 93, (byte) 106, (byte) 85, (byte) 9, (byte) 20, (byte) -85, (byte) 53, (byte) -32, (byte) 104)).isTrue();

        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5).matches((byte[]) null)).isFalse();
        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5).matches((byte) 124, (byte) -3, (byte) -48, (byte) 120, (byte) -119, (byte) -77, (byte) 41, (byte) 93, (byte) 106, (byte) 85, (byte) 9, (byte) 20, (byte) -85, (byte) 53, (byte) -32)).isFalse();
        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5).matches((byte) 124, (byte) -3, (byte) -48, (byte) 120, (byte) -119, (byte) -77, (byte) 41, (byte) 93, (byte) 106, (byte) 85, (byte) 9, (byte) 20, (byte) -85, (byte) 53, (byte) -32, (byte) 105)).isFalse();
    }

    /**
     * {@link Hash} class test.
     */
    @Test
    public void matchesIntArrayTest() {
        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5).matches(124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104)).isTrue();

        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5).matches((int[]) null)).isFalse();
        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5).matches(124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32)).isFalse();
        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5).matches(124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 105)).isFalse();
    }

}
