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

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;

/**
 * Tests for {@link HashException}.
 *
 * @author Dmitry Shapovalov
 */
public final class HashExceptionTest {

    /**
     * Test class constructor.
     */
    public HashExceptionTest() {
        super();
    }

    /**
     * {@link HashException} class test.
     */
    @Test
    public void errorCauseTest() {
        Assertions.assertThat(new HashException(new GeneralSecurityException())).isCauseInstanceOf(GeneralSecurityException.class);
        Assertions.assertThat(new HashException(new GeneralSecurityException())).isCauseInstanceOf(Exception.class);

        Assertions.assertThat(new HashException(new IOException())).isCauseInstanceOf(IOException.class);
        Assertions.assertThat(new HashException(new IOException())).isCauseInstanceOf(Exception.class);
    }

    /**
     * {@link HashException} class test.
     */
    @Test
    public void errorMessageTest() {
        Assertions.assertThat(new HashException(new GeneralSecurityException())).hasMessage("java.security.GeneralSecurityException");
        Assertions.assertThat(new HashException(new GeneralSecurityException("Security exception message"))).hasMessage("java.security.GeneralSecurityException: Security exception message");

        Assertions.assertThat(new HashException(new IOException())).hasMessage("java.io.IOException");
        Assertions.assertThat(new HashException(new IOException("IO exception message"))).hasMessage("java.io.IOException: IO exception message");
    }

}
