package by.bsuir.vt4.hotel.repository;

import by.bsuir.vt4.hotel.entity.Entity;
import by.bsuir.vt4.hotel.exception.RepositoryException;
import by.bsuir.vt4.hotel.specification.Specification;

import java.util.List;

public interface Repository< T extends Entity> {

    boolean add(T entity) throws RepositoryException;
    boolean remove(T entity) throws RepositoryException;
    boolean update(T entity) throws RepositoryException;

    List<T> query(Specification specification) throws RepositoryException;

}
