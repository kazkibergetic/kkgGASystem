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
* The ParametersInitializationException triggers during parameters initialization when some if the parameters are not defined or have invalid values 
*
* @author  Oleg Rybkin, kazkibergetic@gmail.com
* @version 1.0
* @since   2014-08-31 
*/

public class ParametersInitializationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** triggers when some of the parameters are not defined or have invalid values
	 * 
	 */
	public ParametersInitializationException()
	{
		super("Some of the parameters are not defined or have invalid values.");
	}
	
	/** triggers when the parameter specified is not defined or has invalid value. It provides information, which parameter caused the exception.
	 * @param parameterName : name of the parameter, that caused the exception
	 */
	public ParametersInitializationException(String parameterName)
	{
		super("Parameter '" + parameterName + "' is not defined or has an inavlid value.");
	}
	
	/** triggers when the specified parameter has an invalid value (not in range).
	 * @param parameterName : name of the parameter, that caused the exception
	 * @param min : minimum value of the parameter
	 * @param max : maximum  value of the parameter
	 */
	public ParametersInitializationException(String parameterName, int min, int max)
	{
		super("Parameter '" + parameterName + "' has invalid value. The value must be in range between '"+min+"' and '"+max+"'");
	}
}
