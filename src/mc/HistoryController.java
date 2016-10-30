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

import cz.control4j.resources.historian.FileReader;
import cz.control4j.resources.historian.LS;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author jilm
 */
public class HistoryController implements Initializable {

  public ObservableList<String> historyData;

  @FXML TableView<String> historyTV;
  @FXML TableColumn<String, String> historySignalTC;
  @FXML LineChart<Number, Number> historyChart;


  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {

    // load index information
    Set<String> signals = new HashSet<>();
    LS historyListing = new LS();
    for (FileReader reader : historyListing) {
      signals.addAll(Arrays.asList(reader.getLabels()));
    }
    historyData = FXCollections.observableArrayList();
    historyData.addAll(signals);

    // bind data table
    historyTV.setItems(historyData);
    historySignalTC.setCellValueFactory(param -> new SimpleStringProperty(param.getValue()));

    XYChart.Series series = new XYChart.Series();
    series.setName("My portfolio");
    //populating the series with data
    historyChart.getData().add(series);

    historyTV.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        FileReader reader = historyListing.iterator().next();
        String[] labels = reader.getLabels();
        // get index of the signal
        if (newValue != null) {
          series.getData().clear();
          int labelIndex = Arrays.asList(labels).indexOf(newValue);
          float[] buffer = new float[2555];
          try {
            reader.read(buffer, 0, 2555, labelIndex);
            for (int i = 0; i < 2525; i++) {
              if (Float.isFinite(buffer[i]) && buffer[i]<100.0) {
                series.getData().add(new XYChart.Data(i, buffer[i]));
              }
            }
          } catch (IOException ex) {
            Logger.getLogger(HistoryController.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
      }
    });

  }

}
