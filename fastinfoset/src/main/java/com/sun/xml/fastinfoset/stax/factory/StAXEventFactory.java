/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2004, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sun.xml.fastinfoset.stax.factory;

import javax.xml.namespace.QName;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.Location;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.events.*;
import java.util.Iterator;
import com.sun.xml.fastinfoset.stax.events.*;


public class StAXEventFactory extends XMLEventFactory {
    Location location = null;
    
    /** Creates a new instance of StAXEventFactory */
    public StAXEventFactory() {
    }
    /**
    * This method allows setting of the Location on each event that
    * is created by this factory.  The values are copied by value into
    * the events created by this factory.  To reset the location 
    * information set the location to null.
    * @param location the location to set on each event created
    */
    @Override
    public void setLocation(Location location) {
        this.location = location;
    }
    
  /**
   * Create a new Attribute
   * @param prefix the prefix of this attribute, may not be null
   * @param namespaceURI the attribute value is set to this value, may not be null
   * @param localName the local name of the XML name of the attribute, localName cannot be null
   * @param value the attribute value to set, may not be null
   * @return the Attribute with specified values
   */
    @Override
    public Attribute createAttribute(String prefix, String namespaceURI, String localName, String value) {
        AttributeBase attr =  new AttributeBase(prefix, namespaceURI, localName, value, null);
        if(location != null)attr.setLocation(location);
        return attr;
    }
    
  /**
   * Create a new Attribute
   * @param localName the local name of the XML name of the attribute, localName cannot be null
   * @param value the attribute value to set, may not be null
   * @return the Attribute with specified values
   */
    @Override
    public Attribute createAttribute(String localName, String value) {
        AttributeBase attr =  new AttributeBase(localName, value);
        if(location != null)attr.setLocation(location);
        return attr;
    }
    
    @Override
    public Attribute createAttribute(QName name, String value) {
        AttributeBase attr =  new AttributeBase(name, value);
        if(location != null)attr.setLocation(location);
        return attr;
    }
    
  /**
   * Create a new default Namespace
   * @param namespaceURI the default namespace uri
   * @return the Namespace with the specified value
   */
    @Override
    public Namespace createNamespace(String namespaceURI) {
        NamespaceBase event =  new NamespaceBase(namespaceURI);
        if(location != null)event.setLocation(location);
        return event;
    }
    
  /**
   * Create a new Namespace
   * @param prefix the prefix of this namespace, may not be null
   * @param namespaceURI the attribute value is set to this value, may not be null
   * @return the Namespace with the specified values
   */
    @Override
    public Namespace createNamespace(String prefix, String namespaceURI) {
        NamespaceBase event =  new NamespaceBase(prefix, namespaceURI);
        if(location != null)event.setLocation(location);
        return event;
    }
        
  /**
   * Create a new StartElement.  
   * @param name the qualified name of the attribute, may not be null
   * @param attributes an optional unordered set of objects that 
   * implement Attribute to add to the new StartElement, may be null
   * @param namespaces an optional unordered set of objects that 
   * implement Namespace to add to the new StartElement, may be null
   * @return an instance of the requested StartElement
   */
    // Generics removed because they break build with release=8
    @Override
    public StartElement createStartElement(QName name,
            Iterator attributes, Iterator namespaces) {
        return createStartElement(name.getPrefix(), name.getNamespaceURI(), name.getLocalPart(), attributes, namespaces);
    }
    
    @Override
    public StartElement createStartElement(String prefix, String namespaceUri, String localName) {
        StartElementEvent event =  new StartElementEvent(prefix, namespaceUri, localName);
        if(location != null)event.setLocation(location);
        return event;
    }

    // Generics removed because they break build with release=8
    @Override
    public StartElement createStartElement(String prefix, String namespaceUri, String localName,
            Iterator attributes, Iterator namespaces) {
        return createStartElement(prefix, namespaceUri, localName, attributes, namespaces, null);
    }

    // Generics removed because they break build with release=8
    @Override
    public StartElement createStartElement(String prefix, String namespaceUri, String localName,
            Iterator attributes, Iterator namespaces, NamespaceContext context) {
        StartElementEvent elem =  new StartElementEvent(prefix, namespaceUri, localName);
        elem.addAttributes(attributes);
        elem.addNamespaces(namespaces);
        elem.setNamespaceContext(context);
        if(location != null)elem.setLocation(location);
        return elem;
    }
    
  /**
   * Create a new EndElement
   * @param name the qualified name of the EndElement
   * @param namespaces an optional unordered set of objects that 
   * implement Namespace that have gone out of scope, may be null
   * @return an instance of the requested EndElement
   */
    // Generics removed because they break build with release=8
    @Override
    public EndElement createEndElement(QName name, Iterator namespaces) {
        return createEndElement(name.getPrefix(), name.getNamespaceURI(), name.getLocalPart(), namespaces);
    }
    
  /**
   * Create a new EndElement
   * @param namespaceUri the uri of the QName of the new StartElement
   * @param localName the local name of the QName of the new StartElement
   * @param prefix the prefix of the QName of the new StartElement
   * @return an instance of the requested EndElement
   */
    @Override
    public EndElement createEndElement(String prefix, String namespaceUri, String localName) {
        EndElementEvent event =  new EndElementEvent(prefix, namespaceUri, localName);
        if(location != null)event.setLocation(location);
        return event;
    }
    
