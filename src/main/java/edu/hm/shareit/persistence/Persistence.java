package edu.hm.shareit.persistence;

import edu.hm.shareit.models.Book;

import java.io.Serializable;
import java.util.List;

public interface Persistence {

    <T, K extends Serializable> boolean exist(Class<T> tClass, K key);

    <T, K extends Serializable> T get(Class<T> tClass, K key);

    <T> void add(T elem);

    <T> List<T> getAll(Class<T> tClass);

    <T> void update(T elem);
}
