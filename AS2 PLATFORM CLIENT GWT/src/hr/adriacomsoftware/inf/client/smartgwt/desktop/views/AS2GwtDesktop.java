package hr.adriacomsoftware.inf.client.smartgwt.desktop.views;

import hr.adriacomsoftware.inf.client.smartgwt.core.AS2ClientContext;
import hr.adriacomsoftware.inf.client.smartgwt.desktop.perspective.records.AS2NavigationPaneRecord;
import hr.adriacomsoftware.inf.client.smartgwt.desktop.perspective.records.AS2NavigationPaneSectionRecordsModel;
import hr.adriacomsoftware.inf.client.smartgwt.desktop.perspective.xml.AS2NavigationPaneSectionDaoModel;
import hr.adriacomsoftware.inf.client.smartgwt.desktop.perspective.xml.AS2NavigationPaneSectionModel;
import hr.adriacomsoftware.inf.client.smartgwt.desktop.perspective.xml.AS2NavigationPaneSectionXml;
import hr.adriacomsoftware.inf.client.smartgwt.desktop.perspective.xml.AS2NavigationPaneSectionXmlListGrid;
import hr.adriacomsoftware.inf.client.smartgwt.desktop.perspective.xml.AS2NavigationPaneSectionXmlModel;
import hr.adriacomsoftware.inf.client.smartgwt.formatters.AS2DateFormat;
import hr.adriacomsoftware.inf.client.smartgwt.formatters.AS2DateFormatFactory;
import hr.adriacomsoftware.inf.client.smartgwt.models.AS2RestJSONDataSource;

import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.SmartGwtEntryPoint;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.i18n.SmartGwtMessages;
import com.smartgwt.client.rpc.HandleErrorCallback;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.events.ResizedEvent;
import com.smartgwt.client.widgets.events.ResizedHandler;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

public abstract class AS2GwtDesktop extends SmartGwtEntryPoint  {
	private static AS2GwtDesktop instance = null;
	protected static String DEFAULT_VIEW_NAME;
	protected static String DEFAULT_VIEW_DISPLAY_NAME;
	private static String LANGUAGE = "hr"; //hr,usa,eu, ...
    protected static int NORTH_HEIGHT = 85; // MASTHEAD_HEIGHT + APPLICATION_MENU_HEIGHT
    protected static int DEFAULT_MENU_WIDTH = 70;
    private VLayout mainLayout;
    private HLayout northLayout;
    protected static HLayout southLayout;
    protected VLayout eastLayout;
    protected static VLayout westLayout;
    private VLayout vLayout;
    protected static AS2NavigationPaneHeader navigationPaneHeader;
    protected static AS2NavigationPane navigationPane;
    protected AS2ApplicationMenu applicationMenu;
    private AS2MasterHead masterHead;
	private String _default_perspective_name ="";
	private String _default_perspective_viewDisplayName = "";
	public String _userId;
	public String _role;
    private static boolean USE_RECORDS_SECTIONS = false;
    protected List<DataSource> dropDownCache;
    public static SmartGwtMessages _messages = GWT.create(SmartGwtMessages.class);
	//TODO RootPanel.get("loadingTitle").add(new Label(getMasterHeadTitle()));
	//TODO RootPanel.get("loadingMsg").add(new Label(messages.loadingMsg()));
	
	public static AS2GwtDesktop getInstance() {
		return instance;
	}
    public void onModuleLoad() {
    	super.onModuleLoad();
    	instance=this;
    	initHistory();
    	initErrorHandling();
    	initialSettings();
    	if(isAuthenticationNeeded())
    		initUser();
    	else
    		initDefaultUser();
    	//MainLayout is initialized after user credentials are retrieved from server see @AS2GwtDesktop.initUser and 
    }

