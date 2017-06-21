// PathVisio,
// a tool for data visualization and analysis using Biological Pathways
// Copyright 2006-2015 BiGCaT Bioinformatics
//
// Licensed under the Apache License, Version 2.0 (the "Llcense");
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
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.pathvisio.core.biopax.BiopaxElement;
import org.pathvisio.core.model.PathwayElement.MAnchor;
import org.pathvisio.core.model.PathwayElement.MPoint;
import org.pathvisio.core.view.ShapeRegistry;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.List;

class GpmlFormat2017a extends GpmlFormatAbstract2017a implements GpmlFormatReader, GpmlFormatWriter
{
	public static final GpmlFormat2017a GPML_2017A = new GpmlFormat2017a(
			"GPML2017a.xsd", Namespace.getNamespace("http://pathvisio.org/GPML/2017a")
		);

	public GpmlFormat2017a(String xsdFile, Namespace ns) {
		super (xsdFile, ns);
	}

	private static final Map<String, AttributeInfo> ATTRIBUTE_INFO = initAttributeInfo();

	private static Map<String, AttributeInfo> initAttributeInfo()
	{
		Map<String, AttributeInfo> result = new HashMap<String, AttributeInfo>();
		// IMPORTANT: this array has been generated from the xsd with
		// an automated perl script. Don't edit this directly, use the perl script instead.
		/* START OF AUTO-GENERATED CONTENT */
		result.put("Comment@source", new AttributeInfo ("xsd:string", null, "optional"));
		result.put("PublicationXref@ID", new AttributeInfo ("xsd:string", null, "required"));
		result.put("PublicationXref@dataSource", new AttributeInfo ("xsd:string", null, "required"));
		result.put("Attribute@key", new AttributeInfo ("xsd:string", null, "required"));
		result.put("Attribute@value", new AttributeInfo ("xsd:string", null, "required"));
		result.put("Pathway@boardWidth", new AttributeInfo ("gpml:Dimension", null, "optional"));
		result.put("Pathway@boardHeight", new AttributeInfo ("gpml:Dimension", null, "optional"));
		result.put("Pathway@name", new AttributeInfo ("xsd:string", null, "required"));
		result.put("Pathway@organism", new AttributeInfo ("xsd:string", null, "optional"));
		result.put("Pathway@dataSource", new AttributeInfo ("xsd:string", null, "optional"));
		result.put("Pathway@version", new AttributeInfo ("xsd:string", null, "optional"));
		result.put("Pathway@author", new AttributeInfo ("xsd:string", null, "optional"));
		result.put("Pathway@maintainer", new AttributeInfo ("xsd:string", null, "optional"));
		result.put("Pathway@email", new AttributeInfo ("xsd:string", null, "optional"));
		result.put("Pathway@license", new AttributeInfo ("xsd:string", null, "optional"));
		result.put("Pathway@lastModified", new AttributeInfo ("xsd:string", null, "optional"));
		result.put("DataNode@centerX", new AttributeInfo ("xsd:float", null, "required"));
		result.put("DataNode@centerY", new AttributeInfo ("xsd:float", null, "required"));
		result.put("DataNode@width", new AttributeInfo ("gpml:Dimension", null, "required"));
		result.put("DataNode@height", new AttributeInfo ("gpml:Dimension", null, "required"));
		result.put("DataNode@fontName", new AttributeInfo ("xsd:string", "Arial", "optional"));
		result.put("DataNode@fontStyle", new AttributeInfo ("xsd:string", "Normal", "optional"));
		result.put("DataNode@fontDecoration", new AttributeInfo ("xsd:string", "Normal", "optional"));
		result.put("DataNode@fontStrikethru", new AttributeInfo ("xsd:string", "Normal", "optional"));
		result.put("DataNode@fontWeight", new AttributeInfo ("xsd:string", "Normal", "optional"));
		result.put("DataNode@fontSize", new AttributeInfo ("xsd:nonNegativeInteger", "12", "optional"));
		result.put("DataNode@align", new AttributeInfo ("xsd:string", "Center", "optional"));
		result.put("DataNode@vAlign", new AttributeInfo ("xsd:string", "Top", "optional"));
		result.put("DataNode@color", new AttributeInfo ("gpml:ColorType", "Black", "optional"));
		result.put("DataNode@lineStyle", new AttributeInfo ("gpml:StyleType", "Solid", "optional"));
		result.put("DataNode@lineThickness", new AttributeInfo ("xsd:float", "1.0", "optional"));
		result.put("DataNode@fillColor", new AttributeInfo ("gpml:ColorType", "White", "optional"));
		result.put("DataNode@shapeType", new AttributeInfo ("xsd:string", "Rectangle", "optional"));
		result.put("DataNode@zOrder", new AttributeInfo ("xsd:integer", null, "optional"));
		result.put("DataNode.Xref@dataSource", new AttributeInfo ("xsd:string", null, "required"));
		result.put("DataNode.Xref@ID", new AttributeInfo ("xsd:string", null, "required"));
		result.put("DataNode@graphId", new AttributeInfo ("xsd:ID", null, "required"));
		result.put("DataNode@groupRef", new AttributeInfo ("xsd:string", null, "optional"));
		result.put("DataNode@textLabel", new AttributeInfo ("xsd:string", null, "required"));
		result.put("DataNode@type", new AttributeInfo ("xsd:string", "Unknown", "optional"));
		result.put("State@relX", new AttributeInfo ("xsd:float", null, "required"));
		result.put("State@relY", new AttributeInfo ("xsd:float", null, "required"));
		result.put("State@width", new AttributeInfo ("gpml:Dimension", null, "required"));
		result.put("State@height", new AttributeInfo ("gpml:Dimension", null, "required"));
		result.put("State@color", new AttributeInfo ("gpml:ColorType", "Black", "optional"));
		result.put("State@lineStyle", new AttributeInfo ("gpml:StyleType", "Solid", "optional"));
		result.put("State@lineThickness", new AttributeInfo ("xsd:float", "1.0", "optional"));
		result.put("State@fillColor", new AttributeInfo ("gpml:ColorType", "White", "optional"));
		result.put("State@shapeType", new AttributeInfo ("xsd:string", "Rectangle", "optional"));
		result.put("State@zOrder", new AttributeInfo ("xsd:integer", null, "optional"));
		result.put("State.Xref@dataSource", new AttributeInfo ("xsd:string", null, "required"));
		result.put("State.Xref@ID", new AttributeInfo ("xsd:string", null, "required"));
		result.put("State@graphId", new AttributeInfo ("xsd:ID", null, "required"));
		result.put("State@graphRef", new AttributeInfo ("xsd:IDREF", null, "optional"));
		result.put("State@textLabel", new AttributeInfo ("xsd:string", null, "required"));
		result.put("State@stateType", new AttributeInfo ("xsd:string", "Unknown", "optional"));
		result.put("GraphicalLine.Point@x", new AttributeInfo ("xsd:float", null, "required"));
		result.put("GraphicalLine.Point@y", new AttributeInfo ("xsd:float", null, "required"));
		result.put("GraphicalLine.Point@relX", new AttributeInfo ("xsd:float", null, "optional"));
		result.put("GraphicalLine.Point@relY", new AttributeInfo ("xsd:float", null, "optional"));
		result.put("GraphicalLine.Point@graphRef", new AttributeInfo ("xsd:IDREF", null, "optional"));
		result.put("GraphicalLine.Point@graphId", new AttributeInfo ("xsd:ID", null, "required"));
		result.put("GraphicalLine.Point@arrowHead", new AttributeInfo ("xsd:string", "Line", "optional"));
		result.put("GraphicalLine.Anchor@position", new AttributeInfo ("xsd:float", null, "required"));
		result.put("GraphicalLine.Anchor@graphId", new AttributeInfo ("xsd:ID", null, "required"));
		result.put("GraphicalLine.Anchor@shape", new AttributeInfo ("xsd:string", "ReceptorRound", "optional"));
		result.put("GraphicalLine@color", new AttributeInfo ("gpml:ColorType", "Black", "optional"));
		result.put("GraphicalLine@lineThickness", new AttributeInfo ("xsd:float", null, "optional"));
		result.put("GraphicalLine@lineStyle", new AttributeInfo ("gpml:StyleType", "Solid", "optional"));
		result.put("GraphicalLine@connectorType", new AttributeInfo ("xsd:string", "Straight", "optional"));
		result.put("GraphicalLine@zOrder", new AttributeInfo ("xsd:integer", null, "optional"));
		result.put("GraphicalLine@groupRef", new AttributeInfo ("xsd:string", null, "optional"));
		result.put("GraphicalLine@graphId", new AttributeInfo ("xsd:ID", null, "required"));
		result.put("GraphicalLine@type", new AttributeInfo ("xsd:string", null, "optional"));
		result.put("Interaction.Point@x", new AttributeInfo ("xsd:float", null, "required"));
		result.put("Interaction.Point@y", new AttributeInfo ("xsd:float", null, "required"));
		result.put("Interaction.Point@relX", new AttributeInfo ("xsd:float", null, "optional"));
		result.put("Interaction.Point@relY", new AttributeInfo ("xsd:float", null, "optional"));
		result.put("Interaction.Point@graphRef", new AttributeInfo ("xsd:IDREF", null, "optional"));
		result.put("Interaction.Point@graphId", new AttributeInfo ("xsd:ID", null, "required"));
		result.put("Interaction.Point@arrowHead", new AttributeInfo ("xsd:string", "Line", "optional"));
		result.put("Interaction.Anchor@position", new AttributeInfo ("xsd:float", null, "required"));
		result.put("Interaction.Anchor@graphId", new AttributeInfo ("xsd:ID", null, "required"));
		result.put("Interaction.Anchor@shape", new AttributeInfo ("xsd:string", "ReceptorRound", "optional"));
		result.put("Interaction@color", new AttributeInfo ("gpml:ColorType", "Black", "optional"));
		result.put("Interaction@lineThickness", new AttributeInfo ("xsd:float", null, "optional"));
		result.put("Interaction@lineStyle", new AttributeInfo ("gpml:StyleType", "Solid", "optional"));
		result.put("Interaction@connectorType", new AttributeInfo ("xsd:string", "Straight", "optional"));
		result.put("Interaction@zOrder", new AttributeInfo ("xsd:integer", null, "optional"));
		result.put("Interaction.Xref@dataSource", new AttributeInfo ("xsd:string", null, "required"));
		result.put("Interaction.Xref@ID", new AttributeInfo ("xsd:string", null, "required"));
		result.put("Interaction@groupRef", new AttributeInfo ("xsd:string", null, "optional"));
		result.put("Interaction@graphId", new AttributeInfo ("xsd:ID", null, "required"));
		result.put("Interaction@type", new AttributeInfo ("xsd:string", null, "optional"));
		result.put("Label@centerX", new AttributeInfo ("xsd:float", null, "required"));
		result.put("Label@centerY", new AttributeInfo ("xsd:float", null, "required"));
		result.put("Label@width", new AttributeInfo ("gpml:Dimension", null, "required"));
		result.put("Label@height", new AttributeInfo ("gpml:Dimension", null, "required"));
		result.put("Label@fontName", new AttributeInfo ("xsd:string", "Arial", "optional"));
		result.put("Label@fontStyle", new AttributeInfo ("xsd:string", "Normal", "optional"));
		result.put("Label@fontDecoration", new AttributeInfo ("xsd:string", "Normal", "optional"));
		result.put("Label@fontStrikethru", new AttributeInfo ("xsd:string", "Normal", "optional"));
		result.put("Label@fontWeight", new AttributeInfo ("xsd:string", "Normal", "optional"));
		result.put("Label@fontSize", new AttributeInfo ("xsd:nonNegativeInteger", "12", "optional"));
		result.put("Label@align", new AttributeInfo ("xsd:string", "Center", "optional"));
		result.put("Label@vAlign", new AttributeInfo ("xsd:string", "Top", "optional"));
		result.put("Label@color", new AttributeInfo ("gpml:ColorType", "Black", "optional"));
		result.put("Label@lineStyle", new AttributeInfo ("gpml:StyleType", "Solid", "optional"));
		result.put("Label@lineThickness", new AttributeInfo ("xsd:float", "1.0", "optional"));
		result.put("Label@fillColor", new AttributeInfo ("gpml:ColorType", "Transparent", "optional"));
		result.put("Label@shapeType", new AttributeInfo ("xsd:string", "None", "optional"));
		result.put("Label@zOrder", new AttributeInfo ("xsd:integer", null, "optional"));
		result.put("Label@href", new AttributeInfo ("xsd:string", null, "optional"));
		result.put("Label@graphId", new AttributeInfo ("xsd:ID", null, "required"));
		result.put("Label@groupRef", new AttributeInfo ("xsd:string", null, "optional"));
		result.put("Label@textLabel", new AttributeInfo ("xsd:string", null, "required"));
		result.put("Shape@centerX", new AttributeInfo ("xsd:float", null, "required"));
		result.put("Shape@centerY", new AttributeInfo ("xsd:float", null, "required"));
		result.put("Shape@width", new AttributeInfo ("gpml:Dimension", null, "required"));
		result.put("Shape@height", new AttributeInfo ("gpml:Dimension", null, "required"));
		result.put("Shape@fontName", new AttributeInfo ("xsd:string", "Arial", "optional"));
		result.put("Shape@fontStyle", new AttributeInfo ("xsd:string", "Normal", "optional"));
		result.put("Shape@fontDecoration", new AttributeInfo ("xsd:string", "Normal", "optional"));
		result.put("Shape@fontStrikethru", new AttributeInfo ("xsd:string", "Normal", "optional"));
		result.put("Shape@fontWeight", new AttributeInfo ("xsd:string", "Normal", "optional"));
		result.put("Shape@fontSize", new AttributeInfo ("xsd:nonNegativeInteger", "12", "optional"));
		result.put("Shape@align", new AttributeInfo ("xsd:string", "Center", "optional"));
		result.put("Shape@vAlign", new AttributeInfo ("xsd:string", "Top", "optional"));
		result.put("Shape@color", new AttributeInfo ("gpml:ColorType", "Black", "optional"));
		result.put("Shape@lineStyle", new AttributeInfo ("gpml:StyleType", "Solid", "optional"));
		result.put("Shape@lineThickness", new AttributeInfo ("xsd:float", "1.0", "optional"));
		result.put("Shape@fillColor", new AttributeInfo ("gpml:ColorType", "Transparent", "optional"));
		result.put("Shape@shapeType", new AttributeInfo ("xsd:string", null, "required"));
		result.put("Shape@zOrder", new AttributeInfo ("xsd:integer", null, "optional"));
		result.put("Shape@rotation", new AttributeInfo ("gpml:RotationType", "Top", "optional"));
		result.put("Shape@graphId", new AttributeInfo ("xsd:ID", null, "required"));
		result.put("Shape@groupRef", new AttributeInfo ("xsd:string", null, "optional"));
		result.put("Shape@textLabel", new AttributeInfo ("xsd:string", null, "optional"));
		result.put("Group@groupId", new AttributeInfo ("xsd:string", null, "optional"));
		result.put("Group@groupRef", new AttributeInfo ("xsd:string", null, "optional"));
		result.put("Group@style", new AttributeInfo ("xsd:string", "None", "optional"));
		result.put("Group@textLabel", new AttributeInfo ("xsd:string", null, "optional"));
		result.put("Group@graphId", new AttributeInfo ("xsd:ID", null, "required"));
		result.put("InfoBox@centerX", new AttributeInfo ("xsd:float", null, "required"));
		result.put("InfoBox@centerY", new AttributeInfo ("xsd:float", null, "required"));
		result.put("Legend@centerX", new AttributeInfo ("xsd:float", null, "required"));
		result.put("Legend@centerY", new AttributeInfo ("xsd:float", null, "required"));
		result.put("Citations.Citation.Xref@dataSource", new AttributeInfo ("xsd:string", null, "required"));
		result.put("Citations.Citation.Xref@ID", new AttributeInfo ("xsd:string", null, "required"));
		result.put("Citations.Citation.Author@name", new AttributeInfo ("xsd:string", null, "required"));
		result.put("Citations.Citation@citationId", new AttributeInfo ("xsd:string", null, "required"));
		result.put("Citations.Citation@title", new AttributeInfo ("xsd:string", null, "required"));
		result.put("Citations.Citation@URL", new AttributeInfo ("xsd:string", null, "required"));
		result.put("Citations.Citation@source", new AttributeInfo ("xsd:string", null, "optional"));
		result.put("Citations.Citation@year", new AttributeInfo ("xsd:string", null, "optional"));
		result.put("OntologyTerms.OntologyTerm@ID", new AttributeInfo ("xsd:string", null, "required"));
		result.put("OntologyTerms.OntologyTerm@term", new AttributeInfo ("xsd:string", null, "required"));
		result.put("OntologyTerms.OntologyTerm@ontology", new AttributeInfo ("xsd:string", null, "required"));
		result.put("OntologyTerms.OntologyTerm@ontologyTermId", new AttributeInfo ("xsd:string", null, "required"));
		/* END OF AUTO-GENERATED CONTENT */

		return result;
	}

