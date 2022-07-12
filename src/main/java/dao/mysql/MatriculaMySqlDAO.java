package dao.mysql;

import dao.DAOException;
import dao.MatriculaDAO;
import dao.mysql.querys.MatriculaQuery;
import entities.Matricula;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatriculaMySqlDAO implements MatriculaDAO {
    final private Connection connection;

    public MatriculaMySqlDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Matricula m) throws DAOException{
        try(var statement = connection.prepareStatement(
                MatriculaQuery.INSERT, new String[]{
                        "alumno", "asignatura", "fecha"})) {
            statement.setLong(1, m.getIdMatricula().getAlumno());
            statement.setLong(2, m.getIdMatricula().getAsignatura());
            statement.setInt(3, m.getIdMatricula().getYear());
            if (m.getNota() != null) statement.setInt(4, m.getNota());
            else statement.setNull(4, Types.INTEGER);
            if (statement.executeUpdate() == 0)
                throw new DAOException("No se ha podido completar la operación");
        } catch (SQLException error) {
            throw new DAOException("Error en SQL", error);
        }
    }

    @Override
    public void update(Matricula m) throws DAOException{
        try(var statement = connection.prepareStatement(MatriculaQuery.UPDATE)) {
            statement.setInt(1, m.getNota());
            statement.setLong(2, m.getIdMatricula().getAlumno());
            statement.setLong(3, m.getIdMatricula().getAsignatura());
            statement.setInt(4, m.getIdMatricula().getYear());
            if (statement.executeUpdate() == 0)
                throw new DAOException("No se ha podido completar la operación");
        } catch (SQLException error) {
            throw new DAOException("Error en SQL", error);
        }
    }

    @Override
    public void delete(Matricula m) throws DAOException{
        try(var statement = connection.prepareStatement(MatriculaQuery.DELETE)) {
            statement.setLong(1, m.getIdMatricula().getAlumno());
            statement.setLong(2, m.getIdMatricula().getAsignatura());
            statement.setInt(3, m.getIdMatricula().getYear());
            if (statement.executeUpdate() == 0)
                throw new DAOException("No se ha podido completar la operación");
        } catch (SQLException error) {
            throw new DAOException("Error en SQL", error);
        }
    }

    private void close(ResultSet resultSet) throws DAOException {
        if (resultSet != null)
            try{
                resultSet.close();
            }catch (SQLException error){
                throw new DAOException("Error en SQL", error);
            }
    }

    private Matricula transform(ResultSet resultSet) throws SQLException {
        var matricula = new Matricula(resultSet.getLong("alumno"),
                resultSet.getLong("asignatura"),
                resultSet.getInt("fecha"));
        Integer nota = resultSet.getInt("nota");
        if (! resultSet.wasNull()) matricula.setNota(nota);
        return matricula;
    }

    @Override
    public List<Matricula> getAll() throws DAOException {
        ResultSet resultSet = null;
        List<Matricula> matriculas = new ArrayList<>();
        try(var statement = connection.prepareStatement(MatriculaQuery.GETALL)){
            resultSet = statement.executeQuery();
            while (resultSet.next()) matriculas.add(transform(resultSet));
        }catch (SQLException error) {
            throw new DAOException("No existe tal matricula", error);
        } finally {
            close(resultSet);
        }
        return matriculas;
    }

    @Override
    public Matricula get(Matricula.IdMatricula id) throws DAOException {
        ResultSet resultSet = null;
        Matricula matricula;
        try(var statement = connection.prepareStatement(MatriculaQuery.GETONE)){
            statement.setLong(1, id.getAlumno());
            statement.setLong(2, id.getAsignatura());
            statement.setInt(3, id.getYear());
            resultSet = statement.executeQuery();
            if (resultSet.next()) matricula = transform(resultSet);
            else throw new DAOException("No se ha encontrado el registro");
        }catch (SQLException error) {
            throw new DAOException("No existe tal matricula", error);
        } finally {
            close(resultSet);
        }
        return matricula;
    }
}
