package View;

import Model.CursoEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoControl {

    private List<CursoEntity> cursos = new ArrayList<>();

    // Ajustando as configurações do  banco de dados
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

    public void atualizarCurso(CursoEntity cursoAtualizado) {
        // Atualizar na lista local
        cursos.removeIf(c -> c.getId() == cursoAtualizado.getId());
        cursos.add(cursoAtualizado);

        // Atualizar no banco de dados
        atualizarCursoNoBanco(cursoAtualizado);
    }

    public void excluirCurso(int id) {
        // Excluir da lista local
        cursos.removeIf(c -> c.getId() == id);

        // Excluir do banco de dados
        excluirCursoNoBanco(id);
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
            e.printStackTrace(); 
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
            e.printStackTrace(); 
        }
    }

    private void atualizarCursoNoBanco(CursoEntity curso) {
        // Lógica para atualizar curso no banco de dados
        try (Connection connection = DriverManager.getConnection(URL, USUARIO, SENHA)) {
            String sql = "UPDATE Curso SET nome=?, codCurso=?, coordenador=?, qtdAlunos=? WHERE id=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, curso.getNome());
                statement.setInt(2, curso.getCodCurso());
                statement.setString(3, curso.getCoordenador());
                statement.setInt(4, curso.getQtdAlunos());
                statement.setInt(5, curso.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    private void excluirCursoNoBanco(int id) {
        // Lógica para excluir curso do banco de dados
        try (Connection connection = DriverManager.getConnection(URL, USUARIO, SENHA)) {
            String sql = "DELETE FROM Curso WHERE id=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    private CursoEntity buscarCursoNoBanco(int idPesquisa) {
        
        return null;
    }
}
