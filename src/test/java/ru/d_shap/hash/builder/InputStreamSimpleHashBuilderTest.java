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

import java.io.ByteArrayInputStream;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.hash.Hash;
import ru.d_shap.hash.HashAlgorithms;
import ru.d_shap.hash.HashHelper;
import ru.d_shap.hash.SaltStoreType;

/**
 * Tests for {@link InputStreamSimpleHashBuilder}.
 *
 * @author Dmitry Shapovalov
 */
public final class InputStreamSimpleHashBuilderTest {

    /**
     * Test class constructor.
     */
    public InputStreamSimpleHashBuilderTest() {
        super();
    }

    /**
     * {@link InputStreamSimpleHashBuilder} class test.
     */
    @Test
    public void getAlgorithmTest() {
        Assertions.assertThat(new InputStreamSimpleHashBuilder(null).getAlgorithm()).isEqualTo(HashAlgorithms.MD5);
        Assertions.assertThat(new InputStreamSimpleHashBuilder(null).setAlgorithm("value").getAlgorithm()).isEqualTo("value");
    }

    /**
     * {@link InputStreamSimpleHashBuilder} class test.
     */
    @Test
    public void setAlgorithmTest() {
        InputStreamSimpleHashBuilder builder = new InputStreamSimpleHashBuilder(null);
        builder.setAlgorithm("value");
        Assertions.assertThat(builder.getAlgorithm()).isEqualTo("value");
        builder.setAlgorithm(HashAlgorithms.MD5);
        Assertions.assertThat(builder.getAlgorithm()).isEqualTo(HashAlgorithms.MD5);
        builder.setAlgorithm(null);
        Assertions.assertThat(builder.getAlgorithm()).isNull();
    }

    /**
     * {@link InputStreamSimpleHashBuilder} class test.
     */
    @Test
    public void getStoredHashTest() {
        Assertions.assertThat(new InputStreamSimpleHashBuilder(null).getStoredHash()).isNull();
        Assertions.assertThat(new InputStreamSimpleHashBuilder(null).setStoredHash(new byte[]{1, 2, 3}).getStoredHash()).containsExactlyInOrder(1, 2, 3);
    }

    /**
     * {@link InputStreamSimpleHashBuilder} class test.
     */
    @Test
    public void setStoredHashTest() {
        InputStreamSimpleHashBuilder builder = new InputStreamSimpleHashBuilder(null);
        builder.setStoredHash(new byte[]{1, 2, 3});
        Assertions.assertThat(builder.getStoredHash()).containsExactlyInOrder(1, 2, 3);
        builder.setStoredHash(new byte[]{10, 11});
        Assertions.assertThat(builder.getStoredHash()).containsExactlyInOrder(10, 11);
        builder.setStoredHash(new byte[]{});
        Assertions.assertThat(builder.getStoredHash()).containsExactlyInOrder();
        builder.setStoredHash(null);
        Assertions.assertThat(builder.getStoredHash()).isNull();
    }

    /**
     * {@link InputStreamSimpleHashBuilder} class test.
     */
    @Test
    public void storedHashDifferentTest() {
        InputStreamSimpleHashBuilder builder = new InputStreamSimpleHashBuilder(null);
        byte[] storedHash = new byte[]{1, 2, 3};
        builder.setStoredHash(storedHash);
        byte[] storedHash1 = builder.getStoredHash();
        byte[] storedHash2 = builder.getStoredHash();
        Assertions.assertThat(storedHash1).isNotSameAs(storedHash);
        Assertions.assertThat(storedHash2).isNotSameAs(storedHash);
        Assertions.assertThat(storedHash1).isNotSameAs(storedHash2);
        Assertions.assertThat(storedHash1).containsExactlyInOrder(storedHash);
        Assertions.assertThat(storedHash2).containsExactlyInOrder(storedHash);
    }

