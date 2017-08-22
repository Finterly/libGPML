// PathVisio,
// a tool for data visualization and analysis using Biological Pathways
// Copyright 2006-2011 BiGCaT Bioinformatics
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

import junit.framework.TestCase;
import org.pathvisio.core.exporter.BiopaxExporter;
import org.pathvisio.core.exporter.BiopaxExporterStrict;
import org.pathvisio.core.exporter.SIFExporter;
import org.pathvisio.core.util.FileUtils;

import java.io.File;
import java.io.IOException;

public class TestConversions extends TestCase
{
	private static final File BASEDIR = new File ("./testData/Pathways20170810/");

	public void testTo2017() throws ConverterException
	{
//		for(File pathwaysFolder: new File (BASEDIR,"2013a").listFiles())
//			for(File in:pathwaysFolder.listFiles()){
//				Pathway pwy = new Pathway();
//				pwy.readFromXml(in, true);
//				File out = new File(BASEDIR+"/2017/"+pathwaysFolder.getName(), in.getName());
//				GpmlFormat2017.GPML_2017.writeToXml(pwy, out, true);
//			}
	}

	public void testToBioPAX() throws ConverterException, IOException
	{
//		for(File pathwaysFolder: new File (BASEDIR,"2013a").listFiles())
//			for(File in:pathwaysFolder.listFiles()){
//				Pathway pwy = new Pathway();
//				pwy.readFromXml(in, true);
//				File out = new File(BASEDIR+"/BioPAX/"+pathwaysFolder.getName(), in.getName());
//				FileUtils.replaceExtension(out, "owl");
//				BiopaxExporterStrict biopaxExporter = new BiopaxExporterStrict(pwy);
//				biopaxExporter.export(out,false);
//			}
	}

	public void testToBioPAXOne() throws ConverterException, IOException
	{
//		String path = "./testData/Pathways20170810/2013a/wikipathways-20170810-gpml-Bos_taurus/Bt_Folate_Metabolism_WP1075_86818.gpml";
//		File in = new File (path);
//		Pathway pwy = new Pathway();
//		pwy.readFromXml(in, true);
//		File out = File.createTempFile("temp","owl");
//		BiopaxExporterStrict biopaxExporter = new BiopaxExporterStrict(pwy);
//		biopaxExporter.export(out,false);
	}

	public void testToSIF() throws ConverterException, IOException
	{
//		for(File pathwaysFolder: new File (BASEDIR,"2013a").listFiles())
//			for(File in:pathwaysFolder.listFiles()){
//				Pathway pwy = new Pathway();
//				pwy.readFromXml(in, true);
//				File out = new File(BASEDIR+"/SIF/"+pathwaysFolder.getName(), in.getName());
//				FileUtils.replaceExtension(out, "sif");
//				SIFExporter sifExporter = new SIFExporter(pwy);
//				sifExporter.export(out);
//			}
	}

}
