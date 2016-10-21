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

import cz.control4j.Module;
import cz.control4j.modules.papouch.OMTQS3;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author jilm
 */
public class IOController implements Initializable {

  @FXML public TableView<IO> ioTable;
  @FXML private TableColumn<IO, String> ioTableTypeCol;
  @FXML private TextField hostTF;
  @FXML private TextField portTF;
  @FXML private TextField addressTF;
  @FXML private TextField virtualAddressTF;
  @FXML private CheckBox virtualizeCB;

  public ObservableList<IO> ioData;


  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    ioData = FXCollections.observableArrayList();
    ioTable.setItems(ioData);
    ioTableTypeCol.setCellValueFactory(param -> param.getValue().typeProperty());
    virtualizeCB.disableProperty().bind(Bindings.lessThan(ioTable.getSelectionModel().selectedIndexProperty(), 0));
    hostTF.disableProperty().bind(Bindings.lessThan(ioTable.getSelectionModel().selectedIndexProperty(), 0));
    portTF.disableProperty().bind(Bindings.lessThan(ioTable.getSelectionModel().selectedIndexProperty(), 0));
    addressTF.disableProperty().bind(Bindings.lessThan(ioTable.getSelectionModel().selectedIndexProperty(), 0));
    virtualAddressTF.disableProperty().bind(Bindings.lessThan(ioTable.getSelectionModel().selectedIndexProperty(), 0));

    // bind left-side pane controllers
    ioTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IO>() {
      @Override
      public void changed(ObservableValue<? extends IO> observable, IO oldValue, IO newValue) {
        if (oldValue != null) {
          hostTF.textProperty().unbindBidirectional(oldValue.hostProperty());
          portTF.textProperty().unbindBidirectional(oldValue.portProperty());
          addressTF.textProperty().unbindBidirectional(oldValue.addressProperty());
          virtualAddressTF.textProperty().unbindBidirectional(oldValue.virtualAddressProperty());
        }
        if (newValue != null) {
          hostTF.textProperty().bindBidirectional(newValue.hostProperty());
          portTF.textProperty().bindBidirectional(newValue.portProperty(), NumberFormat.getInstance());
          addressTF.textProperty().bindBidirectional(newValue.addressProperty(), NumberFormat.getInstance());
          virtualAddressTF.textProperty().bindBidirectional(newValue.virtualAddressProperty(), NumberFormat.getInstance());
        }
      }

    });

  }

  @FXML
  public void onTFAction(ActionEvent event) {
    if (event.getSource() instanceof TextField) {
      ((TextField)event.getSource()).selectAll();
    }
  }

  @FXML
  public void onVirtualizeCBAction(ActionEvent event) {
    if (virtualizeCB.isArmed()) {
      IO io = ioTable.getSelectionModel().getSelectedItem();
      String host = io.getHost();
      int port = io.getPort();
      int address = io.getAddress();
      int virtualAddress = io.getVirtualAddress();
      try {
        Data.getSpinelDService().getSpinelD().createRule(virtualAddress, address, host, port);
      } catch (UnknownHostException ex) {
        Logger.getLogger(IOController.class.getName()).log(Level.SEVERE, null, ex);
      }
      Logger.getLogger("cz.lidinsky").fine("virtualize");
    }
  }

  public void add(IO io) {
    ioData.add(io);
  }

  public boolean isSupported(Module module) {
    return module instanceof OMTQS3;
  }

  public void add(Module module) {
    IO io = new IO();
    io.setType(module.getClass().getName());
    if (module instanceof OMTQS3) {
      io.setHost(((OMTQS3)module).getHost());
      io.setPort(((OMTQS3)module).getPort());
      io.setAddress(((OMTQS3)module).getAddress());
    }
    add(io);
  }


}
