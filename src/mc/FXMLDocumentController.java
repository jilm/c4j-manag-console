/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mc;

import java.io.File;
import java.net.URL;
import java.time.Instant;
import java.util.ResourceBundle;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * @author jilm
 */
public class FXMLDocumentController implements Initializable {

  public static final Logger logger = Logger.getLogger("cz.lidinsky");

  @FXML private TableView<LogRecord> logTable;
  @FXML private TableColumn<LogRecord, Instant> logTableTimestampCol;
  @FXML private TableColumn<LogRecord, String> logTableNameCol;
  @FXML private TableColumn<LogRecord, Level> logTableLevelCol;
  @FXML private TableColumn<LogRecord, String> logTableMessageCol;

  @FXML private SignalsController signalsController;
  @FXML private IOController ioController;

  private ObservableList<LogRecord> logData;
  private C4jAppLoader c4jLoader;

  @Override
  public void initialize(URL url, ResourceBundle rb) {

    // Actualize log records

    // log timestamp
    logTableTimestampCol.setCellValueFactory(
            param -> new SimpleObjectProperty(
                    Instant.ofEpochMilli(param.getValue().getMillis()))
    );

    // log name
    logTableNameCol.setCellValueFactory(
            param -> new SimpleStringProperty(param.getValue().getLoggerName())
    );

    // log message
    logTableMessageCol.setCellValueFactory(
            param -> new SimpleStringProperty(
              param.getValue().getMessage())
    );

    // log level
    logTableLevelCol.setCellValueFactory(
            param -> new SimpleObjectProperty(param.getValue().getLevel())
    );

    logData = FXCollections.observableArrayList();
    logTable.setItems(logData);

    Handler logHandler = new Handler() {
      @Override
      public void publish(LogRecord record) {
        Platform.runLater(() -> logData.add(record));
      }

      @Override
      public void flush() {
      }

      @Override
      public void close() throws SecurityException {
      }
    };

    logger.addHandler(logHandler);
    Logger.getLogger("cz.control4j").addHandler(logHandler);
    logData.add(new LogRecord(Level.INFO, "Starting management console."));
    Logger.getLogger("cz.lidinsky").info("Starting the managemen console");

  }

  public void onExit(ActionEvent event) {
    System.exit(0);
  }

  public void onFileOpen(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open C4J application File");
    fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("XML Files", "*.xml"),
            new ExtensionFilter("All Files", "*.*"));
    File selectedFile = fileChooser.showOpenDialog(Mc.mainStage);
    if (selectedFile != null) {
      Logger.getLogger("cz.lidinsky").info("File chosen: "
          + selectedFile.toString());
      c4jLoader = new C4jAppLoader(selectedFile);
      //signalsController.signalsTable.setItems(c4jLoader.signalsProperty());
      //ioController.ioTable.setItems(c4jLoader.iosProperty());
      c4jLoader.setOnFailed(new EventHandler() {
        @Override
        public void handle(Event event) {
          logger.severe("Exception while c4j loading: "
            + c4jLoader.getException().toString());
        }
      });
      c4jLoader.setOnSucceeded(new EventHandler() {
        @Override
        public void handle(Event event) {
          // fill-in signal table
          c4jLoader.getValue().getSignals().keySet().stream()
                  .map(
                          key -> {
                            Signal signal = new Signal();
                            signal.setName(key.getName());
                            //signal.setDeclarationRef(preprocessor.getSignals().get(key.getName(), key.getScope()).getDeclarationReferenceText());
                            return signal;
                          })
                  .forEach(signalsController::add);
          // fill-in io table
          c4jLoader.getValue().getModules().stream()
                  .filter(module -> ioController.isSupported(module))
                  .forEach(ioController::add);

        }

      });
      new Thread(c4jLoader).start();
    }
  }

}
