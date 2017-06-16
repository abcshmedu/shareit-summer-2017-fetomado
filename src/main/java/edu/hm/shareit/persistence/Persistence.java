package edu.hm.shareit.persistence;

import java.io.Serializable;
import java.util.List;

/**
 * The interface for our generic persistence layer.
 */
public interface Persistence {

    /**
     * Checks if a object of the given type with a specified key exists.
     * @param tClass the class of the requested object
     * @param key the key to look for
     * @param <T> type of the object
     * @param <K> type of the key
     * @return true if the object exists, false otherwise
     */
    <T, K extends Serializable> boolean exist(Class<T> tClass, K key);

    /**
     * Retrieves a object of the given type with a specified key.
     * @param tClass the class of the requested object
     * @param key the key to look for
     * @param <T> type of the object
     * @param <K> type of the key
     * @return the object, or null if it does not exist
     */
    <T, K extends Serializable> T get(Class<T> tClass, K key);

    /**
     * Adds a new object.
     * @param elem the object to add
     * @param <T> type of the object
     */
    <T> void add(T elem);

    /**
     * Retrieves all objects of a given type.
     * @param tClass the class of the requested objects
     * @param <T> type of the objects
     * @return a list with all objects
     */
    <T> List<T> getAll(Class<T> tClass);

    /**
     * Updates a given object. The object to update is identified by the key in the given object.
     * @param elem the object containing the new data
     * @param <T> type of the object to update
     */
    <T> void update(T elem);
}
