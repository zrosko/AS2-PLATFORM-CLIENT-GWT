package hr.adriacomsoftware.inf.client.smartgwt.views;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Progressbar;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.VLayout;
 
/**
 * A progress bar window to represent the progress of a lengthy operation. The window is modal.
 *
 * Usage example
*
 *      AS2WaitPopupProgressBar = new AS2WaitPopupProgressBar("Searching", "Searching for data...");
 *      AS2WaitPopupProgressBar.show();
 *
 *      // later after lengthy process is finished
 *      AS2WaitPopupProgressBar.hide();
 *
 *
 * @author Chad Darby
 */
public class AS2WaitPopupProgressBar extends Window {
    private static final int REPEAT_INTERVAL = 250;
    private static final int PROGRESS_VALUE_RANGE = 10;
    private static final int MAX_VALUE = 100;
 
    private Timer timer;
    private Progressbar progressBar;
 
    public AS2WaitPopupProgressBar() {
        this("Processing...");
    }
 
    /**
     * Constructor
     *
     * @param message
     */
    public AS2WaitPopupProgressBar(String message) {
        this("Processing", message);
    }
 
    /**
     * Constructor
     *
     * @param title
     * @param message
     */
    public AS2WaitPopupProgressBar(String title, String message) {
        setShowModalMask(true);
        buildGui(title, message);
    }
 
    /**
     * Build the GUI components
     *
     * @param title
     * @param message
     */
    private void buildGui(String title, String message) {
        setAutoSize(true);
        setTitle(title);
        setWidth(300);
        centerInPage();
 
        setCanDragReposition(true);
        setCanDragResize(false);
        setIsModal(true);
 
        this.setShowMaximizeButton(false);
        this.setShowMinimizeButton(false);
 
        VLayout layout = new VLayout();
        layout.setWidth100();
        layout.setPadding(5);
        addItem(layout);
 
        Label messageLabel = new Label(message);
        messageLabel.setWidth100();
        messageLabel.setHeight(40);
        layout.addMember(messageLabel);
 
        progressBar = new Progressbar();
        progressBar.setHeight(24);
        progressBar.setVertical(false);
        layout.addMember(progressBar);
    }
 
    /**
     * Starts the progress timer
     */
    private void startTimer() {
        timer = new ProgressTimer();
        timer.scheduleRepeating(REPEAT_INTERVAL);
    }
 
    @Override
    public void show() {
        super.show();
 
        startTimer();
    }
 
    @Override
    public void hide() {
        super.hide();
 
        stopTimer();
    }
 
    /**
     * Stop the timer
     */
    public void stopTimer() {
        timer.cancel();
    }
 
    /**
     * A timer that updates the progress bar
     */
    class ProgressTimer extends Timer {
 
        int value;
 
        @Override
        public void run() {
 
            value += getRandomValue(PROGRESS_VALUE_RANGE);
 
            if (value > MAX_VALUE) {
                progressBar.setPercentDone(MAX_VALUE);
                value = 0;
            } else {
                progressBar.setPercentDone(value);
            }
        }
 
        /**
         * Get random value for a given range
         *
         * @param range
         * @return
         */
        protected int getRandomValue(int range) {
            return (int) (range * Math.random());
        }
   }
}
