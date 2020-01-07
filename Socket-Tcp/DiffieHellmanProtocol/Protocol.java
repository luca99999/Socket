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


import java.io.Serializable;
import java.math.BigInteger;

/*
 *  A Pojo Serializable object 
 * 	for sending encrypted data and parameters
 */

public class Protocol implements Serializable {
	private BigInteger p, g;
	private BigInteger PubKey;
	private int type;
	private byte[] encryptedMessage;
	
	
	public BigInteger getPubKey() {
		return PubKey;
	}
	public void setPubKey(BigInteger pubKey) {
		PubKey = pubKey;
	}
	
	public BigInteger getP() {
		return p;
	}
	public void setP(BigInteger p) {
		this.p = p;
	}
	@Override
	public String toString() {
		return "Protocol [p=" + p + ", g=" + g + ", type=" + type + ", encryptedMessage=" + encryptedMessage + "]";
	}
	public Protocol(BigInteger p, BigInteger g) {
		super();
		this.p = p;
		this.g = g;
	}
	public BigInteger getG() {
		return g;
	}
	public void setG(BigInteger g) {
		this.g = g;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public byte[] getEncryptedMessage() {
		return encryptedMessage;
	}
	public void setEncryptedMessage(byte[] encryptedMessage) {
		this.encryptedMessage = encryptedMessage;
	}
	

}
