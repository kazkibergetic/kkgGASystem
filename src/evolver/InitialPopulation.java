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
package evolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import chromosome.ChromosomeRepresentationInterface;
import params.ClassInitialization;

/**
 * @author or13uw
 *
 */
public class InitialPopulation implements Runnable {

	int beginIndex=0;
	int endIndex=0; 
	private ArrayList<ChromosomeRepresentationInterface> chromosomes;
	
	private ClassInitialization ci = new ClassInitialization();
	List l;
	InitialPopulation(ArrayList<ChromosomeRepresentationInterface> ch)
	{
		chromosomes=ch;
		l = Collections.synchronizedList(ch);
	}
	
	public void run() {
		ClassInitialization chromosome = new ClassInitialization();
		System.out.println("FROM "+beginIndex+" TO "+endIndex);
		for (int i = beginIndex; i < endIndex; i++) {
			ChromosomeRepresentationInterface ch = chromosome
					.getChromosomeRepresentation();
			ch.generateChromosome(new Random());
			//System.out.println(ch.getChromosome());
			l.add(ch);
		}
		System.out.println("TOTAL: "+chromosomes.size());
		chromosomes.addAll(new ArrayList<ChromosomeRepresentationInterface>(l));
	}

}
