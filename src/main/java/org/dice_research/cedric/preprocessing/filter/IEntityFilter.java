package org.dice_research.cedric.preprocessing.filter;

import org.dice_research.cedric.model.ILabelledEntity;

/**
 * A filter which allow to mark labelled entities
 *
 * @author Cedric Richter
 */
public interface IEntityFilter {

    public boolean isFiltered(ILabelledEntity e);

}