	public void initMainLayout() {
    	  GWT.log("init (OnLoadModule)...", null);
          // inject global styles
          // GWT.<GlobalResources>create(GlobalResources.class).css().ensureInjected();
          // get rid of scroll bars, and clear out the window's built-in margin,
          // because we want to take advantage of the entire client area
          Window.enableScrolling(false);
          Window.setMargin("0px");
          //setup overall layout / viewport
          // initialise the main layout container
          mainLayout = new VLayout();
          mainLayout.addDrawHandler(new DrawHandler() {
          	@Override
          	public void onDraw(DrawEvent event) {
          		if (History.getToken().length() != 0) {
          			onDrawHistory(History.getToken(),0);
          		}else{
          			//Set the default view
          			if(navigationPane!=null)
          				setDefaultContextAreaView();
          		}
          	}
          });
          mainLayout.setWidth100();
          mainLayout.setHeight100();
          mainLayout.setID("MainLayout");
          // initialise the North layout container
          northLayout = new HLayout();
          northLayout.setHeight(NORTH_HEIGHT);
          northLayout.setID("NorthLayout");
          vLayout = new VLayout();
          // add the Masthead to the nested layout container
          masterHead = new AS2MasterHead();
          masterHead.setMasterHeadTitle(getMasterHeadTitle());
          _userId=getUser();
          _role=getRole();
          masterHead.setUser(_userId,_role);
          masterHead.setID("AS2MasterHead");
          vLayout.addMember(masterHead);
          // initialise the Application menu
          initApplicationMenu();
     	 	// initialise the Navigation Pane Header
          navigationPaneHeader = initNavigationPaneHeader();
          // add the Application Menu and the Navigation Pane Header to the nested layout container
//          vLayout.addMember(applicationMenu);
          if(navigationPaneHeader!=null){
          	navigationPaneHeader.setID("AS2NavigationPaneHeader");
          	vLayout.addMember(navigationPaneHeader);
          }
          // add the nested layout container to the North layout container
          northLayout.addMember(vLayout);
          navigationPane = getNavigationPane();
          if(navigationPane==null){
        	  if(USE_RECORDS_SECTIONS){
        		  initNavigationPaneUsingRecords();
        	  }
        	  else
        		  initNavigationPane();
          }
          if(navigationPane!=null){
        	  navigationPane.setID("AS2NavigationPane");
        	  navigationPane.setShowResizeBar(true);
          }

          // initialise the South layout container
          southLayout = new HLayout();
          
          // initialise the West layout container
          westLayout = initWestLayout();
          if(westLayout!=null){
  	        westLayout.setID("WestLayout");
  	        westLayout.addResizedHandler(new ResizedHandler() {
  	
  				@Override
  				public void onResized(ResizedEvent event) {
  					if(navigationPaneHeader!=null)
  						navigationPaneHeader.getNavigationPaneHeaderLabel().setWidth(westLayout.getWidth());
  	
  				}
  			});
  	        southLayout.addMember(westLayout);
          }
    
          
          // initialise the East layout container
          eastLayout = initEastLayout();
          if(eastLayout!=null){
            eastLayout.setID("AS2ContextArea");
            southLayout.addMember(eastLayout);
          }
          // set the Navigation Pane and Context Area as members of the South
          // layout container
//          southLayout.setMembers(westLayout, eastLayout);
          southLayout.setID("SouthLayout");

          // add the North and South layout containers to the main layout container
          mainLayout.addMember(northLayout);
          mainLayout.addMember(southLayout);
          
          customizeApplication();
          mainLayout.draw();
          
          //Removes loading application panel
          if(DOM.getElementById("loadingWrapper")!=null)
          	DOM.getElementById("loadingWrapper").removeFromParent();
	}

//    private void setDefaultContextAreaView() {
////    	setContextAreaViewXml("","");
//    	final String name =  getDefaultViewName();
//    	SectionStackSection sec = getDefaultSection();
//    	if(sec instanceof AS2NavigationPaneSectionXml){
//    		final AS2NavigationPaneSectionXml section = (AS2NavigationPaneSectionXml)sec;
//    		navigationPane.getSectionStack().expandSection(section.getID());
//    		section.getListGrid().addDataArrivedHandler(new DataArrivedHandler() {
//    			@Override
//    			public void onDataArrived(DataArrivedEvent event) {
//    				if(section.getRecord(name)!=null){
//    					if(section.selectRecord(name)){
//    						String view_display_name = section.getRecordViewDisplayName(name);
//    						setContextAreaViewXml(name,view_display_name);
//    					}
//    				}
//    			}
//    		});
//    	}
//
//    }
    private void setDefaultContextAreaView() {
    	if(_default_perspective_name.equals("") &&
    			_default_perspective_viewDisplayName.equals("")){
    		if(getDefaultSectionName().length()>0 && getDefaultSectionName().length()>0
    				&& getDefaultSection()!=null){
    			final String name =  getDefaultViewName();
    			SectionStackSection sec = getDefaultSection();
    			if(sec instanceof AS2NavigationPaneSectionXml){
    				final AS2NavigationPaneSectionXml section = (AS2NavigationPaneSectionXml)sec;
    				navigationPane.getSectionStack().expandSection(section.getID());
    				section.getListGrid().addDataArrivedHandler(new DataArrivedHandler() {
    					@Override
    					public void onDataArrived(DataArrivedEvent event) {
    						if(section.getRecord(name)!=null){
    							if(section.selectRecord(name)){
    								String view_display_name = section.getRecordViewDisplayName(name);
    								_default_perspective_name = name;
    								_default_perspective_viewDisplayName = view_display_name;
    								setContextAreaViewXml(_default_perspective_name,_default_perspective_viewDisplayName);
    							}
    						}
    					}
    				});
    			}
    		}else{
    			setDefaultContextAreaViewDao();
    		}
    	}else{
    		setContextAreaViewXml(_default_perspective_name,_default_perspective_viewDisplayName);
    	}
    }
    
