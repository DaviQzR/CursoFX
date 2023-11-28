package View;

import Model.CursoEntity;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;

public class CursoBoundary extends Application {

    private CursoControl cursoControl = new CursoControl();

    private TextField txtId = new TextField();
    private TextField txtNome = new TextField();
    private TextField txtCodigo = new TextField();
    private TextField txtCoordenador = new TextField();
    private TextField txtQuantidade = new TextField();

    private Button btnAdicionar = new Button("Adicionar");
    private Button btnPesquisar = new Button("Pesquisar");
    private Button btnAtualizar = new Button("Atualizar");
    private Button btnExcluir = new Button("Excluir");
    private Button btnLimpar = new Button("Limpar Seleção");

    private TableView<CursoEntity> tabelaCursos = new TableView<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CRUD de Cursos");

        VBox root = new VBox(10);

        criarCamposTexto(root);
        criarBotoes(root);
        criarTabela(root);

        Scene scene = new Scene(root, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void criarCamposTexto(VBox root) {
        adicionarLabelETextField("ID:", txtId, root);
        adicionarLabelETextField("Nome:", txtNome, root);
        adicionarLabelETextField("Código do Curso:", txtCodigo, root);
        adicionarLabelETextField("Nome do Coordenador:", txtCoordenador, root);
        adicionarLabelETextField("Quantidade de Alunos:", txtQuantidade, root);
    }

    private void adicionarLabelETextField(String labelText, TextField textField, VBox root) {
        HBox hbox = new HBox(10);
        Label label = new Label(labelText);
        hbox.getChildren().addAll(label, textField);
        root.getChildren().add(hbox);
    }

    private void criarBotoes(VBox root) {
        HBox hbox = new HBox(10);

        btnAdicionar.setOnAction(event -> adicionarCurso());
        btnPesquisar.setOnAction(event -> pesquisarCurso());
        btnAtualizar.setOnAction(event -> atualizarCurso());
        btnExcluir.setOnAction(event -> excluirCurso());
        btnLimpar.setOnAction(event -> limparSelecao());

        hbox.getChildren().addAll(btnAdicionar, btnPesquisar, btnAtualizar, btnExcluir, btnLimpar);
        root.getChildren().add(hbox);
    }

    private void criarTabela(VBox root) {
        TableColumn<CursoEntity, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<CursoEntity, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<CursoEntity, Integer> colCodigo = new TableColumn<>("Código do Curso");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codCurso"));

        TableColumn<CursoEntity, String> colCoordenador = new TableColumn<>("Coordenador");
        colCoordenador.setCellValueFactory(new PropertyValueFactory<>("coordenador"));

        TableColumn<CursoEntity, Integer> colQuantidade = new TableColumn<>("Quantidade de Alunos");
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("qtdAlunos"));

        tabelaCursos.getColumns().addAll(colId, colNome, colCodigo, colCoordenador, colQuantidade);
        tabelaCursos.setItems(FXCollections.observableArrayList(cursoControl.getCursos()));

        tabelaCursos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                preencherCampos(newSelection);
            }
        });

        root.getChildren().add(tabelaCursos);
    }

    private void preencherCampos(CursoEntity curso) {
        txtId.setText(String.valueOf(curso.getId()));
        txtNome.setText(curso.getNome());
        txtCodigo.setText(String.valueOf(curso.getCodCurso()));
        txtCoordenador.setText(curso.getCoordenador());
        txtQuantidade.setText(String.valueOf(curso.getQtdAlunos()));
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
            e.printStackTrace();
        }
    }

    private void pesquisarCurso() {
        try {
            int idPesquisa = Integer.parseInt(txtId.getText());
            CursoEntity cursoEncontrado = cursoControl.pesquisarCursoEntity(idPesquisa);

            if (cursoEncontrado != null) {
                preencherCampos(cursoEncontrado);
            } else {
                limparCampos();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void atualizarCurso() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String nome = txtNome.getText();
            int codCurso = Integer.parseInt(txtCodigo.getText());
            String coordenador = txtCoordenador.getText();
            int qtdAlunos = Integer.parseInt(txtQuantidade.getText());

            CursoEntity cursoAtualizado = new CursoEntity(id, nome, codCurso, coordenador, qtdAlunos);
            cursoControl.atualizarCurso(cursoAtualizado);

            limparCampos();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void excluirCurso() {
        try {
            int id = Integer.parseInt(txtId.getText());
            cursoControl.excluirCurso(id);

            limparCampos();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void limparSelecao() {
        tabelaCursos.getSelectionModel().clearSelection();
        limparCampos();
    }

    private void limparCampos() {
        txtId.clear();
        txtNome.clear();
        txtCodigo.clear();
        txtCoordenador.clear();
        txtQuantidade.clear();
    }
}
