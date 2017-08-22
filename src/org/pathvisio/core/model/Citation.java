// PathVisio,
// a tool for data visualization and analysis using Biological Pathways
// Copyright 2006-2015 BiGCaT Bioinformatics
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
package org.pathvisio.core.model;

import org.bridgedb.DataSource;
import org.bridgedb.Xref;

import java.util.ArrayList;

/**
 * Citation class stores all information relevant to a Citation tag for Pathways
 * @author saurabh
 */
public class Citation {
    private String citationId;
    private String URL="";
    private String title="";
    private String year;
    private String source;
    private ArrayList<String> authors;
    private Xref xref;

    /**
     * Instantiate a Citation object
     * @param citationId ID of the CItation Element
     * @param URL URL of the Citations
     * @param title Title of the Citation
     */
    public Citation(String citationId, String URL, String title){
        authors = new ArrayList<>();
        this.citationId = citationId;
        if(URL!=null)
            this.URL = URL;
        if(title!=null)
            this.title = title;
    }

    /**
     * Set ID of the citations
     * @param citationId ID of the Citation
     */
    public void setCitationId(String citationId) {
        this.citationId = citationId;
    }

    /**
     * Set Year of the Citation
     * @param year Year of the Citation
     */
    public void setYear(String year){
        this.year=year;
    }

    /**
     * Setter for Citation Source
     * @param source Citation source
     */
    public void setSource(String source){
        this.source=source;
    }

    /**
     * Add an author to the Citation
     * @param author Author's name to be added
     */
    public void addAuthor(String author){
        authors.add(author);
    }

    /**
     * Set Xref of the Citation
     * @param ID Xref ID
     * @param dataSource Xref Data Source
     */
    public void setXref(String ID,String dataSource){
        xref = new Xref(ID, DataSource.getByFullName(dataSource));
    }

    /**
     * @return Get Citation Xref
     */
    public Xref getXref() {
        return xref;
    }

    /**
     * Get Citation ID
     * @return
     */
    public String getCitationId() {
        return citationId;
    }

    /**
     * @return Get Citation URL
     */
    public String getURL() {
        return URL;
    }

    /**
     * @return Get all Authors
     */
    public ArrayList<String> getAuthors() {
        return authors;
    }

    /**
     * @return Get Citation  Source
     */
    public String getSource() {
        return source;
    }

    /**
     * @return Get Citation Title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return Get Citation Year
     */
    public String getYear() {
        return year;
    }

}
