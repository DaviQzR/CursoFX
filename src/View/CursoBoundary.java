package View;

import Model.CursoEntity;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class CursoBoundary extends Application {

    private CursoControl cursoControl = new CursoControl();

    private IntegerProperty txtId = new SimpleIntegerProperty();
    private StringProperty txtNome = new SimpleStringProperty();
    private IntegerProperty txtCodigo = new SimpleIntegerProperty();
    private StringProperty txtCoordenador = new SimpleStringProperty();
    private IntegerProperty txtQuantidade = new SimpleIntegerProperty();

    private Button btnAdicionar, btnPesquisar;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CRUD de Cursos");

        Pane root = new Pane();

        CamposTexto(root);
        criarBotoes(root);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void CamposTexto(Pane root) {
        LabelETextField("ID:", txtId, 20, 20, 200, 20, root);
        LabelETextField("Nome:", txtNome, 20, 60, 200, 60, root);
        LabelETextField("Código do Curso:", txtCodigo, 20, 100, 200, 100, root);
        LabelETextField("Nome do Coordenador:", txtCoordenador, 20, 140, 200, 140, root);
        LabelETextField("Quantidade de Alunos:", txtQuantidade, 20, 180, 200, 180, root);
    }

    private void LabelETextField(String labelText, Property<?> property, double labelX, double labelY, double fieldX, double fieldY, Pane root) {
      Label label = new Label(labelText);
      TextField textField = new TextField();
  
      // Vincula a propriedade ao campo de texto
      if (property instanceof IntegerProperty) {
          textField.textProperty().bindBidirectional((IntegerProperty) property, new NumberStringConverter());
      } else if (property instanceof StringProperty) {
          textField.textProperty().bindBidirectional((StringProperty) property);
      }
  
      label.setLayoutX(labelX);
      label.setLayoutY(labelY);
      textField.setLayoutX(fieldX);
      textField.setLayoutY(fieldY);
  
      // Adicionando o TextField ao root (Pane)
      root.getChildren().addAll(label, textField);
  }
  


    private void criarBotoes(Pane root) {
        btnAdicionar = new Button("Adicionar");
        btnPesquisar = new Button("Pesquisar");
        configurarBotoes();

        root.getChildren().addAll(btnAdicionar, btnPesquisar);
    }

    private void configurarBotoes() {
        btnAdicionar.setLayoutX(20);
        btnAdicionar.setLayoutY(220);

        btnPesquisar.setLayoutX(200);
        btnPesquisar.setLayoutY(220);

        btnAdicionar.setOnAction(event -> adicionarCurso());
        btnPesquisar.setOnAction(event -> pesquisarCurso());
    }

    private void adicionarCurso() {
        int id = txtId.get();
        String nome = txtNome.get();
        int codCurso = txtCodigo.get();
        String coordenador = txtCoordenador.get();
        int qtdAlunos = txtQuantidade.get();

        cursoControl.adicionarCursoEntity(id, nome, codCurso, coordenador, qtdAlunos);

        limparCampos();
    }

    private void pesquisarCurso() {
        int idPesquisa = txtId.get();
        CursoEntity cursoEncontrado = cursoControl.pesquisarCursoEntity(idPesquisa);

        if (cursoEncontrado != null) {
            atualizarCamposTexto(cursoEncontrado);
        } else {
            limparCampos();
        }
    }

    private void atualizarCamposTexto(CursoEntity curso) {
        txtId.set(curso.getId());
        txtNome.set(curso.getNome());
        txtCodigo.set(curso.getCodCurso());
        txtCoordenador.set(curso.getCoordenador());
        txtQuantidade.set(curso.getQtdAlunos());
    }

    private void limparCampos() {
        txtId.set(0);
        txtNome.set("");
        txtCodigo.set(0);
        txtCoordenador.set("");
        txtQuantidade.set(0);
    }
}
