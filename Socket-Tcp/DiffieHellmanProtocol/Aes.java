/*
 * Copyright (c) 2019 - Luca Bernardini
 * 
 *  This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Aes {
	private SecretKeySpec chiave;
    
	
    public static SecretKeySpec getAesKey(String key) throws Exception {
           	
        byte[] keyUTF8 = key.getBytes("UTF-8");
    	// creates a message digest instance which uses the SHA-1
        // cryptographic hash algorithm in order to have a fixed size
        // of 160 bits unique for each input key
        
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        // result is a 160-bit digest but 
    	// only first 128 bits are used for Aes encryption
        
    	byte[] keyDigest = sha.digest(keyUTF8);
    	keyDigest = Arrays.copyOf(keyDigest, 16); // 128 bits
     	return new SecretKeySpec(keyDigest, "AES");
    }

    // Encrypts plain String in AES using the generated secret key 
    // from getAesKey() method 
    
    public static byte[] encryptAes(String str, SecretKeySpec Aeskey) throws Exception{

    	// AES defaults to AES/ECB/PKCS5Padding
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, Aeskey);
        // it encrypts the passed String, pads it out to the necessary length, and then returns it. 
        byte[] byteCipherText = aesCipher.doFinal(str.getBytes());
        return byteCipherText;

    }
    
    // Decrypts encrypted byte array using the key used for encryption.
     
    public static String decryptAes(byte[] byteEncrypted, SecretKeySpec Aeskey) throws Exception {

       // defaults to AES/ECB/PKCS5Padding 
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, Aeskey);
        byte[] byteClear = aesCipher.doFinal(byteEncrypted);
        return new String(byteClear);
    }

    // Convert a binary byte array into readable Base64 format String

    public static String bytesToBase64(byte[] byteArray) {

    	return  Base64.getEncoder().encodeToString(byteArray);
    }
    
    // Convert a String into a Base64 format String
    
    public static String Base64toString(String base64) {

    	byte[] decodedArray = Base64.getDecoder().decode(base64);
    	return new String(decodedArray);
    }
    
    public static void main(String[] args) throws Exception 
    {
    	Random r3 = new Random();
 	    BigInteger y = java.math.BigInteger.probablePrime(1024, r3);
    	SecretKeySpec sc = getAesKey(y.toString(16));
    	byte[] encrypted = encryptAes("prova", sc);
    	String base64 = bytesToBase64(encrypted);
    	System.out.println("testo criptato in base64: " + base64);
    	byte[] encrypted1 = Base64toString(base64).getBytes();
    	String decrypted = decryptAes(encrypted, sc);
    	//String decrypted1 = decryptAes(encrypted1, sc);
    	
    	System.out.println("testo decifrato: " + decrypted );
    	
    }
}


