/**
 * Interface that allows objects to be destroyed.
 */

public interface Destroyable {

    /**
     * Destroys object
     */
    public default void destroy(){
    }

    /**
     * Gets destroyed status of object
     * @return boolean describing whether object is destroyed
     */
    public default boolean isDestroyed(){
        return false;
    }
}
