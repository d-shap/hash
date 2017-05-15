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
    @Test
    public void getNullByteArrayHashFailTest() {
        try {
            HashHelper.getHash((byte[]) null, HashAlgorithms.MD5);
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Source byte array is null");
        }
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void getNullAlgorithmByteArrayHashFailTest() {
        try {
            HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, null);
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Algorithm is null");
        }
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void getWrongAlgorithmByteArrayHashFailTest() {
        try {
            HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, "wrong algorithm");
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong algorithm name: wrong algorithm");
        }
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
    @Test
    public void getNullStringHashFailTest() {
        try {
            HashHelper.getHash(null, "UTF-8", HashAlgorithms.MD5);
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Source string is null");
        }
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void getNullEncodingStringHashFailTest() {
        try {
            HashHelper.getHash("12345", null, HashAlgorithms.MD5);
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Source string encoding is null");
        }
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void getWrongEncodingStringHashFailTest() {
        try {
            HashHelper.getHash("12345", "wrong encoding", HashAlgorithms.MD5);
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong source string encoding: wrong encoding");
        }
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void getNullAlgorithmStringHashFailTest() {
        try {
            HashHelper.getHash("12345", "UTF-8", null);
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Algorithm is null");
        }
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void getWrongAlgorithmStringHashFailTest() {
        try {
            HashHelper.getHash("12345", "UTF-8", "wrong algorithm");
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong algorithm name: wrong algorithm");
        }
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void getStreamHashTest() {
        Assertions.assertThat(HashHelper.getHash(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5}), HashAlgorithms.MD5).getBytes()).containsExactlyInOrder(124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104);
        Assertions.assertThat(HashHelper.getHash(new ByteArrayInputStream(new byte[]{15, 38, -17, 0, 105}), HashAlgorithms.MD5).getBytes()).containsExactlyInOrder(-81, 85, 33, 121, -50, -12, 26, 12, 7, -71, -104, -96, -63, 15, 73, 98);
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void getNullStreamHashFailTest() {
        try {
            HashHelper.getHash((InputStream) null, HashAlgorithms.MD5);
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Source stream is null");
        }
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void getErrorStreamHashFailTest() {
        try {
            HashHelper.getHash(new ErrorInputStream(), HashAlgorithms.MD5);
            Assertions.fail("HashHelper test fail");
        } catch (HashIOException ex) {
            Assertions.assertThat(ex).isCauseInstanceOf(IOException.class);
        }
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void closeStreamHashTest() {
        CloseStream stream = new CloseStream();
        HashHelper.getHash(stream, HashAlgorithms.MD5);
        Assertions.assertThat(stream.isClosed()).isTrue();
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void getNullAlgorithmStreamHashFailTest() {
        try {
            HashHelper.getHash(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5}), null);
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Algorithm is null");
        }
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void getWrongAlgorithmStreamHashFailTest() {
        try {
            HashHelper.getHash(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5}), "wrong algorithm");
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong algorithm name: wrong algorithm");
        }
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void addStoredSaltFirstTest() {
        Hash hash = HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5);
        HashHelper.addSalt(hash, new byte[]{10, 11}, new byte[]{20, 21}, SaltOrder.STORED_SALT_FIRST);
        Assertions.assertThat(hash.getBytes()).containsExactlyInOrder(-122, 12, -120, 10, -103, 109, 69, 100, 64, 125, 79, 99, 25, -37, -87, -127);
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void addNullHashStoredSaltFirstFailTest() {
        try {
            HashHelper.addSalt(null, new byte[]{10, 11}, new byte[]{20, 21}, SaltOrder.STORED_SALT_FIRST);
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Hash is null");
        }
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void addNullStoredSaltFirstFailTest() {
        try {
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5);
            HashHelper.addSalt(hash, null, new byte[]{20, 21}, SaltOrder.STORED_SALT_FIRST);
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Stored salt is null");
        }
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void addNullFixedSaltStoredSaltFirstFailTest() {
        try {
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5);
            HashHelper.addSalt(hash, new byte[]{10, 11}, null, SaltOrder.STORED_SALT_FIRST);
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Fixed salt is null");
        }
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void addFixedSaltFirstTest() {
        Hash hash = HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5);
        HashHelper.addSalt(hash, new byte[]{10, 11}, new byte[]{20, 21}, SaltOrder.FIXED_SALT_FIRST);
        Assertions.assertThat(hash.getBytes()).containsExactlyInOrder(124, -1, -105, -22, 35, 1, 122, 57, 121, -46, 112, -40, -7, 90, -120, 23);
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void addNullHashFixedSaltFirstFailTest() {
        try {
            HashHelper.addSalt(null, new byte[]{10, 11}, new byte[]{20, 21}, SaltOrder.FIXED_SALT_FIRST);
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Hash is null");
        }
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void addNullStoredSaltFixedSaltFirstFailTest() {
        try {
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5);
            HashHelper.addSalt(hash, null, new byte[]{20, 21}, SaltOrder.FIXED_SALT_FIRST);
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Stored salt is null");
        }
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void addNullFixedSaltFirstFailTest() {
        try {
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5);
            HashHelper.addSalt(hash, new byte[]{10, 11}, null, SaltOrder.FIXED_SALT_FIRST);
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Fixed salt is null");
        }
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void addNullSaltOrderFailTest() {
        try {
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5);
            HashHelper.addSalt(hash, new byte[]{10, 11}, new byte[]{20, 21}, null);
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt order is null");
        }
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
    @Test
    public void addNullHashSaltBytesFailTest() {
        try {
            HashHelper.addSaltBytes(null, new byte[]{10, 11, 12}, SaltStoreType.AT_THE_BEGINNING);
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Hash byte array is null");
        }
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void addNullSaltBytesFailTest() {
        try {
            HashHelper.addSaltBytes(new byte[]{1, 2, 3}, null, SaltStoreType.AT_THE_BEGINNING);
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt byte array is null");
        }
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void addNullSaltStoreTypeSaltBytesFailTest() {
        try {
            HashHelper.addSaltBytes(new byte[]{1, 2, 3}, new byte[]{10, 11, 12}, null);
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt store type is null");
        }
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void getHashBytesTest() {
        Assertions.assertThat(HashHelper.getHashBytes(new byte[]{1, 2, 3}, SaltStoreType.DO_NOT_STORE, 1)).containsExactlyInOrder(1, 2, 3);
        Assertions.assertThat(HashHelper.getHashBytes(new byte[]{1, 2, 3}, SaltStoreType.DO_NOT_STORE, 0)).containsExactlyInOrder(1, 2, 3);
        Assertions.assertThat(HashHelper.getHashBytes(new byte[]{10, 11, 12, 1, 2, 3}, SaltStoreType.AT_THE_BEGINNING, 3)).containsExactlyInOrder(1, 2, 3);
        Assertions.assertThat(HashHelper.getHashBytes(new byte[]{10, 11, 12, 1, 2, 3}, SaltStoreType.AT_THE_BEGINNING, 0)).containsExactlyInOrder(10, 11, 12, 1, 2, 3);
        Assertions.assertThat(HashHelper.getHashBytes(new byte[]{1, 2, 3, 10, 11, 12}, SaltStoreType.AT_THE_END, 3)).containsExactlyInOrder(1, 2, 3);
        Assertions.assertThat(HashHelper.getHashBytes(new byte[]{1, 2, 3, 10, 11, 12}, SaltStoreType.AT_THE_END, 0)).containsExactlyInOrder(1, 2, 3, 10, 11, 12);
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void getNullArrayHashBytesFailTest() {
        try {
            HashHelper.getHashBytes(null, SaltStoreType.AT_THE_BEGINNING, 3);
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Byte array is null");
        }
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void getNullSaltStoreTypeHashBytesFailTest() {
        try {
            HashHelper.getHashBytes(new byte[]{10, 11, 12, 1, 2, 3}, null, 3);
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt store type is null");
        }
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void getNegativeLengthHashBytesFailTest() {
        try {
            HashHelper.getHashBytes(new byte[]{10, 11, 12, 1, 2, 3}, SaltStoreType.AT_THE_BEGINNING, -1);
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt length is not within [0; 6)");
        }
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void getTooLargeLengthHashBytesFailTest() {
        try {
            HashHelper.getHashBytes(new byte[]{10, 11, 12, 1, 2, 3}, SaltStoreType.AT_THE_BEGINNING, 6);
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt length is not within [0; 6)");
        }
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void getSaltBytesTest() {
        Assertions.assertThat(HashHelper.getSaltBytes(new byte[]{1, 2, 3}, SaltStoreType.DO_NOT_STORE, 1)).containsExactlyInOrder();
        Assertions.assertThat(HashHelper.getSaltBytes(new byte[]{1, 2, 3}, SaltStoreType.DO_NOT_STORE, 0)).containsExactlyInOrder();
        Assertions.assertThat(HashHelper.getSaltBytes(new byte[]{10, 11, 12, 1, 2, 3}, SaltStoreType.AT_THE_BEGINNING, 3)).containsExactlyInOrder(10, 11, 12);
        Assertions.assertThat(HashHelper.getSaltBytes(new byte[]{10, 11, 12, 1, 2, 3}, SaltStoreType.AT_THE_BEGINNING, 0)).containsExactlyInOrder();
        Assertions.assertThat(HashHelper.getSaltBytes(new byte[]{1, 2, 3, 10, 11, 12}, SaltStoreType.AT_THE_END, 3)).containsExactlyInOrder(10, 11, 12);
        Assertions.assertThat(HashHelper.getSaltBytes(new byte[]{1, 2, 3, 10, 11, 12}, SaltStoreType.AT_THE_END, 0)).containsExactlyInOrder();
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void getNullArraySaltBytesFailTest() {
        try {
            HashHelper.getSaltBytes(null, SaltStoreType.AT_THE_BEGINNING, 3);
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Byte array is null");
        }
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void getNullSaltStoreTypeSaltBytesFailTest() {
        try {
            HashHelper.getSaltBytes(new byte[]{10, 11, 12, 1, 2, 3}, null, 3);
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt store type is null");
        }
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void getNegativeLengthSaltBytesFailTest() {
        try {
            HashHelper.getSaltBytes(new byte[]{10, 11, 12, 1, 2, 3}, SaltStoreType.AT_THE_BEGINNING, -1);
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt length is not within [0; 6)");
        }
    }

    /**
     * {@link HashHelper} class test.
     */
    @Test
    public void getTooLargeLengthSaltBytesFailTest() {
        try {
            HashHelper.getSaltBytes(new byte[]{10, 11, 12, 1, 2, 3}, SaltStoreType.AT_THE_BEGINNING, 6);
            Assertions.fail("HashHelper test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt length is not within [0; 6)");
        }
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

    /**
     * Input stream to test close method.
     *
     * @author Dmitry Shapovalov
     */
    private static final class CloseStream extends InputStream {

        private boolean _closed;

        CloseStream() {
            super();
            _closed = false;
        }

        @Override
        public int read() throws IOException {
            return -1;
        }

        boolean isClosed() {
            return _closed;
        }

        @Override
        public void close() throws IOException {
            super.close();
            _closed = true;
        }

    }

}
