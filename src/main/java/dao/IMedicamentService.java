package dao;

import java.util.List;

public interface IMedicamentService<T> {
    boolean create(T item); // Retourne false si doublon
    List<T> readAll();
    T readOne(String code);
    boolean update(String code, T newItem);
    boolean delete(String code);
}