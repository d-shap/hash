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

import org.junit.Test;

import ru.d_shap.assertions.Assertions;

/**
 * Tests for {@link SaltOrder}.
 *
 * @author Dmitry Shapovalov
 */
public final class SaltOrderTest {

    /**
     * Test class constructor.
     */
    public SaltOrderTest() {
        super();
    }

    /**
     * {@link SaltOrder} class test.
     */
    @Test
    public void valueCountTest() {
        Assertions.assertThat(SaltOrder.class).asEnum().hasValueCount(2);
    }

    /**
     * {@link SaltOrder} class test.
     */
    @Test
    public void storedSaltFirstTest() {
        Hash hash = HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5);
        byte[] storedSalt = new byte[]{10, 11};
        byte[] fixedSalt = new byte[]{21, 22};
        SaltOrder.STORED_SALT_FIRST.addSalt(hash, storedSalt, fixedSalt);
        Assertions.assertThat(hash.getBytes()).containsExactlyInOrder(126, 35, 24, 110, 18, -4, 100, 98, 114, -28, -85, 35, 91, -7, -17, 73);
    }

    /**
     * {@link SaltOrder} class test.
     */
    @Test(expected = NullPointerException.class)
    public void storedSaltFirstNullHashFailTest() {
        byte[] storedSalt = new byte[]{10, 11};
        byte[] fixedSalt = new byte[]{21, 22};
        SaltOrder.STORED_SALT_FIRST.addSalt(null, storedSalt, fixedSalt);
    }

    /**
     * {@link SaltOrder} class test.
     */
    @Test(expected = IllegalArgumentException.class)
    public void storedSaltFirstNullStoredSaltFailTest() {
        Hash hash = HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5);
        byte[] fixedSalt = new byte[]{21, 22};
        SaltOrder.STORED_SALT_FIRST.addSalt(hash, null, fixedSalt);
    }

    /**
     * {@link SaltOrder} class test.
     */
    @Test(expected = IllegalArgumentException.class)
    public void storedSaltFirstNullFixedSaltFailTest() {
        Hash hash = HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5);
        byte[] storedSalt = new byte[]{10, 11};
        SaltOrder.STORED_SALT_FIRST.addSalt(hash, storedSalt, null);
    }

    /**
     * {@link SaltOrder} class test.
     */
    @Test
    public void fixedSaltFirstTest() {
        Hash hash = HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5);
        byte[] storedSalt = new byte[]{10, 11};
        byte[] fixedSalt = new byte[]{21, 22};
        SaltOrder.FIXED_SALT_FIRST.addSalt(hash, storedSalt, fixedSalt);
        Assertions.assertThat(hash.getBytes()).containsExactlyInOrder(-69, -67, -64, 87, -124, -113, -70, 9, 28, -65, 100, 55, -42, -46, 77, 99);
    }

    /**
     * {@link SaltOrder} class test.
     */
    @Test(expected = NullPointerException.class)
    public void fixedSaltFirstNullHashFailTest() {
        byte[] storedSalt = new byte[]{10, 11};
        byte[] fixedSalt = new byte[]{21, 22};
        SaltOrder.FIXED_SALT_FIRST.addSalt(null, storedSalt, fixedSalt);
    }

    /**
     * {@link SaltOrder} class test.
     */
    @Test(expected = IllegalArgumentException.class)
    public void fixedSaltFirstNullStoredSaltFailTest() {
        Hash hash = HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5);
        byte[] fixedSalt = new byte[]{21, 22};
        SaltOrder.FIXED_SALT_FIRST.addSalt(hash, null, fixedSalt);
    }

    /**
     * {@link SaltOrder} class test.
     */
    @Test(expected = IllegalArgumentException.class)
    public void fixedSaltFirstNullFixedSaltFailTest() {
        Hash hash = HashHelper.getHash(new byte[]{1, 2, 3, 4, 5}, HashAlgorithms.MD5);
        byte[] storedSalt = new byte[]{10, 11};
        SaltOrder.FIXED_SALT_FIRST.addSalt(hash, storedSalt, null);
    }

}
