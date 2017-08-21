package org.pathvisio.core.model;

import org.bridgedb.DataSource;
import org.bridgedb.Xref;

import java.util.ArrayList;

/**
 * Created by saurabhk351 on 20/06/2017.
 */
public class Citation {
    private String citationId;
    private String URL="";
    private String title="";
    private String year;
    private String source;
    private ArrayList<String> authors;
    private Xref xref;
    public Citation(String citationId, String URL, String title){
        authors = new ArrayList<>();
        this.citationId = citationId;
        if(URL!=null)
            this.URL = URL;
        if(title!=null)
            this.title = title;
    }

    public void setCitationId(String citationId) {
        this.citationId = citationId;
    }

    public void setYear(String year){
        this.year=year;
    }

    public void setSource(String source){
        this.source=source;
    }

    public void addAuthor(String author){
        authors.add(author);
    }

    public void setXref(String ID,String dataSource){
        xref = new Xref(ID, DataSource.getByFullName(dataSource));
    }

    public Xref getXref() {
        return xref;
    }

    public String getCitationId() {
        return citationId;
    }

    public String getURL() {
        return URL;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public String getSource() {
        return source;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

}
