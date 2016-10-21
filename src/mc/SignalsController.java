/*
 * Copyright (C) 2016 jilm
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package mc;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author jilm
 */
public class SignalsController implements Initializable {

  @FXML private TableView<Signal> signalsTable;
  @FXML private TableColumn<Signal, String> signalsTableNameCol;
  @FXML private Label name;
  @FXML private Label declarationRefLabel;

  private ObservableList<Signal> signalsData;

  /**
   * Initializes the controller class.
   * @param url
   * @param rb
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    signalsData = FXCollections.observableArrayList();
    signalsTable.setItems(signalsData);
    signalsTableNameCol.setCellValueFactory(param -> param.getValue().nameProperty());
    // TODO
    name.textProperty().bind(
    Bindings.createStringBinding(
            () -> signalsTable.getSelectionModel().getSelectedItem() == null
                    ? ""
                    : signalsTable.getSelectionModel().getSelectedItem().getName(),
            signalsTable.getSelectionModel().selectedItemProperty()));
    declarationRefLabel.textProperty().bind(
    Bindings.createStringBinding(
            () -> signalsTable.getSelectionModel().getSelectedItem() == null
                    ? ""
                    : signalsTable.getSelectionModel().getSelectedItem().getDeclarationRef(),
            signalsTable.getSelectionModel().selectedItemProperty()));
  }

  /**
   *
   * @param signal
   */
  public void add(Signal signal) {
    signalsData.add(signal);
  }

}
