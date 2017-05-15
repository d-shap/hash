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

import java.io.InputStream;

/**
 * Hash builder entry point.
 *
 * @author Dmitry Shapovalov
 */
public final class HashBuilder {

    private HashBuilder() {
        super();
    }

    /**
     * Create simple hash builder.
     *
     * @param bytes the byte array.
     * @return the simple hash builder.
     */
    public static SimpleHashBuilder newSimpleHashBuilder(final byte[] bytes) {
        return new ByteArraySimpleHashBuilder(bytes);
    }

    /**
     * Create simple hash builder.
     *
     * @param str      the string.
     * @param encoding the string encoding.
     * @return the simple hash builder.
     */
    public static SimpleHashBuilder newSimpleHashBuilder(final String str, final String encoding) {
        return new StringSimpleHashBuilder(str, encoding);
    }

    /**
     * Create simple hash builder.
     *
     * @param stream the input stream.
     * @return the simple hash builder.
     */
    public static SimpleHashBuilder newSimpleHashBuilder(final InputStream stream) {
        return new InputStreamSimpleHashBuilder(stream);
    }

    /**
     * Create hash with the salt builder.
     *
     * @param bytes the byte array.
     * @return the hash with the salt builder.
     */
    public static HashWithSaltBuilder newHashWithSaltBuilder(final byte[] bytes) {
        return new ByteArrayHashWithSaltBuilder(bytes);
    }

    /**
     * Create hash with the salt builder.
     *
     * @param str      the string.
     * @param encoding the string encoding.
     * @return the hash with the salt builder.
     */
    public static HashWithSaltBuilder newHashWithSaltBuilder(final String str, final String encoding) {
        return new StringHashWithSaltBuilder(str, encoding);
    }

    /**
     * Create hash with the salt builder.
     *
     * @param stream the input stream.
     * @return the hash with the salt builder.
     */
    public static HashWithSaltBuilder newHashWithSaltBuilder(final InputStream stream) {
        return new InputStreamHashWithSaltBuilder(stream);
    }

    /**
     * Create hash with the stored salt and the fixed salt builder.
     *
     * @param bytes the byte array.
     * @return the hash with the stored salt and the fixed salt builder.
     */
    public static HashWithSalt2Builder newHashWithSalt2Builder(final byte[] bytes) {
        return new ByteArrayHashWithSalt2Builder(bytes);
    }

    /**
     * Create hash with the stored salt and the fixed salt builder.
     *
     * @param str      the string.
     * @param encoding the string encoding.
     * @return the hash with the stored salt and the fixed salt builder.
     */
    public static HashWithSalt2Builder newHashWithSalt2Builder(final String str, final String encoding) {
        return new StringHashWithSalt2Builder(str, encoding);
    }

    /**
     * Create hash with the stored salt and the fixed salt builder.
     *
     * @param stream the input stream.
     * @return the hash with the stored salt and the fixed salt builder.
     */
    public static HashWithSalt2Builder newHashWithSalt2Builder(final InputStream stream) {
        return new InputStreamHashWithSalt2Builder(stream);
    }

}
