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

import ru.d_shap.hash.HashHelper;

/**
 * Hash builder for the input stream.
 *
 * @author Dmitry Shapovalov
 */
final class InputStreamSimpleHashBuilder extends SimpleHashBuilder {

    private final InputStream _stream;

    InputStreamSimpleHashBuilder(final InputStream stream) {
        super();
        _stream = stream;
    }

    @Override
    public byte[] getHash() {
        return HashHelper.getHash(_stream, getAlgorithm()).getBytes();
    }

    @Override
    public boolean isHashValid() {
        return matches(HashHelper.getHash(_stream, getAlgorithm()));
    }

}
