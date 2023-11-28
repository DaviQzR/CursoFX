package View;

import Model.CursoEntity;

import java.util.ArrayList;
import java.util.List;

public class CursoControl {

    private List<CursoEntity> cursos = new ArrayList<>();

    public void adicionarCursoEntity(int id, String nome, int codCurso, String coordenador, int qtdAlunos) {
        CursoEntity novoCurso = new CursoEntity(id, nome, codCurso, coordenador, qtdAlunos);
        cursos.add(novoCurso);
    }

    public CursoEntity pesquisarCursoEntity(int idPesquisa) {
        for (CursoEntity curso : cursos) {
            if (curso.getId() == idPesquisa) {
                return curso;
            }
        }
        return null; // Se não encontrar nenhum curso
    }

    public List<CursoEntity> getCursos() {
        return cursos;
    }

   
}