    private void setDefaultContextAreaViewDao() {
    	for(SectionStackSection sec : navigationPane.getSections()){
    		if(sec instanceof AS2NavigationPaneSectionXml){
    			AS2NavigationPaneSectionXml section = (AS2NavigationPaneSectionXml)sec;
    			navigationPane.getSectionStack().expandSection(section.getID());
//    			section.getListGrid().addSelectionChangedHandler(new SelectionChangedHandler() {
//					@Override
//    				public void onSelectionChanged(SelectionEvent event) {
//    					Record selected_record = event.getSelectedRecord();
//    					if(selected_record!=null && selected_record.getAttributeAsObject(AS2NavigationPaneSectionModel.NAME)!=null){
//	    					_default_perspective_name = selected_record.getAttribute(AS2NavigationPaneSectionModel.NAME);
//	    					_default_perspective_viewDisplayName = selected_record.getAttribute(AS2NavigationPaneSectionModel.VIEW_DISPLAY_NAME);
//	    					setContextAreaViewXml(_default_perspective_name,_default_perspective_viewDisplayName);
//    					}
//    				}
//    			});
        	}
    	}
    }
    	
	protected VLayout initWestLayout() {
		return navigationPane;
	}

    protected VLayout initEastLayout() {
		return new AS2ContextArea();
	}

