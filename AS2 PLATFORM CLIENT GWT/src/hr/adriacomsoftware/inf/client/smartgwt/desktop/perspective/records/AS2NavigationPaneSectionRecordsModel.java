package hr.adriacomsoftware.inf.client.smartgwt.desktop.perspective.records;






public abstract class AS2NavigationPaneSectionRecordsModel {
    private AS2NavigationPaneRecord[] records;

    public AS2NavigationPaneRecord[] getRecords() {
        if (records == null) {
            records = getNewRecords();
        }
        return records;
    }
    public AS2NavigationPaneRecord[] getNewRecords() {
        return new AS2NavigationPaneRecord[]{
                new AS2NavigationPaneRecord("test1", "Test1", null, null),
                new AS2NavigationPaneRecord("test2", "Test2", null, null),
                new AS2NavigationPaneRecord("test3", "Test3", null, null),
        };
    }
    public abstract String getTitle();
}