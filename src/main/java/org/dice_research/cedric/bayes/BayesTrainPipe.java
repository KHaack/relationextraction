package org.dice_research.cedric.bayes;

import org.dice_research.cedric.model.ILabelledEntity;
import org.dice_research.cedric.pipeline.APipe;

/**
 * A pipe to train a bayes estimator while processing training data.
 *
 * @author Cedric Richter
 */
public class BayesTrainPipe extends APipe<ILabelledEntity, ILabelledEntity> {

    private IBayesEstimator estimator;

    /**
     *
     * @param estimator the estimator which should be trained in the processing
     */
    public BayesTrainPipe(IBayesEstimator estimator) {
        this.estimator = estimator;
    }

    @Override
    public void push(ILabelledEntity obj) {
        estimator.train(obj);
        sink.push(obj);
    }

}
