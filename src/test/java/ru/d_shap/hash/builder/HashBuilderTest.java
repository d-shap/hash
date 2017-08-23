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
import ru.d_shap.hash.HashAlgorithms;
import ru.d_shap.hash.SaltOrder;
import ru.d_shap.hash.SaltStoreType;

/**
 * Tests for {@link HashBuilder}.
 *
 * @author Dmitry Shapovalov
 */
public final class HashBuilderTest {

    /**
     * Test class constructor.
     */
    public HashBuilderTest() {
        super();
    }

    /**
     * {@link HashBuilder} class test.
     */
    @Test
    public void constructorTest() {
        Assertions.assertThat(HashBuilder.class).hasOnePrivateConstructor();
    }

    /**
     * {@link HashBuilder} class test.
     */
    @Test
    public void newByteArraySimpleHashBuilderTest() {
        Assertions.assertThat(HashBuilder.newSimpleHashBuilder(new byte[]{1, 2, 3, 4, 5}).setAlgorithm(HashAlgorithms.MD5).getHash()).containsExactlyInOrder(124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104);
        Assertions.assertThat(HashBuilder.newSimpleHashBuilder(new byte[]{1, 2, 3, 4, 5}).setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104}).isHashValid()).isTrue();
    }

    /**
     * {@link HashBuilder} class test.
     */
    @Test
    public void newCharSequenceSimpleHashBuilderTest() {
        Assertions.assertThat(HashBuilder.newSimpleHashBuilder("12345").setAlgorithm(HashAlgorithms.MD5).getHash()).containsExactlyInOrder(-126, 124, -53, 14, -22, -118, 112, 108, 76, 52, -95, 104, -111, -8, 78, 123);
        Assertions.assertThat(HashBuilder.newSimpleHashBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).getHash()).containsExactlyInOrder(-126, 124, -53, 14, -22, -118, 112, 108, 76, 52, -95, 104, -111, -8, 78, 123);
        Assertions.assertThat(HashBuilder.newSimpleHashBuilder("12345").setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{-126, 124, -53, 14, -22, -118, 112, 108, 76, 52, -95, 104, -111, -8, 78, 123}).isHashValid()).isTrue();
        Assertions.assertThat(HashBuilder.newSimpleHashBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{-126, 124, -53, 14, -22, -118, 112, 108, 76, 52, -95, 104, -111, -8, 78, 123}).isHashValid()).isTrue();
    }

    /**
     * {@link HashBuilder} class test.
     */
    @Test
    public void newInputStreamSimpleHashBuilderTest() {
        Assertions.assertThat(HashBuilder.newSimpleHashBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setAlgorithm(HashAlgorithms.MD5).getHash()).containsExactlyInOrder(124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104);
        Assertions.assertThat(HashBuilder.newSimpleHashBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setAlgorithm(HashAlgorithms.MD5).setStoredHash(new byte[]{124, -3, -48, 120, -119, -77, 41, 93, 106, 85, 9, 20, -85, 53, -32, 104}).isHashValid()).isTrue();
    }

    /**
     * {@link HashBuilder} class test.
     */
    @Test
    public void newByteArrayHashWithSaltBuilderTest() {
        Assertions.assertThat(HashBuilder.newHashWithSaltBuilder(new byte[]{1, 2, 3, 4, 5}).setAlgorithm(HashAlgorithms.MD5).setSalt(new byte[]{10, 11}).getHash()).containsExactlyInOrder(-125, -55, 98, 99, -96, 6, -52, 4, 25, 56, -88, -22, 69, -121, -101, -23);
        Assertions.assertThat(HashBuilder.newHashWithSaltBuilder(new byte[]{1, 2, 3, 4, 5}).setAlgorithm(HashAlgorithms.MD5).setSalt(new byte[]{10, 11}).setStoredHash(new byte[]{-125, -55, 98, 99, -96, 6, -52, 4, 25, 56, -88, -22, 69, -121, -101, -23}).isHashValid()).isTrue();

        Assertions.assertThat(HashBuilder.newHashWithSaltBuilder(new byte[]{1, 2, 3, 4, 5}).setAlgorithm(HashAlgorithms.MD5).setSalt(new byte[]{10, 11}).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).getHash()).containsExactlyInOrder(10, 11, -125, -55, 98, 99, -96, 6, -52, 4, 25, 56, -88, -22, 69, -121, -101, -23);
        Assertions.assertThat(HashBuilder.newHashWithSaltBuilder(new byte[]{1, 2, 3, 4, 5}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, -125, -55, 98, 99, -96, 6, -52, 4, 25, 56, -88, -22, 69, -121, -101, -23}).isHashValid()).isTrue();
    }

    /**
     * {@link HashBuilder} class test.
     */
    @Test
    public void newCharSequenceHashWithSaltBuilderTest() {
        Assertions.assertThat(HashBuilder.newHashWithSaltBuilder("12345").setAlgorithm(HashAlgorithms.MD5).setSalt(new byte[]{10, 11}).getHash()).containsExactlyInOrder(-92, -92, -91, -78, -54, 57, 120, -113, -55, -39, 59, -51, 93, 51, -127, -53);
        Assertions.assertThat(HashBuilder.newHashWithSaltBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSalt(new byte[]{10, 11}).getHash()).containsExactlyInOrder(-92, -92, -91, -78, -54, 57, 120, -113, -55, -39, 59, -51, 93, 51, -127, -53);
        Assertions.assertThat(HashBuilder.newHashWithSaltBuilder("12345").setAlgorithm(HashAlgorithms.MD5).setSalt(new byte[]{10, 11}).setStoredHash(new byte[]{-92, -92, -91, -78, -54, 57, 120, -113, -55, -39, 59, -51, 93, 51, -127, -53}).isHashValid()).isTrue();
        Assertions.assertThat(HashBuilder.newHashWithSaltBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSalt(new byte[]{10, 11}).setStoredHash(new byte[]{-92, -92, -91, -78, -54, 57, 120, -113, -55, -39, 59, -51, 93, 51, -127, -53}).isHashValid()).isTrue();

        Assertions.assertThat(HashBuilder.newHashWithSaltBuilder("12345").setAlgorithm(HashAlgorithms.MD5).setSalt(new byte[]{10, 11}).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).getHash()).containsExactlyInOrder(10, 11, -92, -92, -91, -78, -54, 57, 120, -113, -55, -39, 59, -51, 93, 51, -127, -53);
        Assertions.assertThat(HashBuilder.newHashWithSaltBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSalt(new byte[]{10, 11}).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).getHash()).containsExactlyInOrder(10, 11, -92, -92, -91, -78, -54, 57, 120, -113, -55, -39, 59, -51, 93, 51, -127, -53);
        Assertions.assertThat(HashBuilder.newHashWithSaltBuilder("12345").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, -92, -92, -91, -78, -54, 57, 120, -113, -55, -39, 59, -51, 93, 51, -127, -53}).isHashValid()).isTrue();
        Assertions.assertThat(HashBuilder.newHashWithSaltBuilder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, -92, -92, -91, -78, -54, 57, 120, -113, -55, -39, 59, -51, 93, 51, -127, -53}).isHashValid()).isTrue();
    }

    /**
     * {@link HashBuilder} class test.
     */
    @Test
    public void newInputStreamHashWithSaltBuilderTest() {
        Assertions.assertThat(HashBuilder.newHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setAlgorithm(HashAlgorithms.MD5).setSalt(new byte[]{10, 11}).getHash()).containsExactlyInOrder(-125, -55, 98, 99, -96, 6, -52, 4, 25, 56, -88, -22, 69, -121, -101, -23);
        Assertions.assertThat(HashBuilder.newHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setAlgorithm(HashAlgorithms.MD5).setSalt(new byte[]{10, 11}).setStoredHash(new byte[]{-125, -55, 98, 99, -96, 6, -52, 4, 25, 56, -88, -22, 69, -121, -101, -23}).isHashValid()).isTrue();

        Assertions.assertThat(HashBuilder.newHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setAlgorithm(HashAlgorithms.MD5).setSalt(new byte[]{10, 11}).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).getHash()).containsExactlyInOrder(10, 11, -125, -55, 98, 99, -96, 6, -52, 4, 25, 56, -88, -22, 69, -121, -101, -23);
        Assertions.assertThat(HashBuilder.newHashWithSaltBuilder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setStoredHash(new byte[]{10, 11, -125, -55, 98, 99, -96, 6, -52, 4, 25, 56, -88, -22, 69, -121, -101, -23}).isHashValid()).isTrue();
    }

    /**
     * {@link HashBuilder} class test.
     */
    @Test
    public void newByteArrayHashWithSalt2BuilderTest() {
        Assertions.assertThat(HashBuilder.newHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setAlgorithm(HashAlgorithms.MD5).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{20, 30, 40}).getHash()).containsExactlyInOrder(90, 49, 37, 101, -5, 53, 107, 124, -39, 10, -6, -123, 67, -119, 18, -71);
        Assertions.assertThat(HashBuilder.newHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setAlgorithm(HashAlgorithms.MD5).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{20, 30, 40}).setStoredHash(new byte[]{90, 49, 37, 101, -5, 53, 107, 124, -39, 10, -6, -123, 67, -119, 18, -71}).isHashValid()).isTrue();

        Assertions.assertThat(HashBuilder.newHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setAlgorithm(HashAlgorithms.MD5).setStoredSalt(new byte[]{10, 11}).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setFixedSalt(new byte[]{20, 30, 40}).setSaltOrder(SaltOrder.STORED_SALT_FIRST).getHash()).containsExactlyInOrder(10, 11, 90, 49, 37, 101, -5, 53, 107, 124, -39, 10, -6, -123, 67, -119, 18, -71);
        Assertions.assertThat(HashBuilder.newHashWithSalt2Builder(new byte[]{1, 2, 3, 4, 5}).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setFixedSalt(new byte[]{20, 30, 40}).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setStoredHash(new byte[]{10, 11, 90, 49, 37, 101, -5, 53, 107, 124, -39, 10, -6, -123, 67, -119, 18, -71}).isHashValid()).isTrue();
    }

    /**
     * {@link HashBuilder} class test.
     */
    @Test
    public void newCharSequenceHashWithSalt2BuilderTest() {
        Assertions.assertThat(HashBuilder.newHashWithSalt2Builder("12345").setAlgorithm(HashAlgorithms.MD5).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{20, 30, 40}).getHash()).containsExactlyInOrder(70, 73, 73, 69, 61, 71, 124, -14, -11, -84, -69, -7, -90, 59, 23, -86);
        Assertions.assertThat(HashBuilder.newHashWithSalt2Builder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{20, 30, 40}).getHash()).containsExactlyInOrder(70, 73, 73, 69, 61, 71, 124, -14, -11, -84, -69, -7, -90, 59, 23, -86);
        Assertions.assertThat(HashBuilder.newHashWithSalt2Builder("12345").setAlgorithm(HashAlgorithms.MD5).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{20, 30, 40}).setStoredHash(new byte[]{70, 73, 73, 69, 61, 71, 124, -14, -11, -84, -69, -7, -90, 59, 23, -86}).isHashValid()).isTrue();
        Assertions.assertThat(HashBuilder.newHashWithSalt2Builder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{20, 30, 40}).setStoredHash(new byte[]{70, 73, 73, 69, 61, 71, 124, -14, -11, -84, -69, -7, -90, 59, 23, -86}).isHashValid()).isTrue();

        Assertions.assertThat(HashBuilder.newHashWithSalt2Builder("12345").setAlgorithm(HashAlgorithms.MD5).setStoredSalt(new byte[]{10, 11}).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setFixedSalt(new byte[]{20, 30, 40}).setSaltOrder(SaltOrder.STORED_SALT_FIRST).getHash()).containsExactlyInOrder(10, 11, 70, 73, 73, 69, 61, 71, 124, -14, -11, -84, -69, -7, -90, 59, 23, -86);
        Assertions.assertThat(HashBuilder.newHashWithSalt2Builder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setStoredSalt(new byte[]{10, 11}).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setFixedSalt(new byte[]{20, 30, 40}).setSaltOrder(SaltOrder.STORED_SALT_FIRST).getHash()).containsExactlyInOrder(10, 11, 70, 73, 73, 69, 61, 71, 124, -14, -11, -84, -69, -7, -90, 59, 23, -86);
        Assertions.assertThat(HashBuilder.newHashWithSalt2Builder("12345").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setFixedSalt(new byte[]{20, 30, 40}).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setStoredHash(new byte[]{10, 11, 70, 73, 73, 69, 61, 71, 124, -14, -11, -84, -69, -7, -90, 59, 23, -86}).isHashValid()).isTrue();
        Assertions.assertThat(HashBuilder.newHashWithSalt2Builder("12345", "UTF-8").setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setFixedSalt(new byte[]{20, 30, 40}).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setStoredHash(new byte[]{10, 11, 70, 73, 73, 69, 61, 71, 124, -14, -11, -84, -69, -7, -90, 59, 23, -86}).isHashValid()).isTrue();
    }

    /**
     * {@link HashBuilder} class test.
     */
    @Test
    public void newInputStreamHashWithSalt2BuilderTest() {
        Assertions.assertThat(HashBuilder.newHashWithSalt2Builder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setAlgorithm(HashAlgorithms.MD5).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{20, 30, 40}).getHash()).containsExactlyInOrder(90, 49, 37, 101, -5, 53, 107, 124, -39, 10, -6, -123, 67, -119, 18, -71);
        Assertions.assertThat(HashBuilder.newHashWithSalt2Builder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setAlgorithm(HashAlgorithms.MD5).setStoredSalt(new byte[]{10, 11}).setFixedSalt(new byte[]{20, 30, 40}).setStoredHash(new byte[]{90, 49, 37, 101, -5, 53, 107, 124, -39, 10, -6, -123, 67, -119, 18, -71}).isHashValid()).isTrue();

        Assertions.assertThat(HashBuilder.newHashWithSalt2Builder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setAlgorithm(HashAlgorithms.MD5).setStoredSalt(new byte[]{10, 11}).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setFixedSalt(new byte[]{20, 30, 40}).setSaltOrder(SaltOrder.STORED_SALT_FIRST).getHash()).containsExactlyInOrder(10, 11, 90, 49, 37, 101, -5, 53, 107, 124, -39, 10, -6, -123, 67, -119, 18, -71);
        Assertions.assertThat(HashBuilder.newHashWithSalt2Builder(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})).setAlgorithm(HashAlgorithms.MD5).setSaltStoreType(SaltStoreType.AT_THE_BEGINNING).setFixedSalt(new byte[]{20, 30, 40}).setSaltOrder(SaltOrder.STORED_SALT_FIRST).setStoredHash(new byte[]{10, 11, 90, 49, 37, 101, -5, 53, 107, 124, -39, 10, -6, -123, 67, -119, 18, -71}).isHashValid()).isTrue();
    }

}
