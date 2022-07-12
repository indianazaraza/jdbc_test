package dao.mysql;

import dao.AsignaturaDAO;
import dao.DAOException;
import dao.mysql.querys.AsignaturaQuery;
import entities.Asignatura;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AsignaturaMySqlDAO implements AsignaturaDAO {
    final private Connection connection;

    public AsignaturaMySqlDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Asignatura a) throws DAOException {
        ResultSet resultSet = null;
        try(var statement = connection.prepareStatement(
                AsignaturaQuery.INSERT, new String[]{"id_alumno"})) {
            statement.setString(1, a.getNombre());
            statement.setLong(2, a.getIdProfesor());
            if (statement.executeUpdate() == 0)
                throw new DAOException("No se ha podido completar la operación");
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) a.setId(resultSet.getLong(1));
            else throw new DAOException("No puede asignarse el ID a la asignatura");
        } catch (SQLException error) {
            throw new DAOException("Error en SQL", error);
        }finally {
            close(resultSet);
        }
    }

    @Override
    public void update(Asignatura a) throws DAOException {
        try(var statement = connection.prepareStatement(AsignaturaQuery.UPDATE)) {
            statement.setString(1, a.getNombre());
            statement.setLong(2, a.getIdProfesor());
            statement.setLong(3, a.getId());
            if (statement.executeUpdate() == 0)
                throw new DAOException("No se ah podido completar la operación");
        } catch (SQLException error) {
            throw new DAOException("Error en SQL", error);
        }
    }

    @Override
    public void delete(Asignatura a) throws DAOException {
        try(var statement = connection.prepareStatement(AsignaturaQuery.DELETE)) {
            statement.setLong(1, a.getId());
            if (statement.executeUpdate() == 0)
                throw new DAOException("No se ah podido completar la operación");
        } catch (SQLException error) {
            throw new DAOException("Error en SQL", error);
        }
    }

    private Asignatura transform(ResultSet resultSet) throws SQLException {
        var asignatura = new Asignatura(resultSet.getString("nombre"),
                resultSet.getLong("profesor"));
        asignatura.setId(resultSet.getLong("id_asignatura"));
        return asignatura;
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
    public List<Asignatura> getAll() throws DAOException {
        ResultSet resultSet = null;
        List<Asignatura> asignaturas = new ArrayList<>();
        try(var statement = connection.prepareStatement(AsignaturaQuery.GETALL)){
            resultSet = statement.executeQuery();
            while (resultSet.next()) asignaturas.add(transform(resultSet));
        }catch (SQLException error) {
            throw new DAOException("Error en SQL", error);
        } finally {
            close(resultSet);
        }
        return asignaturas;
    }

    @Override
    public Asignatura get(Long id) throws DAOException {
        ResultSet resultSet = null;
        Asignatura asignatura;
        try(var statement = connection.prepareStatement(AsignaturaQuery.GETONE)){
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) asignatura = transform(resultSet);
            else throw new DAOException("No se ha encontrado el registro");
        }catch (SQLException error) {
            throw new DAOException("Error en SQL", error);
        } finally {
            close(resultSet);
        }
        return asignatura;
    }
}
