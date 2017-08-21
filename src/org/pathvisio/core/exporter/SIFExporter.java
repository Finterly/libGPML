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

public class SIFExporter {

    private PathwayAsNetwork panPwy;
    private Pathway pvPwy;

    public SIFExporter(Pathway pathway){
        this.pvPwy = pathway;
        panPwy = new PathwayAsNetwork(pathway);
    }

    public void export(File file) throws IOException
    {
        FileOutputStream fout = (new FileOutputStream(file));
        fout.write(panPwy.toTSV().getBytes());
        fout.close();
    }

}