    /**
     * {@link InputStreamSimpleHashBuilder} class test.
     */
    @Test
    public void getStoredSaltLengthTest() {
        InputStreamSimpleHashBuilder builder = new InputStreamSimpleHashBuilder(null);
        builder.setStoredHash(new byte[]{1, 2, 3, 4, 5});
        Assertions.assertThat(builder.getStoredSaltLength(0)).isEqualTo(5);
        Assertions.assertThat(builder.getStoredSaltLength(1)).isEqualTo(4);
        Assertions.assertThat(builder.getStoredSaltLength(4)).isEqualTo(1);
        Assertions.assertThat(builder.getStoredSaltLength(5)).isEqualTo(0);
        Assertions.assertThat(builder.getStoredSaltLength(7)).isEqualTo(-2);
        Assertions.assertThat(builder.getStoredSaltLength(-3)).isEqualTo(8);
    }

    /**
     * {@link InputStreamSimpleHashBuilder} class test.
     */
    @Test
    public void getNullStoredHashStoredSaltLengthFailTest() {
        try {
            new InputStreamSimpleHashBuilder(null).getStoredSaltLength(1);
            Assertions.fail("InputStreamSimpleHashBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Stored hash is null");
        }
    }

    /**
     * {@link InputStreamSimpleHashBuilder} class test.
     */
    @Test
    public void matchesTest() {
        InputStreamSimpleHashBuilder builder = new InputStreamSimpleHashBuilder(null);
        builder.setStoredHash(new byte[]{82, -119, -33, 115, 125, -11, 115, 38, -4, -35, 34, 89, 122, -5, 31, -84});
        Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
        Assertions.assertThat(builder.matches(hash)).isTrue();
    }

    /**
     * {@link InputStreamSimpleHashBuilder} class test.
     */
    @Test
    public void nullHashMatchesFailTest() {
        try {
            InputStreamSimpleHashBuilder builder = new InputStreamSimpleHashBuilder(null);
            builder.setStoredHash(new byte[]{82, -119, -33, 115, 125, -11, 115, 38, -4, -35, 34, 89, 122, -5, 31, -84});
            builder.matches(null);
            Assertions.fail("InputStreamSimpleHashBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Hash is null");
        }
    }

    /**
     * {@link InputStreamSimpleHashBuilder} class test.
     */
    @Test
    public void nullStoredHashMatchesFailTest() {
        try {
            InputStreamSimpleHashBuilder builder = new InputStreamSimpleHashBuilder(null);
            builder.setStoredHash(null);
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
            Assertions.assertThat(builder.matches(hash)).isTrue();
            Assertions.fail("InputStreamSimpleHashBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Hash byte array is null");
        }
    }

    /**
     * {@link InputStreamSimpleHashBuilder} class test.
     */
    @Test
    public void getHashFromStoredHashTest() {
        InputStreamSimpleHashBuilder builder = new InputStreamSimpleHashBuilder(null);
        byte[] storedHash = new byte[]{1, 2, 3};
        builder.setStoredHash(storedHash);
        Assertions.assertThat(builder.getHashFromStoredHash(SaltStoreType.DO_NOT_STORE, 0)).containsExactlyInOrder(1, 2, 3);
        Assertions.assertThat(builder.getHashFromStoredHash(SaltStoreType.DO_NOT_STORE, 1)).containsExactlyInOrder(1, 2, 3);
        Assertions.assertThat(builder.getHashFromStoredHash(SaltStoreType.AT_THE_BEGINNING, 1)).containsExactlyInOrder(2, 3);
        Assertions.assertThat(builder.getHashFromStoredHash(SaltStoreType.AT_THE_BEGINNING, 2)).containsExactlyInOrder(3);
        Assertions.assertThat(builder.getHashFromStoredHash(SaltStoreType.AT_THE_END, 1)).containsExactlyInOrder(1, 2);
        Assertions.assertThat(builder.getHashFromStoredHash(SaltStoreType.AT_THE_END, 2)).containsExactlyInOrder(1);
    }

