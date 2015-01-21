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

import chromosome.ChromosomeRepresentationInterface;
import params.ClassInitialization;

import java.util.ArrayList;
import java.util.List;

/**
 * KazKiberGetic GA System (kkgGA) The Evaluator implements concurrent fitness
 * evaluation using threads.
 * 
 * @author Oleg Rybkin, kazkibergetic@gmail.com
 * @version 1.0
 * @since 2014-08-31
 */

public class Evaluator implements Runnable {

	// first gene in chromosome to evaluate in current thread
	int beginIndex = 0;

	// last gene in chromosome to evaluate in current thread
	int endIndex = 0;

	private ArrayList<ChromosomeRepresentationInterface> chromosomes = new ArrayList<ChromosomeRepresentationInterface>();
	private ClassInitialization ci = new ClassInitialization();

	public Evaluator(List<ChromosomeRepresentationInterface> ch) {
		chromosomes.addAll(ch);

	}

	/*
	 * (non-Javadoc) Evaluates fitness if genes from beginIndex to endIndex in
	 * provided chromosome
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		for (int i = beginIndex; i < endIndex; i++) {
			double f;
			try {
				f = this.ci.getFitnessEvaluationOperator().evaluateFitness(
						this.chromosomes.get(i));
				this.chromosomes.get(i).setFitness(f);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

}