	@Override
	protected Map<String, AttributeInfo> getAttributeInfo() 
	{
		return ATTRIBUTE_INFO;
	}

	@Override
	protected void mapMappInfoDataVariable(PathwayElement o, Element e)
			throws ConverterException {
		o.setCopyright (getAttribute("Pathway", "license", e));
	}

	@Override
	protected void updateMappInfoVariable(Element root, PathwayElement o)
			throws ConverterException {
		setAttribute("Pathway", "license", root, o.getCopyright());
	}

	private void updateCommon(PathwayElement o, Element e) throws ConverterException
	{
		updateComments(o, e);
		updateBiopaxRef(o, e);
		updateAttributes(o, e);
	}

	private void mapCommon(PathwayElement o, Element e) throws ConverterException
	{
		mapComments(o, e);
		mapBiopaxRef(o, e);
		mapAttributes(o, e);
	}

	// common to Label, Shape, State, DataNode
	private void updateShapeCommon(PathwayElement o, Element e) throws ConverterException
	{
		updateShapeColor(o, e); // FillColor and Transparent
		updateFontData(o, e); // TextLabel. FontName, -Weight, -Style, -Decoration, -StrikeThru, -Size.
		updateGraphId(o, e); // GraphId
		updateShapeType(o, e); // ShapeType
		updateLineStyle(o, e); // LineStyle, LineThickness, Color
	}

