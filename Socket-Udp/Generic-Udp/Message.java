/*
 *  Copyright (c) 2019 - Luca Bernardini
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




import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Message {
		int id;
		String message;
		int lenght;
		

		public int getId() {
			return id;
		}

		@XmlAttribute
		public void setId(int id) {
			this.id = id;
		}
		
		public String getMessage() {
			return message;
		}

		@XmlElement
		public void setMessage(String message) {
			this.message = message;
		}

		public int getLenght() {
			return lenght;
		}

		@XmlElement
		public void setLenght(int lenght) {
			this.lenght = lenght;
		}

}
