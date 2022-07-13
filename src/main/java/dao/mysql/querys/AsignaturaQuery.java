package dao.mysql.querys;

public class AsignaturaQuery {
    public static String UPDATE = "UPDATE asignaturas SET nombre=?, profesor=? WHERE id_asignatura=?";
    public static String DELETE = "DELETE FROM asignaturas WHERE id_asignatura=?";
    public static String INSERT = "INSERT INTO asignaturas(nombre, profesor) VALUES (?, ?)";
    public static String GETONE = "SELECT * FROM asignaturas WHERE id_asignatura=?";
    public static String GETALL = "SELECT * FROM asignaturas";
}