	// common to Label, Shape, State, DataNode
	private void mapShapeCommon(PathwayElement o, Element e) throws ConverterException
	{
		mapShapeColor(o, e); // FillColor and Transparent
		mapFontData(o, e); // TextLabel. FontName, -Weight, -Style, -Decoration, -StrikeThru, -Size.
		mapGraphId(o, e);
		mapShapeType(o, e); // ShapeType
	}

	public Element createJdomElement(PathwayElement o) throws ConverterException
	{
		Element e = null;
		switch (o.getObjectType())
		{
			case DATANODE:
				e = new Element("DataNode", getGpmlNamespace());
				updateCommon (o, e);
				e.addContent(new Element("Xref", getGpmlNamespace()));
				updateShapePosition(o, e);
				updateShapeCommon(o, e);
				updateDataNode(o, e); // Type & Xref
				updateGroupRef(o, e);
				break;
			case STATE:
				e = new Element("State", getGpmlNamespace());
				updateCommon (o, e);
				e.addContent(new Element("Xref", getGpmlNamespace()));
				updateStateData(o, e);
				updateShapeCommon(o, e);
				break;
			case SHAPE:
				e = new Element ("Shape", getGpmlNamespace());
				updateCommon (o, e);
				updateShapePosition(o, e);
				updateShapeCommon(o, e);
				updateRotation(o, e);
				updateGroupRef(o, e);
				break;
			case LINE:
				e = new Element("Interaction", getGpmlNamespace());
				updateCommon (o, e);
				e.addContent(new Element("Xref", getGpmlNamespace()));
				updateLine(o, e); // Xref
				updateLineData(o, e);
				updateLineStyle(o, e);
				updateGraphId(o, e);
				updateGroupRef(o, e);
				break;
			case GRAPHLINE:
				e = new Element("GraphicalLine", getGpmlNamespace());
				updateCommon (o, e);
				updateLineData(o, e);
				updateLineStyle(o, e);
				updateGraphId(o, e);
				updateGroupRef(o, e);
				break;	
			case LABEL:
				e = new Element("Label", getGpmlNamespace());
				updateCommon (o, e);
				updateShapePosition(o, e);
				updateShapeCommon(o, e);
				updateHref(o, e);
				updateGroupRef(o, e);
				break;
			case LEGEND:
				e = new Element ("Legend", getGpmlNamespace());
				updateSimpleCenter (o, e);
				break;
			case INFOBOX:
				e = new Element ("InfoBox", getGpmlNamespace());
				updateSimpleCenter (o, e);
				break;
			case GROUP:
				e = new Element ("Group", getGpmlNamespace());
				updateCommon (o, e);
				updateGroup (o, e);
				updateGroupRef(o, e);
				break;
			case BIOPAX:
				e = new Element ("Biopax", getGpmlNamespace());
				updateBiopax(o, e);
				break;
			case CITATION:
				e = new Element("Citations", getGpmlNamespace());
				updateCitations(o, e);
				break;
			case ONTOLOGY:
				e = new Element("OntologyTerms", getGpmlNamespace());
				updateOntologyTags (o, e);
				break;
		}
		if (e == null)
		{
			throw new ConverterException ("Error creating jdom element with objectType " + o.getObjectType());
		}
		return e;
	}

