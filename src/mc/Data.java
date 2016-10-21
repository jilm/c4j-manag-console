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

/**
 *
 * @author jilm
 */
class Data {

  private static Data instance;

  private final SpinelDService spinelDService;

  Data() {
    spinelDService = new SpinelDService();
  }

  static Data getInstance() {
    if (instance == null) {
      instance = new Data();
    }
    return instance;
  }

  static SpinelDService getSpinelDService() {
    return getInstance().spinelDService;
  }


}
