package Model;

public class CursoEntity {

    private int id;
    private String nome;
    private int codCurso;
    private String coordenador;
    private int qtdAlunos;

    public CursoEntity(int id, String nome, int codCurso, String coordenador, int qtdAlunos) {
        this.id = id;
        this.nome = nome;
        this.codCurso = codCurso;
        this.coordenador = coordenador;
        this.qtdAlunos = qtdAlunos;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getCodCurso() {
        return codCurso;
    }

    public String getCoordenador() {
        return coordenador;
    }

    public int getQtdAlunos() {
        return qtdAlunos;
    }
}