	/**
	   Create a single PathwayElement based on a piece of Jdom tree. Used also by Patch utility
	   Pathway p may be null
	 */
	public PathwayElement mapElement(Element e, Pathway p) throws ConverterException
	{
		String tag = e.getName();
		if(tag.equalsIgnoreCase("Interaction")){
			tag = "Line";
		}
		ObjectType ot = ObjectType.getTagMapping(tag);
		if (ot == null)
		{
			// do nothing. This could be caused by
			// tags <comment> or <graphics> that appear
			// as subtags of <pathway>
			return null;
		}

		PathwayElement o = PathwayElement.createPathwayElement(ot);
		if (p != null)
		{
			p.add (o);
		}

		switch (o.getObjectType())
		{
			case DATANODE:
				mapCommon(o, e);
				mapShapePosition(o, e);
				mapShapeCommon(o, e);
				mapDataNode(o, e);
				mapGroupRef(o, e);
				break;
			case STATE:
				mapCommon(o, e);
				mapStateData(o, e);
				mapShapeCommon(o, e);
				break;
			case LABEL:
				mapCommon(o, e);
				mapShapePosition(o, e);
				mapShapeCommon(o, e);
				mapGroupRef(o, e);
				mapHref(o, e);
				break;
			case LINE:
				mapCommon(o, e);
				mapLine(o,e);
				mapLineData(o, e); // Points, ConnectorType, ZOrder
				mapLineStyle(o, e); // LineStyle, LineThickness, Color
				mapGraphId(o, e);
				mapGroupRef(o, e);
				break;
			case GRAPHLINE:
				mapCommon(o, e);
				mapLineData(o, e); // Points, ConnectorType, ZOrder
				mapLineStyle(o, e); // LineStyle, LineThickness, Color
				mapGraphId(o, e);
				mapGroupRef(o, e);
				break;	
			case MAPPINFO:
				mapCommon(o, e);
				mapMappInfoData(o, e);
				break;
			case SHAPE:
				mapCommon(o, e);
				mapShapePosition(o, e);
				mapShapeCommon(o, e);
				mapRotation(o, e);
				mapGroupRef(o, e);
				break;
			case LEGEND:
				mapSimpleCenter(o, e);
				break;
			case INFOBOX:
				mapSimpleCenter (o, e);
				break;
			case GROUP:
				mapCommon(o, e);
				mapGroupRef(o, e);
				mapGroup (o, e);
				break;
			case CITATION:
				mapCitations(o, e, p);
				break;
			case ONTOLOGY:
				mapOntology(o, e, p);
				break;
			default:
				throw new ConverterException("Invalid ObjectType'" + tag + "'");
		}
		return o;
	}