	protected void initUser() {
		if(Cookies.getCookie(AS2ClientContext.AS2_USERNAME)!=null){
			RestDataSource as2_user_model = new RestDataSource();
			as2_user_model.setID("as2_user_model");
			as2_user_model.setDataURL(AS2RestJSONDataSource.DEFAULT_DATA_URL);
			as2_user_model.setDataFormat(DSDataFormat.JSON);
			as2_user_model.setRecordXPath(AS2RestJSONDataSource.AS2_RECORD_X_PATH);
			Criteria criteria = new Criteria();
			for(String name: Cookies.getCookieNames()){
				criteria.addCriteria(name,Cookies.getCookie(name));
			}
			criteria.addCriteria(AS2RestJSONDataSource.REMOTE_OBJECT,"hr.as2.inf.server.security.authorization.facade.AS2AuthorizationFacadeServer");
			criteria.addCriteria(AS2RestJSONDataSource.REMOTE_METHOD,"autorizirajKorisnika");
			criteria.addCriteria(AS2RestJSONDataSource.TRANSFORM_TO,"hr.as2.inf.common.security.user.AS2User");
			criteria.addCriteria(AS2ClientContext.APPLICATION,getApplicationId());
			as2_user_model.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
					boolean authorized=false;
					if(dsResponse.getStatus()>=0 
							&& dsResponse.getDataAsRecordList()!=null
							&& dsResponse.getDataAsRecordList().get(0)!=null){
						Record record = dsResponse.getDataAsRecordList().get(0);
						if(record.getAttributeAsObject(AS2ClientContext.APPLICATION)!=null
								&& record.getAttribute(AS2ClientContext.APPLICATION).equalsIgnoreCase(getApplicationId())){
							authorized=true;
							record.getAttributes();
							AS2ClientContext.setSessionValue(AS2ClientContext.USER_FULL_NAME, record.getAttribute(AS2ClientContext.USER_FULL_NAME));
							AS2ClientContext.setSessionValue(AS2ClientContext.AS2_USERNAME, record.getAttribute(AS2ClientContext.AS2_USERNAME));
							AS2ClientContext.setSessionValue(AS2ClientContext.ROLE, record.getAttribute(AS2ClientContext.ROLE));
							AS2ClientContext.setSessionValue(AS2ClientContext.ROLE_ID, record.getAttribute(AS2ClientContext.ROLE_ID));
							AS2ClientContext.setSessionValue(AS2ClientContext.APPLICATION_ID, getApplicationId());
							AS2ClientContext.setSessionValue(AS2ClientContext.APPLICATION,getApplicationName());
							AS2ClientContext.setSessionValue(AS2ClientContext.LOGIN_TIME,AS2ClientContext.formatDateTimeToString(new java.util.Date()));
							AS2ClientContext.setSessionValue(AS2ClientContext.TIMEOUT, "100000");
							initCacheModels();
						}
					}
					if(!authorized){
						SC.warn(_messages.user_authorizationError(), new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								AS2ClientContext.showLoginForm();
							}
						});

					}
				}
			});
		}else{
			AS2ClientContext.showLoginForm();
		}
	}
	protected void initDefaultUser() {
		if(Cookies.getCookie(AS2ClientContext.AS2_USERNAME)!=null){
			AS2ClientContext.setSessionValue(AS2ClientContext.USER_FULL_NAME, "Default user");
			AS2ClientContext.setSessionValue(AS2ClientContext.AS2_USERNAME, "Default");
			AS2ClientContext.setSessionValue(AS2ClientContext.ROLE, "Admin");
			AS2ClientContext.setSessionValue(AS2ClientContext.ROLE_ID, "1");
			AS2ClientContext.setSessionValue(AS2ClientContext.APPLICATION_ID, getApplicationId());
			AS2ClientContext.setSessionValue(AS2ClientContext.APPLICATION,getApplicationName());
			AS2ClientContext.setSessionValue(AS2ClientContext.LOGIN_TIME,AS2ClientContext.formatDateTimeToString(new java.util.Date()));
			AS2ClientContext.setSessionValue(AS2ClientContext.TIMEOUT, "100000");
			initCacheModels();
		}
	}
	
	private void initCacheModels() {
		dropDownCache = getDropDownCacheModels();
		if(dropDownCache!=null){
			for(int i=0;i<dropDownCache.size();i++){
				DSRequest request = new DSRequest();
				request.setAttribute("_dataSourceID", dropDownCache.get(i).getID());
				if(i==dropDownCache.size()-1){
					dropDownCache.get(i).fetchData(new Criteria(), new DSCallback() {

						@Override
						public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
							if(dsResponse.getStatus()>=0){
								RecordList records = dsResponse.getDataAsRecordList();
								LinkedHashMap<String, Object> vrsta = new LinkedHashMap<String, Object>();
								LinkedHashMap<String, Object> valueMap = new LinkedHashMap<String, Object>();
								String temp = null;
								for (int i = 0; i < records.getLength(); i++) {
									if (records.get(i) != null) {
										if (i == 0)
											temp = records.get(i).getAttribute("vrsta");

										if (!temp.equals(records.get(i).getAttributeAsString("vrsta"))) {
											vrsta.put(temp,valueMap);
											temp = records.get(i).getAttributeAsString("vrsta");
											valueMap = new LinkedHashMap<String, Object>();
										}
										if (temp.equals(records.get(i).getAttributeAsString("vrsta"))) {
											valueMap.put(records.get(i).getAttributeAsString("id_sifre"),records.get(i).getAttributeAsString("naziv_sifre"));
										}
										if (i == (records.getLength() - 1))
											vrsta.put(temp,valueMap);
									}
								}
								//TODO zamjeniti _cache sa ovime (koristiti njihov framework)
								//DataSource.get(dsRequest.getAttribute("_dataSourceID")).setCacheData(records.toArray());
								((AS2RestJSONDataSource)DataSource.get(dsRequest.getAttribute("_dataSourceID"))).setCache(vrsta);
								initMainLayout();
							}
						}
					},request);
				}else{
					dropDownCache.get(i).fetchData(new Criteria(), new DSCallback() {
						@Override
						public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
							if(dsResponse.getStatus()>=0){
								RecordList records = dsResponse.getDataAsRecordList();
								LinkedHashMap<String, Object> vrsta = new LinkedHashMap<String, Object>();
								LinkedHashMap<String, Object> valueMap = new LinkedHashMap<String, Object>();
								String temp = null;
								for (int i = 0; i < records.getLength(); i++) {
									if (records.get(i) != null) {
										if (i == 0)
											temp = records.get(i).getAttribute("vrsta");

										if (!temp.equals(records.get(i).getAttributeAsString("vrsta"))) {
											vrsta.put(temp,valueMap);
											temp = records.get(i).getAttributeAsString("vrsta");
											valueMap = new LinkedHashMap<String, Object>();
										}
										if (temp.equals(records.get(i).getAttributeAsString("vrsta"))) {
											valueMap.put(records.get(i).getAttributeAsString("id_sifre"),records.get(i).getAttributeAsString("naziv_sifre"));
										}
										if (i == (records.getLength() - 1))
											vrsta.put(temp,valueMap);
									}
								}
								((AS2RestJSONDataSource)DataSource.get(dsRequest.getAttribute("_dataSourceID"))).setCache(vrsta);
							}
						}
					},request);
				}
			}
		}
		else{
			initMainLayout();
		}

	}
	
	protected abstract List<DataSource> getDropDownCacheModels();
	
	public static LinkedHashMap<String,Object> getCacheData(DataSource cacheModel,String name){
		if(cacheModel instanceof AS2RestJSONDataSource){
			AS2RestJSONDataSource cm = (AS2RestJSONDataSource)cacheModel;
			return cm.getCache(name);
		}else
			return null;
	}
	
	//Centralized error handling when data is returned from server with negative status
	//this handler is called
	private void initErrorHandling() {
		RPCManager.setHandleErrorCallback(new HandleErrorCallback() {
			@Override
			public void handleError(DSResponse response, DSRequest request) {
				if(response.getStatus()==-551){//Login required
					AS2ClientContext.showLoginForm();
				}else if(response.getStatus()<0){
					SC.warn(response.getDataAsString());
				}
			}
		});
	}

	private void initialSettings() {
		Window.setTitle(getMasterHeadTitle());
    	LANGUAGE = getLanguage();    
		//Lokalizaija datuma
		AS2DateFormat dateTimeFormat =null;
		if(LANGUAGE.equals("hr")){
			dateTimeFormat = AS2DateFormatFactory.getDateTimeFormat(AS2DateFormatFactory.CROATIAN);
		}else{
			//TODO za drugi jezik
		}
		if(dateTimeFormat!=null){
			DateUtil.setShortDatetimeDisplayFormatter(dateTimeFormat); 
		}
	}

	protected AS2NavigationPaneHeader initNavigationPaneHeader() {
		AS2NavigationPaneHeader navigationPaneHeader = new AS2NavigationPaneHeader();
		navigationPaneHeader
				.setNavigationPaneHeaderLabelContents(getNavigationPaneHeader());
		return navigationPaneHeader;
	}

	protected void customizeApplication() {

	}
	protected AS2NavigationPane getNavigationPane() {
		// initialise the Navigation Pane
		AS2NavigationPane navigationPane = new AS2NavigationPane();
    	if(preparePerspectiveSections()==null){
    		//New
    		getNavigationPaneSections(navigationPane);
    		if(navigationPane.getSections()!=null){
	    		for(SectionStackSection sec: navigationPane.getSections()){
	    			String sectionID = sec.getName();
	    			if(navigationPane.getSection(sectionID) instanceof AS2NavigationPaneSectionXml){
	    				final AS2NavigationPaneSectionXml section = (AS2NavigationPaneSectionXml)navigationPane.getSection(sectionID);
	    				if(section.getListGrid()!=null){        	
	    					navigationPane.addRecordClickHandler(sectionID,
	    							new RecordClickHandler() {
	    						@Override
	    						public void onRecordClick(RecordClickEvent event) {
	    							onRecordClicked(event);
	    						}
	    					});
	    				}
	    			}
	    		}
    		}

    	}else{
    		//Old
    		AS2NavigationPaneSectionModel[] perspectiveSections_list = preparePerspectiveSections();
    		for(AS2NavigationPaneSectionModel model: perspectiveSections_list){
    			String sectionID = model.getSectionName();
    			navigationPane.addSection(sectionID,(DataSource) model);
    			navigationPane.addRecordClickHandler(sectionID,
						new RecordClickHandler() {
					@Override
					public void onRecordClick(RecordClickEvent event) {
						onRecordClicked(event);
					}
				});
    		}
    	}
    	return navigationPane;
    }

	public String getNavigationPaneHeader(){
    	return "Izbornik";
    }

	protected String getMasterHeadTitle() {
		return getApplicationName();
	}

	protected String getUser() {
		return AS2ClientContext.getSessionValue(AS2ClientContext.USER_FULL_NAME);
	}

	protected String getRole() {
		return AS2ClientContext.getSessionValue(AS2ClientContext.ROLE);
	}
	
	protected abstract void getNavigationPaneSections(AS2NavigationPane navigationPane);

	protected abstract String getApplicationName();

	protected abstract String getApplicationId();

	protected  String getLanguage() {
		LocaleInfo localeInfo = LocaleInfo.getCurrentLocale();
		return localeInfo.getLocaleName();
	}

    protected String getApplicationMenuContents(){
    	return "Jadranska Banka d.d.";
    }
    protected void initApplicationMenu() {
    	 Label applicationMenuLabel = new Label(getApplicationMenuContents());
         applicationMenu = new AS2ApplicationMenu(applicationMenuLabel);
         applicationMenu.setID("AS2ApplicationMenu");
         //         applicationMenu.addMenu("<u>N</u>ew Activity", DEFAULT_MENU_WIDTH,
//                                 "Task, Fax, Phone Call, Email, Letter, " +
//                                 "Appointment", new ApplicationMenuClickHandler());
//         applicationMenu.addMenu("New Re<u>c</u>ord", DEFAULT_MENU_WIDTH,
//                                 "Account, Contact, separator, Lead, Opportunity",
//                                 new ApplicationMenuClickHandler());
//         Menu goToMenu = applicationMenu.addMenu("<u>G</u>o To", DEFAULT_MENU_WIDTH - 30);
//         applicationMenu.addSubMenu(goToMenu, "Sales", "Leads, Opportunities, Accounts, Contacts",
//                                    new ApplicationMenuClickHandler());
//         applicationMenu.addSubMenu(goToMenu, "Settings", "Administration, Templates, Data Management",
//                                    new ApplicationMenuClickHandler());
//         applicationMenu.addSubMenu(goToMenu, "Resource Centre", "Highlights, Sales, Settings",
//                                    new ApplicationMenuClickHandler());
//         applicationMenu.addMenu("<u>T</u>ools", DEFAULT_MENU_WIDTH - 30,
//                                 "Import Data, Duplicate Detection, Advanced Find, Options",
//                                 new ApplicationMenuClickHandler());
//         applicationMenu.addMenu("<u>H</u>elp", DEFAULT_MENU_WIDTH - 30,
//                                 "Help on this Page, Contents, myCRM Online, About myCRM",
//                                 new ApplicationMenuClickHandler());

    }
    
    protected void initNavigationPane() {
    	
    }

