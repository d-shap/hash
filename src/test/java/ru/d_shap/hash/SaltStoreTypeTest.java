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
 * Tests for {@link SaltStoreType}.
 *
 * @author Dmitry Shapovalov
 */
public final class SaltStoreTypeTest {

    /**
     * Test class constructor.
     */
    public SaltStoreTypeTest() {
        super();
    }

    /**
     * {@link SaltStoreType} class test.
     */
    @Test
    public void valueCountTest() {
        Assertions.assertThat(SaltStoreType.class).asEnum().hasValueCount(3);
    }

    /**
     * {@link SaltStoreType} class test.
     */
    @Test
    public void addSaltBytesDoNotStoreTest() {
        Assertions.assertThat(SaltStoreType.DO_NOT_STORE.addSaltBytes(new byte[]{1, 2, 3}, null)).containsExactlyInOrder(1, 2, 3);
        Assertions.assertThat(SaltStoreType.DO_NOT_STORE.addSaltBytes(new byte[]{1, 2, 3}, new byte[]{4, 5, 6})).containsExactlyInOrder(1, 2, 3);
    }

    /**
     * {@link SaltStoreType} class test.
     */
    @Test(expected = NullPointerException.class)
    public void addNullHashSaltBytesDoNotStoreFailTest() {
        SaltStoreType.DO_NOT_STORE.addSaltBytes(null, new byte[]{4, 5, 6});
    }

    /**
     * {@link SaltStoreType} class test.
     */
    @Test
    public void getSaltBytesDoNotStoreTest() {
        Assertions.assertThat(SaltStoreType.DO_NOT_STORE.getSaltBytes(new byte[]{1, 2, 3}, 3)).containsExactlyInOrder();
        Assertions.assertThat(SaltStoreType.DO_NOT_STORE.getSaltBytes(new byte[]{1, 2, 3}, 0)).containsExactlyInOrder();
        Assertions.assertThat(SaltStoreType.DO_NOT_STORE.getSaltBytes(new byte[]{1, 2, 3}, -1)).containsExactlyInOrder();
    }

    /**
     * {@link SaltStoreType} class test.
     */
    @Test
    public void getNullHashSaltBytesDoNotStoreTest() {
        Assertions.assertThat(SaltStoreType.DO_NOT_STORE.getSaltBytes(null, 3)).containsExactlyInOrder();
    }

    /**
     * {@link SaltStoreType} class test.
     */
    @Test
    public void getHashBytesDoNotStoreTest() {
        Assertions.assertThat(SaltStoreType.DO_NOT_STORE.getHashBytes(new byte[]{1, 2, 3}, 3)).containsExactlyInOrder(1, 2, 3);
        Assertions.assertThat(SaltStoreType.DO_NOT_STORE.getHashBytes(new byte[]{1, 2, 3}, 0)).containsExactlyInOrder(1, 2, 3);
        Assertions.assertThat(SaltStoreType.DO_NOT_STORE.getHashBytes(new byte[]{1, 2, 3}, -1)).containsExactlyInOrder(1, 2, 3);
    }

    /**
     * {@link SaltStoreType} class test.
     */
    @Test(expected = NullPointerException.class)
    public void getNullHashBytesDoNotStoreFailTest() {
        SaltStoreType.DO_NOT_STORE.getHashBytes(null, 3);
    }

    /**
     * {@link SaltStoreType} class test.
     */
    @Test
    public void addSaltBytesAtTheBeginningTest() {
        Assertions.assertThat(SaltStoreType.AT_THE_BEGINNING.addSaltBytes(new byte[]{1, 2, 3}, new byte[]{4, 5, 6})).containsExactlyInOrder(4, 5, 6, 1, 2, 3);
    }

    /**
     * {@link SaltStoreType} class test.
     */
    @Test(expected = NullPointerException.class)
    public void addNullHashSaltBytesAtTheBeginningFailTest() {
        SaltStoreType.AT_THE_BEGINNING.addSaltBytes(null, new byte[]{4, 5, 6});
    }

    /**
     * {@link SaltStoreType} class test.
     */
    @Test(expected = NullPointerException.class)
    public void addNullSaltBytesAtTheBeginningFailTest() {
        SaltStoreType.AT_THE_BEGINNING.addSaltBytes(new byte[]{1, 2, 3}, null);
    }

    /**
     * {@link SaltStoreType} class test.
     */
    @Test
    public void getSaltBytesAtTheBeginningTest() {
        Assertions.assertThat(SaltStoreType.AT_THE_BEGINNING.getSaltBytes(new byte[]{4, 5, 6, 1, 2, 3}, 3)).containsExactlyInOrder(4, 5, 6);
        Assertions.assertThat(SaltStoreType.AT_THE_BEGINNING.getSaltBytes(new byte[]{4, 5, 6, 1, 2, 3}, 0)).containsExactlyInOrder();
    }