	protected void mapRotation(PathwayElement o, Element e) throws ConverterException
	{
    	String rotation = getAttribute("Shape", "rotation", e);
    	double result;
    	if (rotation.equals("Top"))
    	{
    		result = 0.0;
    	}
    	else if (rotation.equals("Right"))
		{
    		result = 0.5 * Math.PI;
		}
    	else if (rotation.equals("Bottom"))
    	{
    		result = Math.PI;
    	}
    	else if (rotation.equals("Left"))
    	{
    		result = 1.5 * Math.PI;
    	}
    	else
    	{
    		result = Double.parseDouble(rotation);
    	}
    	o.setRotation (result);
	}
	
	/**
	 * Converts deprecated shapes to contemporary analogs. This allows us to
	 * maintain backward compatibility while at the same time cleaning up old
	 * shape usages.
	 * 
	 */ 
	protected void mapShapeType(PathwayElement o, Element e) throws ConverterException
	{
		String base = e.getName();
    	IShape s= ShapeRegistry.fromName(getAttribute(base, "shapeType", e));
    	if (ShapeType.DEPRECATED_MAP.containsKey(s)){
    		s = ShapeType.DEPRECATED_MAP.get(s);
    		o.setShapeType(s);
       		if (s.equals(ShapeType.ROUNDED_RECTANGLE) 
       				|| s.equals(ShapeType.OVAL)){
    			o.setLineStyle(LineStyle.DOUBLE);
    			o.setLineThickness(3.0);
    			o.setColor(Color.LIGHT_GRAY);
    		}
    	} 
    	else 
    	{
    	o.setShapeType (s);
		mapLineStyle(o, e); // LineStyle
    	}
	}

	protected void updateRotation(PathwayElement o, Element e) throws ConverterException
	{
		setAttribute("Shape", "rotation", e, Double.toString(o.getRotation()));
	}
	
	protected void updateShapeType(PathwayElement o, Element e) throws ConverterException
	{
		String base = e.getName();
		String shapeName = o.getShapeType().getName();
		setAttribute(base, "shapeType", e, shapeName);
	}
	
	protected void updateHref(PathwayElement o, Element e) throws ConverterException
	{
		setAttribute ("Label", "href", e, o.getHref());
	}
	
	protected void mapHref(PathwayElement o, Element e) throws ConverterException
	{
		o.setHref(getAttribute("Label", "href", e));
	}

	protected void mapFontData(PathwayElement o, Element e) throws ConverterException
	{
		String base = e.getName();
		o.setTextLabel (getAttribute(base, "textLabel", e));

		// TODO dirty hack: the fact that state doesn't allow font data is a bug 
		if (e.getName().equals ("State")) return;
		
    	String fontSizeString = getAttribute(base, "fontSize", e);
    	o.setMFontSize (Integer.parseInt(fontSizeString));

    	String fontWeight = getAttribute(base, "fontWeight", e);
    	String fontStyle = getAttribute(base, "fontStyle", e);
    	String fontDecoration = getAttribute(base, "fontDecoration", e);
    	String fontStrikethru = getAttribute(base, "fontStrikethru", e);

    	o.setBold (fontWeight != null && fontWeight.equals("Bold"));
    	o.setItalic (fontStyle != null && fontStyle.equals("Italic"));
    	o.setUnderline (fontDecoration != null && fontDecoration.equals("Underline"));
    	o.setStrikethru (fontStrikethru != null && fontStrikethru.equals("Strikethru"));
    	
    	o.setFontName (getAttribute(base, "fontName", e));
	    
		o.setValign(ValignType.fromGpmlName(getAttribute(base, "vAlign", e)));
		o.setAlign(AlignType.fromGpmlName(getAttribute(base, "align", e)));
	}
	
