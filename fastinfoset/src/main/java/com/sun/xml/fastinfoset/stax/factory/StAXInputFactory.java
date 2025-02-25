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

import com.sun.xml.fastinfoset.stax.*;
import com.sun.xml.fastinfoset.stax.events.StAXEventReader;
import com.sun.xml.fastinfoset.stax.events.StAXFilteredEvent;
import com.sun.xml.fastinfoset.stax.util.StAXFilteredParser;
import com.sun.xml.fastinfoset.tools.XML_SAX_FI;

import java.io.InputStream;
import java.io.Reader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

import javax.xml.stream.*;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.util.XMLEventAllocator ;
import javax.xml.transform.Source;
import com.sun.xml.fastinfoset.CommonResourceBundle;

public class StAXInputFactory extends XMLInputFactory {    
    //List of supported properties and default values.
    private StAXManager _manager = new StAXManager(StAXManager.CONTEXT_READER) ;
    
    public StAXInputFactory() {
    }
    
    public static XMLInputFactory newInstance() {
        return XMLInputFactory.newInstance();
    }

  /**
   * Create a new XMLStreamReader from a reader
   * @param xmlfile the XML data to read from
   */
    public XMLStreamReader createXMLStreamReader(Reader xmlfile) throws XMLStreamException {
        return getXMLStreamReader(xmlfile);
    }    
    
    public XMLStreamReader createXMLStreamReader(InputStream s) throws XMLStreamException {
        return new StAXDocumentParser(s, _manager);
    }
    
    public XMLStreamReader createXMLStreamReader(String systemId, Reader xmlfile) throws XMLStreamException {
        return getXMLStreamReader(xmlfile);
    }
    
    public XMLStreamReader createXMLStreamReader(Source source) throws XMLStreamException {
        return null;
    }
    
    public XMLStreamReader createXMLStreamReader(String systemId, InputStream inputstream) throws XMLStreamException {
        return createXMLStreamReader(inputstream);
    }
    
    
    public XMLStreamReader createXMLStreamReader(InputStream inputstream, String encoding) throws XMLStreamException {
        return createXMLStreamReader(inputstream);
    }

    
    XMLStreamReader getXMLStreamReader(String systemId, InputStream inputstream, String encoding) 
        throws XMLStreamException{        
        return createXMLStreamReader(inputstream);

    }

