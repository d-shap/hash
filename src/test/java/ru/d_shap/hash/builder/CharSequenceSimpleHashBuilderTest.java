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

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.hash.Hash;
import ru.d_shap.hash.HashAlgorithms;
import ru.d_shap.hash.HashHelper;
import ru.d_shap.hash.SaltStoreType;

/**
 * Tests for {@link CharSequenceSimpleHashBuilder}.
 *
 * @author Dmitry Shapovalov
 */
public final class CharSequenceSimpleHashBuilderTest {

    /**
     * Test class constructor.
     */
    public CharSequenceSimpleHashBuilderTest() {
        super();
    }

    /**
     * {@link CharSequenceSimpleHashBuilder} class test.
     */
    @Test
    public void getAlgorithmTest() {
        Assertions.assertThat(new CharSequenceSimpleHashBuilder(null, null).getAlgorithm()).isEqualTo(HashAlgorithms.MD5);
        Assertions.assertThat(new CharSequenceSimpleHashBuilder(null, null).setAlgorithm("value").getAlgorithm()).isEqualTo("value");
    }

    /**
     * {@link CharSequenceSimpleHashBuilder} class test.
     */
    @Test
    public void setAlgorithmTest() {
        CharSequenceSimpleHashBuilder builder = new CharSequenceSimpleHashBuilder(null, null);
        builder.setAlgorithm("value");
        Assertions.assertThat(builder.getAlgorithm()).isEqualTo("value");
        builder.setAlgorithm(HashAlgorithms.MD5);
        Assertions.assertThat(builder.getAlgorithm()).isEqualTo(HashAlgorithms.MD5);
        builder.setAlgorithm(null);
        Assertions.assertThat(builder.getAlgorithm()).isNull();
    }

    /**
     * {@link CharSequenceSimpleHashBuilder} class test.
     */
    @Test
    public void getStoredHashTest() {
        Assertions.assertThat(new CharSequenceSimpleHashBuilder(null, null).getStoredHash()).isNull();
        Assertions.assertThat(new CharSequenceSimpleHashBuilder(null, null).setStoredHash(new byte[]{1, 2, 3}).getStoredHash()).containsExactlyInOrder(1, 2, 3);
    }