	protected void updateFontData(PathwayElement o, Element e) throws ConverterException
	{
		String base = e.getName();
		setAttribute(base, "textLabel", e, o.getTextLabel());

		// TODO dirty hack: the fact that state doesn't allow font data is a bug 
		if (e.getName().equals ("State")) return;
		
		setAttribute(base, "fontName", e, o.getFontName() == null ? "" : o.getFontName());
		setAttribute(base, "fontWeight", e, o.isBold() ? "Bold" : "Normal");
		setAttribute(base, "fontStyle", e, o.isItalic() ? "Italic" : "Normal");
		setAttribute(base, "fontDecoration", e, o.isUnderline() ? "Underline" : "Normal");
		setAttribute(base, "fontStrikethru", e, o.isStrikethru() ? "Strikethru" : "Normal");
		setAttribute(base, "fontSize", e, Integer.toString((int)o.getMFontSize()));
		setAttribute(base, "vAlign", e, o.getValign().getGpmlName());
		setAttribute(base, "align", e, o.getAlign().getGpmlName());
	}

	protected void mapShapePosition(PathwayElement o, Element e) throws ConverterException
	{
		String base = e.getName();
    	o.setMCenterX (Double.parseDouble(getAttribute(base , "centerX", e)));
    	o.setMCenterY (Double.parseDouble(getAttribute(base , "centerY", e)));
		o.setMWidth (Double.parseDouble(getAttribute(base , "width", e)));
		o.setMHeight (Double.parseDouble(getAttribute(base , "height", e)));
		String zorder = e.getAttributeValue("zOrder");
		if (zorder != null)
			o.setZOrder(Integer.parseInt(zorder));
	}

	protected void updateShapePosition(PathwayElement o, Element e) throws ConverterException
	{
		String base = e.getName();

		setAttribute(base, "centerX", e, "" + o.getMCenterX());
		setAttribute(base, "centerY", e, "" + o.getMCenterY());
		setAttribute(base, "width", e, "" + o.getMWidth());
		setAttribute(base, "height", e, "" + o.getMHeight());
		setAttribute(base, "zOrder", e, "" + o.getZOrder());
	}

	protected void mapDataNode(PathwayElement o, Element e) throws ConverterException
	{
		o.setDataNodeType (getAttribute("DataNode", "type", e));
		Element xref = e.getChild ("Xref", e.getNamespace());
		o.setElementID (getAttribute("DataNode.Xref", "ID", xref));
		o.setDataSource (DataSource.getByFullName (getAttribute("DataNode.Xref", "dataSource", xref)));
	}

	protected void updateDataNode(PathwayElement o, Element e) throws ConverterException
	{
		setAttribute ("DataNode", "type", e, o.getDataNodeType());
		Element xref = e.getChild("Xref", e.getNamespace());
		String database = o.getDataSource() == null ? "" : o.getDataSource().getFullName();
		setAttribute ("DataNode.Xref", "dataSource", xref, database == null ? "" : database);
		setAttribute ("DataNode.Xref", "ID", xref, o.getElementID());
	}
	
	protected void mapLine(PathwayElement o, Element e) throws ConverterException
	{
		Element xref = e.getChild ("Xref", e.getNamespace());
		o.setElementID (getAttribute("Interaction.Xref", "ID", xref));
		o.setDataSource (DataSource.getByFullName (getAttribute("Interaction.Xref", "dataSource", xref)));
	}

	
	protected void updateLine(PathwayElement o, Element e) throws ConverterException
	{
		Element xref = e.getChild("Xref", e.getNamespace());
		String database = o.getDataSource() == null ? "" : o.getDataSource().getFullName();
		setAttribute ("Interaction.Xref", "dataSource", xref, database == null ? "" : database);
		setAttribute ("Interaction.Xref", "ID", xref, o.getElementID());
	}

	protected void mapStateData(PathwayElement o, Element e) throws ConverterException
	{
    	String ref = getAttribute("State", "graphRef", e);
    	if (ref != null) {
    		o.setGraphRef(ref);
    	}

    	o.setRelX(Double.parseDouble(getAttribute("State", "relX", e)));
    	o.setRelY(Double.parseDouble(getAttribute("State", "relY", e)));
		o.setMWidth (Double.parseDouble(getAttribute("State", "width", e)));
		o.setMHeight (Double.parseDouble(getAttribute("State", "height", e)));

		o.setDataNodeType (getAttribute("State", "stateType", e));
		o.setGraphRef(getAttribute("State", "graphRef", e));
		Element xref = e.getChild ("Xref", e.getNamespace());
		o.setElementID (getAttribute("State.Xref", "ID", xref));
		o.setDataSource (DataSource.getByFullName (getAttribute("State.Xref", "dataSource", xref)));
	}

	protected void updateStateData(PathwayElement o, Element e) throws ConverterException
	{
		String base = e.getName();

		setAttribute(base, "relX", e, "" + o.getRelX());
		setAttribute(base, "relY", e, "" + o.getRelY());
		setAttribute(base, "width", e, "" + o.getMWidth());
		setAttribute(base, "height", e, "" + o.getMHeight());
		
		setAttribute ("State", "stateType", e, o.getDataNodeType());
		setAttribute ("State", "graphRef", e, o.getGraphRef());
		Element xref = e.getChild("Xref", e.getNamespace());
		String database = o.getDataSource() == null ? "" : o.getDataSource().getFullName();
		setAttribute ("State.Xref", "dataSource", xref, database == null ? "" : database);
		setAttribute ("State.Xref", "ID", xref, o.getElementID());
	}

