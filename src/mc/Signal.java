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
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author jilm
 */
public class Signal {

  private final SimpleStringProperty nameProperty;
  private final SimpleStringProperty declarationRefProperty;

  public Signal() {
    nameProperty = new SimpleStringProperty();
    declarationRefProperty = new SimpleStringProperty();
  }

  public void setName(String name) {
    nameProperty.setValue(name);
  }

  public String getName() {
    return nameProperty.getValue();
  }

  public Property<String> nameProperty() {
    return nameProperty;
  }

  public void setDeclarationRef(String declarationRef) {
    declarationRefProperty.setValue(declarationRef);
  }

  public String getDeclarationRef() {
    return declarationRefProperty.getValue();
  }

  public Property<String> declarationRefProperty() {
    return declarationRefProperty;
  }



}
