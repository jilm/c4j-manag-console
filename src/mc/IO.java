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

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author jilm
 */
public class IO {

  private final SimpleStringProperty typeProperty;
  private final SimpleStringProperty hostProperty;
  private final SimpleIntegerProperty portProperty;
  private final SimpleIntegerProperty addressProperty;
  private final SimpleIntegerProperty virtualAddressProperty;


  public IO() {
    typeProperty = new SimpleStringProperty();
    hostProperty = new SimpleStringProperty("localhost");
    portProperty = new SimpleIntegerProperty(10001);
    addressProperty = new SimpleIntegerProperty();
    virtualAddressProperty = new SimpleIntegerProperty();
  }

  public String getType() {
    return typeProperty.getValue();
  }

  public void setType(String type) {
    typeProperty.setValue(type);
  }

  public Property<String> typeProperty() {
    return typeProperty;
  }

  public Property<String> hostProperty() {
    return hostProperty;
  }

  public String getHost() {
    return hostProperty.getValue();
  }

  public void setHost(String host) {
    hostProperty.setValue(host);
  }

  public IntegerProperty portProperty() {
    return portProperty;
  }

  public int getPort() {
    return portProperty.getValue();
  }

  public void setPort(int port) {
    portProperty.setValue(port);
  }

  public IntegerProperty addressProperty() {
    return addressProperty;
  }

  public void setAddress(int address) {
    this.addressProperty.setValue(address);
  }

  public int getAddress() {
    return addressProperty.getValue();
  }

  public IntegerProperty virtualAddressProperty() {
    return virtualAddressProperty;
  }
  
  public int getVirtualAddress() {
    return virtualAddressProperty().getValue();
  }

}
