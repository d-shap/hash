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
import ru.d_shap.hash.SaltOrder;
import ru.d_shap.hash.SaltStoreType;

/**
 * Tests for {@link StringHashWithSalt2Builder}.
 *
 * @author Dmitry Shapovalov
 */
public final class StringHashWithSalt2BuilderTest {

    /**
     * Test class constructor.
     */
    public StringHashWithSalt2BuilderTest() {
        super();
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void getStoredSaltTest() {
        Assertions.assertThat(new StringHashWithSalt2Builder(null, null).getStoredSalt()).isNull();
        Assertions.assertThat(new StringHashWithSalt2Builder(null, null).setStoredSalt(new byte[]{1, 2, 3}).getStoredSalt()).containsExactlyInOrder(1, 2, 3);
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void setStoredSaltTest() {
        StringHashWithSalt2Builder builder = new StringHashWithSalt2Builder(null, null);
        builder.setStoredSalt(new byte[]{1, 2, 3});
        Assertions.assertThat(builder.getStoredSalt()).containsExactlyInOrder(1, 2, 3);
        builder.setStoredSalt(new byte[]{10, 11});
        Assertions.assertThat(builder.getStoredSalt()).containsExactlyInOrder(10, 11);
        builder.setStoredSalt(new byte[]{});
        Assertions.assertThat(builder.getStoredSalt()).containsExactlyInOrder();
        builder.setStoredSalt(null);
        Assertions.assertThat(builder.getStoredSalt()).isNull();
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void storedSaltDifferentTest() {
        StringHashWithSalt2Builder builder = new StringHashWithSalt2Builder(null, null);
        byte[] salt = new byte[]{1, 2, 3};
        builder.setStoredSalt(salt);
        byte[] salt1 = builder.getStoredSalt();
        byte[] salt2 = builder.getStoredSalt();
        Assertions.assertThat(salt1).isNotSameAs(salt);
        Assertions.assertThat(salt2).isNotSameAs(salt);
        Assertions.assertThat(salt1).isNotSameAs(salt2);
        Assertions.assertThat(salt1).containsExactlyInOrder(salt);
        Assertions.assertThat(salt2).containsExactlyInOrder(salt);
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void getFixedSaltTest() {
        Assertions.assertThat(new StringHashWithSalt2Builder(null, null).getFixedSalt()).isNull();
        Assertions.assertThat(new StringHashWithSalt2Builder(null, null).setFixedSalt(new byte[]{1, 2, 3}).getFixedSalt()).containsExactlyInOrder(1, 2, 3);
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void setFixedSaltTest() {
        StringHashWithSalt2Builder builder = new StringHashWithSalt2Builder(null, null);
        builder.setFixedSalt(new byte[]{1, 2, 3});
        Assertions.assertThat(builder.getFixedSalt()).containsExactlyInOrder(1, 2, 3);
        builder.setFixedSalt(new byte[]{10, 11});
        Assertions.assertThat(builder.getFixedSalt()).containsExactlyInOrder(10, 11);
        builder.setFixedSalt(new byte[]{});
        Assertions.assertThat(builder.getFixedSalt()).containsExactlyInOrder();
        builder.setFixedSalt(null);
        Assertions.assertThat(builder.getFixedSalt()).isNull();
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void fixedSaltDifferentTest() {
        StringHashWithSalt2Builder builder = new StringHashWithSalt2Builder(null, null);
        byte[] salt = new byte[]{1, 2, 3};
        builder.setFixedSalt(salt);
        byte[] salt1 = builder.getFixedSalt();
        byte[] salt2 = builder.getFixedSalt();
        Assertions.assertThat(salt1).isNotSameAs(salt);
        Assertions.assertThat(salt2).isNotSameAs(salt);
        Assertions.assertThat(salt1).isNotSameAs(salt2);
        Assertions.assertThat(salt1).containsExactlyInOrder(salt);
        Assertions.assertThat(salt2).containsExactlyInOrder(salt);
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void addSaltTest() {
        Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
        Assertions.assertThat(hash.getBytes()).containsExactlyInOrder(82, -119, -33, 115, 125, -11, 115, 38, -4, -35, 34, 89, 122, -5, 31, -84);

        StringHashWithSalt2Builder builder = new StringHashWithSalt2Builder(null, null);
        builder.setStoredSalt(new byte[]{10, 11});
        builder.setFixedSalt(new byte[]{100, 101, 102});
        builder.setSaltOrder(SaltOrder.STORED_SALT_FIRST);
        builder.addSalt(hash);
        Assertions.assertThat(hash.getBytes()).containsExactlyInOrder(95, 120, -45, -63, 46, 32, -100, 115, -48, -65, -69, 71, 12, -90, 79, -31);
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void addNullHashSaltFailTest() {
        try {
            StringHashWithSalt2Builder builder = new StringHashWithSalt2Builder(null, null);
            builder.setStoredSalt(new byte[]{10, 11});
            builder.setFixedSalt(new byte[]{100, 101, 102});
            builder.setSaltOrder(SaltOrder.STORED_SALT_FIRST);
            builder.addSalt(null);
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Hash is null");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void addNullStoredSaltSaltFailTest() {
        try {
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
            StringHashWithSalt2Builder builder = new StringHashWithSalt2Builder(null, null);
            builder.setStoredSalt(null);
            builder.setFixedSalt(new byte[]{100, 101, 102});
            builder.setSaltOrder(SaltOrder.STORED_SALT_FIRST);
            builder.addSalt(hash);
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Stored salt is null");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void addNullFixedSaltSaltFailTest() {
        try {
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
            StringHashWithSalt2Builder builder = new StringHashWithSalt2Builder(null, null);
            builder.setStoredSalt(new byte[]{10, 11});
            builder.setFixedSalt(null);
            builder.setSaltOrder(SaltOrder.STORED_SALT_FIRST);
            builder.addSalt(hash);
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Fixed salt is null");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void addNullSaltOrderSaltFailTest() {
        try {
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
            StringHashWithSalt2Builder builder = new StringHashWithSalt2Builder(null, null);
            builder.setStoredSalt(new byte[]{10, 11});
            builder.setFixedSalt(new byte[]{100, 101, 102});
            builder.setSaltOrder(null);
            builder.addSalt(hash);
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt order is null");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void addStoredSaltTest() {
        Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
        Assertions.assertThat(hash.getBytes()).containsExactlyInOrder(82, -119, -33, 115, 125, -11, 115, 38, -4, -35, 34, 89, 122, -5, 31, -84);

        StringHashWithSalt2Builder builder = new StringHashWithSalt2Builder(null, null);
        builder.setFixedSalt(new byte[]{100, 101, 102});
        builder.setSaltOrder(SaltOrder.STORED_SALT_FIRST);
        builder.addSalt(hash, new byte[]{10, 11});
        Assertions.assertThat(hash.getBytes()).containsExactlyInOrder(95, 120, -45, -63, 46, 32, -100, 115, -48, -65, -69, 71, 12, -90, 79, -31);
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void addNullHashStoredSaltTest() {
        try {
            StringHashWithSalt2Builder builder = new StringHashWithSalt2Builder(null, null);
            builder.setFixedSalt(new byte[]{100, 101, 102});
            builder.setSaltOrder(SaltOrder.STORED_SALT_FIRST);
            builder.addSalt(null, new byte[]{10, 11});
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Hash is null");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void addNullStoredSaltStoredSaltTest() {
        try {
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
            StringHashWithSalt2Builder builder = new StringHashWithSalt2Builder(null, null);
            builder.setFixedSalt(new byte[]{100, 101, 102});
            builder.setSaltOrder(SaltOrder.STORED_SALT_FIRST);
            builder.addSalt(hash, null);
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Stored salt is null");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void addNullFixedSaltStoredSaltTest() {
        try {
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
            StringHashWithSalt2Builder builder = new StringHashWithSalt2Builder(null, null);
            builder.setFixedSalt(null);
            builder.setSaltOrder(SaltOrder.STORED_SALT_FIRST);
            builder.addSalt(hash, new byte[]{10, 11});
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Fixed salt is null");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void addNullSaltOrderStoredSaltTest() {
        try {
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
            StringHashWithSalt2Builder builder = new StringHashWithSalt2Builder(null, null);
            builder.setFixedSalt(new byte[]{100, 101, 102});
            builder.setSaltOrder(null);
            builder.addSalt(hash, new byte[]{10, 11});
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt order is null");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void addSaltBytesTest() {
        Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
        Assertions.assertThat(hash.getBytes()).containsExactlyInOrder(82, -119, -33, 115, 125, -11, 115, 38, -4, -35, 34, 89, 122, -5, 31, -84);

        StringHashWithSalt2Builder builder = new StringHashWithSalt2Builder(null, null);
        builder.setStoredSalt(new byte[]{0, 0, 0});
        builder.setFixedSalt(new byte[]{100, 101, 102});
        builder.setSaltOrder(SaltOrder.STORED_SALT_FIRST);
        Assertions.assertThat(builder.addSaltBytes(hash)).containsExactlyInOrder(82, -119, -33, 115, 125, -11, 115, 38, -4, -35, 34, 89, 122, -5, 31, -84);

        builder.setSaltStoreType(SaltStoreType.AT_THE_BEGINNING);
        Assertions.assertThat(builder.addSaltBytes(hash)).containsExactlyInOrder(0, 0, 0, 82, -119, -33, 115, 125, -11, 115, 38, -4, -35, 34, 89, 122, -5, 31, -84);

        builder.setSaltStoreType(SaltStoreType.AT_THE_END);
        Assertions.assertThat(builder.addSaltBytes(hash)).containsExactlyInOrder(82, -119, -33, 115, 125, -11, 115, 38, -4, -35, 34, 89, 122, -5, 31, -84, 0, 0, 0);

        builder.setSaltStoreType(SaltStoreType.DO_NOT_STORE);
        Assertions.assertThat(builder.addSaltBytes(hash)).containsExactlyInOrder(82, -119, -33, 115, 125, -11, 115, 38, -4, -35, 34, 89, 122, -5, 31, -84);
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void addNullHashSaltBytesFailTest() {
        try {
            StringHashWithSalt2Builder builder = new StringHashWithSalt2Builder(null, null);
            builder.setStoredSalt(new byte[]{0, 0, 0});
            builder.setFixedSalt(new byte[]{100, 101, 102});
            builder.setSaltOrder(SaltOrder.STORED_SALT_FIRST);
            builder.setSaltStoreType(SaltStoreType.AT_THE_BEGINNING);
            builder.addSaltBytes(null);
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Hash is null");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void addNullStoredSaltSaltBytesFailTest() {
        try {
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
            StringHashWithSalt2Builder builder = new StringHashWithSalt2Builder(null, null);
            builder.setStoredSalt(null);
            builder.setFixedSalt(new byte[]{100, 101, 102});
            builder.setSaltOrder(SaltOrder.STORED_SALT_FIRST);
            builder.setSaltStoreType(SaltStoreType.AT_THE_BEGINNING);
            builder.addSaltBytes(hash);
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt byte array is null");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void addNullSaltStoreTypeSaltBytesFailTest() {
        try {
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
            StringHashWithSalt2Builder builder = new StringHashWithSalt2Builder(null, null);
            builder.setStoredSalt(new byte[]{0, 0, 0});
            builder.setFixedSalt(new byte[]{100, 101, 102});
            builder.setSaltOrder(SaltOrder.STORED_SALT_FIRST);
            builder.setSaltStoreType(null);
            builder.addSaltBytes(hash);
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt store type is null");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void getSaltOrderTest() {
        Assertions.assertThat(new StringHashWithSalt2Builder(null, null).getSaltOrder()).isEqualTo(SaltOrder.STORED_SALT_FIRST);
        Assertions.assertThat(new StringHashWithSalt2Builder(null, null).setSaltOrder(SaltOrder.STORED_SALT_FIRST).getSaltOrder()).isEqualTo(SaltOrder.STORED_SALT_FIRST);
        Assertions.assertThat(new StringHashWithSalt2Builder(null, null).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).getSaltOrder()).isEqualTo(SaltOrder.FIXED_SALT_FIRST);
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void setSaltOrderTest() {
        StringHashWithSalt2Builder builder = new StringHashWithSalt2Builder(null, null);
        Assertions.assertThat(builder.getSaltOrder()).isEqualTo(SaltOrder.STORED_SALT_FIRST);
        builder.setSaltOrder(SaltOrder.FIXED_SALT_FIRST);
        Assertions.assertThat(builder.getSaltOrder()).isEqualTo(SaltOrder.FIXED_SALT_FIRST);
        builder.setSaltOrder(null);
        Assertions.assertThat(builder.getSaltOrder()).isNull();
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void getSaltStoreTypeTest() {
        Assertions.assertThat(new StringHashWithSalt2Builder(null, null).getSaltStoreType()).isEqualTo(SaltStoreType.DO_NOT_STORE);
        Assertions.assertThat(new StringHashWithSalt2Builder(null, null).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).getSaltStoreType()).isEqualTo(SaltStoreType.AT_THE_BEGINNING);
        Assertions.assertThat(new StringHashWithSalt2Builder(null, null).setSaltStoreType(SaltStoreType.AT_THE_END).getSaltStoreType()).isEqualTo(SaltStoreType.AT_THE_END);
        Assertions.assertThat(new StringHashWithSalt2Builder(null, null).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getSaltStoreType()).isEqualTo(SaltStoreType.DO_NOT_STORE);
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void setSaltStoreTypeTest() {
        StringHashWithSalt2Builder builder = new StringHashWithSalt2Builder(null, null);
        Assertions.assertThat(builder.getSaltStoreType()).isEqualTo(SaltStoreType.DO_NOT_STORE);
        builder.setSaltStoreType(SaltStoreType.AT_THE_BEGINNING);
        Assertions.assertThat(builder.getSaltStoreType()).isEqualTo(SaltStoreType.AT_THE_BEGINNING);
        builder.setSaltStoreType(null);
        Assertions.assertThat(builder.getSaltStoreType()).isNull();
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void getHashTest() {
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash()).containsExactlyInOrder(-69, 125, 101, -116, 8, -76, 112, 126, -74, -85, -30, -33, -98, -35, -96, -36);
        Assertions.assertThat(new StringHashWithSalt2Builder("12365", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash()).containsExactlyInOrder(-7, -41, 26, 83, 111, 104, 6, 102, 8, 85, -62, -117, -115, -83, 4, -18);
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 12}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash()).containsExactlyInOrder(94, 48, -92, -74, -67, -124, -93, -97, -104, 51, 37, -65, 82, 13, -32, -89);
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash()).containsExactlyInOrder(-68, 111, 97, -112, 0, 111, 6, 52, 103, 72, -78, 125, -52, -94, -77, 109);
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash()).containsExactlyInOrder(-56, -54, 43, 102, 27, -32, 7, 57, -124, -58, 61, -25, -58, 60, 18, 51);

        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).getHash()).containsExactlyInOrder(10, 11, -69, 125, 101, -116, 8, -76, 112, 126, -74, -85, -30, -33, -98, -35, -96, -36);
        Assertions.assertThat(new StringHashWithSalt2Builder("12365", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).getHash()).containsExactlyInOrder(10, 11, -7, -41, 26, 83, 111, 104, 6, 102, 8, 85, -62, -117, -115, -83, 4, -18);
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 12}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).getHash()).containsExactlyInOrder(10, 12, 94, 48, -92, -74, -67, -124, -93, -97, -104, 51, 37, -65, 82, 13, -32, -89);
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).getHash()).containsExactlyInOrder(10, 11, -68, 111, 97, -112, 0, 111, 6, 52, 103, 72, -78, 125, -52, -94, -77, 109);
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).getHash()).containsExactlyInOrder(10, 11, -56, -54, 43, 102, 27, -32, 7, 57, -124, -58, 61, -25, -58, 60, 18, 51);

        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).getHash()).containsExactlyInOrder(-69, 125, 101, -116, 8, -76, 112, 126, -74, -85, -30, -33, -98, -35, -96, -36, 10, 11);
        Assertions.assertThat(new StringHashWithSalt2Builder("12365", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).getHash()).containsExactlyInOrder(-7, -41, 26, 83, 111, 104, 6, 102, 8, 85, -62, -117, -115, -83, 4, -18, 10, 11);
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 12}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).getHash()).containsExactlyInOrder(94, 48, -92, -74, -67, -124, -93, -97, -104, 51, 37, -65, 82, 13, -32, -89, 10, 12);
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).getHash()).containsExactlyInOrder(-68, 111, 97, -112, 0, 111, 6, 52, 103, 72, -78, 125, -52, -94, -77, 109, 10, 11);
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).getHash()).containsExactlyInOrder(-56, -54, 43, 102, 27, -32, 7, 57, -124, -58, 61, -25, -58, 60, 18, 51, 10, 11);
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void getNullHashFailTest() {
        try {
            new StringHashWithSalt2Builder(null, "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash();
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Source string is null");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void getNullEncodingHashFailTest() {
        try {
            new StringHashWithSalt2Builder("12345", null).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash();
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Source string encoding is null");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void getWrongEncodingHashFailTest() {
        try {
            new StringHashWithSalt2Builder("12345", "wrong encoding").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash();
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong source string encoding: wrong encoding");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void getNullStoredSaltHashFailTest() {
        try {
            new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(null).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash();
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Stored salt is null");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void getNullFixedSaltHashFailTest() {
        try {
            new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(null).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash();
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Fixed salt is null");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void getNullAlgorithmHashFailTest() {
        try {
            new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(null).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash();
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Algorithm is null");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void getWrongAlgorithmHashFailTest() {
        try {
            new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm("wrong algorithm").setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash();
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong algorithm name: wrong algorithm");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void getNullSaltOrderHashFailTest() {
        try {
            new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(null).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash();
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt order is null");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void getNullSaltStoreTypeHashFailTest() {
        try {
            new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(null).getHash();
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt store type is null");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void isHashValidTest() {
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-69, 125, 101, -116, 8, -76, 112, 126, -74, -85, -30, -33, -98, -35, -96, -36}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSalt2Builder("92345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-69, 125, 101, -116, 8, -76, 112, 126, -74, -85, -30, -33, -98, -35, -96, -36}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{9, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-69, 125, 101, -116, 8, -76, 112, 126, -74, -85, -30, -33, -98, -35, -96, -36}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{109, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-69, 125, 101, -116, 8, -76, 112, 126, -74, -85, -30, -33, -98, -35, -96, -36}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-69, 125, 101, -116, 8, -76, 112, 126, -74, -85, -30, -33, -98, -35, -96, -36}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-60, 125, 101, -116, 8, -76, 112, 126, -74, -85, -30, -33, -98, -35, -96, -36}).isHashValid()).isFalse();

        Assertions.assertThat(new StringHashWithSalt2Builder("12365", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-7, -41, 26, 83, 111, 104, 6, 102, 8, 85, -62, -117, -115, -83, 4, -18}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSalt2Builder("92365", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-7, -41, 26, 83, 111, 104, 6, 102, 8, 85, -62, -117, -115, -83, 4, -18}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12365", "UTF-8").setStoredSalt(new byte[]{9, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-7, -41, 26, 83, 111, 104, 6, 102, 8, 85, -62, -117, -115, -83, 4, -18}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12365", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{109, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-7, -41, 26, 83, 111, 104, 6, 102, 8, 85, -62, -117, -115, -83, 4, -18}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12365", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-7, -41, 26, 83, 111, 104, 6, 102, 8, 85, -62, -117, -115, -83, 4, -18}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12365", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-9, -41, 26, 83, 111, 104, 6, 102, 8, 85, -62, -117, -115, -83, 4, -18}).isHashValid()).isFalse();

        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 12}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{94, 48, -92, -74, -67, -124, -93, -97, -104, 51, 37, -65, 82, 13, -32, -89}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSalt2Builder("92345", "UTF-8").setStoredSalt(new byte[]{10, 12}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{94, 48, -92, -74, -67, -124, -93, -97, -104, 51, 37, -65, 82, 13, -32, -89}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{9, 12}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{94, 48, -92, -74, -67, -124, -93, -97, -104, 51, 37, -65, 82, 13, -32, -89}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 12}).setFixedSalt(new byte[]{109, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{94, 48, -92, -74, -67, -124, -93, -97, -104, 51, 37, -65, 82, 13, -32, -89}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 12}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{94, 48, -92, -74, -67, -124, -93, -97, -104, 51, 37, -65, 82, 13, -32, -89}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 12}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{99, 48, -92, -74, -67, -124, -93, -97, -104, 51, 37, -65, 82, 13, -32, -89}).isHashValid()).isFalse();

        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-68, 111, 97, -112, 0, 111, 6, 52, 103, 72, -78, 125, -52, -94, -77, 109}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSalt2Builder("92345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-68, 111, 97, -112, 0, 111, 6, 52, 103, 72, -78, 125, -52, -94, -77, 109}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{9, 11}).setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-68, 111, 97, -112, 0, 111, 6, 52, 103, 72, -78, 125, -52, -94, -77, 109}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{109, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-68, 111, 97, -112, 0, 111, 6, 52, 103, 72, -78, 125, -52, -94, -77, 109}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-68, 111, 97, -112, 0, 111, 6, 52, 103, 72, -78, 125, -52, -94, -77, 109}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-69, 111, 97, -112, 0, 111, 6, 52, 103, 72, -78, 125, -52, -94, -77, 109}).isHashValid()).isFalse();

        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-56, -54, 43, 102, 27, -32, 7, 57, -124, -58, 61, -25, -58, 60, 18, 51}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSalt2Builder("92345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-56, -54, 43, 102, 27, -32, 7, 57, -124, -58, 61, -25, -58, 60, 18, 51}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{9, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-56, -54, 43, 102, 27, -32, 7, 57, -124, -58, 61, -25, -58, 60, 18, 51}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{109, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-56, -54, 43, 102, 27, -32, 7, 57, -124, -58, 61, -25, -58, 60, 18, 51}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-56, -54, 43, 102, 27, -32, 7, 57, -124, -58, 61, -25, -58, 60, 18, 51}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-59, -54, 43, 102, 27, -32, 7, 57, -124, -58, 61, -25, -58, 60, 18, 51}).isHashValid()).isFalse();

        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, -69, 125, 101, -116, 8, -76, 112, 126, -74, -85, -30, -33, -98, -35, -96, -36}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSalt2Builder("92345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, -69, 125, 101, -116, 8, -76, 112, 126, -74, -85, -30, -33, -98, -35, -96, -36}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{9, 11, -69, 125, 101, -116, 8, -76, 112, 126, -74, -85, -30, -33, -98, -35, -96, -36}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{109, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, -69, 125, 101, -116, 8, -76, 112, 126, -74, -85, -30, -33, -98, -35, -96, -36}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, -69, 125, 101, -116, 8, -76, 112, 126, -74, -85, -30, -33, -98, -35, -96, -36}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, -60, 125, 101, -116, 8, -76, 112, 126, -74, -85, -30, -33, -98, -35, -96, -36}).isHashValid()).isFalse();

        Assertions.assertThat(new StringHashWithSalt2Builder("12365", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, -7, -41, 26, 83, 111, 104, 6, 102, 8, 85, -62, -117, -115, -83, 4, -18}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSalt2Builder("92365", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, -7, -41, 26, 83, 111, 104, 6, 102, 8, 85, -62, -117, -115, -83, 4, -18}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12365", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{9, 11, -7, -41, 26, 83, 111, 104, 6, 102, 8, 85, -62, -117, -115, -83, 4, -18}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12365", "UTF-8").setFixedSalt(new byte[]{109, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, -7, -41, 26, 83, 111, 104, 6, 102, 8, 85, -62, -117, -115, -83, 4, -18}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12365", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, -7, -41, 26, 83, 111, 104, 6, 102, 8, 85, -62, -117, -115, -83, 4, -18}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12365", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, -9, -41, 26, 83, 111, 104, 6, 102, 8, 85, -62, -117, -115, -83, 4, -18}).isHashValid()).isFalse();

        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 12, 94, 48, -92, -74, -67, -124, -93, -97, -104, 51, 37, -65, 82, 13, -32, -89}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSalt2Builder("92345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 12, 94, 48, -92, -74, -67, -124, -93, -97, -104, 51, 37, -65, 82, 13, -32, -89}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{9, 12, 94, 48, -92, -74, -67, -124, -93, -97, -104, 51, 37, -65, 82, 13, -32, -89}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{109, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 12, 94, 48, -92, -74, -67, -124, -93, -97, -104, 51, 37, -65, 82, 13, -32, -89}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 12, 94, 48, -92, -74, -67, -124, -93, -97, -104, 51, 37, -65, 82, 13, -32, -89}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 12, 99, 48, -92, -74, -67, -124, -93, -97, -104, 51, 37, -65, 82, 13, -32, -89}).isHashValid()).isFalse();

        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, -68, 111, 97, -112, 0, 111, 6, 52, 103, 72, -78, 125, -52, -94, -77, 109}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSalt2Builder("92345", "UTF-8").setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, -68, 111, 97, -112, 0, 111, 6, 52, 103, 72, -78, 125, -52, -94, -77, 109}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{9, 11, -68, 111, 97, -112, 0, 111, 6, 52, 103, 72, -78, 125, -52, -94, -77, 109}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{109, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, -68, 111, 97, -112, 0, 111, 6, 52, 103, 72, -78, 125, -52, -94, -77, 109}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, -68, 111, 97, -112, 0, 111, 6, 52, 103, 72, -78, 125, -52, -94, -77, 109}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, -69, 111, 97, -112, 0, 111, 6, 52, 103, 72, -78, 125, -52, -94, -77, 109}).isHashValid()).isFalse();

        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, -56, -54, 43, 102, 27, -32, 7, 57, -124, -58, 61, -25, -58, 60, 18, 51}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSalt2Builder("92345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, -56, -54, 43, 102, 27, -32, 7, 57, -124, -58, 61, -25, -58, 60, 18, 51}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{9, 11, -56, -54, 43, 102, 27, -32, 7, 57, -124, -58, 61, -25, -58, 60, 18, 51}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{109, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, -56, -54, 43, 102, 27, -32, 7, 57, -124, -58, 61, -25, -58, 60, 18, 51}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, -56, -54, 43, 102, 27, -32, 7, 57, -124, -58, 61, -25, -58, 60, 18, 51}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, -59, -54, 43, 102, 27, -32, 7, 57, -124, -58, 61, -25, -58, 60, 18, 51}).isHashValid()).isFalse();

        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-69, 125, 101, -116, 8, -76, 112, 126, -74, -85, -30, -33, -98, -35, -96, -36, 10, 11}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSalt2Builder("92345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-69, 125, 101, -116, 8, -76, 112, 126, -74, -85, -30, -33, -98, -35, -96, -36, 10, 11}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-69, 125, 101, -116, 8, -76, 112, 126, -74, -85, -30, -33, -98, -35, -96, -36, 9, 11}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{109, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-69, 125, 101, -116, 8, -76, 112, 126, -74, -85, -30, -33, -98, -35, -96, -36, 10, 11}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-69, 125, 101, -116, 8, -76, 112, 126, -74, -85, -30, -33, -98, -35, -96, -36, 10, 11}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-60, 125, 101, -116, 8, -76, 112, 126, -74, -85, -30, -33, -98, -35, -96, -36, 10, 11}).isHashValid()).isFalse();

        Assertions.assertThat(new StringHashWithSalt2Builder("12365", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-7, -41, 26, 83, 111, 104, 6, 102, 8, 85, -62, -117, -115, -83, 4, -18, 10, 11}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSalt2Builder("92365", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-7, -41, 26, 83, 111, 104, 6, 102, 8, 85, -62, -117, -115, -83, 4, -18, 10, 11}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12365", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-7, -41, 26, 83, 111, 104, 6, 102, 8, 85, -62, -117, -115, -83, 4, -18, 9, 11}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12365", "UTF-8").setFixedSalt(new byte[]{109, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-7, -41, 26, 83, 111, 104, 6, 102, 8, 85, -62, -117, -115, -83, 4, -18, 10, 11}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12365", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-7, -41, 26, 83, 111, 104, 6, 102, 8, 85, -62, -117, -115, -83, 4, -18, 10, 11}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12365", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-9, -41, 26, 83, 111, 104, 6, 102, 8, 85, -62, -117, -115, -83, 4, -18, 10, 11}).isHashValid()).isFalse();

        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{94, 48, -92, -74, -67, -124, -93, -97, -104, 51, 37, -65, 82, 13, -32, -89, 10, 12}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSalt2Builder("92345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{94, 48, -92, -74, -67, -124, -93, -97, -104, 51, 37, -65, 82, 13, -32, -89, 10, 12}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{94, 48, -92, -74, -67, -124, -93, -97, -104, 51, 37, -65, 82, 13, -32, -89, 9, 12}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{109, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{94, 48, -92, -74, -67, -124, -93, -97, -104, 51, 37, -65, 82, 13, -32, -89, 10, 12}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{94, 48, -92, -74, -67, -124, -93, -97, -104, 51, 37, -65, 82, 13, -32, -89, 10, 12}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{99, 48, -92, -74, -67, -124, -93, -97, -104, 51, 37, -65, 82, 13, -32, -89, 10, 12}).isHashValid()).isFalse();

        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-68, 111, 97, -112, 0, 111, 6, 52, 103, 72, -78, 125, -52, -94, -77, 109, 10, 11}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSalt2Builder("92345", "UTF-8").setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-68, 111, 97, -112, 0, 111, 6, 52, 103, 72, -78, 125, -52, -94, -77, 109, 10, 11}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-68, 111, 97, -112, 0, 111, 6, 52, 103, 72, -78, 125, -52, -94, -77, 109, 9, 11}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{109, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-68, 111, 97, -112, 0, 111, 6, 52, 103, 72, -78, 125, -52, -94, -77, 109, 10, 11}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-68, 111, 97, -112, 0, 111, 6, 52, 103, 72, -78, 125, -52, -94, -77, 109, 10, 11}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-69, 111, 97, -112, 0, 111, 6, 52, 103, 72, -78, 125, -52, -94, -77, 109, 10, 11}).isHashValid()).isFalse();

        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-56, -54, 43, 102, 27, -32, 7, 57, -124, -58, 61, -25, -58, 60, 18, 51, 10, 11}).isHashValid()).isTrue();
        Assertions.assertThat(new StringHashWithSalt2Builder("92345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-56, -54, 43, 102, 27, -32, 7, 57, -124, -58, 61, -25, -58, 60, 18, 51, 10, 11}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-56, -54, 43, 102, 27, -32, 7, 57, -124, -58, 61, -25, -58, 60, 18, 51, 9, 11}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{109, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-56, -54, 43, 102, 27, -32, 7, 57, -124, -58, 61, -25, -58, 60, 18, 51, 10, 11}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-56, -54, 43, 102, 27, -32, 7, 57, -124, -58, 61, -25, -58, 60, 18, 51, 10, 11}).isHashValid()).isFalse();
        Assertions.assertThat(new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-59, -54, 43, 102, 27, -32, 7, 57, -124, -58, 61, -25, -58, 60, 18, 51, 10, 11}).isHashValid()).isFalse();
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void isNullHashValidFailTest() {
        try {
            new StringHashWithSalt2Builder(null, "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92}).isHashValid();
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Source string is null");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void isNullEncodingHashValidFailTest() {
        try {
            new StringHashWithSalt2Builder("12345", null).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92}).isHashValid();
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Source string encoding is null");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void isWrongEncodingHashValidFailTest() {
        try {
            new StringHashWithSalt2Builder("12345", "wrong encoding").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92}).isHashValid();
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong source string encoding: wrong encoding");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void isNullStoredSaltHashValidFailTest() {
        try {
            new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(null).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92}).isHashValid();
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Stored salt is null");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void isNullFixedSaltHashValidFailTest() {
        try {
            new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(null).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92}).isHashValid();
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Fixed salt is null");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void isNullAlgorithmHashValidFailTest() {
        try {
            new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(null).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92}).isHashValid();
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Algorithm is null");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void isWrongAlgorithmHashValidFailTest() {
        try {
            new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm("wrong algorithm").setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92}).isHashValid();
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong algorithm name: wrong algorithm");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void isNullSaltOrderHashValidFailTest() {
        try {
            new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(null).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92}).isHashValid();
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt order is null");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void isNullSaltStoreTypeHashValidFailTest() {
        try {
            new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(null).setStoredHash(new byte[]{57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92}).isHashValid();
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt store type is null");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void isNullStoredHashHashValidFailTest() {
        try {
            new StringHashWithSalt2Builder("12345", "UTF-8").setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(null).isHashValid();
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Hash byte array is null");
        }
    }

    /**
     * {@link StringHashWithSalt2Builder} class test.
     */
    @Test
    public void isWrongStoredHashLengthHashValidTest() {
        try {
            new StringHashWithSalt2Builder("12345", "UTF-8").setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{1, 2, 3, 4, 5, 6}).isHashValid();
            Assertions.fail("StringHashWithSalt2Builder test fail");
        } catch (IllegalArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt length is not within [0; 6)");
        }
    }

}
