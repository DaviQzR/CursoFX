package View;

import Model.CursoEntity;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CursoBoundary extends Application {

    private CursoControl cursoControl = new CursoControl();

    private TextField txtId = new TextField();
    private TextField txtNome = new TextField();
    private TextField txtCodigo = new TextField();
    private TextField txtCoordenador = new TextField();
    private TextField txtQuantidade = new TextField();

    private Button btnAdicionar = new Button("Adicionar");
    private Button btnPesquisar = new Button("Pesquisar");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CRUD de Cursos");

        Pane root = new Pane();

        criarCamposTexto(root);
        criarBotoes(root);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void criarCamposTexto(Pane root) {
        adicionarLabelETextField("ID:", txtId, 20, 20, 200, 20, root);
        adicionarLabelETextField("Nome:", txtNome, 20, 60, 200, 60, root);
        adicionarLabelETextField("Código do Curso:", txtCodigo, 20, 100, 200, 100, root);
        adicionarLabelETextField("Nome do Coordenador:", txtCoordenador, 20, 140, 200, 140, root);
        adicionarLabelETextField("Quantidade de Alunos:", txtQuantidade, 20, 180, 200, 180, root);
    }

    private void adicionarLabelETextField(String labelText, TextField textField, double labelX, double labelY, double fieldX, double fieldY, Pane root) {
        Label label = new Label(labelText);

        label.setLayoutX(labelX);
        label.setLayoutY(labelY);
        textField.setLayoutX(fieldX);
        textField.setLayoutY(fieldY);

        root.getChildren().addAll(label, textField);
    }

    private void criarBotoes(Pane root) {
        btnAdicionar.setLayoutX(20);
        btnAdicionar.setLayoutY(220);

        btnPesquisar.setLayoutX(200);
        btnPesquisar.setLayoutY(220);

        configurarBotoes();

        root.getChildren().addAll(btnAdicionar, btnPesquisar);
    }

    private void configurarBotoes() {
        btnAdicionar.setOnAction(event -> adicionarCurso());
        btnPesquisar.setOnAction(event -> pesquisarCurso());
    }

    private void adicionarCurso() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String nome = txtNome.getText();
            int codCurso = Integer.parseInt(txtCodigo.getText());
            String coordenador = txtCoordenador.getText();
            int qtdAlunos = Integer.parseInt(txtQuantidade.getText());

            cursoControl.adicionarCursoEntity(id, nome, codCurso, coordenador, qtdAlunos);

            limparCampos();
        } catch (NumberFormatException e) {
            // Trate o erro de conversão de número aqui
            e.printStackTrace();
        }
    }

    private void pesquisarCurso() {
        try {
            int idPesquisa = Integer.parseInt(txtId.getText());
            CursoEntity cursoEncontrado = cursoControl.pesquisarCursoEntity(idPesquisa);

            if (cursoEncontrado != null) {
                atualizarCamposTexto(cursoEncontrado);
            } else {
                limparCampos();
            }
        } catch (NumberFormatException e) {
            // Trate o erro de conversão de número aqui
            e.printStackTrace();
        }
    }

    private void atualizarCamposTexto(CursoEntity curso) {
        txtId.setText(String.valueOf(curso.getId()));
        txtNome.setText(curso.getNome());
        txtCodigo.setText(String.valueOf(curso.getCodCurso()));
        txtCoordenador.setText(curso.getCoordenador());
        txtQuantidade.setText(String.valueOf(curso.getQtdAlunos()));
    }

    private void limparCampos() {
        txtId.clear();
        txtNome.clear();
        txtCodigo.clear();
        txtCoordenador.clear();
        txtQuantidade.clear();
    }
}