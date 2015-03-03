/**
 * (C) Copyright 2010, 2011 upTick Pty Ltd
 *
 * Licensed under the terms of the GNU General Public License version 3
 * as published by the Free Software Foundation. You may obtain a copy of the
 * License at: http://www.gnu.org/copyleft/gpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package hr.adriacomsoftware.inf.client.smartgwt.desktop.perspective.xml;

import com.google.gwt.i18n.client.LocaleInfo;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.FieldType;

public abstract class AS2NavigationPaneSectionXmlModel extends DataSource implements AS2NavigationPaneSectionModel{
  protected static final String URL_PREFIX = "models/perspectives/";
  protected static final String URL_SUFFIX = ".xml";
//  protected static String DEFAULT_RECORD_NAME ="test";
//  public static final String ICON = "icon";
//  public static final String ICON_DISPLAY_NAME = "Icon";
//  public static final String NAME = "name";
//  public static final String NAME_DISPLAY_NAME = "Name";
//  public static final String DISPLAY_NAME = "displayName";
//  public static final String DISPLAY_NAME_DISPLAY_NAME = "Display Name";
//  public static final String VIEW_DISPLAY_NAME = "viewDisplayName";
//  public static final String VIEW_DISPLAY_NAME_DISPLAY_NAME = "View Display Name";
  public static final String DEFAULT = "default";
  private static final String RECORD_XPATH = "/list/perspective";

  public static String DEFAULT_PERSPECTIVE_NAME="zahtjevi";
  public static String DEFAULT_PERSPECTIVE_DISPLAY_NAME="Zahtjevi";
  public static String DEFAULT_PERSPECTIVE_VIEW_DISPLAY_NAME="Zahtjevi za MasterCard kreditnom karticom";

  public AS2NavigationPaneSectionXmlModel(String id) {
    setID(id);
    setDataFormat(DSDataFormat.XML);
    setRecordXPath(RECORD_XPATH);
    DataSourceField iconField = new DataSourceField(ICON, FieldType.TEXT, ICON_DISPLAY_NAME);
    DataSourceField nameField = new DataSourceField(NAME, FieldType.TEXT, NAME_DISPLAY_NAME);
    DataSourceField displayNameField = new DataSourceField(DISPLAY_NAME, FieldType.TEXT, DISPLAY_NAME_DISPLAY_NAME);
    DataSourceField viewDisplayNameField = new DataSourceField(VIEW_DISPLAY_NAME, FieldType.TEXT, VIEW_DISPLAY_NAME_DISPLAY_NAME);
    setFields(iconField, nameField, displayNameField,viewDisplayNameField);
//    DEFAULT_PERSPECTIVE_NAME=getDefaultPerspectiveName();
  }

  public void setDataURL(String urlPrefix, String urlSuffix) {
    String url = urlPrefix;
    LocaleInfo localeInfo = LocaleInfo.getCurrentLocale();
    String localeName = localeInfo.getLocaleName();

    if (localeName.length() > 0) {
      url = url + "_" + localeName;
    }

    url = url + urlSuffix;

    // Log.debug("setDataURL: " + url);

    setDataURL(url);
  }
//  public abstract String getSectionName();
//
//  public abstract String getDefaultPerspectiveName();
}
