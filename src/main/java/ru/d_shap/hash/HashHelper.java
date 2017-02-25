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
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

/**
 * factory.
 *
 * @author Dmitry Shapovalov
 */
public final class HashHelper {

    private static final int INPUT_STREAM_BUFFER_SIZE = 512;

    private HashHelper() {
        super();
    }

    /**
     * Create hash for the specified bytes.
     *
     * @param bytes     the specified bytes.
     * @param algorithm hash algorithm.
     * @return hash object.
     */
    public static Hash getHash(final byte[] bytes, final String algorithm) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.reset();
            messageDigest.update(bytes);
            byte[] currentHash = messageDigest.digest();
            return new Hash(messageDigest, currentHash);
        } catch (GeneralSecurityException ex) {
            throw new HashException(ex);
        }
    }

    /**
     * Create hash for the specified stream.
     *
     * @param stream    the specified stream.
     * @param algorithm hash algorithm.
     * @return hash object.
     */
    public static Hash getHash(final InputStream stream, final String algorithm) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.reset();

            try {
                byte[] buffer = new byte[INPUT_STREAM_BUFFER_SIZE];
                int read;
                while (true) {
                    read = stream.read(buffer);
                    if (read <= 0) {
                        break;
                    }
                    messageDigest.update(buffer, 0, read);
                }
            } finally {
                stream.close();
            }

            byte[] currentHash = messageDigest.digest();
            return new Hash(messageDigest, currentHash);
        } catch (GeneralSecurityException ex) {
            throw new HashException(ex);
        } catch (IOException ex) {
            throw new HashException(ex);
        }
    }

}
