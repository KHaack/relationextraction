package org.dice_research.cedric.learn.validate.simple;

import org.dice_research.cedric.learn.validate.IClassificationTestResult;
import org.dice_research.cedric.model.ILabelledEntity;
import org.dice_research.cedric.model.ILexicalEntity;

public class SimpleClassificationTestResult implements IClassificationTestResult {

    private ILabelledEntity entity;
    private String prediction;


    public SimpleClassificationTestResult(ILabelledEntity entity, String prediction){
        this.entity = entity;
        this.prediction = prediction;
    }

    @Override
    public ILexicalEntity getEntity() {
        return entity;
    }

    @Override
    public String getPredictedRelation() {
        return prediction;
    }

    @Override
    public String getValidRelation() {
        return entity.getPredicate();
    }
}