    /**
     * {@link InputStreamSimpleHashBuilder} class test.
     */
    @Test
    public void getHashFromNullStoredHashFailTest() {
        try {
            InputStreamSimpleHashBuilder builder = new InputStreamSimpleHashBuilder(null);
            builder.getHashFromStoredHash(SaltStoreType.DO_NOT_STORE, 0);
            Assertions.fail("InputStreamSimpleHashBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Byte array is null");
        }
    }

    /**
     * {@link InputStreamSimpleHashBuilder} class test.
     */
    @Test
    public void getSaltFromStoredHashTest() {
        InputStreamSimpleHashBuilder builder = new InputStreamSimpleHashBuilder(null);
        byte[] storedHash = new byte[]{1, 2, 3};
        builder.setStoredHash(storedHash);
        Assertions.assertThat(builder.getSaltFromStoredHash(SaltStoreType.DO_NOT_STORE, 0)).containsExactlyInOrder();
        Assertions.assertThat(builder.getSaltFromStoredHash(SaltStoreType.DO_NOT_STORE, 1)).containsExactlyInOrder();
        Assertions.assertThat(builder.getSaltFromStoredHash(SaltStoreType.AT_THE_BEGINNING, 1)).containsExactlyInOrder(1);
        Assertions.assertThat(builder.getSaltFromStoredHash(SaltStoreType.AT_THE_BEGINNING, 2)).containsExactlyInOrder(1, 2);
        Assertions.assertThat(builder.getSaltFromStoredHash(SaltStoreType.AT_THE_END, 1)).containsExactlyInOrder(3);
        Assertions.assertThat(builder.getSaltFromStoredHash(SaltStoreType.AT_THE_END, 2)).containsExactlyInOrder(2, 3);
    }

    /**
     * {@link InputStreamSimpleHashBuilder} class test.
     */
    @Test
    public void getSaltFromNullStoredHashFailTest() {
        try {
            InputStreamSimpleHashBuilder builder = new InputStreamSimpleHashBuilder(null);
            builder.getSaltFromStoredHash(SaltStoreType.DO_NOT_STORE, 0);
            Assertions.fail("InputStreamSimpleHashBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Byte array is null");
        }
    }

