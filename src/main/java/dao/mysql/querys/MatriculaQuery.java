package dao.mysql.querys;

public class MatriculaQuery {
    public static String UPDATE = "UPDATE matriculas SET nota=? WHERE alumno=? and asignatura=? and fecha=?";
    public static String DELETE = "DELETE FROM matriculas WHERE alumno=? and asignatura=? and fecha=?";
    public static String INSERT = "INSERT INTO matriculas(alumno, asignatura, fecha, nota) VALUES (?, ?, ?, ?)";
    public static String GETONE = "SELECT * FROM matriculas WHERE alumno=? and asignatura=? and fecha=?";
    public static String GETALL = "SELECT * FROM matriculas";
}
