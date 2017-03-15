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

import java.io.ByteArrayInputStream;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.hash.Hash;
import ru.d_shap.hash.HashAlgorithms;
import ru.d_shap.hash.HashHelper;
import ru.d_shap.hash.SaltStoreType;
import ru.d_shap.hash.WrongArgumentException;

/**
 * Tests for {@link InputStreamHashWithSaltBuilder}.
 *
 * @author Dmitry Shapovalov
 */
public final class InputStreamHashWithSaltBuilderTest {

    /**
     * Test class constructor.
     */
    public InputStreamHashWithSaltBuilderTest() {
        super();
    }

    /**
     * {@link InputStreamHashWithSaltBuilder} class test.
     */
    @Test
    public void getSaltTest() {
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(null).getSalt()).isNull();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(null).setSalt(new byte[]{1, 2, 3}).getSalt()).containsExactlyInOrder(1, 2, 3);
    }

    /**
     * {@link InputStreamHashWithSaltBuilder} class test.
     */
    @Test
    public void setSaltTest() {
        InputStreamHashWithSaltBuilder builder = new InputStreamHashWithSaltBuilder(null);
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
     * {@link InputStreamHashWithSaltBuilder} class test.
     */
    @Test
    public void saltDifferentTest() {
        InputStreamHashWithSaltBuilder builder = new InputStreamHashWithSaltBuilder(null);
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
     * {@link InputStreamHashWithSaltBuilder} class test.
     */
    @Test
    public void addSaltTest() {
        Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
        Assertions.assertThat(hash.getBytes()).containsExactlyInOrder(82, -119, -33, 115, 125, -11, 115, 38, -4, -35, 34, 89, 122, -5, 31, -84);

        InputStreamHashWithSaltBuilder builder = new InputStreamHashWithSaltBuilder(null);
        builder.setSalt(new byte[]{10, 11});
        builder.addSalt(hash);
        Assertions.assertThat(hash.getBytes()).containsExactlyInOrder(29, -66, 31, 50, 105, -4, 117, 85, -40, 2, -105, 71, 90, 90, -16, -99);
    }

    /**
     * {@link InputStreamHashWithSaltBuilder} class test.
     */
    @Test
    public void addNullHashSaltFailTest() {
        try {
            InputStreamHashWithSaltBuilder builder = new InputStreamHashWithSaltBuilder(null);
            builder.setSalt(new byte[]{10, 11});
            builder.addSalt(null);
            Assertions.fail("InputStreamHashWithSaltBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Hash is null");
        }
    }

    /**
     * {@link InputStreamHashWithSaltBuilder} class test.
     */
    @Test
    public void addNullSaltFailTest() {
        try {
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
            InputStreamHashWithSaltBuilder builder = new InputStreamHashWithSaltBuilder(null);
            builder.setSalt(null);
            builder.addSalt(hash);
            Assertions.fail("InputStreamHashWithSaltBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt byte array is null");
        }
    }

    /**
     * {@link InputStreamHashWithSaltBuilder} class test.
     */
    @Test
    public void addSaltBytesTest() {
        Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
        Assertions.assertThat(hash.getBytes()).containsExactlyInOrder(82, -119, -33, 115, 125, -11, 115, 38, -4, -35, 34, 89, 122, -5, 31, -84);

        InputStreamHashWithSaltBuilder builder = new InputStreamHashWithSaltBuilder(null);
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
     * {@link InputStreamHashWithSaltBuilder} class test.
     */
    @Test
    public void addNullHashSaltBytesFailTest() {
        try {
            InputStreamHashWithSaltBuilder builder = new InputStreamHashWithSaltBuilder(null);
            builder.setSalt(new byte[]{0, 0, 0});
            builder.setSaltStoreType(SaltStoreType.AT_THE_BEGINNING);
            builder.addSaltBytes(null);
            Assertions.fail("InputStreamHashWithSaltBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Hash is null");
        }
    }

    /**
     * {@link InputStreamHashWithSaltBuilder} class test.
     */
    @Test
    public void addNullSaltBytesFailTest() {
        try {
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
            InputStreamHashWithSaltBuilder builder = new InputStreamHashWithSaltBuilder(null);
            builder.setSalt(null);
            builder.setSaltStoreType(SaltStoreType.AT_THE_BEGINNING);
            builder.addSaltBytes(hash);
            Assertions.fail("InputStreamHashWithSaltBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt byte array is null");
        }
    }

    /**
     * {@link InputStreamHashWithSaltBuilder} class test.
     */
    @Test
    public void addNullSaltStoreTypeSaltBytesFailTest() {
        try {
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
            InputStreamHashWithSaltBuilder builder = new InputStreamHashWithSaltBuilder(null);
            builder.setSalt(new byte[]{0, 0, 0});
            builder.setSaltStoreType(null);
            builder.addSaltBytes(hash);
            Assertions.fail("InputStreamHashWithSaltBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt store type is null");
        }
    }

    /**
     * {@link InputStreamHashWithSaltBuilder} class test.
     */
    @Test
    public void getSaltStoreTypeTest() {
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(null).getSaltStoreType()).isEqualTo(SaltStoreType.DO_NOT_STORE);
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(null).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).getSaltStoreType()).isEqualTo(SaltStoreType.AT_THE_BEGINNING);
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(null).setSaltStoreType(SaltStoreType.AT_THE_END).getSaltStoreType()).isEqualTo(SaltStoreType.AT_THE_END);
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(null).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getSaltStoreType()).isEqualTo(SaltStoreType.DO_NOT_STORE);
    }

    /**
     * {@link InputStreamHashWithSaltBuilder} class test.
     */
    @Test
    public void setSaltStoreTypeTest() {
        InputStreamHashWithSaltBuilder builder = new InputStreamHashWithSaltBuilder(null);
        Assertions.assertThat(builder.getSaltStoreType()).isEqualTo(SaltStoreType.DO_NOT_STORE);
        builder.setSaltStoreType(SaltStoreType.AT_THE_BEGINNING);
        Assertions.assertThat(builder.getSaltStoreType()).isEqualTo(SaltStoreType.AT_THE_BEGINNING);
        builder.setSaltStoreType(null);
        Assertions.assertThat(builder.getSaltStoreType()).isNull();
    }

    /**
     * {@link InputStreamHashWithSaltBuilder} class test.
     */
    @Test
    public void getStoredSaltLenghtTest() {
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(null).getStoredSaltLenght()).isEqualTo(0);
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(null).setStoredSaltLenght(16).getStoredSaltLenght()).isEqualTo(16);
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(null).setStoredSaltLenght(32).getStoredSaltLenght()).isEqualTo(32);
    }

    /**
     * {@link InputStreamHashWithSaltBuilder} class test.
     */
    @Test
    public void setStoredSaltLenghtTest() {
        InputStreamHashWithSaltBuilder builder = new InputStreamHashWithSaltBuilder(null);
        Assertions.assertThat(builder.getStoredSaltLenght()).isEqualTo(0);
        builder.setStoredSaltLenght(15);
        Assertions.assertThat(builder.getStoredSaltLenght()).isEqualTo(15);
        builder.setStoredSaltLenght(0);
        Assertions.assertThat(builder.getStoredSaltLenght()).isEqualTo(0);
        builder.setStoredSaltLenght(-10);
        Assertions.assertThat(builder.getStoredSaltLenght()).isEqualTo(-10);
    }

    /**
     * {@link InputStreamHashWithSaltBuilder} class test.
     */
    @Test
    public void getHashTest() {
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash()).containsExactlyInOrder(-2, 88, 67, -50, -104, -116, 105, 22, -83, 109, 34, 41, 79, 5, 43, -95);
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setSalt(new byte[]{1, 2, 4}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash()).containsExactlyInOrder(-68, -46, 106, 56, -92, 71, -121, -8, -111, -24, -113, 106, 67, 111, 98, -125);
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setSalt(new byte[]{2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash()).containsExactlyInOrder(38, -19, 127, 116, -118, 11, -127, 102, -115, -2, -16, 53, 38, -22, -26, -100);
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 6})).setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash()).containsExactlyInOrder(-8, 125, 3, -95, 17, -82, 3, 100, -52, 60, -78, 67, -63, 33, 58, 44);
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 6})).setSalt(new byte[]{1, 2, 4}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash()).containsExactlyInOrder(101, -59, 51, -74, 107, -56, -95, -20, 58, -53, 114, 33, -9, 18, 42, 59);

        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).getHash()).containsExactlyInOrder(1, 2, 3, -2, 88, 67, -50, -104, -116, 105, 22, -83, 109, 34, 41, 79, 5, 43, -95);
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setSalt(new byte[]{1, 2, 4}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).getHash()).containsExactlyInOrder(1, 2, 4, -68, -46, 106, 56, -92, 71, -121, -8, -111, -24, -113, 106, 67, 111, 98, -125);
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setSalt(new byte[]{2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).getHash()).containsExactlyInOrder(2, 3, 38, -19, 127, 116, -118, 11, -127, 102, -115, -2, -16, 53, 38, -22, -26, -100);
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 6})).setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).getHash()).containsExactlyInOrder(1, 2, 3, -8, 125, 3, -95, 17, -82, 3, 100, -52, 60, -78, 67, -63, 33, 58, 44);
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 6})).setSalt(new byte[]{1, 2, 4}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).getHash()).containsExactlyInOrder(1, 2, 4, 101, -59, 51, -74, 107, -56, -95, -20, 58, -53, 114, 33, -9, 18, 42, 59);

        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).getHash()).containsExactlyInOrder(-2, 88, 67, -50, -104, -116, 105, 22, -83, 109, 34, 41, 79, 5, 43, -95, 1, 2, 3);
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setSalt(new byte[]{1, 2, 4}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).getHash()).containsExactlyInOrder(-68, -46, 106, 56, -92, 71, -121, -8, -111, -24, -113, 106, 67, 111, 98, -125, 1, 2, 4);
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setSalt(new byte[]{2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).getHash()).containsExactlyInOrder(38, -19, 127, 116, -118, 11, -127, 102, -115, -2, -16, 53, 38, -22, -26, -100, 2, 3);
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 6})).setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).getHash()).containsExactlyInOrder(-8, 125, 3, -95, 17, -82, 3, 100, -52, 60, -78, 67, -63, 33, 58, 44, 1, 2, 3);
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 6})).setSalt(new byte[]{1, 2, 4}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).getHash()).containsExactlyInOrder(101, -59, 51, -74, 107, -56, -95, -20, 58, -53, 114, 33, -9, 18, 42, 59, 1, 2, 4);
    }

    /**
     * {@link InputStreamHashWithSaltBuilder} class test.
     */
    @Test
    public void getNullHashFailTest() {
        try {
            new InputStreamHashWithSaltBuilder(null).setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash();
            Assertions.fail("InputStreamHashWithSaltBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Source stream is null");
        }
    }

    /**
     * {@link InputStreamHashWithSaltBuilder} class test.
     */
    @Test
    public void getNullSaltHashFailTest() {
        try {
            new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setSalt(null).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash();
            Assertions.fail("InputStreamHashWithSaltBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt byte array is null");
        }
    }

    /**
     * {@link InputStreamHashWithSaltBuilder} class test.
     */
    @Test
    public void getNullAlgorithmHashFailTest() {
        try {
            new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setSalt(new byte[]{1, 2, 3}).setAlgorithm(null).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash();
            Assertions.fail("InputStreamHashWithSaltBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Algorithm is null");
        }
    }

    /**
     * {@link InputStreamHashWithSaltBuilder} class test.
     */
    @Test
    public void getWrongAlgorithmHashFailTest() {
        try {
            new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).
                    setSalt(new byte[]{1, 2, 3}).setAlgorithm("wrong algorithm").setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash();
            Assertions.fail("InputStreamHashWithSaltBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong algorithm name: wrong algorithm");
        }
    }

    /**
     * {@link InputStreamHashWithSaltBuilder} class test.
     */
    @Test
    public void getNullSaltStoreTypeHashFailTest() {
        try {
            new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).
                    setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(null).getHash();
            Assertions.fail("InputStreamHashWithSaltBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt store type is null");
        }
    }

    /**
     * {@link InputStreamHashWithSaltBuilder} class test.
     */
    @Test
    public void isHashValidTest() {
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-2, 88, 67, -50, -104, -116, 105, 22, -83, 109, 34, 41, 79, 5, 43, -95}).isHashValid()).isTrue();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setSalt(new byte[]{1, 2, 2}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-2, 88, 67, -50, -104, -116, 105, 22, -83, 109, 34, 41, 79, 5, 43, -95}).isHashValid()).isFalse();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-2, 88, 67, -50, -104, -116, 105, 22, -83, 109, 34, 41, 79, 5, 43, -96}).isHashValid()).isFalse();

        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setSalt(new byte[]{1, 2, 4}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-68, -46, 106, 56, -92, 71, -121, -8, -111, -24, -113, 106, 67, 111, 98, -125}).isHashValid()).isTrue();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-68, -46, 106, 56, -92, 71, -121, -8, -111, -24, -113, 106, 67, 111, 98, -125}).isHashValid()).isFalse();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setSalt(new byte[]{1, 2, 4}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-68, -46, 106, 56, -92, 71, -121, -8, -111, -24, -113, 106, 67, 111, 98, -126}).isHashValid()).isFalse();

        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setSalt(new byte[]{2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{38, -19, 127, 116, -118, 11, -127, 102, -115, -2, -16, 53, 38, -22, -26, -100}).isHashValid()).isTrue();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setSalt(new byte[]{2, 2}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{38, -19, 127, 116, -118, 11, -127, 102, -115, -2, -16, 53, 38, -22, -26, -100}).isHashValid()).isFalse();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setSalt(new byte[]{2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{38, -19, 127, 116, -118, 11, -127, 102, -115, -2, -16, 53, 38, -22, -26, -101}).isHashValid()).isFalse();

        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 6})).setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-8, 125, 3, -95, 17, -82, 3, 100, -52, 60, -78, 67, -63, 33, 58, 44}).isHashValid()).isTrue();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 6})).setSalt(new byte[]{1, 2, 2}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-8, 125, 3, -95, 17, -82, 3, 100, -52, 60, -78, 67, -63, 33, 58, 44}).isHashValid()).isFalse();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 6})).setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-8, 125, 3, -95, 17, -82, 3, 100, -52, 60, -78, 67, -63, 33, 58, 43}).isHashValid()).isFalse();

        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 6})).setSalt(new byte[]{1, 2, 4}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{101, -59, 51, -74, 107, -56, -95, -20, 58, -53, 114, 33, -9, 18, 42, 59}).isHashValid()).isTrue();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 6})).setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{101, -59, 51, -74, 107, -56, -95, -20, 58, -53, 114, 33, -9, 18, 42, 59}).isHashValid()).isFalse();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 6})).setSalt(new byte[]{1, 2, 4}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{101, -59, 51, -74, 107, -56, -95, -20, 58, -53, 114, 33, -9, 18, 42, 58}).isHashValid()).isFalse();

        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setStoredSaltLenght(3).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{1, 2, 3, -2, 88, 67, -50, -104, -116, 105, 22, -83, 109, 34, 41, 79, 5, 43, -95}).isHashValid()).isTrue();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setStoredSaltLenght(3).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{1, 2, 2, -2, 88, 67, -50, -104, -116, 105, 22, -83, 109, 34, 41, 79, 5, 43, -95}).isHashValid()).isFalse();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setStoredSaltLenght(3).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{1, 2, 3, -2, 88, 67, -50, -104, -116, 105, 22, -83, 109, 34, 41, 79, 5, 43, -96}).isHashValid()).isFalse();

        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setStoredSaltLenght(3).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{1, 2, 4, -68, -46, 106, 56, -92, 71, -121, -8, -111, -24, -113, 106, 67, 111, 98, -125}).isHashValid()).isTrue();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setStoredSaltLenght(3).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{1, 2, 3, -68, -46, 106, 56, -92, 71, -121, -8, -111, -24, -113, 106, 67, 111, 98, -125}).isHashValid()).isFalse();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setStoredSaltLenght(3).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{1, 2, 4, -68, -46, 106, 56, -92, 71, -121, -8, -111, -24, -113, 106, 67, 111, 98, -126}).isHashValid()).isFalse();

        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setStoredSaltLenght(2).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{2, 3, 38, -19, 127, 116, -118, 11, -127, 102, -115, -2, -16, 53, 38, -22, -26, -100}).isHashValid()).isTrue();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setStoredSaltLenght(2).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{2, 2, 38, -19, 127, 116, -118, 11, -127, 102, -115, -2, -16, 53, 38, -22, -26, -100}).isHashValid()).isFalse();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setStoredSaltLenght(2).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{2, 3, 38, -19, 127, 116, -118, 11, -127, 102, -115, -2, -16, 53, 38, -22, -26, -101}).isHashValid()).isFalse();

        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 6})).setStoredSaltLenght(3).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{1, 2, 3, -8, 125, 3, -95, 17, -82, 3, 100, -52, 60, -78, 67, -63, 33, 58, 44}).isHashValid()).isTrue();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 6})).setStoredSaltLenght(3).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{1, 2, 2, -8, 125, 3, -95, 17, -82, 3, 100, -52, 60, -78, 67, -63, 33, 58, 44}).isHashValid()).isFalse();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 6})).setStoredSaltLenght(3).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{1, 2, 3, -8, 125, 3, -95, 17, -82, 3, 100, -52, 60, -78, 67, -63, 33, 58, 43}).isHashValid()).isFalse();

        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 6})).setStoredSaltLenght(3).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{1, 2, 4, 101, -59, 51, -74, 107, -56, -95, -20, 58, -53, 114, 33, -9, 18, 42, 59}).isHashValid()).isTrue();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 6})).setStoredSaltLenght(3).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{1, 2, 3, 101, -59, 51, -74, 107, -56, -95, -20, 58, -53, 114, 33, -9, 18, 42, 59}).isHashValid()).isFalse();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 6})).setStoredSaltLenght(3).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{1, 2, 4, 101, -59, 51, -74, 107, -56, -95, -20, 58, -53, 114, 33, -9, 18, 42, 58}).isHashValid()).isFalse();

        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setStoredSaltLenght(3).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-2, 88, 67, -50, -104, -116, 105, 22, -83, 109, 34, 41, 79, 5, 43, -95, 1, 2, 3}).isHashValid()).isTrue();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setStoredSaltLenght(3).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-2, 88, 67, -50, -104, -116, 105, 22, -83, 109, 34, 41, 79, 5, 43, -95, 1, 2, 2}).isHashValid()).isFalse();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setStoredSaltLenght(3).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-2, 88, 67, -50, -104, -116, 105, 22, -83, 109, 34, 41, 79, 5, 43, -96, 1, 2, 3}).isHashValid()).isFalse();

        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setStoredSaltLenght(3).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-68, -46, 106, 56, -92, 71, -121, -8, -111, -24, -113, 106, 67, 111, 98, -125, 1, 2, 4}).isHashValid()).isTrue();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setStoredSaltLenght(3).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-68, -46, 106, 56, -92, 71, -121, -8, -111, -24, -113, 106, 67, 111, 98, -125, 1, 2, 3}).isHashValid()).isFalse();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setStoredSaltLenght(3).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-68, -46, 106, 56, -92, 71, -121, -8, -111, -24, -113, 106, 67, 111, 98, -126, 1, 2, 4}).isHashValid()).isFalse();

        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setStoredSaltLenght(2).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{38, -19, 127, 116, -118, 11, -127, 102, -115, -2, -16, 53, 38, -22, -26, -100, 2, 3}).isHashValid()).isTrue();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setStoredSaltLenght(2).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{38, -19, 127, 116, -118, 11, -127, 102, -115, -2, -16, 53, 38, -22, -26, -100, 2, 2}).isHashValid()).isFalse();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setStoredSaltLenght(2).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{38, -19, 127, 116, -118, 11, -127, 102, -115, -2, -16, 53, 38, -22, -26, -101, 2, 3}).isHashValid()).isFalse();

        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 6})).setStoredSaltLenght(3).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-8, 125, 3, -95, 17, -82, 3, 100, -52, 60, -78, 67, -63, 33, 58, 44, 1, 2, 3}).isHashValid()).isTrue();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 6})).setStoredSaltLenght(3).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-8, 125, 3, -95, 17, -82, 3, 100, -52, 60, -78, 67, -63, 33, 58, 44, 1, 2, 2}).isHashValid()).isFalse();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 6})).setStoredSaltLenght(3).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-8, 125, 3, -95, 17, -82, 3, 100, -52, 60, -78, 67, -63, 33, 58, 43, 1, 2, 3}).isHashValid()).isFalse();

        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 6})).setStoredSaltLenght(3).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{101, -59, 51, -74, 107, -56, -95, -20, 58, -53, 114, 33, -9, 18, 42, 59, 1, 2, 4}).isHashValid()).isTrue();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 6})).setStoredSaltLenght(3).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{101, -59, 51, -74, 107, -56, -95, -20, 58, -53, 114, 33, -9, 18, 42, 59, 1, 2, 3}).isHashValid()).isFalse();
        Assertions.assertThat(new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 6})).setStoredSaltLenght(3).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{101, -59, 51, -74, 107, -56, -95, -20, 58, -53, 114, 33, -9, 18, 42, 58, 1, 2, 4}).isHashValid()).isFalse();
    }

    /**
     * {@link InputStreamHashWithSaltBuilder} class test.
     */
    @Test
    public void isNullHashValidFailTest() {
        try {
            new InputStreamHashWithSaltBuilder(null).setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-2, 88, 67, -50, -104, -116, 105, 22, -83, 109, 34, 41, 79, 5, 43, -95}).isHashValid();
            Assertions.fail("InputStreamHashWithSaltBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Source stream is null");
        }
    }

    /**
     * {@link InputStreamHashWithSaltBuilder} class test.
     */
    @Test
    public void isNullSaltHashValidFailTest() {
        try {
            new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setSalt(null).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-2, 88, 67, -50, -104, -116, 105, 22, -83, 109, 34, 41, 79, 5, 43, -95}).isHashValid();
            Assertions.fail("InputStreamHashWithSaltBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt byte array is null");
        }
    }

    /**
     * {@link InputStreamHashWithSaltBuilder} class test.
     */
    @Test
    public void isNullAlgorithmHashValidFailTest() {
        try {
            new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setSalt(new byte[]{1, 2, 3}).setAlgorithm(null).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-2, 88, 67, -50, -104, -116, 105, 22, -83, 109, 34, 41, 79, 5, 43, -95}).isHashValid();
            Assertions.fail("InputStreamHashWithSaltBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Algorithm is null");
        }
    }

    /**
     * {@link InputStreamHashWithSaltBuilder} class test.
     */
    @Test
    public void isWrongAlgorithmHashValidFailTest() {
        try {
            new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setSalt(new byte[]{1, 2, 3}).setAlgorithm("wrong algorithm").setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-2, 88, 67, -50, -104, -116, 105, 22, -83, 109, 34, 41, 79, 5, 43, -95}).isHashValid();
            Assertions.fail("InputStreamHashWithSaltBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong algorithm name: wrong algorithm");
        }
    }

    /**
     * {@link InputStreamHashWithSaltBuilder} class test.
     */
    @Test
    public void isNullSaltStoreTypeHashValidFailTest() {
        try {
            new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(null).setStoredHash(new byte[]{-2, 88, 67, -50, -104, -116, 105, 22, -83, 109, 34, 41, 79, 5, 43, -95}).isHashValid();
            Assertions.fail("InputStreamHashWithSaltBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt store type is null");
        }
    }

    /**
     * {@link InputStreamHashWithSaltBuilder} class test.
     */
    @Test
    public void isNullStoredHashHashValidFailTest() {
        try {
            new InputStreamHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setSalt(new byte[]{1, 2, 3}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(null).isHashValid();
            Assertions.fail("InputStreamHashWithSaltBuilder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Hash byte array is null");
        }
    }

}
