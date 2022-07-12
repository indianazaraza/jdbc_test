package dao.mysql;

import dao.AlumnoDAO;
import dao.DAOException;
import dao.mysql.querys.AlumnoQuery;
import entities.Alumno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoMySqlDAO implements AlumnoDAO {
    final private Connection connection;

    public  AlumnoMySqlDAO(Connection connection){
        this.connection = connection;
    }

    @Override
    public void insert(Alumno a) throws DAOException {
        ResultSet resultSet = null;
        //have to specify the column to return ids,
        //otherwise raise an exception cannot RETURN_GENERATED_KEYS
        try(var statement = connection.prepareStatement(AlumnoQuery.INSERT, new String[]{"id_alumno"})) {
            statement.setString(1, a.getNombre());
            statement.setString(2, a.getApellido());
            statement.setDate(3, Date.valueOf(a.getFechaNacimiento()));
            if (statement.executeUpdate() == 0)
                throw new DAOException("No se ha podido completar la operación");
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) a.setId(resultSet.getLong(1));
            else throw new DAOException("No puede asignarse el ID al alumno");
        } catch (SQLException error) {
            throw new DAOException("Error en SQL", error);
        }finally {
            close(resultSet);
        }
    }

    @Override
    public void update(Alumno a) throws DAOException {
        try(var statement = connection.prepareStatement(AlumnoQuery.UPDATE)) {
            statement.setString(1, a.getNombre());
            statement.setString(2, a.getApellido());
            statement.setDate(3, Date.valueOf(a.getFechaNacimiento()));
            statement.setLong(4, a.getId());
            if (statement.executeUpdate() == 0)
                throw new DAOException("No se ah podido completar la operación");
        } catch (SQLException error) {
            throw new DAOException("Error en SQL", error);
        }
    }

    @Override
    public void delete(Alumno a) throws DAOException {
        try(var statement = connection.prepareStatement(AlumnoQuery.DELETE)) {
            statement.setLong(1, a.getId());
            if (statement.executeUpdate() == 0)
                throw new DAOException("No se ah podido completar la operación");
        } catch (SQLException error) {
            throw new DAOException("Error en SQL", error);
        }
    }

    private Alumno transform(ResultSet resultSet) throws SQLException {
        var alumno = new Alumno(resultSet.getString("nombre"),
                resultSet.getString("apellido"),
                resultSet.getDate("fecha_nac").toLocalDate());
        alumno.setId(resultSet.getLong("id_alumno"));
        return alumno;
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
    public List<Alumno> getAll() throws DAOException {
        ResultSet resultSet = null;
        List<Alumno> alumnos = new ArrayList<>();
        try(var statement = connection.prepareStatement(AlumnoQuery.GETALL)){
            resultSet = statement.executeQuery();
            while (resultSet.next()) alumnos.add(transform(resultSet));
            //in case the list is empty cannot raise an exception
        }catch (SQLException error) {
            throw new DAOException("Error en SQL", error);
        } finally {
            close(resultSet);
        }
        return alumnos;
    }

    @Override
    public Alumno get(Long id) throws DAOException {
        ResultSet resultSet = null;
        Alumno alumno;
        try(var statement = connection.prepareStatement(AlumnoQuery.GETONE)){
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) alumno = transform(resultSet);
            else throw new DAOException("No se ha encontrado el registro");
        }catch (SQLException error) {
            throw new DAOException("Error en SQL", error);
        } finally {
            close(resultSet);
        }
        return alumno;
    }
}
