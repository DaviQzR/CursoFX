package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=curso";
    private static final String USUARIO = "Davi";
    private static final String SENHA = "123456";

    public List<CursoEntity> recuperarCursosDoBanco() {
        List<CursoEntity> cursosDoBanco = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USUARIO, SENHA)) {
            String sql = "SELECT * FROM Curso";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String nome = resultSet.getString("nome");
                        int codCurso = resultSet.getInt("codCurso");
                        String coordenador = resultSet.getString("coordenador");
                        int qtdAlunos = resultSet.getInt("qtdAlunos");

                        CursoEntity curso = new CursoEntity(id, nome, codCurso, coordenador, qtdAlunos);
                        cursosDoBanco.add(curso);
                    }
                }
            }
        } catch (SQLException e) {
            // Lidar com a exceção apropriadamente, como lançar uma exceção personalizada
            e.printStackTrace();
        }

        return cursosDoBanco;
    }

    public void adicionarCurso(CursoEntity curso) {
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
            // Lidar com a exceção apropriadamente, como lançar uma exceção personalizada
            e.printStackTrace();
        }
    }
    public CursoEntity buscarCursoNoBanco(int idPesquisa) {
      CursoEntity cursoEncontrado = null;

      try (Connection connection = DriverManager.getConnection(URL, USUARIO, SENHA)) {
          String sql = "SELECT id, nome, codCurso, coordenador, qtdAlunos FROM Curso WHERE id = ?";
          
          try (PreparedStatement statement = connection.prepareStatement(sql)) {
              statement.setInt(1, idPesquisa);

              try (ResultSet resultSet = statement.executeQuery()) {
                  if (resultSet.next()) {
                      int id = resultSet.getInt("id");
                      String nome = resultSet.getString("nome");
                      int codCurso = resultSet.getInt("codCurso");
                      String coordenador = resultSet.getString("coordenador");
                      int qtdAlunos = resultSet.getInt("qtdAlunos");

                      cursoEncontrado = new CursoEntity(id, nome, codCurso, coordenador, qtdAlunos);
                  }
              }
          }
      } catch (SQLException e) {
          e.printStackTrace(); // Lide com a exceção apropriadamente
      }

      return cursoEncontrado;
  }

    // Adicione métodos para atualizar, excluir, etc., conforme necessário
}
