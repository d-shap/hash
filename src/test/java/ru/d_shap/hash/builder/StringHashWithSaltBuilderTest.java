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
 * Tests for {@link StringHashWithSaltBuilder}.
 *
 * @author Dmitry Shapovalov
 */
public final class StringHashWithSaltBuilderTest {

    /**
     * Test class constructor.
     */
    public StringHashWithSaltBuilderTest() {
        super();
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void getSaltTest() {
        Assertions.assertThat(new StringHashWithSaltBuilder(null, null).getSalt()).isNull();
        Assertions.assertThat(new StringHashWithSaltBuilder(null, null).setSalt(new byte[]{1, 2, 3}).getSalt()).containsExactlyInOrder(1, 2, 3);
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void setSaltTest() {
        StringHashWithSaltBuilder builder = new StringHashWithSaltBuilder(null, null);
        builder.setSalt(new byte[]{1, 2, 3});
        Assertions.assertThat(builder.getSalt()).containsExactlyInOrder(1, 2, 3);
        builder.setSalt(new byte[]{10, 11});
        Assertions.assertThat(builder.getSalt()).containsExactlyInOrder(10, 11);
        builder.setSalt(new byte[]{});
        Assertions.assertThat(builder.getSalt()).containsExactlyInOrder();
        builder.setSalt(null);
        Assertions.assertThat(builder.getSalt()).isNull();
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void saltDifferentTest() {
        StringHashWithSaltBuilder builder = new StringHashWithSaltBuilder(null, null);
        byte[] salt = new byte[]{1, 2, 3};
        builder.setSalt(salt);
        byte[] salt1 = builder.getSalt();
        byte[] salt2 = builder.getSalt();
        Assertions.assertThat(salt1).isNotSameAs(salt);
        Assertions.assertThat(salt2).isNotSameAs(salt);
        Assertions.assertThat(salt1).isNotSameAs(salt2);
        Assertions.assertThat(salt1).containsExactlyInOrder(salt);
        Assertions.assertThat(salt2).containsExactlyInOrder(salt);
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void addSaltTest() {
        Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
        Assertions.assertThat(hash.getBytes()).containsExactlyInOrder(82, -119, -33, 115, 125, -11, 115, 38, -4, -35, 34, 89, 122, -5, 31, -84);

        StringHashWithSaltBuilder builder = new StringHashWithSaltBuilder(null, null);
        builder.setSalt(new byte[]{10, 11});
        builder.addSalt(hash);
        Assertions.assertThat(hash.getBytes()).containsExactlyInOrder(29, -66, 31, 50, 105, -4, 117, 85, -40, 2, -105, 71, 90, 90, -16, -99);
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void addNullHashSaltFailTest() {
        try {
            StringHashWithSaltBuilder builder = new StringHashWithSaltBuilder(null, null);
            builder.setSalt(new byte[]{10, 11});
            builder.addSalt(null);
            Assertions.fail("StringHashWithSaltBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Hash is null");
        }
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void addNullSaltFailTest() {
        try {
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
            StringHashWithSaltBuilder builder = new StringHashWithSaltBuilder(null, null);
            builder.setSalt(null);
            builder.addSalt(hash);
            Assertions.fail("StringHashWithSaltBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt byte array is null");
        }
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void addSaltBytesTest() {
        Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
        Assertions.assertThat(hash.getBytes()).containsExactlyInOrder(82, -119, -33, 115, 125, -11, 115, 38, -4, -35, 34, 89, 122, -5, 31, -84);

        StringHashWithSaltBuilder builder = new StringHashWithSaltBuilder(null, null);
        builder.setSalt(new byte[]{0, 0, 0});
        Assertions.assertThat(builder.addSaltBytes(hash)).containsExactlyInOrder(82, -119, -33, 115, 125, -11, 115, 38, -4, -35, 34, 89, 122, -5, 31, -84);

        builder.setSaltStoreType(SaltStoreType.AT_THE_BEGINNING);
        Assertions.assertThat(builder.addSaltBytes(hash)).containsExactlyInOrder(0, 0, 0, 82, -119, -33, 115, 125, -11, 115, 38, -4, -35, 34, 89, 122, -5, 31, -84);

        builder.setSaltStoreType(SaltStoreType.AT_THE_END);
        Assertions.assertThat(builder.addSaltBytes(hash)).containsExactlyInOrder(82, -119, -33, 115, 125, -11, 115, 38, -4, -35, 34, 89, 122, -5, 31, -84, 0, 0, 0);

        builder.setSaltStoreType(SaltStoreType.DO_NOT_STORE);
        Assertions.assertThat(builder.addSaltBytes(hash)).containsExactlyInOrder(82, -119, -33, 115, 125, -11, 115, 38, -4, -35, 34, 89, 122, -5, 31, -84);
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void addNullHashSaltBytesFailTest() {
        try {
            StringHashWithSaltBuilder builder = new StringHashWithSaltBuilder(null, null);
            builder.setSalt(new byte[]{0, 0, 0});
            builder.setSaltStoreType(SaltStoreType.AT_THE_BEGINNING);
            builder.addSaltBytes(null);
            Assertions.fail("StringHashWithSaltBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Hash is null");
        }
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void addNullSaltBytesFailTest() {
        try {
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
            StringHashWithSaltBuilder builder = new StringHashWithSaltBuilder(null, null);
            builder.setSalt(null);
            builder.setSaltStoreType(SaltStoreType.AT_THE_BEGINNING);
            builder.addSaltBytes(hash);
            Assertions.fail("StringHashWithSaltBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt byte array is null");
        }
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void addNullSaltStoreTypeSaltBytesFailTest() {
        try {
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
            StringHashWithSaltBuilder builder = new StringHashWithSaltBuilder(null, null);
            builder.setSalt(new byte[]{0, 0, 0});
            builder.setSaltStoreType(null);
            builder.addSaltBytes(hash);
            Assertions.fail("StringHashWithSaltBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt store type is null");
        }
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void getSaltStoreTypeTest() {
        Assertions.assertThat(new StringHashWithSaltBuilder(null, null).getSaltStoreType()).isEqualTo(SaltStoreType.DO_NOT_STORE);
        Assertions.assertThat(new StringHashWithSaltBuilder(null, null).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).getSaltStoreType()).isEqualTo(SaltStoreType.AT_THE_BEGINNING);
        Assertions.assertThat(new StringHashWithSaltBuilder(null, null).setSaltStoreType(SaltStoreType.AT_THE_END).getSaltStoreType()).isEqualTo(SaltStoreType.AT_THE_END);
        Assertions.assertThat(new StringHashWithSaltBuilder(null, null).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getSaltStoreType()).isEqualTo(SaltStoreType.DO_NOT_STORE);
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void setSaltStoreTypeTest() {
        StringHashWithSaltBuilder builder = new StringHashWithSaltBuilder(null, null);
        Assertions.assertThat(builder.getSaltStoreType()).isEqualTo(SaltStoreType.DO_NOT_STORE);
        builder.setSaltStoreType(SaltStoreType.AT_THE_BEGINNING);
        Assertions.assertThat(builder.getSaltStoreType()).isEqualTo(SaltStoreType.AT_THE_BEGINNING);
        builder.setSaltStoreType(null);
        Assertions.assertThat(builder.getSaltStoreType()).isNull();
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void getHashTest() {
        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash()).containsExactlyInOrder(106, 21, -123, 68, 97, 35, 81, -14, 63, -88, -27, -102, 66, 24, 89, 102);
        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setSalt(new byte[]{1, 2, 4}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash()).containsExactlyInOrder(-30, 85, -11, 50, -113, 26, -56, -104, 21, -105, 78, -106, -82, -45, -80, -117);
        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setSalt(new byte[]{2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash()).containsExactlyInOrder(-15, 119, 44, 57, -83, 69, -80, 111, 66, -124, -39, 34, 101, 22, 107, 36);
        Assertions.assertThat(new StringHashWithSaltBuilder("12346", "UTF-8").setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash()).containsExactlyInOrder(-33, -117, 91, 73, 92, 31, -87, 85, -57, 94, -40, 125, 104, 81, 55, 50);
        Assertions.assertThat(new StringHashWithSaltBuilder("12346", "UTF-8").setSalt(new byte[]{1, 2, 4}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash()).containsExactlyInOrder(86, 15, 117, -6, 44, 93, 22, -31, 32, -72, -50, -87, 72, 25, -72, -107);

        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).getHash()).containsExactlyInOrder(1, 2, 3, 106, 21, -123, 68, 97, 35, 81, -14, 63, -88, -27, -102, 66, 24, 89, 102);
        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setSalt(new byte[]{1, 2, 4}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).getHash()).containsExactlyInOrder(1, 2, 4, -30, 85, -11, 50, -113, 26, -56, -104, 21, -105, 78, -106, -82, -45, -80, -117);
        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setSalt(new byte[]{2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).getHash()).containsExactlyInOrder(2, 3, -15, 119, 44, 57, -83, 69, -80, 111, 66, -124, -39, 34, 101, 22, 107, 36);
        Assertions.assertThat(new StringHashWithSaltBuilder("12346", "UTF-8").setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).getHash()).containsExactlyInOrder(1, 2, 3, -33, -117, 91, 73, 92, 31, -87, 85, -57, 94, -40, 125, 104, 81, 55, 50);
        Assertions.assertThat(new StringHashWithSaltBuilder("12346", "UTF-8").setSalt(new byte[]{1, 2, 4}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).getHash()).containsExactlyInOrder(1, 2, 4, 86, 15, 117, -6, 44, 93, 22, -31, 32, -72, -50, -87, 72, 25, -72, -107);

        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).getHash()).containsExactlyInOrder(106, 21, -123, 68, 97, 35, 81, -14, 63, -88, -27, -102, 66, 24, 89, 102, 1, 2, 3);
        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setSalt(new byte[]{1, 2, 4}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).getHash()).containsExactlyInOrder(-30, 85, -11, 50, -113, 26, -56, -104, 21, -105, 78, -106, -82, -45, -80, -117, 1, 2, 4);
        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setSalt(new byte[]{2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).getHash()).containsExactlyInOrder(-15, 119, 44, 57, -83, 69, -80, 111, 66, -124, -39, 34, 101, 22, 107, 36, 2, 3);
        Assertions.assertThat(new StringHashWithSaltBuilder("12346", "UTF-8").setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).getHash()).containsExactlyInOrder(-33, -117, 91, 73, 92, 31, -87, 85, -57, 94, -40, 125, 104, 81, 55, 50, 1, 2, 3);
        Assertions.assertThat(new StringHashWithSaltBuilder("12346", "UTF-8").setSalt(new byte[]{1, 2, 4}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).getHash()).containsExactlyInOrder(86, 15, 117, -6, 44, 93, 22, -31, 32, -72, -50, -87, 72, 25, -72, -107, 1, 2, 4);
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void getNullHashFailTest() {
        try {
            new StringHashWithSaltBuilder(null, "UTF-8").setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash();
            Assertions.fail("StringHashWithSaltBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Source string is null");
        }
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void getNullEncodingHashFailTest() {
        try {
            new StringHashWithSaltBuilder("12345", null).setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash();
            Assertions.fail("StringHashWithSaltBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Source string encoding is null");
        }
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void getWrongEncodingHashFailTest() {
        try {
            new StringHashWithSaltBuilder("12345", "wrong encoding").setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash();
            Assertions.fail("StringHashWithSaltBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong source string encoding: wrong encoding");
        }
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void getNullSaltHashFailTest() {
        try {
            new StringHashWithSaltBuilder("12345", "UTF-8").setSalt(null).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash();
            Assertions.fail("StringHashWithSaltBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt byte array is null");
        }
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void getNullAlgorithmHashFailTest() {
        try {
            new StringHashWithSaltBuilder("12345", "UTF-8").setSalt(new byte[]{1, 2, 3}).setAlgorithm(null).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash();
            Assertions.fail("StringHashWithSaltBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Algorithm is null");
        }
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void getWrongAlgorithmHashFailTest() {
        try {
            new StringHashWithSaltBuilder("12345", "UTF-8").setSalt(new byte[]{1, 2, 3}).setAlgorithm("wrong algorithm").setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash();
            Assertions.fail("StringHashWithSaltBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong algorithm name: wrong algorithm");
        }
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void getNullSaltStoreTypeHashFailTest() {
        try {
            new StringHashWithSaltBuilder("12345", "UTF-8").setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(null).getHash();
            Assertions.fail("StringHashWithSaltBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt store type is null");
        }
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void isHashValidTest() {
        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{106, 21, -123, 68, 97, 35, 81, -14, 63, -88, -27, -102, 66, 24, 89, 102}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setSalt(new byte[]{1, 2, 2}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{106, 21, -123, 68, 97, 35, 81, -14, 63, -88, -27, -102, 66, 24, 89, 102}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{106, 21, -123, 68, 97, 35, 81, -14, 63, -88, -27, -102, 66, 24, 89, 101}).isHashValid()).isFalse();

        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setSalt(new byte[]{1, 2, 4}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-30, 85, -11, 50, -113, 26, -56, -104, 21, -105, 78, -106, -82, -45, -80, -117}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-30, 85, -11, 50, -113, 26, -56, -104, 21, -105, 78, -106, -82, -45, -80, -117}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setSalt(new byte[]{1, 2, 4}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-30, 85, -11, 50, -113, 26, -56, -104, 21, -105, 78, -106, -82, -45, -80, -118}).isHashValid()).isFalse();

        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setSalt(new byte[]{2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-15, 119, 44, 57, -83, 69, -80, 111, 66, -124, -39, 34, 101, 22, 107, 36}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setSalt(new byte[]{2, 2}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-15, 119, 44, 57, -83, 69, -80, 111, 66, -124, -39, 34, 101, 22, 107, 36}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setSalt(new byte[]{2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-15, 119, 44, 57, -83, 69, -80, 111, 66, -124, -39, 34, 101, 22, 107, 35}).isHashValid()).isFalse();

        Assertions.assertThat(new StringHashWithSaltBuilder("12346", "UTF-8").setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-33, -117, 91, 73, 92, 31, -87, 85, -57, 94, -40, 125, 104, 81, 55, 50}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSaltBuilder("12346", "UTF-8").setSalt(new byte[]{1, 2, 2}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-33, -117, 91, 73, 92, 31, -87, 85, -57, 94, -40, 125, 104, 81, 55, 50}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSaltBuilder("12346", "UTF-8").setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-33, -117, 91, 73, 92, 31, -87, 85, -57, 94, -40, 125, 104, 81, 55, 49}).isHashValid()).isFalse();

        Assertions.assertThat(new StringHashWithSaltBuilder("12346", "UTF-8").setSalt(new byte[]{1, 2, 4}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{86, 15, 117, -6, 44, 93, 22, -31, 32, -72, -50, -87, 72, 25, -72, -107}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSaltBuilder("12346", "UTF-8").setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{86, 15, 117, -6, 44, 93, 22, -31, 32, -72, -50, -87, 72, 25, -72, -107}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSaltBuilder("12346", "UTF-8").setSalt(new byte[]{1, 2, 4}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{86, 15, 117, -6, 44, 93, 22, -31, 32, -72, -50, -87, 72, 25, -72, -108}).isHashValid()).isFalse();

        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{1, 2, 3, 106, 21, -123, 68, 97, 35, 81, -14, 63, -88, -27, -102, 66, 24, 89, 102}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{1, 2, 2, 106, 21, -123, 68, 97, 35, 81, -14, 63, -88, -27, -102, 66, 24, 89, 102}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{1, 2, 3, 106, 21, -123, 68, 97, 35, 81, -14, 63, -88, -27, -102, 66, 24, 89, 101}).isHashValid()).isFalse();

        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{1, 2, 4, -30, 85, -11, 50, -113, 26, -56, -104, 21, -105, 78, -106, -82, -45, -80, -117}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{1, 2, 3, -30, 85, -11, 50, -113, 26, -56, -104, 21, -105, 78, -106, -82, -45, -80, -117}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{1, 2, 4, -30, 85, -11, 50, -113, 26, -56, -104, 21, -105, 78, -106, -82, -45, -80, -118}).isHashValid()).isFalse();

        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{2, 3, -15, 119, 44, 57, -83, 69, -80, 111, 66, -124, -39, 34, 101, 22, 107, 36}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{2, 2, -15, 119, 44, 57, -83, 69, -80, 111, 66, -124, -39, 34, 101, 22, 107, 36}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{2, 3, -15, 119, 44, 57, -83, 69, -80, 111, 66, -124, -39, 34, 101, 22, 107, 35}).isHashValid()).isFalse();

        Assertions.assertThat(new StringHashWithSaltBuilder("12346", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{1, 2, 3, -33, -117, 91, 73, 92, 31, -87, 85, -57, 94, -40, 125, 104, 81, 55, 50}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSaltBuilder("12346", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{1, 2, 2, -33, -117, 91, 73, 92, 31, -87, 85, -57, 94, -40, 125, 104, 81, 55, 50}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSaltBuilder("12346", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{1, 2, 3, -33, -117, 91, 73, 92, 31, -87, 85, -57, 94, -40, 125, 104, 81, 55, 49}).isHashValid()).isFalse();

        Assertions.assertThat(new StringHashWithSaltBuilder("12346", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{1, 2, 4, 86, 15, 117, -6, 44, 93, 22, -31, 32, -72, -50, -87, 72, 25, -72, -107}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSaltBuilder("12346", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{1, 2, 3, 86, 15, 117, -6, 44, 93, 22, -31, 32, -72, -50, -87, 72, 25, -72, -107}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSaltBuilder("12346", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{1, 2, 4, 86, 15, 117, -6, 44, 93, 22, -31, 32, -72, -50, -87, 72, 25, -72, -108}).isHashValid()).isFalse();

        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{106, 21, -123, 68, 97, 35, 81, -14, 63, -88, -27, -102, 66, 24, 89, 102, 1, 2, 3}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{106, 21, -123, 68, 97, 35, 81, -14, 63, -88, -27, -102, 66, 24, 89, 102, 1, 2, 2}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{106, 21, -123, 68, 97, 35, 81, -14, 63, -88, -27, -102, 66, 24, 89, 101, 1, 2, 3}).isHashValid()).isFalse();

        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-30, 85, -11, 50, -113, 26, -56, -104, 21, -105, 78, -106, -82, -45, -80, -117, 1, 2, 4}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-30, 85, -11, 50, -113, 26, -56, -104, 21, -105, 78, -106, -82, -45, -80, -117, 1, 2, 3}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-30, 85, -11, 50, -113, 26, -56, -104, 21, -105, 78, -106, -82, -45, -80, -118, 1, 2, 4}).isHashValid()).isFalse();

        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-15, 119, 44, 57, -83, 69, -80, 111, 66, -124, -39, 34, 101, 22, 107, 36, 2, 3}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-15, 119, 44, 57, -83, 69, -80, 111, 66, -124, -39, 34, 101, 22, 107, 36, 2, 2}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSaltBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-15, 119, 44, 57, -83, 69, -80, 111, 66, -124, -39, 34, 101, 22, 107, 35, 2, 3}).isHashValid()).isFalse();

        Assertions.assertThat(new StringHashWithSaltBuilder("12346", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-33, -117, 91, 73, 92, 31, -87, 85, -57, 94, -40, 125, 104, 81, 55, 50, 1, 2, 3}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSaltBuilder("12346", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-33, -117, 91, 73, 92, 31, -87, 85, -57, 94, -40, 125, 104, 81, 55, 50, 1, 2, 2}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSaltBuilder("12346", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-33, -117, 91, 73, 92, 31, -87, 85, -57, 94, -40, 125, 104, 81, 55, 49, 1, 2, 3}).isHashValid()).isFalse();

        Assertions.assertThat(new StringHashWithSaltBuilder("12346", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{86, 15, 117, -6, 44, 93, 22, -31, 32, -72, -50, -87, 72, 25, -72, -107, 1, 2, 4}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSaltBuilder("12346", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{86, 15, 117, -6, 44, 93, 22, -31, 32, -72, -50, -87, 72, 25, -72, -107, 1, 2, 3}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSaltBuilder("12346", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{86, 15, 117, -6, 44, 93, 22, -31, 32, -72, -50, -87, 72, 25, -72, -108, 1, 2, 4}).isHashValid()).isFalse();
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void isNullHashValidFailTest() {
        try {
            new StringHashWithSaltBuilder(null, "UTF-8").setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-2, 88, 67, -50, -104, -116, 105, 22, -83, 109, 34, 41, 79, 5, 43, -95}).isHashValid();
            Assertions.fail("StringHashWithSaltBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Source string is null");
        }
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void isNullEncodingHashValidFailTest() {
        try {
            new StringHashWithSaltBuilder("12345", null).setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-2, 88, 67, -50, -104, -116, 105, 22, -83, 109, 34, 41, 79, 5, 43, -95}).isHashValid();
            Assertions.fail("StringHashWithSaltBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Source string encoding is null");
        }
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void isWrongEncodingHashValidFailTest() {
        try {
            new StringHashWithSaltBuilder("12345", "wrong encoding").setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-2, 88, 67, -50, -104, -116, 105, 22, -83, 109, 34, 41, 79, 5, 43, -95}).isHashValid();
            Assertions.fail("StringHashWithSaltBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong source string encoding: wrong encoding");
        }
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void isNullSaltHashValidFailTest() {
        try {
            new StringHashWithSaltBuilder("12345", "UTF-8").setSalt(null).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-2, 88, 67, -50, -104, -116, 105, 22, -83, 109, 34, 41, 79, 5, 43, -95}).isHashValid();
            Assertions.fail("StringHashWithSaltBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt byte array is null");
        }
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void isNullAlgorithmHashValidFailTest() {
        try {
            new StringHashWithSaltBuilder("12345", "UTF-8").setSalt(new byte[]{1, 2, 3}).setAlgorithm(null).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-2, 88, 67, -50, -104, -116, 105, 22, -83, 109, 34, 41, 79, 5, 43, -95}).isHashValid();
            Assertions.fail("StringHashWithSaltBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Algorithm is null");
        }
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void isWrongAlgorithmHashValidFailTest() {
        try {
            new StringHashWithSaltBuilder("12345", "UTF-8").setSalt(new byte[]{1, 2, 3}).setAlgorithm("wrong algorithm").setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-2, 88, 67, -50, -104, -116, 105, 22, -83, 109, 34, 41, 79, 5, 43, -95}).isHashValid();
            Assertions.fail("StringHashWithSaltBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong algorithm name: wrong algorithm");
        }
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void isNullSaltStoreTypeHashValidFailTest() {
        try {
            new StringHashWithSaltBuilder("12345", "UTF-8").setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(null).setStoredHash(new byte[]{-2, 88, 67, -50, -104, -116, 105, 22, -83, 109, 34, 41, 79, 5, 43, -95}).isHashValid();
            Assertions.fail("StringHashWithSaltBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt store type is null");
        }
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void isNullStoredHashHashValidFailTest() {
        try {
            new StringHashWithSaltBuilder("12345", "UTF-8").setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(null).isHashValid();
            Assertions.fail("StringHashWithSaltBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Hash byte array is null");
        }
    }

    /**
     * {@link StringHashWithSaltBuilder} class test.
     */
    @Test
    public void isWrongStoredHashLengthHashValidTest() {
        try {
            new StringHashWithSaltBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{1, 2, 3, 4, 5, 6}).isHashValid();
            Assertions.fail("StringHashWithSaltBuilder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt length is not within [0; 6)");
        }
    }

}