	protected void mapLineStyle(PathwayElement o, Element e) throws ConverterException
	{
    	String base = e.getName();
		String style = getAttribute (base, "lineStyle", e);
		
		//Check for LineStyle.DOUBLE via arbitrary attribute
		if ("Double".equals (o.getDynamicProperty(LineStyle.DOUBLE_LINE_KEY)))
		{
			o.setLineStyle(LineStyle.DOUBLE);
		}
		else
		{
			o.setLineStyle ((style.equals("Solid")) ? LineStyle.SOLID : LineStyle.DASHED);
		}
    	
    	String lt = getAttribute(base, "lineThickness", e);
    	o.setLineThickness(lt == null ? 1.0 : Double.parseDouble(lt));
		mapColor(o, e); // Color
	}

	protected void mapLineData(PathwayElement o, Element e) throws ConverterException
	{    	
    	List<MPoint> mPoints = new ArrayList<MPoint>();

    	String startType = null;
    	String endType = null;

    	List<Element> pointElements = e.getChildren("Point", e.getNamespace());
    	for(int i = 0; i < pointElements.size(); i++) {
    		Element pe = pointElements.get(i);
    		MPoint mp = o.new MPoint(
    		    	Double.parseDouble(getAttribute("Interaction.Point", "x", pe)),
    		    	Double.parseDouble(getAttribute("Interaction.Point", "y", pe))
    		);
    		mPoints.add(mp);
        	String ref = getAttribute("Interaction.Point", "graphRef", pe);
        	String graphId = getAttribute("Interaction.Point", "graphId", pe);

        	if(graphId!=null)
        		mp.setGraphId(graphId);
        	else
        		mp.setGeneratedGraphId();

        	if (ref != null) {
        		mp.setGraphRef(ref);
        		String srx = pe.getAttributeValue("relX");
        		String sry = pe.getAttributeValue("relY");
        		if(srx != null && sry != null) {
        			mp.setRelativePosition(Double.parseDouble(srx), Double.parseDouble(sry));
        		}
        	}

        	if(i == 0) {
        		startType = getAttribute("Interaction.Point", "arrowHead", pe);
        	} else if(i == pointElements.size() - 1) {
    			endType = getAttribute("Interaction.Point", "arrowHead", pe);
        	}
    	}

    	o.setMPoints(mPoints);
		o.setStartLineType (LineType.fromName(startType));
    	o.setEndLineType (LineType.fromName(endType));

    	String connType = getAttribute("Interaction", "connectorType", e);
    	o.setConnectorType(ConnectorType.fromName(connType));

    	String zorder = e.getAttributeValue("zOrder");
		if (zorder != null)
			o.setZOrder(Integer.parseInt(zorder));

    	//Map anchors
    	List<Element> anchors = e.getChildren("Anchor", e.getNamespace());
    	for(Element ae : anchors) {
    		double position = Double.parseDouble(getAttribute("Interaction.Anchor", "position", ae));
    		MAnchor anchor = o.addMAnchor(position);
    		mapGraphId(anchor, ae);
    		String shape = getAttribute("Interaction.Anchor", "shape", ae);
    		if(shape != null) {
    			anchor.setShape(AnchorType.fromName(shape));
    		}
    	}
	}

	protected void updateLineStyle(PathwayElement o, Element e) throws ConverterException
	{
		String base = e.getName();
		setAttribute(base, "lineStyle", e, o.getLineStyle() != LineStyle.DASHED ? "Solid" : "Broken");
		setAttribute (base, "lineThickness", e, "" + o.getLineThickness());
		updateColor(o, e);
	}
	
	protected void updateLineData(PathwayElement o, Element e) throws ConverterException
	{
		List<MPoint> mPoints = o.getMPoints();

		for(int i = 0; i < mPoints.size(); i++) {
			MPoint mp = mPoints.get(i);
			Element pe = new Element("Point", e.getNamespace());
			e.addContent(pe);
			setAttribute("Interaction.Point", "x", pe, Double.toString(mp.getX()));
			setAttribute("Interaction.Point", "y", pe, Double.toString(mp.getY()));

			if(mp.getGraphId()==null)
				mp.setGeneratedGraphId();

			setAttribute("Interaction.Point", "graphId", pe, mp.getGraphId());

			if (mp.getGraphRef() != null && !mp.getGraphRef().equals(""))
			{
				setAttribute("Interaction.Point", "graphRef", pe, mp.getGraphRef());
				setAttribute("Interaction.Point", "relX", pe, Double.toString(mp.getRelX()));
				setAttribute("Interaction.Point", "relY", pe, Double.toString(mp.getRelY()));
			}
			if(i == 0) {
				setAttribute("Interaction.Point", "arrowHead", pe, o.getStartLineType().getName());
			} else if(i == mPoints.size() - 1) {
				setAttribute("Interaction.Point", "arrowHead", pe, o.getEndLineType().getName());
			}
		}

		for(MAnchor anchor : o.getMAnchors()) {
			Element ae = new Element("Anchor", e.getNamespace());
			setAttribute("Interaction.Anchor", "position", ae, Double.toString(anchor.getPosition()));
			setAttribute("Interaction.Anchor", "shape", ae, anchor.getShape().getName());
			updateGraphId(anchor, ae);
			e.addContent(ae);
		}

		ConnectorType ctype = o.getConnectorType();
		setAttribute("Interaction", "connectorType", e, ctype.getName());
		setAttribute("Interaction", "zOrder", e, "" + o.getZOrder());
	}

	public Document createJdom(Pathway data) throws ConverterException
	{
		Document doc = new Document();

		Element root = new Element("Pathway", getGpmlNamespace());
		doc.setRootElement(root);

		List<Element> elementList = new ArrayList<Element>();
		
		List<PathwayElement> pathwayElements = data.getDataObjects();
		Collections.sort(pathwayElements);
		for (PathwayElement o : pathwayElements)
		{
			if (o.getObjectType() == ObjectType.MAPPINFO)
			{
				updateMappInfo(root, o);
			}
			else
			{
				Element e = createJdomElement(o);
				if (e != null)
					elementList.add(e);
			}
		}

    	// now sort the generated elements in the order defined by the xsd
		Collections.sort(elementList, new ByElementName());
		for (Element e : elementList)
		{
			root.addContent(e);
		}

		return doc;
	}

