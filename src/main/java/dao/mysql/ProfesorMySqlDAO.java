package dao.mysql;

import dao.DAOException;
import dao.ProfesorDAO;
import dao.mysql.querys.ProfesorQuery;
import entities.Profesor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfesorMySqlDAO implements ProfesorDAO {
   final private Connection connection;

    public ProfesorMySqlDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Profesor p) throws DAOException {
       ResultSet resultSet = null;
       try(var statement = connection.prepareStatement(
               ProfesorQuery.INSERT, new String[]{"id_alumno"})) {
           statement.setString(1, p.getNombre());
           statement.setString(2, p.getApellido());
           if (statement.executeUpdate() == 0)
            throw new DAOException("No se ha podido completar la operación");
           resultSet = statement.getGeneratedKeys();
           if (resultSet.next()) p.setId(resultSet.getLong(1));
           else throw new DAOException("No puede asignarse el ID al profesor");
       } catch (SQLException error) {
               throw new DAOException("Error en SQL", error);
       }finally {
           close(resultSet);
       }
    }

    @Override
    public void update(Profesor p) throws DAOException {
        try(var statement = connection.prepareStatement(ProfesorQuery.UPDATE)) {
            statement.setString(1, p.getNombre());
            statement.setString(2, p.getApellido());
            statement.setLong(3, p.getId());
            if (statement.executeUpdate() == 0)
             throw new DAOException("No se ah podido completar la operación");
        } catch (SQLException error) {
            throw new DAOException("Error en SQL", error);
        }
    }

    @Override
    public void delete(Profesor p) throws DAOException {
        try(var statement = connection.prepareStatement(ProfesorQuery.DELETE)) {
            statement.setLong(1, p.getId());
            if (statement.executeUpdate() == 0)
             throw new DAOException("No se ah podido completar la operación");
        } catch (SQLException error) {
            throw new DAOException("Error en SQL", error);
        }
    }

    private Profesor transform(ResultSet resultSet) throws SQLException {
        var profesor = new Profesor(resultSet.getString("nombre"),
                resultSet.getString("apellidos"));
        profesor.setId(resultSet.getLong("id_profesor"));
        return profesor;
   }

    private void close(ResultSet resultSet) throws DAOException {
        if (resultSet != null)
            try{
                resultSet.close();
            }catch (SQLException error){
                throw new DAOException("Error en SQL", error);
            }
    }

    @Override
    public List<Profesor> getAll() throws DAOException {
        ResultSet resultSet = null;
        List<Profesor> profesores = new ArrayList<>();
        try(var statement = connection.prepareStatement(ProfesorQuery.GETALL)){
            resultSet = statement.executeQuery();
            while (resultSet.next()) profesores.add(transform(resultSet));
        }catch (SQLException error) {
            throw new DAOException("Error en SQL", error);
        } finally {
            close(resultSet);
        }
        return profesores;
    }

    @Override
    public Profesor get(Long id) throws DAOException {
        ResultSet resultSet = null;
        Profesor profesor;
        try(var statement = connection.prepareStatement(ProfesorQuery.GETONE)){
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) profesor = transform(resultSet);
            else throw new DAOException("No se ha encontrado el registro");
        }catch (SQLException error) {
            throw new DAOException("Error en SQL", error);
        } finally {
            close(resultSet);
        }
        return profesor;
    }
}