//    protected void initNavigationPane() {
//        // initialise the Navigation Pane
//        navigationPane = new AS2NavigationPane();
//        getNavigationPaneSections();
//        AS2NavigationPaneSectionModel[] perspectiveSections_list = preparePerspectiveSections();
//        for(AS2NavigationPaneSectionModel abc: perspectiveSections_list){
//        	String sectionID = abc.getSectionName();
////        	navigationPane.addSection(sectionID,(DataSource) abc);
//        	if(navigationPane.getSection(sectionID) instanceof AS2NavigationPaneSectionXml){
//        		AS2NavigationPaneSectionXml section = ;
//        	 ((AS2NavigationPaneSectionXml)navigationPane.getSection(sectionID)).getType().equals("ListGrid")){        	
//	            navigationPane.addRecordClickHandler(sectionID,
//	                    new RecordClickHandler() {
//	                  @Override
//	                  public void onRecordClick(RecordClickEvent event) {
//	                	  onRecordClicked(event);
//	                  }
//	                });
//        	}
//        }
//        //define default selection
//        navigationPane.addSectionHeaderClickHandler(new SectionHeaderClickHandler() {
//
//			@Override
//			public void onSectionHeaderClick(SectionHeaderClickEvent event) {
//				getSectionHeaderClickHandler(event);
//			}
//		});
//    }
    
