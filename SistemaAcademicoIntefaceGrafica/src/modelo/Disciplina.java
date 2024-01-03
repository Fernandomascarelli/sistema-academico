package modelo;

public class Disciplina implements IDescricao {

    protected Integer idDisciplina;
    protected String nome;
    protected int semestre;
    protected int crgHoraria;
    protected Curso curso;

    public Disciplina() {
        this.curso = new Curso();
    }

    public Disciplina(Integer idDisciplina, String nome, int semestre, int crgHoraria, Curso curso) {
        this.idDisciplina = idDisciplina;
        this.nome = nome;
        this.semestre = semestre;
        this.crgHoraria = crgHoraria;
        this.curso = curso;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Integer getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(Integer idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public int getCrgHoraria() {
        return crgHoraria;
    }

    public void setCrgHoraria(int crgHoraria) {
        this.crgHoraria = crgHoraria;
    }

    @Override
    public String toString() {
        return "Disciplina{" + "idDisciplina=" + idDisciplina + ", nome=" + nome + ", semestre=" + semestre + ", crgHoraria=" + crgHoraria + '}';
    }

    @Override
    public String getDescricao() {
        return nome;
    }

}