  /**
   * Create a new EndElement
   * @param namespaceUri the uri of the QName of the new StartElement
   * @param localName the local name of the QName of the new StartElement
   * @param prefix the prefix of the QName of the new StartElement
   * @param namespaces an unordered set of objects that implement 
   * Namespace that have gone out of scope, may be null
   * @return an instance of the requested EndElement
   */
    // Generics removed because they break build with release=8
    @Override
    public EndElement createEndElement(String prefix, String namespaceUri,
            String localName, Iterator namespaces) {
        
        EndElementEvent event =  new EndElementEvent(prefix, namespaceUri, localName);
        if(namespaces!=null){
            while(namespaces.hasNext())
                event.addNamespace((Namespace)namespaces.next());
        }
        if(location != null)event.setLocation(location);
        return event;
    }
        
  /**
   * Create a Characters event, this method does not check if the content
   * is all whitespace.  To create a space event use #createSpace(String)
   * @param content the string to create
   * @return a Characters event
   */
    @Override
    public Characters createCharacters(String content) {
        CharactersEvent charEvent =  new CharactersEvent(content);
        if(location != null)charEvent.setLocation(location);
        return charEvent;
    }

  /**
   * Create a Characters event with the CData flag set to true
   * @param content the string to create
   * @return a Characters event
   */
    @Override
    public Characters createCData(String content) {
        CharactersEvent charEvent =  new CharactersEvent(content, true);
        if(location != null)charEvent.setLocation(location);
        return charEvent;
    }

   /**
   * Create a Characters event with the isSpace flag set to true
   * @param content the content of the space to create
   * @return a Characters event
   */
    @Override
    public Characters createSpace(String content) {
        CharactersEvent event =  new CharactersEvent(content);
        event.setSpace(true);
        if(location != null)event.setLocation(location);
        return event;
    }
    /**
    * Create an ignorable space
    * @param content the space to create
    * @return a Characters event
    */
    @Override
    public Characters createIgnorableSpace(String content) {
        CharactersEvent event =  new CharactersEvent(content, false);
        event.setSpace(true);
        event.setIgnorable(true);
        if(location != null)event.setLocation(location);
        return event;
    }
  /** 
   * Creates a new instance of a StartDocument event
   * @return a StartDocument event
   */    
    @Override
    public StartDocument createStartDocument() {
        StartDocumentEvent event = new StartDocumentEvent();
        if(location != null)event.setLocation(location);
        return event;
    }
    
  /** 
   * Creates a new instance of a StartDocument event
   *
   * @param encoding the encoding style
   * @return a StartDocument event
   */
    @Override
    public StartDocument createStartDocument(String encoding) {
        StartDocumentEvent event =  new StartDocumentEvent(encoding);
        if(location != null)event.setLocation(location);
        return event;
    }
    
  /** 
   * Creates a new instance of a StartDocument event
   *
   * @param encoding the encoding style
   * @param version the XML version
   * @return a StartDocument event
   */
    @Override
    public StartDocument createStartDocument(String encoding, String version) {
        StartDocumentEvent event =  new StartDocumentEvent(encoding, version);
        if(location != null)event.setLocation(location);
        return event;
    }
    
  /** 
   * Creates a new instance of a StartDocument event
   *
   * @param encoding the encoding style
   * @param version the XML version
   * @param standalone the status of standalone may be set to "true" or "false"
   * @return a StartDocument event
   */
    @Override
    public StartDocument createStartDocument(String encoding, String version, boolean standalone) {
        StartDocumentEvent event =  new StartDocumentEvent(encoding, version);
        event.setStandalone(standalone);
        if(location != null)event.setLocation(location);
        return event;
    }

    @Override
    public EndDocument createEndDocument() {
        EndDocumentEvent event =new EndDocumentEvent();
        if(location != null)event.setLocation(location);
        return event;
    }
    
    /** Creates a new instance of a EntityReference event
    *
    * @param name The name of the reference
    * @param entityDeclaration the declaration for the event
    * @return an EntityReference event
    */
    @Override
    public EntityReference createEntityReference(String name, EntityDeclaration entityDeclaration) {
        EntityReferenceEvent event =  new EntityReferenceEvent(name, entityDeclaration);
        if(location != null)event.setLocation(location);
        return event;
    }
        
    /**
    * Create a comment
    * @param text The text of the comment
    * a Comment event
     */
    @Override
    public Comment createComment(String text) {
        CommentEvent charEvent =  new CommentEvent(text);
        if(location != null)charEvent.setLocation(location);
        return charEvent;
    }
    
    /**
    * Create a document type definition event
    * This string contains the entire document type declaration that matches
    * the doctypedecl in the XML 1.0 specification
    * @param dtd the text of the document type definition
    * @return a DTD event
    */
    @Override
    public DTD createDTD(String dtd) {
        DTDEvent dtdEvent = new DTDEvent(dtd);
        if(location != null)dtdEvent.setLocation(location);
        return dtdEvent;
    }


    /**
    * Create a processing instruction
    * @param target The target of the processing instruction
    * @param data The text of the processing instruction
    * @return a ProcessingInstruction event
    */
    @Override
    public ProcessingInstruction createProcessingInstruction(String target, String data) {
        ProcessingInstructionEvent event =  new ProcessingInstructionEvent(target, data);
        if(location != null)event.setLocation(location);
        return event;
    }

}
