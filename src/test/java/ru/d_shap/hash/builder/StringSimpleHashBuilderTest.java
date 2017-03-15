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
package ru.d_shap.hash.builder;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.hash.Hash;
import ru.d_shap.hash.HashAlgorithms;
import ru.d_shap.hash.HashHelper;
import ru.d_shap.hash.SaltStoreType;
import ru.d_shap.hash.WrongArgumentException;

/**
 * Tests for {@link StringSimpleHashBuilder}.
 *
 * @author Dmitry Shapovalov
 */
public final class StringSimpleHashBuilderTest {

    /**
     * Test class constructor.
     */
    public StringSimpleHashBuilderTest() {
        super();
    }

    /**
     * {@link StringSimpleHashBuilder} class test.
     */
    @Test
    public void getAlgorithmTest() {
        Assertions.assertThat(new StringSimpleHashBuilder(null, null).getAlgorithm()).isEqualTo(HashAlgorithms.MD5);
        Assertions.assertThat(new StringSimpleHashBuilder(null, null).setAlgorithm("value").getAlgorithm()).isEqualTo("value");
    }

    /**
     * {@link StringSimpleHashBuilder} class test.
     */
    @Test
    public void setAlgorithmTest() {
        StringSimpleHashBuilder builder = new StringSimpleHashBuilder(null, null);
        builder.setAlgorithm("value");
        Assertions.assertThat(builder.getAlgorithm()).isEqualTo("value");
        builder.setAlgorithm(HashAlgorithms.MD5);
        Assertions.assertThat(builder.getAlgorithm()).isEqualTo(HashAlgorithms.MD5);
        builder.setAlgorithm(null);
        Assertions.assertThat(builder.getAlgorithm()).isNull();
    }

    /**
     * {@link StringSimpleHashBuilder} class test.
     */
    @Test
    public void getStoredHashTest() {
        Assertions.assertThat(new StringSimpleHashBuilder(null, null).getStoredHash()).isNull();
        Assertions.assertThat(new StringSimpleHashBuilder(null, null).setStoredHash(new byte[]{1, 2, 3}).getStoredHash()).containsExactlyInOrder(1, 2, 3);
    }

