package dao.mysql;

import dao.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlDAOManager implements DAOManager {
    //singleton pattern
    private Connection connection;
    private AlumnoDAO alumnos = null;
    private AsignaturaDAO asignaturas = null;
    private MatriculaDAO matriculas = null;
    private ProfesorDAO profesores = null;

    public MysqlDAOManager(String db, String user, String password) throws SQLException {
        String jdbc = "jdbc:mariadb://localhost/"+db;
        connection = DriverManager.getConnection(jdbc, user, password);
    }

    @Override
    public AlumnoDAO getAlumnoDAO() {
        if (alumnos == null) alumnos = new AlumnoMySqlDAO(connection);
        return alumnos;
    }

    @Override
    public AsignaturaDAO getAsignaturaDAO() {
        if (asignaturas == null) asignaturas = new AsignaturaMySqlDAO(connection);
        return asignaturas;
    }

    @Override
    public MatriculaDAO getMatriculaDAO() {
        if (matriculas == null) matriculas = new MatriculaMySqlDAO(connection);
        return matriculas;
    }

    @Override
    public ProfesorDAO getProfesorDAO() {
        if (profesores == null) profesores = new ProfesorMySqlDAO(connection);
        return profesores;
    }
}
