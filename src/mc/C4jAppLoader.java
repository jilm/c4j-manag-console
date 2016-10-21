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

import cz.control4j.application.Preprocessor;
import cz.control4j.application.ScopeHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import javafx.concurrent.Task;

/**
 *
 * @author jilm
 */
public class C4jAppLoader extends Task<Preprocessor> {

  private File file;

  //private final ObservableList<Signal> signals = FXCollections.observableArrayList();
  //private final ObservableList<IO> ios = FXCollections.observableArrayList();

  /**
   * @param file
   *            a file to load
   */
  public C4jAppLoader(File file) {
    this.file = file;
  }

  //public ObservableList<Signal> signalsProperty() {
    //return signals;
  //}

  //public ObservableList<IO> iosProperty() {
  //  return ios;
  //}

  @Override
  protected Preprocessor call() throws Exception {
    Logger.getLogger("cz.lidinsky").info("Going to load c4j application.");
    cz.control4j.application.c4j.C4j2Control translator
        = new cz.control4j.application.c4j.C4j2Control();
    cz.control4j.application.c4j.XMLHandler c4jHandler
        = new cz.control4j.application.c4j.XMLHandler(new ScopeHandler());
    c4jHandler.setDestination(translator);
    cz.lidinsky.tools.xml.XMLReader reader
        = new cz.lidinsky.tools.xml.XMLReader();
    reader.addHandler(c4jHandler);
    reader.load(file);
    // translate application
    //applicationLogger.info("Going to translate the application...");
    Preprocessor preprocessor = new Preprocessor();
    translator.process(preprocessor);
    // preprocess application
    //applicationLogger.info("Going to preprocess the application...");
    preprocessor.process();
    Logger.getLogger("cz.lidinsky")
            .log(Level.INFO, String.format("The c4j application has been loaded, number of modules: %d, number of signals: %d", preprocessor.getModules().size(), preprocessor.getSignals().values().size()));
    // fill-in signal table
//    preprocessor.getSignals().keySet().stream()
//            .map(
//                    key -> {Signal signal = new Signal();
//                    signal.setName(key.getName());
//                    //signal.setDeclarationRef(preprocessor.getSignals().get(key.getName(), key.getScope()).getDeclarationReferenceText());
//                    return signal;})
//            .forEach(signals::add);
//    // fill-in io table
//    preprocessor.getModules().stream()
//            .map(
//                    module -> {
//                      IO io = new IO();
//                      io.setType(module.getClass().getName());
//                      return io;
//                    }
//            ).forEach(ios::add);

    return preprocessor;
  }

}
