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

import cz.lidinsky.spinel.SpinelD;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author jilm
 */
public class SpinelDService extends Service {

  private SpinelD spinelD;

  @Override
  protected Task createTask() {

    return new Task() {


    @Override
    protected synchronized Void call() throws Exception {

    try {

      FXMLDocumentController.logger.info("going to run the thread... ");
      updateMessage("thread started...");
      FXMLDocumentController.logger.info("thread started ...");
      spinelD = new SpinelD(12345);
      spinelD.run();
      FXMLDocumentController.logger.info("thread ended ...");
          //Platform.runLater(() -> spinelD.setSelected(false));
    } catch (Exception e) {
      FXMLDocumentController.logger.severe("Exception catched." + e.getMessage());
      throw e;
    }
    return null;
  }

  @Override
    protected void cancelled() {
      if (spinelD != null) spinelD.close();
    }

  };
  }

  public SpinelD getSpinelD() {
    return spinelD;
  }

}