//    protected SectionHeaderClickHandler getSectionHeaderClickHandler() {
//    	SectionHeaderClickHandler handler = new SectionHeaderClickHandler() {
//			@Override
//			public void onSectionHeaderClick(SectionHeaderClickEvent event) {
//				SectionStackSection section = event.getSection();
//				if( event.getSection() instanceof AS2NavigationPaneSectionXml){
//					 String name = ((AS2NavigationPaneSectionXml) section).getSelectedRecord();
//
//		              // If there is no selected record (e.g. the data hasn't finished loading)
//		              // then getSelectedRecord() will return an empty string.
////		              if (name.isEmpty()) {
//		              System.out.println("Section " + section.getID()+ " is expanded = "+ section.getSectionStack().sectionIsExpanded(section.getID()));
//		              if(!section.getSectionStack().sectionIsExpanded(section.getID())){
//		            	  name= ((AS2NavigationPaneSectionXml) section).getDefaultPerspectiveName();
////		            	name= getExpandedSectionRecord(section);
//		            	  Record record;
//		            	  if(name.equals("")){
//		            		  record =  ((AS2NavigationPaneSectionXml) section).getListGrid().getRecord(0);
//
//		            	  }
//		            	  else{
//		            		  record = ((AS2NavigationPaneSectionXml) section).getListGrid().getRecord(1);
//
//		            	  }
//		            	  ((AS2NavigationPaneSectionXml) section).selectRecord(name);
//		            	  setContextAreaViewXml(name,((AS2NavigationPaneSectionXml) section).getRecordDisplayName(name));
//		            	    //deprecated
//		            	    //setContextAreaViewRecord(record,name);
//		              }else{
////		            	  name=getExpandedSectionRecord(section);
//		            	  setContextAreaViewXml("","");
//		              }
//				}
//
//			}
//		};
//		return handler;
//	}
    
