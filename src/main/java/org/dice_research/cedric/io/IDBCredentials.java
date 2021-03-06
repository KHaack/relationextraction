package org.dice_research.cedric.io;

/**
 * Login credentials for a MongoDB
 *
 * @author Cedric Richter
 */
public interface IDBCredentials {

    public String getName();

    public char[] getPassword();

}
