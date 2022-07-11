package entities;

import java.util.Objects;

public class Matricula {
    /**
     * Composite key
     * */
    public static class IdMatricula{
        private long alumno;
        private long asignatura;
        private int year;

        public IdMatricula(long alumno, long asignatura, int year) {
            this.alumno = alumno;
            this.asignatura = asignatura;
            this.year = year;
        }

        public long getAlumno() {
            return alumno;
        }

        public void setAlumno(long alumno) {
            this.alumno = alumno;
        }

        public long getAsignatura() {
            return asignatura;
        }

        public void setAsignatura(long asignatura) {
            this.asignatura = asignatura;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        @Override
        public String toString() {
            return "IdMatricula{" +
                    "alumno=" + alumno +
                    ", asignatura=" + asignatura +
                    ", year=" + year +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IdMatricula that = (IdMatricula) o;
            return alumno == that.alumno && asignatura == that.asignatura && year == that.year;
        }

        @Override
        public int hashCode() {
            return Objects.hash(alumno, asignatura, year);
        }
    }

    private IdMatricula idMatricula;
    private Integer nota = null;

    public Matricula(IdMatricula idMatricula) {
        this.idMatricula = idMatricula;
    }

    public Matricula(long alumno, long asignatura, int year) {
        this.idMatricula = new IdMatricula(alumno, asignatura, year);
    }

    public IdMatricula getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(IdMatricula idMatricula) {
        this.idMatricula = idMatricula;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Matrícula{" +
                "idMatricula=" + idMatricula +
                ", nota=" + nota +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matricula matrícula = (Matricula) o;
        return Objects.equals(idMatricula, matrícula.idMatricula) && Objects.equals(nota, matrícula.nota);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMatricula, nota);
    }
}