//    protected void getSectionHeaderClickHandler(SectionHeaderClickEvent event) {
//    	SectionStackSection section = event.getSection();
//    	String name = ((AS2NavigationPaneSectionXml) section).getSelectedRecord();
//    	// If there is no selected record (e.g. the data hasn't finished loading)
//    	// then getSelectedRecord() will return an empty string.
//    	//              if (name.isEmpty()) {
//    	//TODO
//    	System.out.println("Section " + section.getID()+ " is expanded = "+ section.getSectionStack().sectionIsExpanded(section.getID()));
//    	if(!section.getSectionStack().sectionIsExpanded(section.getID())){
//    		name= ((AS2NavigationPaneSectionXml) section).getDefaultPerspectiveName();
//    		Record record = null;
//    		if(name.equals("")){
//    			record =  ((AS2NavigationPaneSectionXml) section).getListGrid().getRecord(0);
//
//    		}
//    		else{
//    			if(((AS2NavigationPaneSectionXml) section).selectRecord(name))
//    				record = ((AS2NavigationPaneSectionXml) section).getListGrid().getSelectedRecord();
//    			else
//    				record =  ((AS2NavigationPaneSectionXml) section).getListGrid().getRecord(0);
//
//    		}
//    		if(record!=null)
//    			setContextAreaViewXml(name,record.getAttribute(AS2NavigationPaneSectionXmlModel.VIEW_DISPLAY_NAME));
//    	}else{
//    		setContextAreaViewXml("","");
//    	}
//    }

	protected AS2NavigationPaneSectionModel[] preparePerspectiveSections(){
//    	return new AS2NavigationPaneSectionModel[1];
		return null;
    };

	protected AS2NavigationPaneSectionXmlModel[] preparePerspectiveSectionsUsingXml(){
    	return new AS2NavigationPaneSectionXmlModel[1];
    };
    protected AS2NavigationPaneSectionDaoModel[] preparePerspectiveSectionsUsingDao(){
    	return new AS2NavigationPaneSectionDaoModel[1];
    };
    @SuppressWarnings("unused")
	private class ApplicationMenuClickHandler implements ClickHandler {
        @Override
        public void onClick(MenuItemClickEvent event) {
            String applicationName = event.getItem().getTitle();
            callMenuAction(applicationName);
        }
    }
    protected void onRecordClicked(RecordClickEvent event) {
    	if(event.getViewer() instanceof AS2NavigationPaneSectionXmlListGrid){
    		Record record = event.getRecord();
    		String name = record.getAttributeAsString(AS2NavigationPaneSectionXmlModel.NAME);
    		if (name != null) {
    			setContextAreaViewXml(name,record.getAttributeAsString(AS2NavigationPaneSectionXmlModel.VIEW_DISPLAY_NAME));
    		}
    	}
      }
    
