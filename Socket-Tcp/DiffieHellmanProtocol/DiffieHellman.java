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
import java.util.Random;

public class DiffieHellman {
	private static final BigInteger BigInteger = null;
	private static BigInteger p, g;
	private static BigInteger privateKey;
	private static BigInteger k;
	private static int bitlength = 1024;
	private static Random r, r1, r2, r3;
	
	// 1024-bit MODP Group with 160-bit Prime Order Subgroup (RFC 5114)

	public static final String PStr = "B10B8F96A080E01DDE92DE5EAE5D54EC52C99FBCFB06A3C6" + 
			"9A6A9DCA52D23B616073E28675A23D189838EF1E2EE652C0" + 
			"13ECB4AEA906112324975C3CD49B83BFACCBDD7D90C4BD70" + 
			"98488E9C219A73724EFFD6FAE5644738FAA31A4FF55BCCC0" + 
			"A151AF5F0DC8B4BD45BF37DF365C1A65E68CFDA76D4DA708" + 
			"DF1FB2BC2E4A4371";
	
	  
	public static final String GStr = "A4D1CBD5C3FD34126765A442EFB99905F8104DD258AC507F" +
			"D6406CFF14266D31266FEA1E5C41564B777E690F5504F213" + 
			"160217B4B01B886A5E91547F9E2749F4D7FBD7D3B9A92EE1" + 
			"909D0D2263F80A76A6A24C087A091F531DBF0A0169B6A28A" + 
			"D662A4D18E73AFA32D779D5918D08BC8858F4DCEF97C2A24" + 
			"855E6EEB22B3B2E5";
	
	
	public static void DHkeys() 
	{
		 r = new Random();
		 // finds a prime number
		 k = java.math.BigInteger.probablePrime(bitlength, r);
		 
		 // if we choose p to be such that p = 2k + 1 with k also prime number
		 // we have a Multiplicative Finite Group G = <Zp*, x> that contains integers from 1 to n-1 
		 // that are relatively prime to p. The order of the finite group is equal to the numbers of 
		 // elements that we can also calculate with Euler function phi(n).
		 // The order of an element x is the smallest integer i such that x^i is congruent with (1 mod p).
		 // When the order of an element is equal to phi(n), that element is a primitive root of the group.
		 // In this group: any number will have an order in this set (1, 2, k, 2k);
		 // phi(p) = p - 1 => 2k + 1 - 1 = 2k; So if we pick a number x we have to check if x^1, x^2, x^k is not 
		 // congruent with (1 mod p). If so then x is a primitive root because we have the last case x^(2k) where
		 // 2k is equal to phi(p).
		 
		 p = (k.multiply(new BigInteger("2"))).add(BigInteger.ONE);
		 BigInteger m = java.math.BigInteger.ONE.mod(p);
		 
		 // find the primitive root g
		 while(true)
		 {
			    r1 = new Random();
		   	    g = java.math.BigInteger.probablePrime(bitlength / 2, r1);
			   	if(((g.modPow(java.math.BigInteger.ONE, p)).compareTo(m) != 0) && ((g.modPow(java.math.BigInteger.valueOf(2L), p)).compareTo(m) != 0) && ((g.modPow(k, p)).compareTo(m) != 0))
			   	{
			       break;
		   		}
			
		  
		   }
		 
		 // private Key 
		 r = new Random();
		 privateKey = java.math.BigInteger.probablePrime(bitlength / 2, r);
		 
	}
	
	
	public static BigInteger getPrivateKey() {
		return privateKey;
	}


	public static BigInteger getP()
	{
		
		return p;
		   
	}
	
	public static BigInteger getG()
	{
		
		return g;
		   
	}
	
	
	
	public static void main(String[] args) {
		 
	   // g public prime generator
	   // p public prime modulus
	   DHkeys();
	   
	   System.out.println("primitive root (generatore) " + g.toString());
	   
	   // x private key of Alice
	   r2 = new Random();
	   BigInteger x = java.math.BigInteger.probablePrime(bitlength / 2, r2);
	   
	   // PubAlice public key of Alice (g^x) mod p
	   BigInteger PubAlice = g.modPow(x, p);
	   
	   
	   // y private key of Bob
	   r3 = new Random();
	   BigInteger y = java.math.BigInteger.probablePrime(bitlength / 2, r3);
	   
	   // PubBob public key of Bob (g^y) mod p
	   BigInteger PubBob = g.modPow(y,  p);
	   
	   // Key agreement
	   // Alice and Bob calculate the keys. They reached the same value
	   // without Bob knowning the value of the secret key of Alice and 
	   // without Alice knowning the value of the secret key of Bob. k = g^(xy)
	   BigInteger chiaveAlice = PubBob.modPow(x,  p);
	   System.out.println("chiave Alice "+ chiaveAlice.toString());
	   
	   BigInteger chiaveBob = PubAlice.modPow(y,  p); // k = g^(xy)
	   System.out.println("Chiave Bob  "+ chiaveBob.toString());
	   
	   if(chiaveAlice.compareTo(chiaveBob) == 0)
	   {
		   System.out.println("Le chiavi sono uguali");	
		   
	   }
	   
	   System.out.println("1024-bit MODP Group");
	   
	   BigInteger bigP = new BigInteger(PStr, 16);
	   System.out.println("p = " + bigP.toString(16)); 
	   
	   BigInteger bigG = new BigInteger(GStr, 16);
	   System.out.println("g = " + bigG.toString(16));
	   
	   BigInteger PubAlice1 = bigG.modPow(x, bigP);
	   	   
	   BigInteger PubBob1 = bigG.modPow(y,  bigP);
	   
	   BigInteger chiaveAlice1 = PubBob1.modPow(x,  bigP);
	   System.out.println("chiave Alice "+ chiaveAlice1.toString());
	   
	   BigInteger chiaveBob1 = PubAlice1.modPow(y,  bigP);
	   System.out.println("Chiave Bob  "+ chiaveBob1.toString());
	   
	   if(chiaveAlice1.compareTo(chiaveBob1) == 0)
	   {
		   System.out.println("Le chiavi mod sono uguali");	
		   
	   }
   
	   
	}

}
