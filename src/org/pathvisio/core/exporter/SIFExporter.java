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
package org.pathvisio.core.exporter;

import org.biopax.paxtools.model.BioPAXLevel;
import org.biopax.paxtools.model.level3.BioSource;
import org.pathvisio.core.debug.Logger;
import org.pathvisio.core.model.GroupStyle;
import org.pathvisio.core.model.ObjectType;
import org.pathvisio.core.model.Pathway;
import org.pathvisio.core.model.PathwayElement;
import org.pathvisio.core.network.PathwayAsNetwork;
import org.pathvisio.core.util.FileUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Export Pathways in tab SIF format
 * , tab separated values of interactions
 * @author saurabh
 */
public class SIFExporter {

    private PathwayAsNetwork panPwy;
    private Pathway pvPwy;

    /**
     * constructor
     * @param pathway used to construct the PathwayAsNetwork object
     */
    public SIFExporter(Pathway pathway){
        this.pvPwy = pathway;
        panPwy = new PathwayAsNetwork(pathway);
    }

    /**
     * export to a file
     * @param file out file for export
     * @throws IOException
     */
    public void export(File file) throws IOException
    {
        FileOutputStream fout = (new FileOutputStream(file));
        fout.write(panPwy.toTSV().getBytes());
        fout.close();
    }

}
