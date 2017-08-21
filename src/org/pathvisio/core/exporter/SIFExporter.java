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
