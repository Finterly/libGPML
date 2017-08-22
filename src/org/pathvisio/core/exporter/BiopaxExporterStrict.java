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
import org.pathvisio.core.model.*;
import org.pathvisio.core.network.Edge;
import org.pathvisio.core.network.Node;
import org.pathvisio.core.network.PathwayAsNetwork;
import org.pathvisio.core.util.FileUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;

/**
 * Strict BioPAX Exporter
 * avoids no.xref error by excluding any data node without proper Xref
 * and any dangling data nodes
 * @author saurabh
 */
public class BiopaxExporterStrict extends ExportHelper{

    private PathwayAsNetwork panPwy;

    /**
     * constructor
     * @param pathway map pathway to BioPAX
     */
    public BiopaxExporterStrict(Pathway pathway) {
        this.pvPwy = pathway;
        factory = BioPAXLevel.L3.getDefaultFactory();
        bpModel = factory.createModel();
        panPwy = new PathwayAsNetwork(pvPwy);

        HashSet<PathwayElement> toKeep = new HashSet<>(), toRemove = new HashSet<>();
        for (Edge edge : panPwy.getEdges()) {
            for (Node node : edge.getSources())
                toKeep.addAll(node.getDataNodes());
            for (Node node : edge.getTargets())
                if (!node.isReactionNode())
                    toKeep.addAll(node.getDataNodes());
        }

        for (PathwayElement pe : pvPwy.getDataObjects())
            if (!toKeep.contains(pe) && pe.getObjectType() == ObjectType.DATANODE && !pe.getDataNodeType().equals(DataNodeType.PATHWAY.toString()))
                toRemove.add(pe);
        for (PathwayElement pe : toRemove){
            pvPwy.remove(pe);
       }
        mapPathway();
    }

    private void mapPathway()
    {
        PathwayElement info = pvPwy.getMappInfo();

        bpPwy = bpModel.addNew (org.biopax.paxtools.model.level3.Pathway.class, generateRdfId());
        transferComments(bpPwy, info);
        bpPwy.setDisplayName(info.getMapInfoName());

//        Use first 25 characters of the Pathway name for WARNING: too.long.display.name
//        recommended maximum is 25 characters

//        bpPwy.setDisplayName(info.getMapInfoName().substring(0,25));

        if (info.getOrganism() != null)
        {
            organism = bpModel.addNew (BioSource.class, generateRdfId());
            organism.setStandardName(info.getOrganism());
            organism.setDisplayName(info.getOrganism());
            bpPwy.setOrganism(organism);
        }

        for (PathwayElement pwElm : pvPwy.getDataObjects())
        {
            // is it a gene, protein or metabolite?
            if (pwElm.getObjectType() == ObjectType.DATANODE)
            {
                createOrGetPhysicalEntity (pwElm);
            }

            // is it a complex (NB not every group automatically counts as a complex)
            if (pwElm.getObjectType() == ObjectType.GROUP &&
                    pwElm.getGroupStyle() == GroupStyle.COMPLEX)
            {
                createOrGetPhysicalEntity (pwElm);
            }

            // is it a biochemical reaction or other relation?
            if(isRelation(pvPwy, pwElm))
            {
                mapRelation(pwElm);
            }

            // is it a line that can not be represented in BioPAX?
            if (pwElm.getObjectType() == ObjectType.LINE){
                if((pwElm.getStartGraphRef() == null) || (pwElm.getEndGraphRef() == null)){
                    Logger.log.info("This pathway contains an incorrect arrow");
                }
            }
        }
    }

    /**
     * export
     * @param file out file to export
     * @param doBpSs to include a stylesheet file
     * @throws IOException
     */
    public void export(File file, boolean doBpSs) throws IOException
    {
        exporter.convertToOWL(bpModel,
                new BufferedOutputStream(new FileOutputStream(file)));
        if (doBpSs)
        {
            File fnSs = FileUtils.replaceExtension(file, "bpss");
            FileOutputStream fos = new FileOutputStream (fnSs);
            bpss.write(fos);
            fos.close();
        }
    }

}
