package org.dice_research.cedric.preprocessing;

import org.dice_research.cedric.model.ILabelledEntity;
import org.dice_research.cedric.pipeline.APipe;

import java.util.HashSet;
import java.util.Set;

/**
 * A pipe which collects all occurring relations which pass the pipe
 *
 * @author Cedric Richter
 */
public class RelationCollector extends APipe<ILabelledEntity, ILabelledEntity>{

    private Set<String> relations = new HashSet<>();

    /**
     *
     * @return a set of passed relations
     */
    public Set<String> getRelations(){
        return relations;
    }

    @Override
    public void push(ILabelledEntity obj) {
        relations.add(obj.getPredicate());
        sink.push(obj);
    }
}
