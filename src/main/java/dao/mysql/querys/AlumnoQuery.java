package dao.mysql.querys;

public class AlumnoQuery {
    public static String UPDATE = "UPDATE alumnos SET nombre=?, apellido=?, fecha_nac=? WHERE id_alumno=?";
    public static String DELETE = "DELETE FROM alumnos WHERE id_alumno=?";
    public static String INSERT = "INSERT INTO alumnos(nombre, apellido, fecha_nac) VALUES (?, ?, ?)";
    public static String GETONE = "SELECT * FROM alumnos WHERE id_alumno=?";
    public static String GETALL = "SELECT * FROM alumnos";
}
