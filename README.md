Hash library
============
Hash library provides facilities for the hash computations.

A hash function is any function that can be used to map data of arbitrary size to data of fixed size.
This can be used for assuring integrity of transmitted data.
This is also useful in cryptography.
A cryptographic hash function allows one to easily verify that some input data maps to a given hash value, but if the input data is unknown, it is deliberately difficult to reconstruct it (or equivalent alternatives) by knowing the stored hash value.

Hash library simplify usage of java.security package classes for the hash functions.

There are some common use-cases for the hash functions.

To check data integrity for transmitted data (for example, some large file) the hash of this file can be computed and stored along with the file. After this file is obtained, the hash can be computed once again and if this hash is equal to the stored hash, then the file is not corrupted.
```
// Obtain hash of the file
File file = ...
byte[] hash = HashBuilder.newSimpleHashBuilder(new FileInputStream(file))
                         .setAlgorithm(HashAlgorithms.MD5)
                         .getHash();

// Check if the file is not corrupted
File file = ...
byte[] storedHash = ...
HashBuilder.newSimpleHashBuilder(new FileInputStream(file))
           .setAlgorithm(HashAlgorithms.MD5)
           .setStoredHash(storedHash)
           .isHashValid();
```

To define a password hash the same code can be used.
But the result is not secure enough.
There are rainbow tables, that contain a precomputed hashes for the passwords.
This tables can be used to get the clear password.
To improve security of the password hash a salt can be used.

A salt is used to complicate a password and to make the same clear passwords different.
A salt is a public data.
It can be stored as a clear text.
A new salt should be generated for each new password.
```
// Obtain hash of the password
byte[] password = ...
byte[] salt = ...
byte[] hash = HashBuilder.newHashWithSaltBuilder(password)
                         .setSalt(salt)
                         .setAlgorithm(HashAlgorithms.SHA256)
                         .setSaltStoreType(SaltStoreType.AT_THE_BEGINNING)
                         .getHash();

// Check if the password is valid
byte[] password = ...
byte[] storedHash = ...
HashBuilder.newHashWithSaltBuilder(password)
           .setAlgorithm(HashAlgorithms.SHA256)
           .setSaltStoreType(SaltStoreType.AT_THE_BEGINNING)
           .setStoredHash(storedHash)
           .isHashValid();
```

The hash in the example above is secure enough, but the salt and the hash are stored together.
Another salt can be added, that is known to the application only.
This salt is fixed, i.e. is not changed during application existance.
But this salt is a peace of information, that is unknown to someone who has the stored hash.
```
// Obtain hash of the password
byte[] password = ...
byte[] storedSalt = ...
byte[] fixedSalt = ...
byte[] hash = HashBuilder.newHashWithSalt2Builder(password)
                         .setStoredSalt(storedSalt)
                         .setFixedSalt(fixedSalt)
                         .setAlgorithm(HashAlgorithms.SHA256)
                         .setSaltOrder(SaltOrder.STORED_SALT_FIRST)
                         .setSaltStoreType(SaltStoreType.AT_THE_BEGINNING)
                         .getHash();

// Check if the password is valid
byte[] password = ...
byte[] fixedSalt = ...
byte[] storedHash = ...
HashBuilder.newHashWithSalt2Builder(password)
           .setFixedSalt(fixedSalt)
           .setAlgorithm(HashAlgorithms.SHA256)
           .setSaltOrder(SaltOrder.STORED_SALT_FIRST)
           .setSaltStoreType(SaltStoreType.AT_THE_END)
           .setStoredHash(storedHash)
           .isHashValid();
```

Donate
======
I am a full time software developer.
I code in my spare time and to extend the use of software that has purpose.

If you find value in what I do then, please, feel free to donate to me.
I'm not going to force you to donate, you can use my software completely free of charge.
If you are really want to give something to me then you are welcome to do so.
I don't expect donations, nor do I insist that you give them.

[![Donate](https://img.shields.io/badge/Donate-PayPal-green.svg)](https://www.paypal.me/dshapovalov)
