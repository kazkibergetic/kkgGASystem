/*********************************************************************************
 * Copyright 2014 Oleg Rybkin
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *********************************************************************************/
package exceptions;

/**
* KazKiberGetic GA System (kkgGA)
* The ChromosomeInequalityException triggers when we're trying to perform a genetic operation on the chromosomes, whose length is different . 
*
* @author  Oleg Rybkin, kazkibergetic@gmail.com
* @version 1.0
* @since   2014-08-31 
*/

public class ChromomesInequalityException extends Exception{

	private static final long serialVersionUID = 1L;
	public ChromomesInequalityException(String message) {
        super(message);
    }
	public ChromomesInequalityException() {
        super("Chromosomes, provided to perform genetic operations, have different length");
    }
}
