package com.ariadne.ga;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

public class GeneticAlgorithmCoordinator 
{
	private int[] weights;
	/*
	 * Class to help in solving genetic algorithms for summarization.
	 * While using, create the object, and call start().
	 */
	public GeneticAlgorithmCoordinator(int[] weights) 
	{
		this.weights=weights;
	}
	public void start() throws InvalidConfigurationException
	{
		Configuration conf=new DefaultConfiguration();
        conf.setFitnessFunction(new SummaryFitnessFunction());
        Gene[] featureWeights=new Gene[12];
        for(int i=0;i<12;i++)
        	featureWeights[i]=new IntegerGene(conf,0,15);
        conf.setSampleChromosome(new Chromosome(conf, featureWeights));
        conf.setPopulationSize(100);
	}
}
