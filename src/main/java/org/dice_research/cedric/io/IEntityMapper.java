package org.dice_research.cedric.io;

import org.dice_research.cedric.model.ILabelledEntity;
import org.bson.BSONObject;

/**
 *
 * A mapper which can map MongoDB entries to labelled data.
 *
 * @author Cedric Richter
 */
public interface IEntityMapper {

    public ILabelledEntity mapEntity(BSONObject object);

}
