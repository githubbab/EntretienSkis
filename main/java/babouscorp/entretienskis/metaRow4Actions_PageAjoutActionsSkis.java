package babouscorp.entretienskis;

public class metaRow4Actions_PageAjoutActionsSkis {

    private String id;
    private long date;
    private String type;

    public metaRow4Actions_PageAjoutActionsSkis(String strId,long lDate, String iType){
        super();
        id = strId;
        date = lDate;
        type = iType;
    }

    public String getDate() {
        String strDate = Long.toString(date);
        return strDate.substring(6,8) + "/" +
                strDate.substring(4,6) + "/" +
                strDate.substring(0,4) + " " +
                strDate.substring(8,10) + ":" +
                strDate.substring( 10, 12 ) + ":" +
                strDate.substring(12);
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }
}
