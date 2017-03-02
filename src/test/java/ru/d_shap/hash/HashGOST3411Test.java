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

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Before;
import org.junit.Test;

import ru.d_shap.assertions.Assertions;

/**
 * Tests for {@link HashAlgorithms}.
 *
 * @author Dmitry Shapovalov
 */
public final class HashGOST3411Test {

    /**
     * Test class constructor.
     */
    public HashGOST3411Test() {
        super();
    }

    /**
     * Set up test.
     */
    @Before
    public void setup() {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void simpleHashTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] hash = HashHelper.getHash(original, "GOST3411").getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(-24, -74, 55, -113, -33, 53, -31, -58, 1, 101, -83, 40, 110, -6, -64, -66, -95, -1, 85, 3, -110, -57, -26, -20, -42, 3, -103, 127, -100, -11, 103, -128);
        Assertions.assertThat(HashHelper.getHash(original, "GOST3411").matches(hash)).isTrue();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void saltAtTheBeginningTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] salt = new byte[]{48, 49, 50};
        byte[] hash = HashHelper.getHash(original, "GOST3411").addSalt(salt).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(-69, 57, 120, 109, 117, -7, -63, 4, 98, -71, 31, 111, -91, -31, -83, 46, -60, 84, 57, 109, 109, 29, -24, -51, 13, 20, -61, 55, 48, 71, 11, -90);

        byte[] full = HashHelper.addSaltBytes(hash, salt, SaltStoreType.AT_THE_BEGINNING);
        Assertions.assertThat(full).containsExactlyInOrder(48, 49, 50, -69, 57, 120, 109, 117, -7, -63, 4, 98, -71, 31, 111, -91, -31, -83, 46, -60, 84, 57, 109, 109, 29, -24, -51, 13, 20, -61, 55, 48, 71, 11, -90);

        Assertions.assertThat(HashHelper.getHash(original, "GOST3411").addSalt(HashHelper.getSaltBytes(full, SaltStoreType.AT_THE_BEGINNING, 3)).matches(HashHelper.getHashBytes(full, SaltStoreType.AT_THE_BEGINNING, 3))).isTrue();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void saltAtTheEndTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] salt = new byte[]{48, 49, 50};
        byte[] hash = HashHelper.getHash(original, "GOST3411").addSalt(salt).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(-69, 57, 120, 109, 117, -7, -63, 4, 98, -71, 31, 111, -91, -31, -83, 46, -60, 84, 57, 109, 109, 29, -24, -51, 13, 20, -61, 55, 48, 71, 11, -90);

        byte[] full = HashHelper.addSaltBytes(hash, salt, SaltStoreType.AT_THE_END);
        Assertions.assertThat(full).containsExactlyInOrder(-69, 57, 120, 109, 117, -7, -63, 4, 98, -71, 31, 111, -91, -31, -83, 46, -60, 84, 57, 109, 109, 29, -24, -51, 13, 20, -61, 55, 48, 71, 11, -90, 48, 49, 50);

        Assertions.assertThat(HashHelper.getHash(original, "GOST3411").addSalt(HashHelper.getSaltBytes(full, SaltStoreType.AT_THE_END, 3)).matches(HashHelper.getHashBytes(full, SaltStoreType.AT_THE_END, 3))).isTrue();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void salt2AtTheBeginningTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] salt1 = new byte[]{48, 49, 50};
        byte[] salt2 = new byte[]{101, 102, 103};
        byte[] hash = HashHelper.getHash(original, "GOST3411").addSalt(salt1).addSalt(salt2).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(8, -46, 125, 55, 53, 59, 14, -56, 70, 1, 3, -10, -8, 110, 113, -64, -9, 71, 59, 85, -86, -104, 92, 94, -107, -73, 51, -48, 57, -28, 4, -66);

        byte[] full = HashHelper.addSaltBytes(hash, salt1, SaltStoreType.AT_THE_BEGINNING);
        Assertions.assertThat(full).containsExactlyInOrder(48, 49, 50, 8, -46, 125, 55, 53, 59, 14, -56, 70, 1, 3, -10, -8, 110, 113, -64, -9, 71, 59, 85, -86, -104, 92, 94, -107, -73, 51, -48, 57, -28, 4, -66);

        Assertions.assertThat(HashHelper.getHash(original, "GOST3411").addSalt(HashHelper.getSaltBytes(full, SaltStoreType.AT_THE_BEGINNING, 3)).addSalt(salt2).matches(HashHelper.getHashBytes(full, SaltStoreType.AT_THE_BEGINNING, 3))).isTrue();
    }

    /**
     * {@link HashAlgorithms} class test.
     */
    @Test
    public void salt2AtTheEndTest() {
        byte[] original = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        byte[] salt1 = new byte[]{48, 49, 50};
        byte[] salt2 = new byte[]{101, 102, 103};
        byte[] hash = HashHelper.getHash(original, "GOST3411").addSalt(salt1).addSalt(salt2).getBytes();
        Assertions.assertThat(hash).containsExactlyInOrder(8, -46, 125, 55, 53, 59, 14, -56, 70, 1, 3, -10, -8, 110, 113, -64, -9, 71, 59, 85, -86, -104, 92, 94, -107, -73, 51, -48, 57, -28, 4, -66);

        byte[] full = HashHelper.addSaltBytes(hash, salt1, SaltStoreType.AT_THE_END);
        Assertions.assertThat(full).containsExactlyInOrder(8, -46, 125, 55, 53, 59, 14, -56, 70, 1, 3, -10, -8, 110, 113, -64, -9, 71, 59, 85, -86, -104, 92, 94, -107, -73, 51, -48, 57, -28, 4, -66, 48, 49, 50);

        Assertions.assertThat(HashHelper.getHash(original, "GOST3411").addSalt(HashHelper.getSaltBytes(full, SaltStoreType.AT_THE_END, 3)).addSalt(salt2).matches(HashHelper.getHashBytes(full, SaltStoreType.AT_THE_END, 3))).isTrue();
    }

}