    /**
     * {@link SaltStoreType} class test.
     */
    @Test(expected = NullPointerException.class)
    public void getNullSaltBytesAtTheBeginningFailTest() {
        SaltStoreType.AT_THE_BEGINNING.getSaltBytes(null, 3);
    }

    /**
     * {@link SaltStoreType} class test.
     */
    @Test(expected = NegativeArraySizeException.class)
    public void getWrongLengthSaltBytesAtTheBeginningFailTest() {
        SaltStoreType.AT_THE_BEGINNING.getSaltBytes(new byte[]{4, 5, 6, 1, 2, 3}, -1);
    }

    /**
     * {@link SaltStoreType} class test.
     */
    @Test
    public void getHashBytesAtTheBeginningTest() {
        Assertions.assertThat(SaltStoreType.AT_THE_BEGINNING.getHashBytes(new byte[]{4, 5, 6, 1, 2, 3}, 3)).containsExactlyInOrder(1, 2, 3);
        Assertions.assertThat(SaltStoreType.AT_THE_BEGINNING.getHashBytes(new byte[]{4, 5, 6, 1, 2, 3}, 0)).containsExactlyInOrder(4, 5, 6, 1, 2, 3);

    }

    /**
     * {@link SaltStoreType} class test.
     */
    @Test(expected = NullPointerException.class)
    public void getNullHashBytesAtTheBeginningFailTest() {
        SaltStoreType.AT_THE_BEGINNING.getHashBytes(null, 3);
    }

    /**
     * {@link SaltStoreType} class test.
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void getWrongLengthHashBytesAtTheBeginningFailTest() {
        SaltStoreType.AT_THE_BEGINNING.getHashBytes(new byte[]{4, 5, 6, 1, 2, 3}, -1);
    }

    /**
     * {@link SaltStoreType} class test.
     */
    @Test
    public void addSaltBytesAtTheEndTest() {
        Assertions.assertThat(SaltStoreType.AT_THE_END.addSaltBytes(new byte[]{1, 2, 3}, new byte[]{4, 5, 6})).containsExactlyInOrder(1, 2, 3, 4, 5, 6);
    }

    /**
     * {@link SaltStoreType} class test.
     */
    @Test(expected = NullPointerException.class)
    public void addNullHashSaltBytesAtTheEndFailTest() {
        SaltStoreType.AT_THE_END.addSaltBytes(null, new byte[]{4, 5, 6});
    }

    /**
     * {@link SaltStoreType} class test.
     */
    @Test(expected = NullPointerException.class)
    public void addNullSaltBytesAtTheEndFailTest() {
        SaltStoreType.AT_THE_END.addSaltBytes(new byte[]{1, 2, 3}, null);
    }

    /**
     * {@link SaltStoreType} class test.
     */
    @Test
    public void getSaltBytesAtTheEndTest() {
        Assertions.assertThat(SaltStoreType.AT_THE_END.getSaltBytes(new byte[]{1, 2, 3, 4, 5, 6}, 3)).containsExactlyInOrder(4, 5, 6);
        Assertions.assertThat(SaltStoreType.AT_THE_END.getSaltBytes(new byte[]{1, 2, 3, 4, 5, 6}, 0)).containsExactlyInOrder();
    }

    /**
     * {@link SaltStoreType} class test.
     */
    @Test(expected = NullPointerException.class)
    public void getNullSaltBytesAtTheEndFailTest() {
        SaltStoreType.AT_THE_END.getSaltBytes(null, 3);
    }

    /**
     * {@link SaltStoreType} class test.
     */
    @Test(expected = NegativeArraySizeException.class)
    public void getWrongLengthSaltBytesAtTheEndFailTest() {
        SaltStoreType.AT_THE_END.getSaltBytes(new byte[]{1, 2, 3, 4, 5, 6}, -1);
    }

    /**
     * {@link SaltStoreType} class test.
     */
    @Test
    public void getHashBytesAtTheEndTest() {
        Assertions.assertThat(SaltStoreType.AT_THE_END.getHashBytes(new byte[]{1, 2, 3, 4, 5, 6}, 3)).containsExactlyInOrder(1, 2, 3);
        Assertions.assertThat(SaltStoreType.AT_THE_END.getHashBytes(new byte[]{1, 2, 3, 4, 5, 6}, 0)).containsExactlyInOrder(1, 2, 3, 4, 5, 6);
    }

    /**
     * {@link SaltStoreType} class test.
     */
    @Test(expected = NullPointerException.class)
    public void getNullHashBytesAtTheEndFailTest() {
        SaltStoreType.AT_THE_END.getHashBytes(null, 3);
    }

    /**
     * {@link SaltStoreType} class test.
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void getWrongLengthHashBytesAtTheEndFailTest() {
        SaltStoreType.AT_THE_END.getHashBytes(new byte[]{1, 2, 3, 4, 5, 6}, -1);
    }

}
