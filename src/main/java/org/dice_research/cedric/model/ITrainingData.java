package org.dice_research.cedric.model;

import org.dice_research.cedric.bayes.ICategory;
import com.google.common.collect.ImmutableSet;

/**
 * Training data which can be used to train a relation classifier
 *
 * @author Cedric Richter
 */
public interface ITrainingData {

    /**
     *
     * @return The category of this sample
     */
    public ICategory getCategory();

    /**
     *
     * @return a set of lexical features which are used for training
     */
    public ImmutableSet<ILexicalFeature> getFeatures();

}
