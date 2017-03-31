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

/**
 * Exception is thrown when a method argument is wrong - null, empty, etc.
 *
 * @author Dmitry Shapovalov
 */
public final class WrongArgumentException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Create new object.
     *
     * @param message the exception message.
     */
    public WrongArgumentException(final String message) {
        super(message);
    }

    /**
     * Create new object.
     *
     * @param message the exception message.
     * @param ex      the cause of the exception.
     */
    public WrongArgumentException(final String message, final Exception ex) {
        super(message, ex);
    }

}
