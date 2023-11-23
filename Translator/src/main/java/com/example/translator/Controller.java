package com.example.translator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class Controller {

    @FXML
    private ListView<String> searchListView;

    @FXML
    private TextField searchBar;

    @FXML
    private Label target;

    @FXML
    private TextArea definition;

    @FXML
    private TextArea spelling;

    @FXML
    private TextArea GGAPIText;

    @FXML
    private TextArea GGAPITranslate;

    @FXML
    private Button eraseButton;

    @FXML
    private Button speechButton;

    @FXML
    private Button editButton;

    @FXML
    private Button SaveEditButton;

    String selectedWord = "";
    int selectedWordIndex = 0;

    @FXML
    void editButton() {
        toggleEditState();
    }

    @FXML
    void SaveEditWord(ActionEvent event) {
        saveEditedWord();
    }

    @FXML
    void TranslateAPIButton(ActionEvent event) throws IOException {
        GGAPITranslate.setText(TranslatorAPIwithScripts.translate("", "vi", GGAPIText.getText()));
    }

    @FXML
    void sound(ActionEvent event) {
        Speech.speak(selectedWord);
    }

    @FXML
    void outputs() throws IOException {
        TranslatorGUIManagement.exportDictGUIDataToFile();
        showAlert("Update Success", "Update Success To File ");
    }

    @FXML
    void minus(ActionEvent event) {
        handleEraseAction();
    }

    @FXML
    void plus(ActionEvent event) throws IOException {
        openPlusScene();
    }

    private void toggleEditState() {
        editButton.setDisable(true);
        eraseButton.setDisable(true);

        if (definition.isEditable()) {
            definition.setEditable(false);
            definition.setBlendMode(BlendMode.DARKEN);
            spelling.setEditable(false);
            spelling.setBlendMode(BlendMode.DARKEN);
        } else {
            definition.setEditable(true);
            definition.setBlendMode(BlendMode.SRC_OVER);
            spelling.setEditable(true);
            spelling.setBlendMode(BlendMode.SRC_OVER);
        }
        SaveEditButton.setVisible(true);
    }

    private void saveEditedWord() {
        editButton.setDisable(false);
        SaveEditButton.setVisible(false);
        eraseButton.setDisable(false);

        definition.setEditable(false);
        definition.setBlendMode(BlendMode.DARKEN);
        spelling.setEditable(false);
        spelling.setBlendMode(BlendMode.DARKEN);

        Word word = new Word(selectedWord, spelling.getText(), definition.getText());
        if (TranslatorGUIManagement.editWord(word)) {
            showAlert("SUCCESS", "Edit success : " + selectedWord + "\nPlease press UPDATE to save your change");
        }
    }

    private void handleEraseAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Erase Word");
        alert.setHeaderText("Do you want to erase this word ?");
        alert.setContentText(target.getText());
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yesButton) {
            int previousSearchListViewIndex = searchListView.getSelectionModel().getSelectedIndex();
            TranslatorGUIManagement.removeWord(selectedWordIndex);
            updateListView();
            searchListView.getSelectionModel().select(previousSearchListViewIndex);
            if (searchListView.getItems().isEmpty()) {
                clearFields();
            }
        } else if (result.get() == noButton) {
            ActionEvent event = null;
            event.consume();
        }
    }

    private void openPlusScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("plusScene.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.showAndWait();
        updateListView();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(content);
        alert.showAndWait();
    }

    private void updateListView() {
        searchListView.getItems().clear();
        createListView(searchList(searchBar.getText(), TranslatorGUI.dict));
    }

    private ArrayList<String> searchList(String searchWords, ArrayList<Word> dict) {
        ArrayList<String> searchResultArray = new ArrayList<>();
        for (Word word : dict) {
            if (word.getWord_target().startsWith(searchWords)) {
                searchResultArray.add(word.getWord_target());
            }
        }
        return searchResultArray;
    }

    private void createListView(ArrayList<String> dict) {
        searchListView.getItems().addAll(dict);
    }

    private void clearFields() {
        target.setText("");
        definition.clear();
        spelling.clear();
        disableButtonAndTextArea();
    }

    private void disableButtonAndTextArea() {
        eraseButton.setDisable(true);
        speechButton.setDisable(true);
        editButton.setDisable(true);

        definition.setEditable(false);
        definition.setBlendMode(BlendMode.DARKEN);
        spelling.setEditable(false);
        spelling.setBlendMode(BlendMode.DARKEN);
    }

    private void resetButtonAndTextAreaState() {
        eraseButton.setDisable(false);
        speechButton.setDisable(false);
        editButton.setDisable(false);

        SaveEditButton.setVisible(false);

        definition.setEditable(false);
        definition.setBlendMode(BlendMode.DARKEN);
        spelling.setEditable(false);
        spelling.setBlendMode(BlendMode.DARKEN);
    }

    @FXML
    void initialize() throws IOException {
        TranslatorGUIManagement.insertDictGUIDataFromFile();
        for (Word word : TranslatorGUI.dict) {
            searchListView.getItems().add(word.getWord_target());
        }

        searchBar.textProperty().addListener((observableValue, s, t1) -> updateListView());

        searchListView.getSelectionModel().selectedItemProperty()
                .addListener((observableValue, s, t1) -> {
                    if (t1 != null) {
                        selectedWord = searchListView.getSelectionModel().getSelectedItem();
                        selectedWordIndex = TranslatorGUIManagement.DictionaryGUILookup(selectedWord);

                        target.setText(TranslatorGUI.dict.get(selectedWordIndex).getWord_target());
                        spelling.setText(TranslatorGUI.dict.get(selectedWordIndex).getWord_spelling());
                        definition.setText(TranslatorGUI.dict.get(selectedWordIndex).getWord_explain());
                        resetButtonAndTextAreaState();

                        if (selectedWordIndex == 0) {
                            disableButtonAndTextArea();
                        }
                    }
                });

        searchListView.getSelectionModel().select(0);
    }

    @FXML
    void openFillInBlankGame() throws IOException {

        // Tạo FXMLLoader và Parent từ fillInBlankGame.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fillInBlankGame.fxml"));
        Parent root = loader.load();

        // Lấy controller từ FXMLLoader và khởi tạo thông tin câu hỏi và đáp án
        FillInBlankGameController gameController = loader.getController();
        gameController.initialize();

        // Tạo cửa sổ mới cho minigame
        Stage gameStage = new Stage();
        gameStage.setScene(new Scene(root));
        gameStage.setWidth(600); // Đặt chiều rộng
        gameStage.setHeight(400); // Đặt chiều cao
        gameStage.setTitle("Fill in the Blank Game");
        gameStage.show();
    }

}
