package org.dice_research.cedric;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

public class Main {
	public static void main(String[] args) {
		Stopwatch watch = Stopwatch.createStarted();
		
		Extractor.setup();
		
		System.out.println("Start training phase: ");
		Extractor.train();
		watch.stop();
		System.out.println("Time for training: " + watch.elapsed(TimeUnit.MILLISECONDS) + " ms");
	
		System.out.println("-----------------------------------------------------------------------------------------------");
		watch.reset();
		watch.start();
		System.out.println(Extractor.extract("Barack Obama and his wife Michelle Obama were born in New York."));
		System.out.println("Time for extraction: " + watch.elapsed(TimeUnit.MILLISECONDS) + " ms");
		watch.stop();
		System.out.println("-----------------------------------------------------------------------------------------------");
		watch.reset();
		watch.start();
		System.out.println(Extractor.extract("Tony Hall leads the BBC."));
		System.out.println("Time for extraction: " + watch.elapsed(TimeUnit.MILLISECONDS) + " ms");
		watch.stop();
		System.out.println("-----------------------------------------------------------------------------------------------");
		watch.reset();
		watch.start();
		System.out.println(Extractor.extract("Michael Jackson, born 29. August 1958 in Gary, Indiana and died 25. June 2009 in Los Angeles, California, was an american artist."));
		System.out.println("Time for extraction: " + watch.elapsed(TimeUnit.MILLISECONDS) + " ms");
		watch.stop();
		System.out.println("-----------------------------------------------------------------------------------------------");
	}
}