    /**
     * {@link InputStreamSimpleHashBuilder} class test.
     */
    @Test
    public void getHashTest() {
        Assertions.assertThat(new InputStreamSimpleHashBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).getHash()).containsExactlyInOrder(124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104);
        Assertions.assertThat(new InputStreamSimpleHashBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setAlgorithm(HashAlgorithms.MD5).getHash()).containsExactlyInOrder(124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104);
        Assertions.assertThat(new InputStreamSimpleHashBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 6})).setAlgorithm(HashAlgorithms.MD5).getHash()).containsExactlyInOrder(-5, 68, -47, -78, 110, -128, -81, -117, -97, 32, -24, 49, 13, -1, 23, -107);
        Assertions.assertThat(new InputStreamSimpleHashBuilder(new ByteArrayInputStream(new byte[]{2, 2, 3, 4, 5})).setAlgorithm(HashAlgorithms.MD5).getHash()).containsExactlyInOrder(57, -70, 57, -105, -60, 60, -57, -126, -69, 114, 50, -3, -52, -45, -88, -40);
    }

    /**
     * {@link InputStreamSimpleHashBuilder} class test.
     */
    @Test
    public void getNullHashFailTest() {
        try {
            new InputStreamSimpleHashBuilder(null).setAlgorithm(HashAlgorithms.MD5).getHash();
            Assertions.fail("InputStreamSimpleHashBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Source stream is null");
        }
    }

    /**
     * {@link InputStreamSimpleHashBuilder} class test.
     */
    @Test
    public void getNullAlgorithmHashFailTest() {
        try {
            new InputStreamSimpleHashBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setAlgorithm(null).getHash();
            Assertions.fail("InputStreamSimpleHashBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Algorithm is null");
        }
    }

    /**
     * {@link InputStreamSimpleHashBuilder} class test.
     */
    @Test
    public void getWrongAlgorithmHashFailTest() {
        try {
            new InputStreamSimpleHashBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setAlgorithm("wrong algorithm").getHash();
            Assertions.fail("InputStreamSimpleHashBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong algorithm name: wrong algorithm");
        }
    }

    /**
     * {@link InputStreamSimpleHashBuilder} class test.
     */
    @Test
    public void isHashValidTest() {
        Assertions.assertThat(new InputStreamSimpleHashBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setStoredHash(new byte[]{124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104}).isHashValid()).isTrue();
        Assertions.assertThat(new InputStreamSimpleHashBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104}).isHashValid()).isTrue();
        Assertions.assertThat(new InputStreamSimpleHashBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 105}).isHashValid()).isFalse();

        Assertions.assertThat(new InputStreamSimpleHashBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 6})).setStoredHash(new byte[]{-5, 68, -47, -78, 110, -128, -81, -117, -97, 32, -24, 49, 13, -1, 23, -107}).isHashValid()).isTrue();
        Assertions.assertThat(new InputStreamSimpleHashBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 6})).setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{-5, 68, -47, -78, 110, -128, -81, -117, -97, 32, -24, 49, 13, -1, 23, -107}).isHashValid()).isTrue();
        Assertions.assertThat(new InputStreamSimpleHashBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 6})).setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{-5, 68, -47, -78, 110, -128, -81, -117, -97, 32, -24, 49, 13, -1, 22, -107}).isHashValid()).isFalse();

        Assertions.assertThat(new InputStreamSimpleHashBuilder(new ByteArrayInputStream(new byte[]{2, 2, 3, 4, 5})).setStoredHash(new byte[]{57, -70, 57, -105, -60, 60, -57, -126, -69, 114, 50, -3, -52, -45, -88, -40}).isHashValid()).isTrue();
        Assertions.assertThat(new InputStreamSimpleHashBuilder(new ByteArrayInputStream(new byte[]{2, 2, 3, 4, 5})).setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{57, -70, 57, -105, -60, 60, -57, -126, -69, 114, 50, -3, -52, -45, -88, -40}).isHashValid()).isTrue();
        Assertions.assertThat(new InputStreamSimpleHashBuilder(new ByteArrayInputStream(new byte[]{2, 2, 3, 4, 5})).setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{57, -70, 57, -105, -60, 60, -57, -126, -69, 114, 50, 0, -52, -45, -88, -40}).isHashValid()).isFalse();
    }

    /**
     * {@link InputStreamSimpleHashBuilder} class test.
     */
    @Test
    public void isNullHashValidFailTest() {
        try {
            new InputStreamSimpleHashBuilder(null).setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104}).isHashValid();
            Assertions.fail("InputStreamSimpleHashBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Source stream is null");
        }
    }

    /**
     * {@link InputStreamSimpleHashBuilder} class test.
     */
    @Test
    public void isNullAlgorithmHashValidFailTest() {
        try {
            new InputStreamSimpleHashBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setAlgorithm(null).setStoredHash(new byte[]{124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104}).isHashValid();
            Assertions.fail("InputStreamSimpleHashBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Algorithm is null");
        }
    }

    /**
     * {@link InputStreamSimpleHashBuilder} class test.
     */
    @Test
    public void isWrongAlgorithmHashValidFailTest() {
        try {
            new InputStreamSimpleHashBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setAlgorithm("wrong algorithm").setStoredHash(new byte[]{124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104}).isHashValid();
            Assertions.fail("InputStreamSimpleHashBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong algorithm name: wrong algorithm");
        }
    }

    /**
     * {@link InputStreamSimpleHashBuilder} class test.
     */
    @Test
    public void isNullStoredHashValidFailTest() {
        try {
            new InputStreamSimpleHashBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setAlgorithm(HashAlgorithms.MD5).setStoredHash(null).isHashValid();
            Assertions.fail("InputStreamSimpleHashBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Hash byte array is null");
        }
    }

}
