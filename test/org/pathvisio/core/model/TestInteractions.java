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

import java.io.File;
import java.io.IOException;

public class TestInteractions extends TestCase
{
	private static final File PATHVISIO_BASEDIR = new File (".");

	public static void testFail1() throws ConverterException, IOException
	{
		File in = new File (PATHVISIO_BASEDIR, "testData/2017a/Interactions/fail1.gpml");
		assertTrue (in.exists());

		Pathway pwy = new Pathway();
		pwy.readFromXml(in, true);
		pwy.updateInteractions();

		assertEquals(0,pwy.getInteractions().size());

		File tmp = File.createTempFile("test", "gpml");
		GpmlFormat2017a.GPML_2017A.writeToXml(pwy, tmp, true);
	}

	public static void testBasic1() throws ConverterException, IOException
	{
		File in = new File (PATHVISIO_BASEDIR, "testData/2017a/Interactions/basic1.gpml");
		assertTrue (in.exists());

		Pathway pwy = new Pathway();
		pwy.readFromXml(in, true);
		pwy.updateInteractions();

		assertEquals(1,pwy.getInteractions().size());
		for(Interaction interaction: pwy.getInteractions()) {
			assertEquals(1, interaction.getSource().size());
			assertEquals(1, interaction.getTarget().size());
		}
		File tmp = File.createTempFile("test", "gpml");
		GpmlFormat2017a.GPML_2017A.writeToXml(pwy, tmp, true);
	}

	public static void testBasic2() throws ConverterException, IOException
	{
		File in = new File (PATHVISIO_BASEDIR, "testData/2017a/Interactions/basic2.gpml");
		assertTrue (in.exists());

		Pathway pwy = new Pathway();
		pwy.readFromXml(in, true);
		pwy.updateInteractions();

		assertEquals(1,pwy.getInteractions().size());
		for(Interaction interaction: pwy.getInteractions()) {
			assertEquals(1, interaction.getSource().size());
			assertEquals(2, interaction.getTarget().size());
		}
		File tmp = File.createTempFile("test", "gpml");
		GpmlFormat2017a.GPML_2017A.writeToXml(pwy, tmp, true);
	}

	public static void testBasic3() throws ConverterException, IOException
	{
		File in = new File (PATHVISIO_BASEDIR, "testData/2017a/Interactions/basic3.gpml");
		assertTrue (in.exists());

		Pathway pwy = new Pathway();
		pwy.readFromXml(in, true);
		pwy.updateInteractions();

		assertEquals(1,pwy.getInteractions().size());
		for(Interaction interaction: pwy.getInteractions()) {
			assertEquals(2, interaction.getSource().size());
			assertEquals(1, interaction.getTarget().size());
		}

		File tmp = File.createTempFile("test", "gpml");
		GpmlFormat2017a.GPML_2017A.writeToXml(pwy, tmp, true);
	}

	public static void testBasic4() throws ConverterException, IOException
	{
		File in = new File (PATHVISIO_BASEDIR, "testData/2017a/Interactions/basic4.gpml");
		assertTrue (in.exists());

		Pathway pwy = new Pathway();
		pwy.readFromXml(in, true);
		pwy.updateInteractions();

		assertEquals(1,pwy.getInteractions().size());
		for(Interaction interaction: pwy.getInteractions()) {
			assertEquals(2, interaction.getSource().size());
			assertEquals(2, interaction.getTarget().size());
		}

		File tmp = File.createTempFile("test", "gpml");
		GpmlFormat2017a.GPML_2017A.writeToXml(pwy, tmp, true);
	}

	public static void testChain1() throws ConverterException, IOException
	{
		File in = new File (PATHVISIO_BASEDIR, "testData/2017a/Interactions/chain1.gpml");
		assertTrue (in.exists());

		Pathway pwy = new Pathway();
		pwy.readFromXml(in, true);
		pwy.updateInteractions();

		assertEquals(2,pwy.getInteractions().size());

		File tmp = File.createTempFile("test", "gpml");
		GpmlFormat2017a.GPML_2017A.writeToXml(pwy, tmp, true);
	}

	public static void testChain2() throws ConverterException, IOException
	{
		File in = new File (PATHVISIO_BASEDIR, "testData/2017a/Interactions/chain2.gpml");
		assertTrue (in.exists());

		Pathway pwy = new Pathway();
		pwy.readFromXml(in, true);
		pwy.updateInteractions();

		assertEquals(3,pwy.getInteractions().size());

		File tmp = File.createTempFile("test", "gpml");
		GpmlFormat2017a.GPML_2017A.writeToXml(pwy, tmp, true);
	}

	public static void testChain3() throws ConverterException, IOException
	{
		File in = new File (PATHVISIO_BASEDIR, "testData/2017a/Interactions/chain3.gpml");
		assertTrue (in.exists());

		Pathway pwy = new Pathway();
		pwy.readFromXml(in, true);
		pwy.updateInteractions();

		assertEquals(2,pwy.getInteractions().size());


		File tmp = File.createTempFile("test", "gpml");
		GpmlFormat2017a.GPML_2017A.writeToXml(pwy, tmp, true);
	}

	public static void testComplex1() throws ConverterException, IOException
	{
		File in = new File (PATHVISIO_BASEDIR, "testData/2013a/complex1.gpml");
		assertTrue (in.exists());

		Pathway pwy = new Pathway();
		pwy.readFromXml(in, true);
		pwy.updateInteractions();
		pwy.updateInteractions();

		assertEquals(1,pwy.getInteractions().size());
		for(Interaction interaction: pwy.getInteractions()) {
			assertEquals(3, interaction.getSource().size());
			assertEquals(2, interaction.getTarget().size());
		}
		PathwayElement target = PathwayElement.createPathwayElement(ObjectType.DATANODE);
		pwy.add(target);
		target.setParent(pwy);
		target.setGeneratedGraphId();
		PathwayElement line = PathwayElement.createPathwayElement(ObjectType.LINE);
		pwy.add(line);
		line.setParent(pwy);
		line.setGeneratedGraphId();
		line.getMStart().setGraphRef("f3835");
		line.getMEnd().setGraphRef(target.getGraphId());
		line.updateInteractions();
		Interaction interaction = line.getLineInteraction();
		assertEquals(1, interaction.getSource().size());
		assertEquals(1, interaction.getTarget().size());
		assertEquals(2, pwy.getInteractions().size());
//		File tmp = new File (PATHVISIO_BASEDIR, "testData/2017a/Interactions/complex1.gpml");
		File tmp = File.createTempFile("test", "gpml");
		GpmlFormat2017a.GPML_2017A.writeToXml(pwy, tmp, true);
	}
}
