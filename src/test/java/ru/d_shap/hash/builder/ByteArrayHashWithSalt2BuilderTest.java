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
import ru.d_shap.hash.SaltOrder;
import ru.d_shap.hash.SaltStoreType;
import ru.d_shap.hash.WrongArgumentException;

/**
 * Tests for {@link ByteArrayHashWithSalt2Builder}.
 *
 * @author Dmitry Shapovalov
 */
public final class ByteArrayHashWithSalt2BuilderTest {

    /**
     * Test class constructor.
     */
    public ByteArrayHashWithSalt2BuilderTest() {
        super();
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void getStoredSaltTest() {
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(null).getStoredSalt()).isNull();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(null).setStoredSalt(new byte[]{1, 2, 3}).getStoredSalt()).containsExactlyInOrder(1, 2, 3);
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void setStoredSaltTest() {
        ByteArrayHashWithSalt2Builder builder = new ByteArrayHashWithSalt2Builder(null);
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
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void storedSaltDifferentTest() {
        ByteArrayHashWithSalt2Builder builder = new ByteArrayHashWithSalt2Builder(null);
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
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void getFixedSaltTest() {
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(null).getFixedSalt()).isNull();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(null).setFixedSalt(new byte[]{1, 2, 3}).getFixedSalt()).containsExactlyInOrder(1, 2, 3);
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void setFixedSaltTest() {
        ByteArrayHashWithSalt2Builder builder = new ByteArrayHashWithSalt2Builder(null);
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
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void fixedSaltDifferentTest() {
        ByteArrayHashWithSalt2Builder builder = new ByteArrayHashWithSalt2Builder(null);
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
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void addSaltTest() {
        Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
        Assertions.assertThat(hash.getBytes()).containsExactlyInOrder(82, -119, -33, 115, 125, -11, 115, 38, -4, -35, 34, 89, 122, -5, 31, -84);

        ByteArrayHashWithSalt2Builder builder = new ByteArrayHashWithSalt2Builder(null);
        builder.setStoredSalt(new byte[]{10, 11});
        builder.setFixedSalt(new byte[]{100, 101, 102});
        builder.setSaltOrder(SaltOrder.STORED_SALT_FIRST);
        builder.addSalt(hash);
        Assertions.assertThat(hash.getBytes()).containsExactlyInOrder(95, 120, -45, -63, 46, 32, -100, 115, -48, -65, -69, 71, 12, -90, 79, -31);
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void addNullHashSaltFailTest() {
        try {
            ByteArrayHashWithSalt2Builder builder = new ByteArrayHashWithSalt2Builder(null);
            builder.setStoredSalt(new byte[]{10, 11});
            builder.setFixedSalt(new byte[]{100, 101, 102});
            builder.setSaltOrder(SaltOrder.STORED_SALT_FIRST);
            builder.addSalt(null);
            Assertions.fail("ByteArrayHashWithSalt2Builder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Hash is null");
        }
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void addNullStoredSaltSaltFailTest() {
        try {
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
            ByteArrayHashWithSalt2Builder builder = new ByteArrayHashWithSalt2Builder(null);
            builder.setStoredSalt(null);
            builder.setFixedSalt(new byte[]{100, 101, 102});
            builder.setSaltOrder(SaltOrder.STORED_SALT_FIRST);
            builder.addSalt(hash);
            Assertions.fail("ByteArrayHashWithSalt2Builder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Stored salt is null");
        }
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void addNullFixedSaltSaltFailTest() {
        try {
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
            ByteArrayHashWithSalt2Builder builder = new ByteArrayHashWithSalt2Builder(null);
            builder.setStoredSalt(new byte[]{10, 11});
            builder.setFixedSalt(null);
            builder.setSaltOrder(SaltOrder.STORED_SALT_FIRST);
            builder.addSalt(hash);
            Assertions.fail("ByteArrayHashWithSalt2Builder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Fixed salt is null");
        }
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void addNullSaltOrderSaltFailTest() {
        try {
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
            ByteArrayHashWithSalt2Builder builder = new ByteArrayHashWithSalt2Builder(null);
            builder.setStoredSalt(new byte[]{10, 11});
            builder.setFixedSalt(new byte[]{100, 101, 102});
            builder.setSaltOrder(null);
            builder.addSalt(hash);
            Assertions.fail("ByteArrayHashWithSalt2Builder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt order is null");
        }
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void addStoredSaltTest() {
        Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
        Assertions.assertThat(hash.getBytes()).containsExactlyInOrder(82, -119, -33, 115, 125, -11, 115, 38, -4, -35, 34, 89, 122, -5, 31, -84);

        ByteArrayHashWithSalt2Builder builder = new ByteArrayHashWithSalt2Builder(null);
        builder.setFixedSalt(new byte[]{100, 101, 102});
        builder.setSaltOrder(SaltOrder.STORED_SALT_FIRST);
        builder.addSalt(hash, new byte[]{10, 11});
        Assertions.assertThat(hash.getBytes()).containsExactlyInOrder(95, 120, -45, -63, 46, 32, -100, 115, -48, -65, -69, 71, 12, -90, 79, -31);
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void addNullHashStoredSaltTest() {
        try {
            ByteArrayHashWithSalt2Builder builder = new ByteArrayHashWithSalt2Builder(null);
            builder.setFixedSalt(new byte[]{100, 101, 102});
            builder.setSaltOrder(SaltOrder.STORED_SALT_FIRST);
            builder.addSalt(null, new byte[]{10, 11});
            Assertions.fail("ByteArrayHashWithSalt2Builder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Hash is null");
        }
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void addNullStoredSaltStoredSaltTest() {
        try {
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
            ByteArrayHashWithSalt2Builder builder = new ByteArrayHashWithSalt2Builder(null);
            builder.setFixedSalt(new byte[]{100, 101, 102});
            builder.setSaltOrder(SaltOrder.STORED_SALT_FIRST);
            builder.addSalt(hash, null);
            Assertions.fail("ByteArrayHashWithSalt2Builder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Stored salt is null");
        }
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void addNullFixedSaltStoredSaltTest() {
        try {
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
            ByteArrayHashWithSalt2Builder builder = new ByteArrayHashWithSalt2Builder(null);
            builder.setFixedSalt(null);
            builder.setSaltOrder(SaltOrder.STORED_SALT_FIRST);
            builder.addSalt(hash, new byte[]{10, 11});
            Assertions.fail("ByteArrayHashWithSalt2Builder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Fixed salt is null");
        }
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void addNullSaltOrderStoredSaltTest() {
        try {
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
            ByteArrayHashWithSalt2Builder builder = new ByteArrayHashWithSalt2Builder(null);
            builder.setFixedSalt(new byte[]{100, 101, 102});
            builder.setSaltOrder(null);
            builder.addSalt(hash, new byte[]{10, 11});
            Assertions.fail("ByteArrayHashWithSalt2Builder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt order is null");
        }
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void addSaltBytesTest() {
        Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
        Assertions.assertThat(hash.getBytes()).containsExactlyInOrder(82, -119, -33, 115, 125, -11, 115, 38, -4, -35, 34, 89, 122, -5, 31, -84);

        ByteArrayHashWithSalt2Builder builder = new ByteArrayHashWithSalt2Builder(null);
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
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void addNullHashSaltBytesFailTest() {
        try {
            ByteArrayHashWithSalt2Builder builder = new ByteArrayHashWithSalt2Builder(null);
            builder.setStoredSalt(new byte[]{0, 0, 0});
            builder.setFixedSalt(new byte[]{100, 101, 102});
            builder.setSaltOrder(SaltOrder.STORED_SALT_FIRST);
            builder.setSaltStoreType(SaltStoreType.AT_THE_BEGINNING);
            builder.addSaltBytes(null);
            Assertions.fail("ByteArrayHashWithSalt2Builder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Hash is null");
        }
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void addNullStoredSaltSaltBytesFailTest() {
        try {
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
            ByteArrayHashWithSalt2Builder builder = new ByteArrayHashWithSalt2Builder(null);
            builder.setStoredSalt(null);
            builder.setFixedSalt(new byte[]{100, 101, 102});
            builder.setSaltOrder(SaltOrder.STORED_SALT_FIRST);
            builder.setSaltStoreType(SaltStoreType.AT_THE_BEGINNING);
            builder.addSaltBytes(hash);
            Assertions.fail("ByteArrayHashWithSalt2Builder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt byte array is null");
        }
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void addNullSaltStoreTypeSaltBytesFailTest() {
        try {
            Hash hash = HashHelper.getHash(new byte[]{1, 2, 3}, HashAlgorithms.MD5);
            ByteArrayHashWithSalt2Builder builder = new ByteArrayHashWithSalt2Builder(null);
            builder.setStoredSalt(new byte[]{0, 0, 0});
            builder.setFixedSalt(new byte[]{100, 101, 102});
            builder.setSaltOrder(SaltOrder.STORED_SALT_FIRST);
            builder.setSaltStoreType(null);
            builder.addSaltBytes(hash);
            Assertions.fail("ByteArrayHashWithSalt2Builder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt store type is null");
        }
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void getSaltOrderTest() {
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(null).getSaltOrder()).isEqualTo(SaltOrder.STORED_SALT_FIRST);
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(null).setSaltOrder(SaltOrder.STORED_SALT_FIRST).getSaltOrder()).isEqualTo(SaltOrder.STORED_SALT_FIRST);
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(null).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).getSaltOrder()).isEqualTo(SaltOrder.FIXED_SALT_FIRST);
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void setSaltOrderTest() {
        ByteArrayHashWithSalt2Builder builder = new ByteArrayHashWithSalt2Builder(null);
        Assertions.assertThat(builder.getSaltOrder()).isEqualTo(SaltOrder.STORED_SALT_FIRST);
        builder.setSaltOrder(SaltOrder.FIXED_SALT_FIRST);
        Assertions.assertThat(builder.getSaltOrder()).isEqualTo(SaltOrder.FIXED_SALT_FIRST);
        builder.setSaltOrder(null);
        Assertions.assertThat(builder.getSaltOrder()).isNull();
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void getSaltStoreTypeTest() {
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(null).getSaltStoreType()).isEqualTo(SaltStoreType.DO_NOT_STORE);
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(null).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).getSaltStoreType()).isEqualTo(SaltStoreType.AT_THE_BEGINNING);
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(null).setSaltStoreType(SaltStoreType.AT_THE_END).getSaltStoreType()).isEqualTo(SaltStoreType.AT_THE_END);
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(null).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getSaltStoreType()).isEqualTo(SaltStoreType.DO_NOT_STORE);
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void setSaltStoreTypeTest() {
        ByteArrayHashWithSalt2Builder builder = new ByteArrayHashWithSalt2Builder(null);
        Assertions.assertThat(builder.getSaltStoreType()).isEqualTo(SaltStoreType.DO_NOT_STORE);
        builder.setSaltStoreType(SaltStoreType.AT_THE_BEGINNING);
        Assertions.assertThat(builder.getSaltStoreType()).isEqualTo(SaltStoreType.AT_THE_BEGINNING);
        builder.setSaltStoreType(null);
        Assertions.assertThat(builder.getSaltStoreType()).isNull();
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void getStoredSaltLenghtTest() {
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(null).getStoredSaltLenght()).isEqualTo(0);
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(null).setStoredSaltLenght(16).getStoredSaltLenght()).isEqualTo(16);
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(null).setStoredSaltLenght(32).getStoredSaltLenght()).isEqualTo(32);
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void setStoredSaltLenghtTest() {
        ByteArrayHashWithSalt2Builder builder = new ByteArrayHashWithSalt2Builder(null);
        Assertions.assertThat(builder.getStoredSaltLenght()).isEqualTo(0);
        builder.setStoredSaltLenght(15);
        Assertions.assertThat(builder.getStoredSaltLenght()).isEqualTo(15);
        builder.setStoredSaltLenght(0);
        Assertions.assertThat(builder.getStoredSaltLenght()).isEqualTo(0);
        builder.setStoredSaltLenght(-10);
        Assertions.assertThat(builder.getStoredSaltLenght()).isEqualTo(-10);
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void getHashTest() {
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash()).containsExactlyInOrder(57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92);
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 6, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash()).containsExactlyInOrder(22, 107, -58, 64, -113, -45, 32, -28, -29, 121, -103, -34, 119, -102, 122, -52);
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 12}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash()).containsExactlyInOrder(-119, 109, 15, 79, 104, -82, 82, -6, -128, 108, 81, 16, -16, 55, 37, 60);
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash()).containsExactlyInOrder(115, 46, 121, -41, 123, -8, 72, -111, -109, 74, 20, -83, -97, 55, -82, 56);
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash()).containsExactlyInOrder(-93, -111, 72, -22, 99, 18, -94, 26, -46, 50, 122, -40, 12, -5, -79, 50);

        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).getHash()).containsExactlyInOrder(10, 11, 57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92);
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 6, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).getHash()).containsExactlyInOrder(10, 11, 22, 107, -58, 64, -113, -45, 32, -28, -29, 121, -103, -34, 119, -102, 122, -52);
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 12}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).getHash()).containsExactlyInOrder(10, 12, -119, 109, 15, 79, 104, -82, 82, -6, -128, 108, 81, 16, -16, 55, 37, 60);
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).getHash()).containsExactlyInOrder(10, 11, 115, 46, 121, -41, 123, -8, 72, -111, -109, 74, 20, -83, -97, 55, -82, 56);
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).getHash()).containsExactlyInOrder(10, 11, -93, -111, 72, -22, 99, 18, -94, 26, -46, 50, 122, -40, 12, -5, -79, 50);

        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).getHash()).containsExactlyInOrder(57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92, 10, 11);
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 6, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).getHash()).containsExactlyInOrder(22, 107, -58, 64, -113, -45, 32, -28, -29, 121, -103, -34, 119, -102, 122, -52, 10, 11);
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 12}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).getHash()).containsExactlyInOrder(-119, 109, 15, 79, 104, -82, 82, -6, -128, 108, 81, 16, -16, 55, 37, 60, 10, 12);
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).getHash()).containsExactlyInOrder(115, 46, 121, -41, 123, -8, 72, -111, -109, 74, 20, -83, -97, 55, -82, 56, 10, 11);
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).getHash()).containsExactlyInOrder(-93, -111, 72, -22, 99, 18, -94, 26, -46, 50, 122, -40, 12, -5, -79, 50, 10, 11);
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void getNullHashFailTest() {
        try {
            new ByteArrayHashWithSalt2Builder(null).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash();
            Assertions.fail("ByteArrayHashWithSalt2Builder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Source byte array is null");
        }
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void getNullStoredSaltHashFailTest() {
        try {
            new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(null).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash();
            Assertions.fail("ByteArrayHashWithSalt2Builder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Stored salt is null");
        }
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void getNullFixedSaltHashFailTest() {
        try {
            new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(null).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash();
            Assertions.fail("ByteArrayHashWithSalt2Builder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Fixed salt is null");
        }
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void getNullAlgorithmHashFailTest() {
        try {
            new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(null).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash();
            Assertions.fail("ByteArrayHashWithSalt2Builder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Algorithm is null");
        }
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void getWrongAlgorithmHashFailTest() {
        try {
            new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm("wrong algorithm").setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash();
            Assertions.fail("ByteArrayHashWithSalt2Builder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong algorithm name: wrong algorithm");
        }
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void getNullSaltOrderHashFailTest() {
        try {
            new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(null).setSaltStoreType(SaltStoreType.DO_NOT_STORE).getHash();
            Assertions.fail("ByteArrayHashWithSalt2Builder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt order is null");
        }
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void getNullSaltStoreTypeHashFailTest() {
        try {
            new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(null).getHash();
            Assertions.fail("ByteArrayHashWithSalt2Builder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt store type is null");
        }
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void isHashValidTest() {
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92}).isHashValid()).isTrue();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{9, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{9, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{109, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92}).isHashValid()).isFalse();

        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 6, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{22, 107, -58, 64, -113, -45, 32, -28, -29, 121, -103, -34, 119, -102, 122, -52}).isHashValid()).isTrue();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{9, 2, 3, 6, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{22, 107, -58, 64, -113, -45, 32, -28, -29, 121, -103, -34, 119, -102, 122, -52}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 6, 5}).setStoredSalt(new byte[]{9, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{22, 107, -58, 64, -113, -45, 32, -28, -29, 121, -103, -34, 119, -102, 122, -52}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 6, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{109, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{22, 107, -58, 64, -113, -45, 32, -28, -29, 121, -103, -34, 119, -102, 122, -52}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 6, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{22, 107, -58, 64, -113, -45, 32, -28, -29, 121, -103, -34, 119, -102, 122, -52}).isHashValid()).isFalse();

        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 12}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-119, 109, 15, 79, 104, -82, 82, -6, -128, 108, 81, 16, -16, 55, 37, 60}).isHashValid()).isTrue();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{9, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 12}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-119, 109, 15, 79, 104, -82, 82, -6, -128, 108, 81, 16, -16, 55, 37, 60}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{9, 12}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-119, 109, 15, 79, 104, -82, 82, -6, -128, 108, 81, 16, -16, 55, 37, 60}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 12}).setFixedSalt(new byte[]{109, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-119, 109, 15, 79, 104, -82, 82, -6, -128, 108, 81, 16, -16, 55, 37, 60}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 12}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-119, 109, 15, 79, 104, -82, 82, -6, -128, 108, 81, 16, -16, 55, 37, 60}).isHashValid()).isFalse();

        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{115, 46, 121, -41, 123, -8, 72, -111, -109, 74, 20, -83, -97, 55, -82, 56}).isHashValid()).isTrue();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{9, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{115, 46, 121, -41, 123, -8, 72, -111, -109, 74, 20, -83, -97, 55, -82, 56}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{9, 11}).setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{115, 46, 121, -41, 123, -8, 72, -111, -109, 74, 20, -83, -97, 55, -82, 56}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{109, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{115, 46, 121, -41, 123, -8, 72, -111, -109, 74, 20, -83, -97, 55, -82, 56}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{115, 46, 121, -41, 123, -8, 72, -111, -109, 74, 20, -83, -97, 55, -82, 56}).isHashValid()).isFalse();

        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-93, -111, 72, -22, 99, 18, -94, 26, -46, 50, 122, -40, 12, -5, -79, 50}).isHashValid()).isTrue();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{9, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-93, -111, 72, -22, 99, 18, -94, 26, -46, 50, 122, -40, 12, -5, -79, 50}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{9, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-93, -111, 72, -22, 99, 18, -94, 26, -46, 50, 122, -40, 12, -5, -79, 50}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{109, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-93, -111, 72, -22, 99, 18, -94, 26, -46, 50, 122, -40, 12, -5, -79, 50}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{-93, -111, 72, -22, 99, 18, -94, 26, -46, 50, 122, -40, 12, -5, -79, 50}).isHashValid()).isFalse();

        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, 57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92}).isHashValid()).isTrue();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{9, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, 57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{9, 11, 57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{109, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, 57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, 57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92}).isHashValid()).isFalse();

        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 6, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, 22, 107, -58, 64, -113, -45, 32, -28, -29, 121, -103, -34, 119, -102, 122, -52}).isHashValid()).isTrue();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{9, 2, 3, 6, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, 22, 107, -58, 64, -113, -45, 32, -28, -29, 121, -103, -34, 119, -102, 122, -52}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 6, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{9, 11, 22, 107, -58, 64, -113, -45, 32, -28, -29, 121, -103, -34, 119, -102, 122, -52}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 6, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{109, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, 22, 107, -58, 64, -113, -45, 32, -28, -29, 121, -103, -34, 119, -102, 122, -52}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 6, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, 22, 107, -58, 64, -113, -45, 32, -28, -29, 121, -103, -34, 119, -102, 122, -52}).isHashValid()).isFalse();

        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 12, -119, 109, 15, 79, 104, -82, 82, -6, -128, 108, 81, 16, -16, 55, 37, 60}).isHashValid()).isTrue();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{9, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 12, -119, 109, 15, 79, 104, -82, 82, -6, -128, 108, 81, 16, -16, 55, 37, 60}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{9, 12, -119, 109, 15, 79, 104, -82, 82, -6, -128, 108, 81, 16, -16, 55, 37, 60}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{109, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 12, -119, 109, 15, 79, 104, -82, 82, -6, -128, 108, 81, 16, -16, 55, 37, 60}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 12, -119, 109, 15, 79, 104, -82, 82, -6, -128, 108, 81, 16, -16, 55, 37, 60}).isHashValid()).isFalse();

        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, 115, 46, 121, -41, 123, -8, 72, -111, -109, 74, 20, -83, -97, 55, -82, 56}).isHashValid()).isTrue();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{9, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, 115, 46, 121, -41, 123, -8, 72, -111, -109, 74, 20, -83, -97, 55, -82, 56}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{9, 11, 115, 46, 121, -41, 123, -8, 72, -111, -109, 74, 20, -83, -97, 55, -82, 56}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{109, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, 115, 46, 121, -41, 123, -8, 72, -111, -109, 74, 20, -83, -97, 55, -82, 56}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, 115, 46, 121, -41, 123, -8, 72, -111, -109, 74, 20, -83, -97, 55, -82, 56}).isHashValid()).isFalse();

        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, -93, -111, 72, -22, 99, 18, -94, 26, -46, 50, 122, -40, 12, -5, -79, 50}).isHashValid()).isTrue();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{9, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, -93, -111, 72, -22, 99, 18, -94, 26, -46, 50, 122, -40, 12, -5, -79, 50}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{9, 11, -93, -111, 72, -22, 99, 18, -94, 26, -46, 50, 122, -40, 12, -5, -79, 50}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{109, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, -93, -111, 72, -22, 99, 18, -94, 26, -46, 50, 122, -40, 12, -5, -79, 50}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, -93, -111, 72, -22, 99, 18, -94, 26, -46, 50, 122, -40, 12, -5, -79, 50}).isHashValid()).isFalse();

        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92, 10, 11}).isHashValid()).isTrue();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{9, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92, 10, 11}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92, 9, 11}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{109, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92, 10, 11}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92, 10, 11}).isHashValid()).isFalse();

        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 6, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{22, 107, -58, 64, -113, -45, 32, -28, -29, 121, -103, -34, 119, -102, 122, -52, 10, 11}).isHashValid()).isTrue();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{9, 2, 3, 6, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{22, 107, -58, 64, -113, -45, 32, -28, -29, 121, -103, -34, 119, -102, 122, -52, 10, 11}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 6, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{22, 107, -58, 64, -113, -45, 32, -28, -29, 121, -103, -34, 119, -102, 122, -52, 9, 11}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 6, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{109, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{22, 107, -58, 64, -113, -45, 32, -28, -29, 121, -103, -34, 119, -102, 122, -52, 10, 11}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 6, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{22, 107, -58, 64, -113, -45, 32, -28, -29, 121, -103, -34, 119, -102, 122, -52, 10, 11}).isHashValid()).isFalse();

        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-119, 109, 15, 79, 104, -82, 82, -6, -128, 108, 81, 16, -16, 55, 37, 60, 10, 12}).isHashValid()).isTrue();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{9, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-119, 109, 15, 79, 104, -82, 82, -6, -128, 108, 81, 16, -16, 55, 37, 60, 10, 12}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-119, 109, 15, 79, 104, -82, 82, -6, -128, 108, 81, 16, -16, 55, 37, 60, 9, 12}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{109, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-119, 109, 15, 79, 104, -82, 82, -6, -128, 108, 81, 16, -16, 55, 37, 60, 10, 12}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-119, 109, 15, 79, 104, -82, 82, -6, -128, 108, 81, 16, -16, 55, 37, 60, 10, 12}).isHashValid()).isFalse();

        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{115, 46, 121, -41, 123, -8, 72, -111, -109, 74, 20, -83, -97, 55, -82, 56, 10, 11}).isHashValid()).isTrue();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{9, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{115, 46, 121, -41, 123, -8, 72, -111, -109, 74, 20, -83, -97, 55, -82, 56, 10, 11}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{115, 46, 121, -41, 123, -8, 72, -111, -109, 74, 20, -83, -97, 55, -82, 56, 9, 11}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{109, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{115, 46, 121, -41, 123, -8, 72, -111, -109, 74, 20, -83, -97, 55, -82, 56, 10, 11}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 100, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{115, 46, 121, -41, 123, -8, 72, -111, -109, 74, 20, -83, -97, 55, -82, 56, 10, 11}).isHashValid()).isFalse();

        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-93, -111, 72, -22, 99, 18, -94, 26, -46, 50, 122, -40, 12, -5, -79, 50, 10, 11}).isHashValid()).isTrue();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{9, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-93, -111, 72, -22, 99, 18, -94, 26, -46, 50, 122, -40, 12, -5, -79, 50, 10, 11}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-93, -111, 72, -22, 99, 18, -94, 26, -46, 50, 122, -40, 12, -5, -79, 50, 9, 11}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{109, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.FIXED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-93, -111, 72, -22, 99, 18, -94, 26, -46, 50, 122, -40, 12, -5, -79, 50, 10, 11}).isHashValid()).isFalse();
        Assertions.assertThat(new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSaltLenght(2).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.AT_THE_END).setStoredHash(new byte[]{-93, -111, 72, -22, 99, 18, -94, 26, -46, 50, 122, -40, 12, -5, -79, 50, 10, 11}).isHashValid()).isFalse();
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void isNullHashValidFailTest() {
        try {
            new ByteArrayHashWithSalt2Builder(null).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92}).isHashValid();
            Assertions.fail("ByteArrayHashWithSalt2Builder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Source byte array is null");
        }
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void isNullStoredSaltHashValidFailTest() {
        try {
            new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(null).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92}).isHashValid();
            Assertions.fail("ByteArrayHashWithSalt2Builder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Stored salt is null");
        }
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void isNullFixedSaltHashValidFailTest() {
        try {
            new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(null).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92}).isHashValid();
            Assertions.fail("ByteArrayHashWithSalt2Builder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Fixed salt is null");
        }
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void isNullAlgorithmHashValidFailTest() {
        try {
            new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(null).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92}).isHashValid();
            Assertions.fail("ByteArrayHashWithSalt2Builder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Algorithm is null");
        }
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void isWrongAlgorithmHashValidFailTest() {
        try {
            new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm("wrong algorithm").setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92}).isHashValid();
            Assertions.fail("ByteArrayHashWithSalt2Builder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong algorithm name: wrong algorithm");
        }
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void isNullSaltOrderHashValidFailTest() {
        try {
            new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(null).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(new byte[]{57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92}).isHashValid();
            Assertions.fail("ByteArrayHashWithSalt2Builder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt order is null");
        }
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void isNullSaltStoreTypeHashValidFailTest() {
        try {
            new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(null).setStoredHash(new byte[]{57, -18, 39, 38, 65, -20, 93, -36, -128, 110, 117, -30, 53, -12, -126, -92}).isHashValid();
            Assertions.fail("ByteArrayHashWithSalt2Builder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Salt store type is null");
        }
    }

    /**
     * {@link ByteArrayHashWithSalt2Builder} class test.
     */
    @Test
    public void isNullStoredHashHashValidFailTest() {
        try {
            new ByteArrayHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{100, 101, 102}).setAlgorithm(HashAlgorithms.MD5).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setSaltStoreType(SaltStoreType.DO_NOT_STORE).setStoredHash(null).isHashValid();
            Assertions.fail("ByteArrayHashWithSalt2Builder test fail");
        } catch (WrongArgumentException ex) {
            Assertions.assertThat(ex).hasMessage("Hash byte array is null");
        }
    }

}
