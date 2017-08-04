package org.pathvisio.core.exporter;

import com.sun.xml.bind.Util;
import org.pathvisio.core.model.DataNodeType;
import org.pathvisio.core.model.ObjectType;
import org.pathvisio.core.model.Pathway;
import org.pathvisio.core.model.PathwayElement;
import org.pathvisio.core.util.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BiopaxExporter {
    private Pathway pathway;
    BiopaxExporter(Pathway pathway){
        this.pathway = pathway;
    }

    public void export(File file, boolean doBpSs, int level) throws IOException{
        if(level==1){
            ArrayList<PathwayElement> toRemove = new ArrayList<>();
            // collect all Pathway Elements without Xrefs to ignore
            for(PathwayElement pathwayElement:pathway.getDataObjects())
                if(pathwayElement.getObjectType()== ObjectType.DATANODE
                        && pathwayElement.getXref().getDataSource()==null
                        // TODO: check why Xref for pathway is not required
                        && !pathwayElement.getDataNodeType().equals(DataNodeType.PATHWAY.toString()))
                    toRemove.add(pathwayElement);
            // remove them from the pathway
            for(PathwayElement pathwayElement:toRemove)
                pathway.remove(pathwayElement);
            // now export
            ExportHelper exportHelper = new ExportHelper(pathway);
            exportHelper.export(file, doBpSs);
        }
    }

}
