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
package ru.d_shap.hash;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;

/**
 * Tests for {@link HashAlgorithms}.
 *
 * @author Dmitry Shapovalov
 */
public final class HashSHA384Test {

    /**
     * Test class constructor.
     */
    public HashSHA384Test() {
        super();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void simpleHashTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] hash = HashHelper.getHash(original, HashAlgorithms.SHA384).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(19, -43, -86, -71, -76, 2, -12, -103, -15, 125, -14, 39, -119, 108, 31, -46, -54, 8, -9, 90, 57, -49, 66, -101, -66, 79, -84, -1, 125, -123, -117, 124, -10, -14, 64, 33, -8, 87, 39, 123, -79, -32, -53, 89, -25, 91, -118, -47);
        Assertions.assertThat(HashHelper.getHash(original, HashAlgorithms.SHA384).matches(hash)).isTrue();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void saltAtTheBeginningTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] salt = new byte[]{48, 49, 50};
        byte[] hash = HashHelper.getHash(original, HashAlgorithms.SHA384).addSalt(salt).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(-109, -49, 33, -24, -26, 76, -48, 34, 54, 28, 117, 113, -55, 116, -127, -66, -10, 102, -108, 126, -49, 30, -68, -111, 84, -4, 124, 107, -114, -53, -106, 90, -80, -81, -64, -119, 45, 85, -81, -94, -128, -75, 2, 43, -94, -118, 82, -13);

        byte[] full = HashHelper.addSaltBytes(hash, salt, SaltStoreType.AT_THE_BEGINNING);
        Assertions.assertThat(full).containsExactlyInOrder(48, 49, 50, -109, -49, 33, -24, -26, 76, -48, 34, 54, 28, 117, 113, -55, 116, -127, -66, -10, 102, -108, 126, -49, 30, -68, -111, 84, -4, 124, 107, -114, -53, -106, 90, -80, -81, -64, -119, 45, 85, -81, -94, -128, -75, 2, 43, -94, -118, 82, -13);

        Assertions.assertThat(HashHelper.getHash(original, HashAlgorithms.SHA384).addSalt(HashHelper.getSaltBytes(full, SaltStoreType.AT_THE_BEGINNING, 3)).matches(HashHelper.getHashBytes(full, SaltStoreType.AT_THE_BEGINNING, 3))).isTrue();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void saltAtTheEndTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] salt = new byte[]{48, 49, 50};
        byte[] hash = HashHelper.getHash(original, HashAlgorithms.SHA384).addSalt(salt).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(-109, -49, 33, -24, -26, 76, -48, 34, 54, 28, 117, 113, -55, 116, -127, -66, -10, 102, -108, 126, -49, 30, -68, -111, 84, -4, 124, 107, -114, -53, -106, 90, -80, -81, -64, -119, 45, 85, -81, -94, -128, -75, 2, 43, -94, -118, 82, -13);

        byte[] full = HashHelper.addSaltBytes(hash, salt, SaltStoreType.AT_THE_END);
        Assertions.assertThat(full).containsExactlyInOrder(-109, -49, 33, -24, -26, 76, -48, 34, 54, 28, 117, 113, -55, 116, -127, -66, -10, 102, -108, 126, -49, 30, -68, -111, 84, -4, 124, 107, -114, -53, -106, 90, -80, -81, -64, -119, 45, 85, -81, -94, -128, -75, 2, 43, -94, -118, 82, -13, 48, 49, 50);

        Assertions.assertThat(HashHelper.getHash(original, HashAlgorithms.SHA384).addSalt(HashHelper.getSaltBytes(full, SaltStoreType.AT_THE_END, 3)).matches(HashHelper.getHashBytes(full, SaltStoreType.AT_THE_END, 3))).isTrue();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void salt2AtTheBeginningTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] salt1 = new byte[]{48, 49, 50};
        byte[] salt2 = new byte[]{101, 102, 103};
        byte[] hash = HashHelper.getHash(original, HashAlgorithms.SHA384).addSalt(salt1).addSalt(salt2).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(64, 10, 26, -6, -63, 70, -5, 97, -9, -35, -86, -10, -48, 48, -79, 109, 51, -62, -30, 87, -56, 67, -17, -67, 121, 111, 114, -59, -3, 118, -51, -105, -100, 7, -98, 90, 16, -104, 76, -126, -84, 125, 54, 38, 106, -44, -66, 49);

        byte[] full = HashHelper.addSaltBytes(hash, salt1, SaltStoreType.AT_THE_BEGINNING);
        Assertions.assertThat(full).containsExactlyInOrder(48, 49, 50, 64, 10, 26, -6, -63, 70, -5, 97, -9, -35, -86, -10, -48, 48, -79, 109, 51, -62, -30, 87, -56, 67, -17, -67, 121, 111, 114, -59, -3, 118, -51, -105, -100, 7, -98, 90, 16, -104, 76, -126, -84, 125, 54, 38, 106, -44, -66, 49);

        Assertions.assertThat(HashHelper.getHash(original, HashAlgorithms.SHA384).addSalt(HashHelper.getSaltBytes(full, SaltStoreType.AT_THE_BEGINNING, 3)).addSalt(salt2).matches(HashHelper.getHashBytes(full, SaltStoreType.AT_THE_BEGINNING, 3))).isTrue();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void salt2AtTheEndTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] salt1 = new byte[]{48, 49, 50};
        byte[] salt2 = new byte[]{101, 102, 103};
        byte[] hash = HashHelper.getHash(original, HashAlgorithms.SHA384).addSalt(salt1).addSalt(salt2).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(64, 10, 26, -6, -63, 70, -5, 97, -9, -35, -86, -10, -48, 48, -79, 109, 51, -62, -30, 87, -56, 67, -17, -67, 121, 111, 114, -59, -3, 118, -51, -105, -100, 7, -98, 90, 16, -104, 76, -126, -84, 125, 54, 38, 106, -44, -66, 49);

        byte[] full = HashHelper.addSaltBytes(hash, salt1, SaltStoreType.AT_THE_END);
        Assertions.assertThat(full).containsExactlyInOrder(64, 10, 26, -6, -63, 70, -5, 97, -9, -35, -86, -10, -48, 48, -79, 109, 51, -62, -30, 87, -56, 67, -17, -67, 121, 111, 114, -59, -3, 118, -51, -105, -100, 7, -98, 90, 16, -104, 76, -126, -84, 125, 54, 38, 106, -44, -66, 49, 48, 49, 50);

        Assertions.assertThat(HashHelper.getHash(original, HashAlgorithms.SHA384).addSalt(HashHelper.getSaltBytes(full, SaltStoreType.AT_THE_END, 3)).addSalt(salt2).matches(HashHelper.getHashBytes(full, SaltStoreType.AT_THE_END, 3))).isTrue();
    }

}
