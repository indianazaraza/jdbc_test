package dao;


import java.sql.SQLException;
import java.util.List;

public interface DAO <T, K>{
    void insert(T a) throws DAOException;
    void update(T a) throws DAOException;
    void delete(T a) throws DAOException, SQLException;
    List<T> getAll() throws DAOException;
    T get(K id) throws DAOException, SQLException;
}
