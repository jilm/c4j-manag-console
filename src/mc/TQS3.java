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

import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author jilm
 */
public class TQS3 extends IO {

  private final SimpleDoubleProperty temperatureProperty;

  public TQS3() {
    temperatureProperty = new SimpleDoubleProperty();
  }

  public Property<Number> temperatureProperty() {
    return temperatureProperty;
  }

  public double getTemperature() {
    return temperatureProperty.getValue();
  }

  public void setTemperature(double value) {
    temperatureProperty.setValue(value);
  }



}
