package dao.mysql.querys;

public class ProfesorQuery {
    public static String UPDATE = "UPDATE profesores SET nombre=?, apellidos=? WHERE id_profesor=?";
    public static String DELETE = "DELETE FROM profesores WHERE id_profesor=?";
    public static String INSERT = "INSERT INTO profesores(nombre, apellidos) VALUES (?, ?)";
    public static String GETONE = "SELECT * FROM profesores WHERE id_profesor=?";
    public static String GETALL = "SELECT * FROM profesores";
}
