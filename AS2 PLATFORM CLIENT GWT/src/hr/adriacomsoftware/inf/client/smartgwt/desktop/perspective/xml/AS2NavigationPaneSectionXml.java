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



import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.SectionStackSection;

public class AS2NavigationPaneSectionXml extends SectionStackSection {

	public static final int LIST_GRID = 0;
	public static final int TREE_GRID = 1;

	private AS2NavigationPaneSectionItem item;
	private AS2NavigationPaneSectionXmlListGrid listGrid = null;

	public AS2NavigationPaneSectionXml(String sectionName) {
		super(sectionName);
	}

	public AS2NavigationPaneSectionXml(String sectionName, DataSource dataSource) {
		this(sectionName,0,dataSource);
	}

	public AS2NavigationPaneSectionXml(String sectionName, int sectionType, DataSource dataSource) {
		super(sectionName);
		this.setID(sectionName);
		switch (sectionType) {
		case LIST_GRID:
			listGrid = new AS2NavigationPaneSectionXmlListGrid(dataSource,AS2NavigationPaneSectionXml.this);
			item = listGrid;
			this.addItem(listGrid);
			break;
		default:
			listGrid = new AS2NavigationPaneSectionXmlListGrid(dataSource,AS2NavigationPaneSectionXml.this);
			item = listGrid;
			this.addItem(listGrid);
			break;
		}
	}

	public Object getItem(){
		return item;
	}
	public ListGrid getListGrid() {
		return listGrid;
	}

	public void selectRecord(int record) {
		((AS2NavigationPaneSectionItem)getItem()).selectRecord(record);
	}

	public boolean selectRecord(String name) {
		return ((AS2NavigationPaneSectionItem)getItem()).selectRecord(name);
	}

	public int getRecordNum(String name) {
		return ((AS2NavigationPaneSectionItem)getItem()).getRecordNum(name);
	}

	public Record getRecord(String name) {
		return ((AS2NavigationPaneSectionItem)getItem()).getRecord(name);
	}

	//Return default record/perspective in stack
	public String getDefaultPerspectiveName() {
		if(((AS2NavigationPaneSectionItem)getItem()).getDefaultPerspectiveName()!=null){
			return ((AS2NavigationPaneSectionItem)getItem()).getDefaultPerspectiveName();
		}
		return "";
	}

	public String getSelectedRecord() {
		if(((AS2NavigationPaneSectionItem)getItem())==null)
			return null;
		return ((AS2NavigationPaneSectionItem)getItem()).getSelectedAS2Record();
	}

	public void addRecordClickHandler(RecordClickHandler clickHandler) {
		((AS2NavigationPaneSectionItem)getItem()).addRecordClickHandlerAs2(clickHandler);
	}

	public String getRecordViewDisplayName(String name) {
		return ((AS2NavigationPaneSectionItem)getItem()).getRecordViewDisplayName(name);
	}
	public String getRecordDisplayName(String name) {
		return ((AS2NavigationPaneSectionItem)getItem()).getRecordDisplayName(name);
	}
	public ListGridRecord getRecordByName(String name) {
		return ((AS2NavigationPaneSectionItem)getItem()).getRecordByName(name);
	}
}