	/**
	 * Writes the JDOM document to the outputstream specified
	 * @param out	the outputstream to which the JDOM document should be writed
	 * @param validate if true, validate the dom structure before writing. If there is a validation error,
	 * 		or the xsd is not in the classpath, an exception will be thrown.
	 * @throws ConverterException
	 */
	public void writeToXml(Pathway pwy, OutputStream out, boolean validate) throws ConverterException {
		Document doc = createJdom(pwy);
		
		//Validate the JDOM document
		if (validate) validateDocument(doc);
		//			Get the XML code
		XMLOutputter xmlcode = new XMLOutputter(Format.getPrettyFormat());
		Format f = xmlcode.getFormat();
		f.setEncoding("UTF-8");
		f.setTextMode(Format.TextMode.PRESERVE);
		xmlcode.setFormat(f);

		try
		{
			//Send XML code to the outputstream
			xmlcode.output(doc, out);
		}
		catch (IOException ie)
		{
			throw new ConverterException(ie);
		}
	}

	/**
	 * Writes the JDOM document to the file specified
	 * @param file	the file to which the JDOM document should be saved
	 * @param validate if true, validate the dom structure before writing to file. If there is a validation error,
	 * 		or the xsd is not in the classpath, an exception will be thrown.
	 */
	public void writeToXml(Pathway pwy, File file, boolean validate) throws ConverterException
	{
		OutputStream out;
		try
		{
			out = new FileOutputStream(file);
		}
		catch (IOException ex)
		{
			throw new ConverterException (ex);
		}
		writeToXml (pwy, out, validate);
	}

	protected void mapSimpleCenter(PathwayElement o, Element e)
	{
		o.setMCenterX (Double.parseDouble(e.getAttributeValue("centerX")));
		o.setMCenterY (Double.parseDouble(e.getAttributeValue("centerY")));
	}

	protected void updateSimpleCenter(PathwayElement o, Element e)
	{
		if(e != null)
		{
			e.setAttribute("centerX", Double.toString(o.getMCenterX()));
			e.setAttribute("centerY", Double.toString(o.getMCenterY()));
		}
	}

	private void updateOntologyTags(PathwayElement o, Element e)
	{
		if(e != null)
		{
			List<OntologyTag> ontologyTags = o.parent.getOntologyTags();
			for(OntologyTag ontologyTag: ontologyTags){
				Element element = new Element("OntologyTerm", getGpmlNamespace());
				e.addContent(element);
				element.setAttribute("ID",ontologyTag.getId());
				element.setAttribute("term",ontologyTag.getTerm());
				element.setAttribute("ontology",ontologyTag.getOntology());
				element.setAttribute("ontologyTermId",ontologyTag.getOntologyTermId());
			}
		}
	}

	private void mapOntology(PathwayElement o, Element e, Pathway p) throws ConverterException
	{
		List<Element> OntologyTags = e.getChildren("OntologyTerm", e.getNamespace());
		String id,term,ontology,ontologyTermId;
		for(Element ot: OntologyTags){
			id=ot.getAttributeValue("ID");
			term=ot.getAttributeValue("term");
			ontology=ot.getAttributeValue("ontology");
			ontologyTermId=ot.getAttributeValue("ontologyTermId");
			p.addOntologyTag(id,term,ontology,ontologyTermId);
		}
	}

	private void updateCitations(PathwayElement o, Element e)  throws ConverterException
	{
		if(e != null)
		{
			for(Citation citation:o.parent.getCitations()) {

				Element citationElement = new Element("Citation",getGpmlNamespace());
				e.addContent(citationElement);

				citationElement.setAttribute("title", citation.getTitle());
				citationElement.setAttribute("URL", citation.getURL());
				citationElement.setAttribute("citationId", citation.getCitationId());

				if(citation.getYear()!=null)
					citationElement.setAttribute("year", citation.getYear());

				if(citation.getSource()!=null)
					citationElement.setAttribute("source", citation.getSource());

				if(citation.getXref()!=null){
					Element xref = new Element("Xref",getGpmlNamespace());
					citationElement.addContent(xref);
					String dataSource = citation.getXref().getDataSource() == null ? "" : citation.getXref().getDataSource().getFullName();
					xref.setAttribute("dataSource", dataSource);
					xref.setAttribute("ID", citation.getXref().getId());
				}

				List<String> authors = citation.getAuthors();
				for (String author : authors) {
					Element element = new Element("Author", getGpmlNamespace());
					citationElement.addContent(element);
					element.setAttribute("name", author);
				}
			}
		}
	}

	private void mapCitations(PathwayElement o, Element e, Pathway p) throws ConverterException
	{
		List<Element> citationElements = e.getChildren("Citation",getGpmlNamespace());
		for(Element citationElement : citationElements){

			Citation citation = new Citation(citationElement.getAttributeValue("citationId"),
					citationElement.getAttributeValue("URL"),
					citationElement.getAttributeValue("title"));

			p.addCitation(citation);

			citation.setYear(citationElement.getAttributeValue("year"));
			citation.setSource(citationElement.getAttributeValue("source"));

			Element xref = citationElement.getChild ("Xref", e.getNamespace());

			if(xref!=null){
				citation.setXref(xref.getAttributeValue("ID"),
						xref.getAttributeValue("dataSource"));
			}

			List<Element> authors = citationElement.getChildren("Author", e.getNamespace());

			for(Element author: authors){
				citation.addAuthor(author.getAttributeValue("name"));
			}

		}
	}

}
