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
 * Tests for {@link HashAlgorithms}.
 *
 * @author Dmitry Shapovalov
 */
public final class HashSHA512Test {

    /**
     * Test class constructor.
     */
    public HashSHA512Test() {
        super();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void simpleHashTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] hash = HashHelper.getHash(original, HashAlgorithms.SHA512).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(58, -45, -13, 105, 121, 69, 13, 79, 83, 54, 98, 68, -20, -15, 1, 15, 79, -111, 33, -42, -120, -126, -123, -1, 20, 16, 79, -43, -83, -19, -123, -44, -118, -95, 113, -65, 30, 51, -95, 18, 96, 47, -110, -73, -89, 8, -117, 41, -121, -119, 1, 47, -72, 123, -112, 86, 50, 18, 65, -95, -97, -73, 78, 11);
        Assertions.assertThat(HashHelper.getHash(original, HashAlgorithms.SHA512).matches(hash)).isTrue();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void saltAtTheBeginningTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] salt = new byte[]{48, 49, 50};
        byte[] hash = HashHelper.getHash(original, HashAlgorithms.SHA512).addSalt(salt).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(81, -3, -75, -53, 42, 60, 124, -114, 63, -119, -110, 115, 62, -107, 21, 124, 27, 90, 87, -48, 70, 121, 71, 76, -66, 121, -89, -89, 33, 84, 78, 92, 79, -79, -18, 48, -80, -6, -76, -44, 126, -12, -5, 78, 77, -70, -125, -79, -78, -45, -1, -90, 28, -107, -7, 30, 14, -72, -36, 121, 11, 22, 82, 77);

        byte[] full = HashHelper.addSaltBytes(hash, salt, SaltStoreType.AT_THE_BEGINNING);
        Assertions.assertThat(full).containsExactlyInOrder(48, 49, 50, 81, -3, -75, -53, 42, 60, 124, -114, 63, -119, -110, 115, 62, -107, 21, 124, 27, 90, 87, -48, 70, 121, 71, 76, -66, 121, -89, -89, 33, 84, 78, 92, 79, -79, -18, 48, -80, -6, -76, -44, 126, -12, -5, 78, 77, -70, -125, -79, -78, -45, -1, -90, 28, -107, -7, 30, 14, -72, -36, 121, 11, 22, 82, 77);

        Assertions.assertThat(HashHelper.getHash(original, HashAlgorithms.SHA512).addSalt(HashHelper.getSaltBytes(full, SaltStoreType.AT_THE_BEGINNING, 3)).matches(HashHelper.getHashBytes(full, SaltStoreType.AT_THE_BEGINNING, 3))).isTrue();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void saltAtTheEndTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] salt = new byte[]{48, 49, 50};
        byte[] hash = HashHelper.getHash(original, HashAlgorithms.SHA512).addSalt(salt).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(81, -3, -75, -53, 42, 60, 124, -114, 63, -119, -110, 115, 62, -107, 21, 124, 27, 90, 87, -48, 70, 121, 71, 76, -66, 121, -89, -89, 33, 84, 78, 92, 79, -79, -18, 48, -80, -6, -76, -44, 126, -12, -5, 78, 77, -70, -125, -79, -78, -45, -1, -90, 28, -107, -7, 30, 14, -72, -36, 121, 11, 22, 82, 77);

        byte[] full = HashHelper.addSaltBytes(hash, salt, SaltStoreType.AT_THE_END);
        Assertions.assertThat(full).containsExactlyInOrder(81, -3, -75, -53, 42, 60, 124, -114, 63, -119, -110, 115, 62, -107, 21, 124, 27, 90, 87, -48, 70, 121, 71, 76, -66, 121, -89, -89, 33, 84, 78, 92, 79, -79, -18, 48, -80, -6, -76, -44, 126, -12, -5, 78, 77, -70, -125, -79, -78, -45, -1, -90, 28, -107, -7, 30, 14, -72, -36, 121, 11, 22, 82, 77, 48, 49, 50);

        Assertions.assertThat(HashHelper.getHash(original, HashAlgorithms.SHA512).addSalt(HashHelper.getSaltBytes(full, SaltStoreType.AT_THE_END, 3)).matches(HashHelper.getHashBytes(full, SaltStoreType.AT_THE_END, 3))).isTrue();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void salt2AtTheBeginningTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] salt1 = new byte[]{48, 49, 50};
        byte[] salt2 = new byte[]{101, 102, 103};
        byte[] hash = HashHelper.getHash(original, HashAlgorithms.SHA512).addSalt(salt1).addSalt(salt2).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(18, -21, 81, -112, 25, -8, -26, 104, 115, -108, 111, -28, 40, -50, 18, -105, 32, -111, 13, -4, -57, -8, 21, 81, -123, -14, 113, 85, -92, -31, -80, 124, -90, 33, -85, 3, -73, 110, 11, 107, 59, 81, -20, -110, -93, 33, -15, 93, -109, -21, -56, 74, 63, 113, 96, -116, -81, 47, -103, 120, -57, 42, 101, 75);

        byte[] full = HashHelper.addSaltBytes(hash, salt1, SaltStoreType.AT_THE_BEGINNING);
        Assertions.assertThat(full).containsExactlyInOrder(48, 49, 50, 18, -21, 81, -112, 25, -8, -26, 104, 115, -108, 111, -28, 40, -50, 18, -105, 32, -111, 13, -4, -57, -8, 21, 81, -123, -14, 113, 85, -92, -31, -80, 124, -90, 33, -85, 3, -73, 110, 11, 107, 59, 81, -20, -110, -93, 33, -15, 93, -109, -21, -56, 74, 63, 113, 96, -116, -81, 47, -103, 120, -57, 42, 101, 75);

        Assertions.assertThat(HashHelper.getHash(original, HashAlgorithms.SHA512).addSalt(HashHelper.getSaltBytes(full, SaltStoreType.AT_THE_BEGINNING, 3)).addSalt(salt2).matches(HashHelper.getHashBytes(full, SaltStoreType.AT_THE_BEGINNING, 3))).isTrue();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void salt2AtTheEndTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] salt1 = new byte[]{48, 49, 50};
        byte[] salt2 = new byte[]{101, 102, 103};
        byte[] hash = HashHelper.getHash(original, HashAlgorithms.SHA512).addSalt(salt1).addSalt(salt2).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(18, -21, 81, -112, 25, -8, -26, 104, 115, -108, 111, -28, 40, -50, 18, -105, 32, -111, 13, -4, -57, -8, 21, 81, -123, -14, 113, 85, -92, -31, -80, 124, -90, 33, -85, 3, -73, 110, 11, 107, 59, 81, -20, -110, -93, 33, -15, 93, -109, -21, -56, 74, 63, 113, 96, -116, -81, 47, -103, 120, -57, 42, 101, 75);

        byte[] full = HashHelper.addSaltBytes(hash, salt1, SaltStoreType.AT_THE_END);
        Assertions.assertThat(full).containsExactlyInOrder(18, -21, 81, -112, 25, -8, -26, 104, 115, -108, 111, -28, 40, -50, 18, -105, 32, -111, 13, -4, -57, -8, 21, 81, -123, -14, 113, 85, -92, -31, -80, 124, -90, 33, -85, 3, -73, 110, 11, 107, 59, 81, -20, -110, -93, 33, -15, 93, -109, -21, -56, 74, 63, 113, 96, -116, -81, 47, -103, 120, -57, 42, 101, 75, 48, 49, 50);

        Assertions.assertThat(HashHelper.getHash(original, HashAlgorithms.SHA512).addSalt(HashHelper.getSaltBytes(full, SaltStoreType.AT_THE_END, 3)).addSalt(salt2).matches(HashHelper.getHashBytes(full, SaltStoreType.AT_THE_END, 3))).isTrue();
    }

}
