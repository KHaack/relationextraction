package org.dice_research.cedric;

import org.dice_research.cedric.bayes.IBayesEstimator;
import org.dice_research.cedric.bayes.TfidfBayesEstimator;
import org.dice_research.cedric.io.IDBConfiguration;
import org.dice_research.cedric.io.IDBLoadingTask;
import org.dice_research.cedric.io.LemmaEntityMapper;
import org.dice_research.cedric.io.MongoLoadingPipe;
import org.dice_research.cedric.io.config.ConfigHandler;
import org.dice_research.cedric.io.config.GsonApplicationConfig;
import org.dice_research.cedric.io.simple.SimpleDBConfiguration;
import org.dice_research.cedric.io.simple.SimpleDBCredentials;
import org.dice_research.cedric.io.simple.SimpleDBLoadingTask;
import org.dice_research.cedric.learn.*;
import org.dice_research.cedric.model.IConfiguration;
import org.dice_research.cedric.model.ILabelledEntity;
import org.dice_research.cedric.model.Relation;
import org.dice_research.cedric.pipeline.IPipe;
import org.dice_research.cedric.pipeline.SingleCachedSink;
import org.dice_research.cedric.preprocessing.string.WriteOutSink;

import com.google.common.base.Stopwatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

/**
 *
 * The extraction tester allows to perform Relation Extraction on real input data.
 * Firstly it trained the model and then a user can type in single sentences.
 * The output will be all found relations in the sentence.
 *
 * It is possible to pass a path to a configuration file as an argument.
 *
 * @author Cedric Richter
 */
public class ExtractionTester {

    public static void main(String[] args){

        String path = "./system_config.json";

        if(args.length > 0){
            path = args[0];
        }

        ConfigHandler.injectCustomConfig(ExtractionTester.class.getSimpleName(), Paths.get(path));
        ConfigHandler handler = ConfigHandler.createCustom(ExtractionTester.class.getSimpleName());
        IConfiguration base = handler.getConfig("learner", new GsonApplicationConfig());

        int window = base.getLexicalWindow();
        int k = base.getNeighbour();

        IBayesEstimator estimator = new TfidfBayesEstimator(base.getBayesSmoothing());

        RelationExtPipeFactory factory = new RelationExtPipeFactory();


        //Train Pipe
        SingleCachedSink<IRelationClassifier> classificatorSink = new SingleCachedSink<>();

        IPipe<ILabelledEntity, IRelationClassifier> trainModelPipe =
                factory.createTrainingPipe(window, estimator,
                        new VotingRelationFactory(
                                new RCDefaultFactory(k, estimator)
                        ));
        trainModelPipe.setSink(classificatorSink);

        IPipe<IDBLoadingTask, ILabelledEntity>  trainLoader = new MongoLoadingPipe(
                new LemmaEntityMapper()
        );
        trainLoader.setSink(trainModelPipe);


        //Setup train data loading
        IDBConfiguration configuration = handler.getConfig("mongo", new SimpleDBConfiguration(
                "ds113738.mlab.com",  13738,
                new SimpleDBCredentials("admin", "admin".toCharArray() ),
                "relationextraction"
        ));

        IDBLoadingTask trainTask =
                new SimpleDBLoadingTask(configuration, "DataSet", null);

        System.out.println("Start training phase: ");

        Stopwatch watch = Stopwatch.createStarted();
        trainLoader.push(trainTask);
        watch.stop();

        System.out.println("Time for Training: "+watch.elapsed(TimeUnit.MILLISECONDS)+" ms");

        IRelationClassifier classificator = classificatorSink.getObj();

        IPipe<String, Relation> processor = factory.createRelationExtractionPipe(classificator, window);
        processor.setSink(new WriteOutSink(System.out));

        BufferedReader br = null;

        try {

            br = new BufferedReader(new InputStreamReader(System.in));

            while (true) {

                System.out.print("Enter something : ");
                String input = br.readLine();

                if ("q".equals(input)) {
                    System.out.println("Exit!");
                    System.exit(0);
                }

                System.out.println("Processing input: "+input);
                processor.push(input);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
