package com.example.jeffrey.engmathhack;


import android.util.Base64;

import javax.crypto.Cipher;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class RSA
{
    public static final String RSAKeyFactory = "RSA";
    public static final String RSAKeyAlgorithm = "RSA/ECB/PKCS1Padding";
    public static final String UTF_8 = "UTF-8";
    public static final int BASE_64_FLAG = Base64.NO_WRAP;

    public static final String modulus = "ny4wvg1A1p9Zt0r3hWGg5YvcMaa9W2LuUmGd2+w3RG7arYDIDIwiUXE4IvpVHVrtPf+LnAJa7s5lMcOYCBs4M5Ky4X6O+cJWklCivti8/ZWfQTNS0GAzQbzDNtmzcaE874HNRTUDnqVSmYr6dAd2EK4uV5MHMG5gsjLhzg3orb389lO5uBcebgQ6N/06VezwiIvM90tfrxueUhtO/M1R5aQcKQIvj0wkCylrKwFNPsXpUiaSYzHMBeNbH0qy46H5g6MuHCZHuRDk6/2AqfdomT55eSm18aen/Rl/J/RzcANOQCuPM5ey3pTsG69IHK1PKfU5M2d90f0+UJpRKa00iQ==";
    public static final String d = "iM5E/AId+JcpnxtiEhNvGfmS/GVGlvU2yDy3VFz2vuzbKoTk6cvCbuZ0jbNCVaFhBojLbwCjMFzYoeVtk4H5UnHBJYYE0WkRyFxfEtDp1LO0FHBMu2QCiXv29vwxefZOjbWsOs+uVbnWfLnyOKh08y8QCxoE8r3agyyx3ssbb73e55pYh/wyFwkYPCAiO3+YVHXKZpwK/0AMclDIGGT/352U6j39EfYuqJIQco2NNKXJXjhPYikJlaIHVwZbalyywFadQ/Faw4Y5WyTXa3baub+y71V2rmQ1CV7QZU9kIC0h8dc2MAtdWfPrA03yq32VACvCO5IzWeyYfbQyBZqwFQ==";

    private PrivateKey privateKey;

    public RSA() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException
    {
        KeyFactory keyFactory = KeyFactory.getInstance(RSAKeyFactory);
        privateKey = keyFactory.generatePrivate(new RSAPrivateKeySpec(
                new BigInteger(1, Base64.decode(modulus, BASE_64_FLAG)),
                new BigInteger(1, Base64.decode(d, BASE_64_FLAG))));
    }

    public String decrypt(String cipherText) throws Exception
    {
        byte[] cipherBytes = Base64.decode(cipherText, BASE_64_FLAG);
        Cipher cipher = Cipher.getInstance(RSAKeyAlgorithm);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] plainData = cipher.doFinal(cipherBytes);
        return new String(plainData, UTF_8);
    }
}