    /**
     * @param xmlfile source XML file
     * @return new instance of XML Reader
     * @throws XMLStreamException on error
     */    
    XMLStreamReader getXMLStreamReader(Reader xmlfile) 
        throws XMLStreamException{
        
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedStream = new BufferedOutputStream(byteStream);
        StAXDocumentParser sr = null;
        try {
            XML_SAX_FI convertor = new XML_SAX_FI();
            convertor.convert(xmlfile, bufferedStream);

            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteStream.toByteArray());
            InputStream document = new BufferedInputStream(byteInputStream);
            sr = new StAXDocumentParser();
            sr.setInputStream(document);
            sr.setManager(_manager);
            return sr;
            //return new StAXDocumentParser(document, _manager); 
        } catch (Exception e) {
            return null;
        }

    }
           
    
    /**
     * @return XMLEventReader
     */
    public XMLEventReader createXMLEventReader(InputStream inputstream) throws XMLStreamException {
        return new StAXEventReader(createXMLStreamReader(inputstream));
    }
    
    public XMLEventReader createXMLEventReader(Reader reader) throws XMLStreamException {
        return new StAXEventReader(createXMLStreamReader(reader));
    }
    
    public XMLEventReader createXMLEventReader(Source source) throws XMLStreamException {
        return new StAXEventReader(createXMLStreamReader(source));
    }
    
    public XMLEventReader createXMLEventReader(String systemId, InputStream inputstream) throws XMLStreamException {
        return new StAXEventReader(createXMLStreamReader(systemId, inputstream));
    }
    
    public XMLEventReader createXMLEventReader(java.io.InputStream stream, String encoding) throws XMLStreamException {
        return new StAXEventReader(createXMLStreamReader(stream, encoding));
    }
    
    public XMLEventReader createXMLEventReader(String systemId, Reader reader) throws XMLStreamException {
        return new StAXEventReader(createXMLStreamReader(systemId, reader));
    }
    
    /** Create a new XMLEventReader from an XMLStreamReader.  After being used
     * to construct the XMLEventReader instance returned from this method
     * the XMLStreamReader must not be used.
     * @param streamReader the XMLStreamReader to read from (may not be modified)
     * @return a new XMLEventReader
     */
    public XMLEventReader createXMLEventReader(XMLStreamReader streamReader) throws XMLStreamException {
        return new StAXEventReader(streamReader);
    }

    public XMLEventAllocator getEventAllocator() {
        return (XMLEventAllocator)getProperty(XMLInputFactory.ALLOCATOR);
    }
    
    public XMLReporter getXMLReporter() {
        return (XMLReporter)_manager.getProperty(XMLInputFactory.REPORTER);
    }
    
    public XMLResolver getXMLResolver() {
        Object object = _manager.getProperty(XMLInputFactory.RESOLVER);
        return (XMLResolver)object;
        //return (XMLResolver)_manager.getProperty(XMLInputFactory.RESOLVER);
    }
    
    public void setXMLReporter(XMLReporter xmlreporter) {
        _manager.setProperty(XMLInputFactory.REPORTER, xmlreporter);
    }
    
    public void setXMLResolver(XMLResolver xmlresolver) {
        _manager.setProperty(XMLInputFactory.RESOLVER, xmlresolver);
    }
    
    /** Create a filtered event reader that wraps the filter around the event reader
     * @param reader the event reader to wrap
     * @param filter the filter to apply to the event reader
     */
    public XMLEventReader createFilteredReader(XMLEventReader reader, EventFilter filter) throws XMLStreamException {
        return new StAXFilteredEvent(reader, filter);
    }
    
    /** Create a filtered reader that wraps the filter around the reader
     * @param reader the reader to filter
     * @param filter the filter to apply to the reader
     */
    public XMLStreamReader createFilteredReader(XMLStreamReader reader, StreamFilter filter) throws XMLStreamException {
        
        if( reader != null && filter != null )
            return new StAXFilteredParser(reader,filter);
        
        return null;
    }
    
    
    
    /** Get the value of a feature/property from the underlying implementation
     * @param name The name of the property (may not be null)
     * @return The value of the property
     * @throws IllegalArgumentException if the property is not supported
     */
    public Object getProperty(String name) throws IllegalArgumentException {
        if(name == null){
            throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.nullPropertyName"));
        }
        if(_manager.containsProperty(name))
            return _manager.getProperty(name);
        throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.propertyNotSupported", new Object[]{name}));
    }
    
    /** Query the set of Properties that this factory supports.
     *
     * @param name The name of the property (may not be null)
     * @return true if the property is supported and false otherwise
     */
    public boolean isPropertySupported(String name) {
        if(name == null)
            return false ;
        else
            return _manager.containsProperty(name);
    }
    
    /** Set a user defined event allocator for events
     * @param allocator the user defined allocator
     */
    public void setEventAllocator(XMLEventAllocator allocator) {
        _manager.setProperty(XMLInputFactory.ALLOCATOR, allocator);
    }
    
    /** Allows the user to set specific feature/property on the underlying implementation. The underlying implementation
     * is not required to support every setting of every property in the specification and may use IllegalArgumentException
     * to signal that an unsupported property may not be set with the specified value.
     * @param name The name of the property (may not be null)
     * @param value The value of the property
     * @throws IllegalArgumentException if the property is not supported
     */
    public void setProperty(String name, Object value) throws IllegalArgumentException {
        _manager.setProperty(name,value);
    }
    
}
