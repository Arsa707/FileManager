package com.filemanager.filemanager;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.Desktop;
import java.util.ResourceBundle;


public class FileManagerController implements Initializable {

    @FXML
    VBox leftPanel, rightPanel;

    public void menuItemFileExitAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void onClickButtonOpenAction(ActionEvent actionEvent) {
        PanelController leftPC = (PanelController) leftPanel.getProperties().get("ctrl");
        PanelController rightPC = (PanelController) rightPanel.getProperties().get("ctrl");

        if (leftPC.getSelectedFileName() == null && rightPC.getSelectedFileName() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ни один файл не был выбран!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        PanelController srcPC = null, dstPC = null;
        if (leftPC.getSelectedFileName() != null) {
            srcPC = leftPC;
        }
        if (rightPC.getSelectedFileName() != null) {
            srcPC = rightPC;
        }

        Path srcPath = Paths.get(srcPC.getCurrentPath(), srcPC.getSelectedFileName());

        try {
            if (!Desktop.isDesktopSupported()) {
                System.out.println("not supported");
                return;
            }
            Desktop desktop = Desktop.getDesktop();
            desktop.open(srcPath.toFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onClickButtonMoveAction(ActionEvent actionEvent) {
        PanelController leftPC = (PanelController) leftPanel.getProperties().get("ctrl");
        PanelController rightPC = (PanelController) rightPanel.getProperties().get("ctrl");

        if (leftPC.getSelectedFileName() == null && rightPC.getSelectedFileName() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ни один файл не был выбран", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        PanelController srcPC = null, dstPC = null;
        if (leftPC.getSelectedFileName() != null) {
            srcPC = leftPC;
            dstPC = rightPC;
        }
        if (rightPC.getSelectedFileName() != null) {
            srcPC = rightPC;
            dstPC = leftPC;
        }

        Path smallSrcPath = Paths.get(srcPC.getCurrentPath());
        Path srcPath = Paths.get(srcPC.getCurrentPath(), srcPC.getSelectedFileName());
        Path dstPath = Paths.get(dstPC.getCurrentPath()).resolve(srcPath.getFileName().toString());

        if (srcPath.toString().equals(dstPath.toString())) return;
        if (Files.exists(dstPath)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Файл с таким названием уже существует в конечной директории. \nЗаменить его?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Внимание!");
            alert.setHeaderText("");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.NO) return;
        }

        try {
            Files.move(srcPath, dstPath);
            dstPC.updateList(Paths.get(dstPC.getCurrentPath()));
            srcPC.updateList(smallSrcPath);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось переместить файл!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void onClickButtonCopyAction(ActionEvent actionEvent) {
        PanelController leftPC = (PanelController) leftPanel.getProperties().get("ctrl");
        PanelController rightPC = (PanelController) rightPanel.getProperties().get("ctrl");

        if (leftPC.getSelectedFileName() == null && rightPC.getSelectedFileName() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ни один файл не был выбран!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        PanelController srcPC = null, dstPC = null;
        if (leftPC.getSelectedFileName() != null) {
            srcPC = leftPC;
            dstPC = rightPC;
        }
        if (rightPC.getSelectedFileName() != null) {
            srcPC = rightPC;
            dstPC = leftPC;
        }

        Path srcPath = Paths.get(srcPC.getCurrentPath(), srcPC.getSelectedFileName());
        Path dstPath = Paths.get(dstPC.getCurrentPath()).resolve(srcPath.getFileName().toString());

        if (srcPath.toString().equals(dstPath.toString())) return;
        if (Files.exists(dstPath)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Файл с таким названием уже существует в конечной директории! \nЗаменить его?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Внимание!");
            alert.setHeaderText("");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.NO) return;
        }
        try {
            Files.copy(srcPath, dstPath);
            dstPC.updateList(Paths.get(dstPC.getCurrentPath()));

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось скопировать файл!", ButtonType.OK);
            alert.showAndWait();
        }
    }


    public void onClickButtonDeleteAction(ActionEvent actionEvent) {
        PanelController leftPC = (PanelController) leftPanel.getProperties().get("ctrl");
        PanelController rightPC = (PanelController) rightPanel.getProperties().get("ctrl");

        if (leftPC.getSelectedFileName() == null && rightPC.getSelectedFileName() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ни один файл не был выбран!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        PanelController srcPC = null, dstPC = null;
        if (leftPC.getSelectedFileName() != null) {
            srcPC = leftPC;
        }
        if (rightPC.getSelectedFileName() != null) {
            srcPC = rightPC;
        }

        Path smallSrcPath = Path.of(srcPC.getCurrentPath());
        Path srcPath = Paths.get(srcPC.getCurrentPath(), srcPC.getSelectedFileName());

        try {
            Files.delete(srcPath);

            if(rightPC.getCurrentPath().toString().equals(leftPC.getCurrentPath().toString())){
                leftPC.updateList(Path.of(leftPC.getCurrentPath()));
                rightPC.updateList(Path.of(leftPC.getCurrentPath()));
            }
            else srcPC.updateList(smallSrcPath);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось удалить файл!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}