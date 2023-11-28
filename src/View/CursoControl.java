package View;

import Model.CursoEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoControl {

    private List<CursoEntity> cursos = new ArrayList<>();

    // Ajuste as configurações do seu banco de dados
    private static final String USUARIO = "Davi";
    private static final String SENHA = "123456";
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=curso;user=" + USUARIO + ";password=" + SENHA + ";encrypt=false;";


    public CursoControl() {
        // Carregar cursos do banco de dados na inicialização, se necessário
        carregarCursosDoBanco();
    }

    public void adicionarCursoEntity(int id, String nome, int codCurso, String coordenador, int qtdAlunos) {
        CursoEntity novoCurso = new CursoEntity(id, nome, codCurso, coordenador, qtdAlunos);
        cursos.add(novoCurso);

        // Adicionar ao banco de dados
        adicionarCursoNoBanco(novoCurso);
    }

    public CursoEntity pesquisarCursoEntity(int idPesquisa) {
        // Buscar no banco de dados se não encontrar na lista local
        CursoEntity curso = getCursos().stream()
                .filter(c -> c.getId() == idPesquisa)
                .findFirst()
                .orElseGet(() -> buscarCursoNoBanco(idPesquisa));

        return curso;
    }

    public List<CursoEntity> getCursos() {
        return cursos;
    }

    private void carregarCursosDoBanco() {
      // Lógica para carregar cursos do banco de dados para a lista local
      try (Connection connection = DriverManager.getConnection(URL, USUARIO, SENHA)) {
          String sql = "SELECT id, nome, codCurso, coordenador, qtdAlunos FROM Curso";
          try (PreparedStatement statement = connection.prepareStatement(sql)) {
              try (ResultSet resultSet = statement.executeQuery()) {
                  while (resultSet.next()) {
                      int id = resultSet.getInt("id");
                      String nome = resultSet.getString("nome");
                      int codCurso = resultSet.getInt("codCurso");
                      String coordenador = resultSet.getString("coordenador");
                      int qtdAlunos = resultSet.getInt("qtdAlunos");

                      CursoEntity curso = new CursoEntity(id, nome, codCurso, coordenador, qtdAlunos);
                      cursos.add(curso);
                  }
              }
          }
      } catch (SQLException e) {
          e.printStackTrace(); // Lide com a exceção apropriadamente
      }
  }

    private void adicionarCursoNoBanco(CursoEntity curso) {
        // Lógica para adicionar curso ao banco de dados
        try (Connection connection = DriverManager.getConnection(URL, USUARIO, SENHA)) {
            String sql = "INSERT INTO Curso (id, nome, codCurso, coordenador, qtdAlunos) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, curso.getId());
                statement.setString(2, curso.getNome());
                statement.setInt(3, curso.getCodCurso());
                statement.setString(4, curso.getCoordenador());
                statement.setInt(5, curso.getQtdAlunos());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Lide com a exceção apropriadamente
        }
    }

    private CursoEntity buscarCursoNoBanco(int idPesquisa) {
        // Lógica para buscar curso no banco de dados
        // (Implemente de acordo com a sua necessidade)
        return null;
    }
}
