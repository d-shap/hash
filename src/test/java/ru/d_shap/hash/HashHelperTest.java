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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;

/**
 * Tests for {@link HashHelper}.
 *
 * @author Dmitry Shapovalov
 */
public final class HashHelperTest {

    /**
     * Test class constructor.
     */
    public HashHelperTest() {
        super();
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void constructorTest() {
        Assertions.assertThat(HashHelper.class).hasOnePrivateConstructor();
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void getByteArrayHashTest() {
        Assertions.assertThat(HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5).getBytes()).containsExactlyInOrder(124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104);
        Assertions.assertThat(HashHelper.getHash(new byte[]{15, 38, -17, 105}, HashAlgorithms.MD5).getBytes()).containsExactlyInOrder(-101, 7, -89, 24, -27, 126, 87, -96, 29, 71, -91, 47, -77, 123, 22, 28);
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test(expected = NullPointerException.class)
    public void getNullByteArrayHashFailTest() {
        HashHelper.getHash((byte[]) null, HashAlgorithms.MD5);
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test(expected = NullPointerException.class)
    public void getNullAlgorithmByteArrayHashFailTest() {
        HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, null);
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test(expected = HashException.class)
    public void getWrongAlgorithmByteArrayHashFailTest() {
        HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, "wrong algorithm");
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void getStringHashTest() {
        Assertions.assertThat(HashHelper.getHash("12345", "UTF-8", HashAlgorithms.MD5).getBytes()).containsExactlyInOrder(-126, 124, -53, 14, -22, -118, 112, 108, 76, 52, -95, 104, -111, -8, 78, 123);
        Assertions.assertThat(HashHelper.getHash("abc", "UTF-8", HashAlgorithms.MD5).getBytes()).containsExactlyInOrder(-112, 1, 80, -104, 60, -46, 79, -80, -42, -106, 63, 125, 40, -31, 127, 114);
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test(expected = NullPointerException.class)
    public void getNullStringHashFailTest() {
        HashHelper.getHash(null, "UTF-8", HashAlgorithms.MD5);
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test(expected = NullPointerException.class)
    public void getNullEncodingStringHashFailTest() {
        HashHelper.getHash("12345", null, HashAlgorithms.MD5);
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test(expected = HashException.class)
    public void getWrongEncodingStringHashFailTest() {
        HashHelper.getHash("12345", "wrong encoding", HashAlgorithms.MD5);
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test(expected = NullPointerException.class)
    public void getNullAlgorithmStringHashFailTest() {
        HashHelper.getHash("12345", "UTF-8", null);
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test(expected = HashException.class)
    public void getWrongAlgorithmStringHashFailTest() {
        HashHelper.getHash("12345", "UTF-8", "wrong algorithm");
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void getStreamHashTest() {
        Assertions.assertThat(HashHelper.getHash(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5}), HashAlgorithms.MD5).getBytes()).containsExactlyInOrder(124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104);
        Assertions.assertThat(HashHelper.getHash(new ByteArrayInputStream(new byte[]{15, 38, -17, 105}), HashAlgorithms.MD5).getBytes()).containsExactlyInOrder(-101, 7, -89, 24, -27, 126, 87, -96, 29, 71, -91, 47, -77, 123, 22, 28);
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test(expected = NullPointerException.class)
    public void getNullStreamHashFailTest() {
        HashHelper.getHash((InputStream) null, HashAlgorithms.MD5);
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test(expected = HashException.class)
    public void getErrorStreamHashFailTest() {
        HashHelper.getHash(new ErrorInputStream(), HashAlgorithms.MD5);
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test(expected = NullPointerException.class)
    public void getNullAlgorithmStreamHashFailTest() {
        HashHelper.getHash(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5}), null);
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test(expected = HashException.class)
    public void getWrongAlgorithmStreamHashFailTest() {
        HashHelper.getHash(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5}), "wrong algorithm");
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void addSaltBytesTest() {
        Assertions.assertThat(HashHelper.addSaltBytes(new byte[]{1, 2, 3}, new byte[]{10, 11, 12}, SaltStoreType.DO_NOT_STORE)).containsExactlyInOrder(1, 2, 3);
        Assertions.assertThat(HashHelper.addSaltBytes(new byte[]{1, 2, 3}, new byte[]{10, 11, 12}, SaltStoreType.AT_THE_BEGINNING)).containsExactlyInOrder(10, 11, 12, 1, 2, 3);
        Assertions.assertThat(HashHelper.addSaltBytes(new byte[]{1, 2, 3}, new byte[]{10, 11, 12}, SaltStoreType.AT_THE_END)).containsExactlyInOrder(1, 2, 3, 10, 11, 12);
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test(expected = NullPointerException.class)
    public void addNullHashSaltBytesFailTest() {
        HashHelper.addSaltBytes(null, new byte[]{10, 11, 12}, SaltStoreType.AT_THE_BEGINNING);
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test(expected = NullPointerException.class)
    public void addNullSaltBytesFailTest() {
        HashHelper.addSaltBytes(new byte[]{1, 2, 3}, null, SaltStoreType.AT_THE_BEGINNING);
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test(expected = NullPointerException.class)
    public void addNullSaltStoreTypeSaltBytesFailTest() {
        HashHelper.addSaltBytes(new byte[]{1, 2, 3}, new byte[]{10, 11, 12}, null);
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void getSaltBytesTest() {
        Assertions.assertThat(HashHelper.getSaltBytes(new byte[]{1, 2, 3}, SaltStoreType.DO_NOT_STORE, 3)).containsExactlyInOrder();
        Assertions.assertThat(HashHelper.getSaltBytes(new byte[]{1, 2, 3}, SaltStoreType.DO_NOT_STORE, 0)).containsExactlyInOrder();
        Assertions.assertThat(HashHelper.getSaltBytes(new byte[]{1, 2, 3}, SaltStoreType.DO_NOT_STORE, -1)).containsExactlyInOrder();
        Assertions.assertThat(HashHelper.getSaltBytes(new byte[]{10, 11, 12, 1, 2, 3}, SaltStoreType.AT_THE_BEGINNING, 3)).containsExactlyInOrder(10, 11, 12);
        Assertions.assertThat(HashHelper.getSaltBytes(new byte[]{10, 11, 12, 1, 2, 3}, SaltStoreType.AT_THE_BEGINNING, 0)).containsExactlyInOrder();
        Assertions.assertThat(HashHelper.getSaltBytes(new byte[]{1, 2, 3, 10, 11, 12}, SaltStoreType.AT_THE_END, 3)).containsExactlyInOrder(10, 11, 12);
        Assertions.assertThat(HashHelper.getSaltBytes(new byte[]{1, 2, 3, 10, 11, 12}, SaltStoreType.AT_THE_END, 0)).containsExactlyInOrder();
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test(expected = NullPointerException.class)
    public void getNullArraySaltBytesFailTest() {
        HashHelper.getSaltBytes(null, SaltStoreType.AT_THE_BEGINNING, 3);
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test(expected = NullPointerException.class)
    public void getNullSaltStoreTypeSaltBytesFailTest() {
        HashHelper.getSaltBytes(new byte[]{10, 11, 12, 1, 2, 3}, null, 3);
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test(expected = NegativeArraySizeException.class)
    public void getNegativeLengthSaltBytesFailTest() {
        HashHelper.getSaltBytes(new byte[]{10, 11, 12, 1, 2, 3}, SaltStoreType.AT_THE_BEGINNING, -1);
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void getHashBytesTest() {
        Assertions.assertThat(HashHelper.getHashBytes(new byte[]{1, 2, 3}, SaltStoreType.DO_NOT_STORE, 3)).containsExactlyInOrder(1, 2, 3);
        Assertions.assertThat(HashHelper.getHashBytes(new byte[]{1, 2, 3}, SaltStoreType.DO_NOT_STORE, 0)).containsExactlyInOrder(1, 2, 3);
        Assertions.assertThat(HashHelper.getHashBytes(new byte[]{1, 2, 3}, SaltStoreType.DO_NOT_STORE, -1)).containsExactlyInOrder(1, 2, 3);
        Assertions.assertThat(HashHelper.getHashBytes(new byte[]{10, 11, 12, 1, 2, 3}, SaltStoreType.AT_THE_BEGINNING, 3)).containsExactlyInOrder(1, 2, 3);
        Assertions.assertThat(HashHelper.getHashBytes(new byte[]{10, 11, 12, 1, 2, 3}, SaltStoreType.AT_THE_BEGINNING, 0)).containsExactlyInOrder(10, 11, 12, 1, 2, 3);
        Assertions.assertThat(HashHelper.getHashBytes(new byte[]{1, 2, 3, 10, 11, 12}, SaltStoreType.AT_THE_END, 3)).containsExactlyInOrder(1, 2, 3);
        Assertions.assertThat(HashHelper.getHashBytes(new byte[]{1, 2, 3, 10, 11, 12}, SaltStoreType.AT_THE_END, 0)).containsExactlyInOrder(1, 2, 3, 10, 11, 12);
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test(expected = NullPointerException.class)
    public void getNullArrayHashBytesFailTest() {
        HashHelper.getHashBytes(null, SaltStoreType.AT_THE_BEGINNING, 3);
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test(expected = NullPointerException.class)
    public void getNullSaltStoreTypeHashBytesFailTest() {
        HashHelper.getHashBytes(new byte[]{10, 11, 12, 1, 2, 3}, null, 3);
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void getNegativeLengthHashBytesFailTest() {
        HashHelper.getHashBytes(new byte[]{10, 11, 12, 1, 2, 3}, SaltStoreType.AT_THE_BEGINNING, -1);
    }

    /**
     * Input stream implementation, that throws exceptions.
     *
     * @author Dmitry Shapovalov
     */
    private static final class ErrorInputStream extends InputStream {

        ErrorInputStream() {
            super();
        }

        @Override
        public int read() throws IOException {
            throw new IOException("read exception");
        }

    }

}
