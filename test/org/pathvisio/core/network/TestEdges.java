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
package org.pathvisio.core.network;

import junit.framework.TestCase;
import org.pathvisio.core.model.ConverterException;
import org.pathvisio.core.model.Pathway;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class TestEdges extends TestCase
{
	private static final File PATHVISIO_BASEDIR = new File (".");

	public static void testFail1() throws ConverterException, IOException
	{
		File in = new File (PATHVISIO_BASEDIR, "testData/2013a/Interactions/fail1.gpml");
		assertTrue (in.exists());

		Pathway pwy = new Pathway();
		pwy.readFromXml(in, true);
		PathwayAsNetwork pathwayAsNetwork = new PathwayAsNetwork(pwy);
		System.out.print(pathwayAsNetwork.toTSV());

		assertEquals(0,pathwayAsNetwork.getEdges().size());

	}

	public static void testBasic1() throws ConverterException, IOException
	{
		File in = new File (PATHVISIO_BASEDIR, "testData/2013a/Interactions/basic1.gpml");
		assertTrue (in.exists());

		Pathway pwy = new Pathway();
		pwy.readFromXml(in, true);
		PathwayAsNetwork pathwayAsNetwork = new PathwayAsNetwork(pwy);
		System.out.print(pathwayAsNetwork.toTSV());

		assertEquals(1,pathwayAsNetwork.getEdges().size());
	}

	public static void testBasic2() throws ConverterException, IOException
	{
		File in = new File (PATHVISIO_BASEDIR, "testData/2013a/Interactions/basic2.gpml");
		assertTrue (in.exists());

		Pathway pwy = new Pathway();
		pwy.readFromXml(in, true);
		PathwayAsNetwork pathwayAsNetwork = new PathwayAsNetwork(pwy);
		System.out.print(pathwayAsNetwork.toTSV());

		assertEquals(1,pathwayAsNetwork.getEdges().size());
	}

	public static void testBasic3() throws ConverterException, IOException
	{
		File in = new File (PATHVISIO_BASEDIR, "testData/2013a/Interactions/basic3.gpml");
		assertTrue (in.exists());

		Pathway pwy = new Pathway();
		pwy.readFromXml(in, true);
		PathwayAsNetwork pathwayAsNetwork = new PathwayAsNetwork(pwy);
		System.out.print(pathwayAsNetwork.toTSV());

		assertEquals(1,pathwayAsNetwork.getEdges().size());

	}

	public static void testBasic4() throws ConverterException, IOException
	{
		File in = new File (PATHVISIO_BASEDIR, "testData/2013a/Interactions/basic4.gpml");
		assertTrue (in.exists());

		Pathway pwy = new Pathway();
		pwy.readFromXml(in, true);
		PathwayAsNetwork pathwayAsNetwork = new PathwayAsNetwork(pwy);
		System.out.print(pathwayAsNetwork.toTSV());

		assertEquals(1,pathwayAsNetwork.getEdges().size());

	}

	public static void testRegulator() throws ConverterException, IOException
	{
		File in = new File (PATHVISIO_BASEDIR, "testData/2013a/Interactions/regulator1.gpml");
		assertTrue (in.exists());

		Pathway pwy = new Pathway();
		pwy.readFromXml(in, true);
		PathwayAsNetwork pathwayAsNetwork = new PathwayAsNetwork(pwy);
		System.out.print(pathwayAsNetwork.toTSV());

		assertEquals(1,pathwayAsNetwork.getEdges().size());

	}

	public static void testChain1() throws ConverterException, IOException
	{
		File in = new File (PATHVISIO_BASEDIR, "testData/2013a/Interactions/chain1.gpml");
		assertTrue (in.exists());

		Pathway pwy = new Pathway();
		pwy.readFromXml(in, true);
		PathwayAsNetwork pathwayAsNetwork = new PathwayAsNetwork(pwy);
		System.out.print(pathwayAsNetwork.toTSV());

		assertEquals(2,pathwayAsNetwork.getEdges().size());

	}

	public static void testChain2() throws ConverterException, IOException
	{
		File in = new File (PATHVISIO_BASEDIR, "testData/2013a/Interactions/chain2.gpml");
		assertTrue (in.exists());

		Pathway pwy = new Pathway();
		pwy.readFromXml(in, true);
		PathwayAsNetwork pathwayAsNetwork = new PathwayAsNetwork(pwy);
		System.out.print(pathwayAsNetwork.toTSV());

		assertEquals(3,pathwayAsNetwork.getEdges().size());

	}

	public static void testChain3() throws ConverterException, IOException
	{
		File in = new File (PATHVISIO_BASEDIR, "testData/2013a/Interactions/chain3.gpml");
		assertTrue (in.exists());

		Pathway pwy = new Pathway();
		pwy.readFromXml(in, true);
		PathwayAsNetwork pathwayAsNetwork = new PathwayAsNetwork(pwy);
		System.out.print(pathwayAsNetwork.toTSV());

		assertEquals(2,pathwayAsNetwork.getEdges().size());

	}

	public static void testComplex1() throws ConverterException, IOException
	{
		File in = new File (PATHVISIO_BASEDIR, "testData/2013a/Interactions/complex1.gpml");
		assertTrue (in.exists());

		Pathway pwy = new Pathway();
		pwy.readFromXml(in, true);
		PathwayAsNetwork pathwayAsNetwork = new PathwayAsNetwork(pwy);
		System.out.print(pathwayAsNetwork.toTSV());

		assertEquals(1,pathwayAsNetwork.getEdges().size());
	}

	public static void testComplete() throws ConverterException, IOException
	{
		File in = new File (PATHVISIO_BASEDIR, "testData/2017a/out.gpml");
		assertTrue (in.exists());

		Pathway pwy = new Pathway();
		pwy.readFromXml(in, true);
		PathwayAsNetwork pathwayAsNetwork = new PathwayAsNetwork(pwy);

		try{
			PrintWriter writer = new PrintWriter("testData/2017a/interactions.txt", "UTF-8");
			writer.print(pathwayAsNetwork.toTSV());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