    /**
     * {@link CharSequenceSimpleHashBuilder} class test.
     */
    @Test
    public void setStoredHashTest() {
        CharSequenceSimpleHashBuilder builder = new CharSequenceSimpleHashBuilder(null, null);
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
     * {@link CharSequenceSimpleHashBuilder} class test.
     */
    @Test
    public void storedHashDifferentTest() {
        CharSequenceSimpleHashBuilder builder = new CharSequenceSimpleHashBuilder(null, null);
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
     * {@link CharSequenceSimpleHashBuilder} class test.
     */
    @Test
    public void getStoredSaltLengthTest() {
        CharSequenceSimpleHashBuilder builder = new CharSequenceSimpleHashBuilder(null, null);
        builder.setStoredHash(new byte[]{1, 2, 3, 4, 5});
        Assertions.assertThat(builder.getStoredSaltLength(0)).isEqualTo(5);
        Assertions.assertThat(builder.getStoredSaltLength(1)).isEqualTo(4);
        Assertions.assertThat(builder.getStoredSaltLength(4)).isEqualTo(1);
        Assertions.assertThat(builder.getStoredSaltLength(5)).isEqualTo(0);
        Assertions.assertThat(builder.getStoredSaltLength(7)).isEqualTo(-2);
        Assertions.assertThat(builder.getStoredSaltLength(-3)).isEqualTo(8);
    }

    /**
     * {@link CharSequenceSimpleHashBuilder} class test.
     */
    @Test
    public void getNullStoredHashStoredSaltLengthFailTest() {
        try {
            new CharSequenceSimpleHashBuilder(null, null).getStoredSaltLength(1);
            Assertions.fail("CharSequenceSimpleHashBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Stored hash is null");
        }
    }

    /**
     * {@link CharSequenceSimpleHashBuilder} class test.
     */
    @Test
    public void matchesTest() {
        CharSequenceSimpleHashBuilder builder = new CharSequenceSimpleHashBuilder(null, null);
        builder.setStoredHash(new byte[]{82, -119, -33, 115, 125, -11, 115, 38, -4, -35, 34, 89, 122, -5, 31, -84});
        Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
        Assertions.assertThat(builder.matches(hash)).isTrue();
    }

    /**
     * {@link CharSequenceSimpleHashBuilder} class test.
     */
    @Test
    public void nullHashMatchesFailTest() {
        try {
            CharSequenceSimpleHashBuilder builder = new CharSequenceSimpleHashBuilder(null, null);
            builder.setStoredHash(new byte[]{82, -119, -33, 115, 125, -11, 115, 38, -4, -35, 34, 89, 122, -5, 31, -84});
            builder.matches(null);
            Assertions.fail("CharSequenceSimpleHashBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Hash is null");
        }
    }

    /**
     * {@link CharSequenceSimpleHashBuilder} class test.
     */
    @Test
    public void nullStoredHashMatchesFailTest() {
        try {
            CharSequenceSimpleHashBuilder builder = new CharSequenceSimpleHashBuilder(null, null);
            builder.setStoredHash(null);
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
            Assertions.assertThat(builder.matches(hash)).isTrue();
            Assertions.fail("CharSequenceSimpleHashBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Hash byte array is null");
        }
    }

    /**
     * {@link CharSequenceSimpleHashBuilder} class test.
     */
    @Test
    public void getHashFromStoredHashTest() {
        CharSequenceSimpleHashBuilder builder = new CharSequenceSimpleHashBuilder(null, null);
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
     * {@link CharSequenceSimpleHashBuilder} class test.
     */
    @Test
    public void getHashFromNullStoredHashFailTest() {
        try {
            CharSequenceSimpleHashBuilder builder = new CharSequenceSimpleHashBuilder(null, null);
            builder.getHashFromStoredHash(SaltStoreType.DO_NOT_STORE, 0);
            Assertions.fail("CharSequenceSimpleHashBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Byte array is null");
        }
    }

    /**
     * {@link CharSequenceSimpleHashBuilder} class test.
     */
    @Test
    public void getSaltFromStoredHashTest() {
        CharSequenceSimpleHashBuilder builder = new CharSequenceSimpleHashBuilder(null, null);
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
     * {@link CharSequenceSimpleHashBuilder} class test.
     */
    @Test
    public void getSaltFromNullStoredHashFailTest() {
        try {
            CharSequenceSimpleHashBuilder builder = new CharSequenceSimpleHashBuilder(null, null);
            builder.getSaltFromStoredHash(SaltStoreType.DO_NOT_STORE, 0);
            Assertions.fail("CharSequenceSimpleHashBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Byte array is null");
        }
    }

    /**
     * {@link CharSequenceSimpleHashBuilder} class test.
     */
    @Test
    public void getHashTest() {
        Assertions.assertThat(new CharSequenceSimpleHashBuilder("12345", "UTF-8").getHash()).containsExactlyInOrder(-126, 124, -53, 14, -22, -118, 112, 108, 76, 52, -95, 104, -111, -8, 78, 123);
        Assertions.assertThat(new CharSequenceSimpleHashBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).getHash()).containsExactlyInOrder(-126, 124, -53, 14, -22, -118, 112, 108, 76, 52, -95, 104, -111, -8, 78, 123);
        Assertions.assertThat(new CharSequenceSimpleHashBuilder("12346", "UTF-8").setAlgorithm(HashAlgorithms.MD5).getHash()).containsExactlyInOrder(-93, 89, 0, 35, -33, 102, -84, -110, -82, 53, -29, 49, 96, 38, -47, 125);
        Assertions.assertThat(new CharSequenceSimpleHashBuilder("22345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).getHash()).containsExactlyInOrder(9, 35, 105, 104, -2, 23, -98, -70, -103, 89, -18, 8, -69, 91, -8, 15);
    }

    /**
     * {@link CharSequenceSimpleHashBuilder} class test.
     */
    @Test
    public void getNullHashFailTest() {
        try {
            new CharSequenceSimpleHashBuilder(null, "UTF-8").setAlgorithm(HashAlgorithms.MD5).getHash();
            Assertions.fail("CharSequenceSimpleHashBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Source char sequence is null");
        }
    }

    /**
     * {@link CharSequenceSimpleHashBuilder} class test.
     */
    @Test
    public void getNullEncodingHashFailTest() {
        try {
            new CharSequenceSimpleHashBuilder("12345", null).setAlgorithm(HashAlgorithms.MD5).getHash();
            Assertions.fail("CharSequenceSimpleHashBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Source char sequence encoding is null");
        }
    }

    /**
     * {@link CharSequenceSimpleHashBuilder} class test.
     */
    @Test
    public void getWrongEncodingHashFailTest() {
        try {
            new CharSequenceSimpleHashBuilder("12345", "wrong encoding").setAlgorithm(HashAlgorithms.MD5).getHash();
            Assertions.fail("CharSequenceSimpleHashBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong source char sequence encoding: wrong encoding");
        }
    }

    /**
     * {@link CharSequenceSimpleHashBuilder} class test.
     */
    @Test
    public void getNullAlgorithmHashFailTest() {
        try {
            new CharSequenceSimpleHashBuilder("12345", "UTF-8").setAlgorithm(null).getHash();
            Assertions.fail("CharSequenceSimpleHashBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Algorithm is null");
        }
    }

    /**
     * {@link CharSequenceSimpleHashBuilder} class test.
     */
    @Test
    public void getWrongAlgorithmHashFailTest() {
        try {
            new CharSequenceSimpleHashBuilder("12345", "UTF-8").setAlgorithm("wrong algorithm").getHash();
            Assertions.fail("CharSequenceSimpleHashBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong algorithm name: wrong algorithm");
        }
    }

    /**
     * {@link CharSequenceSimpleHashBuilder} class test.
     */
    @Test
    public void isHashValidTest() {
        Assertions.assertThat(new CharSequenceSimpleHashBuilder("12345", "UTF-8").setStoredHash(new byte[]{-126, 124, -53, 14, -22, -118, 112, 108, 76, 52, -95, 104, -111, -8, 78, 123}).isHashValid()).isTrue();
        Assertions.assertThat(new CharSequenceSimpleHashBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{-126, 124, -53, 14, -22, -118, 112, 108, 76, 52, -95, 104, -111, -8, 78, 123}).isHashValid()).isTrue();
        Assertions.assertThat(new CharSequenceSimpleHashBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{-126, 124, -53, 14, -22, -118, 112, 108, 76, 52, -95, 104, -111, -8, 78, 124}).isHashValid()).isFalse();

        Assertions.assertThat(new CharSequenceSimpleHashBuilder("12346", "UTF-8").setStoredHash(new byte[]{-93, 89, 0, 35, -33, 102, -84, -110, -82, 53, -29, 49, 96, 38, -47, 125}).isHashValid()).isTrue();
        Assertions.assertThat(new CharSequenceSimpleHashBuilder("12346", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{-93, 89, 0, 35, -33, 102, -84, -110, -82, 53, -29, 49, 96, 38, -47, 125}).isHashValid()).isTrue();
        Assertions.assertThat(new CharSequenceSimpleHashBuilder("12346", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{-93, 89, 0, 35, -33, 102, -84, -110, -82, 53, -29, 49, 96, 38, -48, 125}).isHashValid()).isFalse();

        Assertions.assertThat(new CharSequenceSimpleHashBuilder("22345", "UTF-8").setStoredHash(new byte[]{9, 35, 105, 104, -2, 23, -98, -70, -103, 89, -18, 8, -69, 91, -8, 15}).isHashValid()).isTrue();
        Assertions.assertThat(new CharSequenceSimpleHashBuilder("22345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{9, 35, 105, 104, -2, 23, -98, -70, -103, 89, -18, 8, -69, 91, -8, 15}).isHashValid()).isTrue();
        Assertions.assertThat(new CharSequenceSimpleHashBuilder("22345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{9, 35, 105, 104, -2, 23, -98, -70, -103, 89, -18, 0, -69, 91, -8, 15}).isHashValid()).isFalse();
    }

    /**
     * {@link CharSequenceSimpleHashBuilder} class test.
     */
    @Test
    public void isNullHashValidFailTest() {
        try {
            new CharSequenceSimpleHashBuilder(null, "UTF-8").setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104}).isHashValid();
            Assertions.fail("CharSequenceSimpleHashBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Source char sequence is null");
        }
    }

    /**
     * {@link CharSequenceSimpleHashBuilder} class test.
     */
    @Test
    public void isNullEncodingHashValidFailTest() {
        try {
            new CharSequenceSimpleHashBuilder("12345", null).setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104}).isHashValid();
            Assertions.fail("CharSequenceSimpleHashBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Source char sequence encoding is null");
        }
    }

    /**
     * {@link CharSequenceSimpleHashBuilder} class test.
     */
    @Test
    public void isWrongEncodingHashValidFailTest() {
        try {
            new CharSequenceSimpleHashBuilder("12345", "wrong encoding").setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104}).isHashValid();
            Assertions.fail("CharSequenceSimpleHashBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong source char sequence encoding: wrong encoding");
        }
    }

    /**
     * {@link CharSequenceSimpleHashBuilder} class test.
     */
    @Test
    public void isNullAlgorithmHashValidFailTest() {
        try {
            new CharSequenceSimpleHashBuilder("12345", "UTF-8").setAlgorithm(null).setStoredHash(new byte[]{124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104}).isHashValid();
            Assertions.fail("CharSequenceSimpleHashBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Algorithm is null");
        }
    }

    /**
     * {@link CharSequenceSimpleHashBuilder} class test.
     */
    @Test
    public void isWrongAlgorithmHashValidFailTest() {
        try {
            new CharSequenceSimpleHashBuilder("12345", "UTF-8").setAlgorithm("wrong algorithm").setStoredHash(new byte[]{124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104}).isHashValid();
            Assertions.fail("CharSequenceSimpleHashBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong algorithm name: wrong algorithm");
        }
    }

    /**
     * {@link CharSequenceSimpleHashBuilder} class test.
     */
    @Test
    public void isNullStoredHashValidFailTest() {
        try {
            new CharSequenceSimpleHashBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setStoredHash(null).isHashValid();
            Assertions.fail("CharSequenceSimpleHashBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Hash byte array is null");
        }
    }

}