    /**
     * {@link StringSimpleHashBuilder} class test.
     */
    @Test
    public void setStoredHashTest() {
        StringSimpleHashBuilder builder = new StringSimpleHashBuilder(null, null);
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
     * {@link StringSimpleHashBuilder} class test.
     */
    @Test
    public void storedHashDifferentTest() {
        StringSimpleHashBuilder builder = new StringSimpleHashBuilder(null, null);
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
     * {@link StringSimpleHashBuilder} class test.
     */
    @Test
    public void matchesTest() {
        StringSimpleHashBuilder builder = new StringSimpleHashBuilder(null, null);
        builder.setStoredHash(new byte[]{82, -119, -33, 115, 125, -11, 115, 38, -4, -35, 34, 89, 122, -5, 31, -84});
        Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
        Assertions.assertThat(builder.matches(hash)).isTrue();
    }

    /**
     * {@link StringSimpleHashBuilder} class test.
     */
    @Test
    public void nullHashMatchesFailTest() {
        try {
            StringSimpleHashBuilder builder = new StringSimpleHashBuilder(null, null);
            builder.setStoredHash(new byte[]{82, -119, -33, 115, 125, -11, 115, 38, -4, -35, 34, 89, 122, -5, 31, -84});
            builder.matches(null);
            Assertions.fail("StringSimpleHashBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Hash is null");
        }
    }

    /**
     * {@link StringSimpleHashBuilder} class test.
     */
    @Test
    public void nullStoredHashMatchesFailTest() {
        try {
            StringSimpleHashBuilder builder = new StringSimpleHashBuilder(null, null);
            builder.setStoredHash(null);
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
            Assertions.assertThat(builder.matches(hash)).isTrue();
            Assertions.fail("StringSimpleHashBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Hash byte array is null");
        }
    }

    /**
     * {@link StringSimpleHashBuilder} class test.
     */
    @Test
    public void getHashFromStoredHashTest() {
        StringSimpleHashBuilder builder = new StringSimpleHashBuilder(null, null);
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
     * {@link StringSimpleHashBuilder} class test.
     */
    @Test
    public void getHashFromNullStoredHashFailTest() {
        try {
            StringSimpleHashBuilder builder = new StringSimpleHashBuilder(null, null);
            builder.getHashFromStoredHash(SaltStoreType.DO_NOT_STORE, 0);
            Assertions.fail("StringSimpleHashBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Byte array is null");
        }
    }

    /**
     * {@link StringSimpleHashBuilder} class test.
     */
    @Test
    public void getSaltFromStoredHashTest() {
        StringSimpleHashBuilder builder = new StringSimpleHashBuilder(null, null);
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
     * {@link StringSimpleHashBuilder} class test.
     */
    @Test
    public void getSaltFromNullStoredHashFailTest() {
        try {
            StringSimpleHashBuilder builder = new StringSimpleHashBuilder(null, null);
            builder.getSaltFromStoredHash(SaltStoreType.DO_NOT_STORE, 0);
            Assertions.fail("StringSimpleHashBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Byte array is null");
        }
    }

    /**
     * {@link StringSimpleHashBuilder} class test.
     */
    @Test
    public void getHashTest() {
        Assertions.assertThat(new StringSimpleHashBuilder("12345", "UTF-8").getHash()).containsExactlyInOrder(-126, 124, -53, 14, -22, -118, 112, 108, 76, 52, -95, 104, -111, -8, 78, 123);
        Assertions.assertThat(new StringSimpleHashBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).getHash()).containsExactlyInOrder(-126, 124, -53, 14, -22, -118, 112, 108, 76, 52, -95, 104, -111, -8, 78, 123);
        Assertions.assertThat(new StringSimpleHashBuilder("12346", "UTF-8").setAlgorithm(HashAlgorithms.MD5).getHash()).containsExactlyInOrder(-93, 89, 0, 35, -33, 102, -84, -110, -82, 53, -29, 49, 96, 38, -47, 125);
        Assertions.assertThat(new StringSimpleHashBuilder("22345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).getHash()).containsExactlyInOrder(9, 35, 105, 104, -2, 23, -98, -70, -103, 89, -18, 8, -69, 91, -8, 15);
    }

    /**
     * {@link StringSimpleHashBuilder} class test.
     */
    @Test
    public void getNullHashFailTest() {
        try {
            new StringSimpleHashBuilder(null, "UTF-8").setAlgorithm(HashAlgorithms.MD5).getHash();
            Assertions.fail("StringSimpleHashBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Source string is null");
        }
    }

    /**
     * {@link StringSimpleHashBuilder} class test.
     */
    @Test
    public void getNullEncodingHashFailTest() {
        try {
            new StringSimpleHashBuilder("12345", null).setAlgorithm(HashAlgorithms.MD5).getHash();
            Assertions.fail("StringSimpleHashBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Source string encoding is null");
        }
    }

    /**
     * {@link StringSimpleHashBuilder} class test.
     */
    @Test
    public void getWrongEncodingHashFailTest() {
        try {
            new StringSimpleHashBuilder("12345", "wrong encoding").setAlgorithm(HashAlgorithms.MD5).getHash();
            Assertions.fail("StringSimpleHashBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong source string encoding: wrong encoding");
        }
    }

    /**
     * {@link StringSimpleHashBuilder} class test.
     */
    @Test
    public void getNullAlgorithmHashFailTest() {
        try {
            new StringSimpleHashBuilder("12345", "UTF-8").setAlgorithm(null).getHash();
            Assertions.fail("StringSimpleHashBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Algorithm is null");
        }
    }

    /**
     * {@link StringSimpleHashBuilder} class test.
     */
    @Test
    public void getWrongAlgorithmHashFailTest() {
        try {
            new StringSimpleHashBuilder("12345", "UTF-8").setAlgorithm("wrong algorithm").getHash();
            Assertions.fail("StringSimpleHashBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong algorithm name: wrong algorithm");
        }
    }

    /**
     * {@link StringSimpleHashBuilder} class test.
     */
    @Test
    public void isHashValidTest() {
        Assertions.assertThat(new StringSimpleHashBuilder("12345", "UTF-8").setStoredHash(new byte[]{-126, 124, -53, 14, -22, -118, 112, 108, 76, 52, -95, 104, -111, -8, 78, 123}).isHashValid()).isTrue();
        Assertions.assertThat(new StringSimpleHashBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{-126, 124, -53, 14, -22, -118, 112, 108, 76, 52, -95, 104, -111, -8, 78, 123}).isHashValid()).isTrue();
        Assertions.assertThat(new StringSimpleHashBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{-126, 124, -53, 14, -22, -118, 112, 108, 76, 52, -95, 104, -111, -8, 78, 124}).isHashValid()).isFalse();

        Assertions.assertThat(new StringSimpleHashBuilder("12346", "UTF-8").setStoredHash(new byte[]{-93, 89, 0, 35, -33, 102, -84, -110, -82, 53, -29, 49, 96, 38, -47, 125}).isHashValid()).isTrue();
        Assertions.assertThat(new StringSimpleHashBuilder("12346", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{-93, 89, 0, 35, -33, 102, -84, -110, -82, 53, -29, 49, 96, 38, -47, 125}).isHashValid()).isTrue();
        Assertions.assertThat(new StringSimpleHashBuilder("12346", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{-93, 89, 0, 35, -33, 102, -84, -110, -82, 53, -29, 49, 96, 38, -48, 125}).isHashValid()).isFalse();

        Assertions.assertThat(new StringSimpleHashBuilder("22345", "UTF-8").setStoredHash(new byte[]{9, 35, 105, 104, -2, 23, -98, -70, -103, 89, -18, 8, -69, 91, -8, 15}).isHashValid()).isTrue();
        Assertions.assertThat(new StringSimpleHashBuilder("22345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{9, 35, 105, 104, -2, 23, -98, -70, -103, 89, -18, 8, -69, 91, -8, 15}).isHashValid()).isTrue();
        Assertions.assertThat(new StringSimpleHashBuilder("22345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{9, 35, 105, 104, -2, 23, -98, -70, -103, 89, -18, 0, -69, 91, -8, 15}).isHashValid()).isFalse();
    }

    /**
     * {@link StringSimpleHashBuilder} class test.
     */
    @Test
    public void isNullHashValidFailTest() {
        try {
            new StringSimpleHashBuilder(null, "UTF-8").setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104}).isHashValid();
            Assertions.fail("StringSimpleHashBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Source string is null");
        }
    }

    /**
     * {@link StringSimpleHashBuilder} class test.
     */
    @Test
    public void isNullEncodingHashValidFailTest() {
        try {
            new StringSimpleHashBuilder("12345", null).setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104}).isHashValid();
            Assertions.fail("StringSimpleHashBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Source string encoding is null");
        }
    }

    /**
     * {@link StringSimpleHashBuilder} class test.
     */
    @Test
    public void isWrongEncodingHashValidFailTest() {
        try {
            new StringSimpleHashBuilder("12345", "wrong encoding").setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104}).isHashValid();
            Assertions.fail("StringSimpleHashBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong source string encoding: wrong encoding");
        }
    }

    /**
     * {@link StringSimpleHashBuilder} class test.
     */
    @Test
    public void isNullAlgorithmHashValidFailTest() {
        try {
            new StringSimpleHashBuilder("12345", "UTF-8").setAlgorithm(null).setStoredHash(new byte[]{124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104}).isHashValid();
            Assertions.fail("StringSimpleHashBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Algorithm is null");
        }
    }

    /**
     * {@link StringSimpleHashBuilder} class test.
     */
    @Test
    public void isWrongAlgorithmHashValidFailTest() {
        try {
            new StringSimpleHashBuilder("12345", "UTF-8").setAlgorithm("wrong algorithm").setStoredHash(new byte[]{124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104}).isHashValid();
            Assertions.fail("StringSimpleHashBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong algorithm name: wrong algorithm");
        }
    }

    /**
     * {@link StringSimpleHashBuilder} class test.
     */
    @Test
    public void isNullStoredHashValidFailTest() {
        try {
            new StringSimpleHashBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setStoredHash(null).isHashValid();
            Assertions.fail("StringSimpleHashBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Hash byte array is null");
        }
    }

}