//    protected void onRecordClicked(RecordClickEvent event) {
//        Record record = event.getRecord();
//        String name = record.getAttributeAsString(AS2NavigationPaneSectionXmlModel.NAME);
//        if (name != null) {
//        	 setContextAreaViewXml(name,record.getAttributeAsString(AS2NavigationPaneSectionXmlModel.VIEW_DISPLAY_NAME));
//        }
//      }


    //test menu call
    protected void callMenuAction(String applicationName){
    	SC.say("You clicked: " + applicationName);
    }

    public void setContextAreaViewVLayout (VLayout view, String navigationPaneHeaderDisplayLabel) {
    	if(navigationPaneHeader!=null)
		navigationPaneHeader.setContextAreaHeaderLabelContents(navigationPaneHeaderDisplayLabel);
        southLayout.setMembers(westLayout, view);
    }


    public void setContextAreaViewXml (String name, String display_name) {
		navigationPaneHeader.setContextAreaHeaderLabelContents(display_name);
        southLayout.setMembers(westLayout, getView(name));
        southLayout.markForRedraw();
        History.newItem(name, false);
    }

    protected Canvas getView(String name){
   	 return new AS2ContextArea();
   }
    
  //Default Section to open when application starts
  	protected SectionStackSection getDefaultSection(){
  		if(!getDefaultSectionName().equals(""))
  			for(SectionStackSection sec: navigationPane.getSections())
  				if(sec.getName().equals(getDefaultSectionName()))
  					return sec;
  		return navigationPane.getSections()[0];
  	}
  	protected String getDefaultSectionName() {
  		return "";
  	}
  	//Default Section View name to open when application starts
  	protected String getDefaultViewName(){
  		return "";
  	}
  	
  	//Default Section View name to open when application starts
  	public void setDefaultViewName(String name){
  		_default_perspective_name=name;
  	}
  	
  	//Default Section View name to open when application start
  	protected String getDefaultViewDisplayName(){
  		return "";

  	}
  	
  	//Default Section View name to open when application start
  	public void setDefaultViewDisplayName(String viewDisplayName){
  		_default_perspective_viewDisplayName=viewDisplayName;
  	}

 /*************************History *****************************/
    private void initHistory() {
    	History.addValueChangeHandler(new ValueChangeHandler<String>() {
    		@Override
    		public void onValueChange(ValueChangeEvent<String> event) {
    			if (event.getValue().length() != 0) {
    				onHistoryChanged(event.getValue());
    			} 
    		}
    	});
    }
    
    public void onHistoryChanged(final String historyToken) {
    	if(historyToken == null || historyToken.equals("")){
    		setDefaultContextAreaView();
    	}else{
    		if(navigationPane!=null){
    			String view_display_name="";
    			for(SectionStackSection sec:navigationPane.getSections()){
    				if(sec instanceof AS2NavigationPaneSectionXml){
    					AS2NavigationPaneSectionXml section = (AS2NavigationPaneSectionXml)sec;
    					navigationPane.getSectionStack().expandSection(section.getID());
    					if(section.getRecord(historyToken)!=null){
    						if(section.selectRecord(historyToken)){
    							view_display_name = section.getRecordViewDisplayName(historyToken);
    							if(view_display_name==null){
    								view_display_name = section.getRecordDisplayName(historyToken);
    							}
    							break;
    						}
    					}
    				}

    			}
    			if(view_display_name!=null && !view_display_name.equals("")){
    				setContextAreaViewXml(historyToken, view_display_name);
    			}else{
    				setDefaultContextAreaView();
    			}
    		}
    	}
    }

    public void onDrawHistory(final String historyToken, int start_position) {
    	if(historyToken == null || historyToken.equals("")){
    		setDefaultContextAreaView();
    	}else{
    		if(navigationPane!=null){
//    			String view_display_name ="";
    			for(int i=start_position;i<navigationPane.getSections().length;i++){
    				SectionStackSection sec =  navigationPane.getSections()[i];
    				if(sec instanceof AS2NavigationPaneSectionXml){
    					AS2NavigationPaneSectionXml section = (AS2NavigationPaneSectionXml)sec;
    					navigationPane.getSectionStack().expandSection(section.getID());
//    					if (section.getRecord(historyToken)!=null){
//    						if(section.selectRecord(historyToken)){
//    							view_display_name = section.getRecordViewDisplayName(historyToken);
//    							if(view_display_name==null){
//    								view_display_name = section.getRecordDisplayName(historyToken);
//    							}
//    							break;
//    						}
//    					}
//    					else{
//    						final int section_position = i;
//    						section.getListGrid().addDataArrivedHandler(new DataArrivedHandler() {
//    							@Override
//    							public void onDataArrived(DataArrivedEvent event) {
//    								onDrawHistory(historyToken,section_position);
//    							}
//    						});
//    					}
    				}
    			}
//    			if(view_display_name!=null && !view_display_name.equals("")){
//    				setContextAreaViewXml(historyToken, view_display_name);
//    			}
    		}
    	}
    }
    
    /********************************************************************************
     * SECOND approach to get application perspectives,
     * using records instead of xml definitions.
     *******************************************************************************/
    private void setContextAreaView(AS2NavigationPaneRecord record) {
        // "Activities" or "Calendar", etc.
        String name = record.getName();
        navigationPaneHeader.setContextAreaHeaderLabelContents(name);
        AS2ContextAreaFactory factory = record.getFactory();
        Canvas view = factory.create();
        southLayout.setMembers(westLayout, view);
    }
    protected AS2NavigationPaneSectionRecordsModel[] preparePerspectiveUsingRecords(){
    	return new AS2NavigationPaneSectionRecordsModel[1];
    };

    private void initNavigationPaneUsingRecords() {
        // initialise the Navigation Pane Header
        navigationPaneHeader = new AS2NavigationPaneHeader();
        // add the Application Menu and the Navigation Pane Header to the nested layout container
        vLayout.addMember(applicationMenu);
        vLayout.addMember(navigationPaneHeader);
        // add the nested layout container to the North layout container
        northLayout.addMember(vLayout);

        // initialise the Navigation Pane
        navigationPane = new AS2NavigationPane();
        AS2NavigationPaneSectionRecordsModel[] perspective_list = preparePerspectiveUsingRecords();
        for(AS2NavigationPaneSectionRecordsModel abc: perspective_list){
        	navigationPane.add(abc.getTitle(), abc.getRecords(), new NavigationPaneClickHandler());
        }
    }
    private class NavigationPaneClickHandler implements RecordClickHandler {
        @Override
        public void onRecordClick(RecordClickEvent event) {
            AS2NavigationPaneRecord record = (AS2NavigationPaneRecord) event.getRecord();
            setContextAreaView(record);
        }
    }

	//deprecated
	protected VLayout getDefaultView(){
		return new AS2ContextArea();

	}
	//Which record in expanded section to open in context area
	protected String getExpandedSectionRecord(SectionStackSection section){
		return new String();
	}
	
	@SuppressWarnings("unused")
	@Deprecated
    private void setContextAreaViewRecord(Record record,String name) {
    	navigationPane.selectRecord(name);
    	navigationPaneHeader.setContextAreaHeaderLabelContents(name);
        southLayout.setMembers(westLayout, getView(name));
    }
	/* In case an application does not need to authenticate, it needs to overwrite this
	 * method and return false.
	 */
	protected boolean isAuthenticationNeeded(){
		return true;
	}

}