package hr.adriacomsoftware.inf.client.smartgwt.desktop.views;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;

public class AS2MasterHead extends HLayout {

    private static final int MASTHEAD_HEIGHT = 58;
    private String logo_ = "logo.png";
    private String header_ = "Aplikacija";
    private String user_ = "Administrator";
    private String role_ = "Administrator";
    private Label name;
    private Label signedInUser;

    public AS2MasterHead() {
        super();
        GWT.log("init Masthead()...", null);
        // initialise the Masthead layout container
        this.setStyleName("crm-Masthead");
        this.setHeight(MASTHEAD_HEIGHT);
        // initialise the Logo image
        Img logo = new Img(logo_, 58, 58);
        logo.setStyleName("crm-Masthead-Logo");
        // initialise the Name label
        name = new Label();
        name.setStyleName("crm-Masthead-Name");
        name.setContents(header_);
        // initialise the West layout container
        HLayout westLayout = new HLayout();
        westLayout.setHeight(MASTHEAD_HEIGHT);
        westLayout.setWidth("50%");
        westLayout.addMember(logo);
        westLayout.addMember(name);
//        westLayout.setBackgroundColor("#E1E1E1");

        // initialise the Signed In User label
        signedInUser = new Label();
        signedInUser.setStyleName("crm-Masthead-SignedInUser");
        signedInUser.setContents("<b>Jadranska banka d.d. Šibenik</b><br /><b>"+user_+"</b>");
        signedInUser.setWidth(200);
//        signedInUser.setBackgroundColor("maroon");
        
        // initialise the East layout container
        HLayout eastLayout = new HLayout();
        eastLayout.setAlign(Alignment.RIGHT);
        eastLayout.setHeight(MASTHEAD_HEIGHT);
        eastLayout.setWidth("50%");
        eastLayout.addMember(signedInUser);
        // add the West and East layout containers to the Masthead layout container
        this.addMember(westLayout);
        this.addMember(eastLayout);
    }

    public void setMasterHeadTitle(String title){
    	 name.setContents(title);
    }

    public void setUser(String user){
    	 signedInUser.setContents("<b>"+user+"</b><br />"+role_+"<br /><a style=\"font-size:10px;\" href=\""+GWT.getHostPageBaseURL()+"j_security_check_as2?action=logout\">odjava</a>");
    }
    
    public void setUser(String user,String role){
    	signedInUser.setContents("<b>"+user+"</b><br />"+role+"<br /><a style=\"font-size:10px;\" href=\""+GWT.getHostPageBaseURL()+"j_security_check_as2?action=logout\">odjava</a>");
   }
  }
