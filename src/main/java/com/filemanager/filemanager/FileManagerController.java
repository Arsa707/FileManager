package com.filemanager.filemanager;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FileManagerController implements Initializable {


    @FXML
    TableView filesTable;

    public void menuItemFileExitAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void onClickButtonOpenAction(ActionEvent actionEvent) {
    }

    public void onClickButtonMoveAction(ActionEvent actionEvent) {
    }

    public void onClickButtonCopyAction(ActionEvent actionEvent) {
    }

    public void onClickButtonPastAction(ActionEvent actionEvent) {
    }

    public void onClickButtonCutAction(ActionEvent actionEvent) {
    }

    public void onClickButtonDeleteAction(ActionEvent actionEvent) {
    }

    public void onClickButtonRenameAction(ActionEvent actionEvent) {
    }

    public void onClickButtonUpAction(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // File name
        TableColumn<FileInfo, String> fileNameColumn = new TableColumn<>("Имя");
        fileNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFileName()));
        fileNameColumn.setPrefWidth(300);



        // Type name
        TableColumn<FileInfo, String> fileTypeColumn = new TableColumn<>("Тип");
        fileTypeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getType().getName()));
        fileTypeColumn.setPrefWidth(100);



        // Size name
        TableColumn<FileInfo, Long> fileSizeColumn = new TableColumn<>("Размер");
        fileSizeColumn.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getSize()));
        fileSizeColumn.setPrefWidth(200);
        fileSizeColumn.setCellFactory(column ->{
            return new TableCell<FileInfo, Long>(){
                @Override
                protected void updateItem(Long item, boolean empty){
                    super.updateItem(item, empty);
                    if(item == null || empty) {
                        setText("");
                        setStyle("");
                    } else {
                        String text = String.format("%,d bytes", item);
                        if(item == -1L){
                            setText("[DIR]");
                        }
                        setText(text);
                    }
                }
            };
        });

        // Last modified
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        TableColumn<FileInfo, String> fileDataColumn = new TableColumn<>("Дата изменения");
        fileDataColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLastModified().format(dtf)));
        fileDataColumn.setPrefWidth(200);

        filesTable.getColumns().addAll(fileNameColumn,fileDataColumn,fileSizeColumn,fileTypeColumn);


        filesTable.getSortOrder().add(fileTypeColumn);
        Path path = Paths.get("U:");
        updateList(path);
    }


    public void updateList(Path path){

      try {
          filesTable.getItems().clear();
          filesTable.getItems().addAll(Files.list(path).map(FileInfo::new).collect(Collectors.toList()));
          filesTable.sort();
      }
      catch (Exception e){
          new RuntimeException(e);
      }
    }
}