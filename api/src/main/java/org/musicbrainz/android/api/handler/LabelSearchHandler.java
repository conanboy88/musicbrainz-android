/*
 * Copyright (C) 2012 Jamie McDonald
 * 
 * This file is part of MusicBrainz for Android.
 * 
 * MusicBrainz for Android is free software: you can redistribute 
 * it and/or modify it under the terms of the GNU General Public 
 * License as published by the Free Software Foundation, either 
 * version 3 of the License, or (at your option) any later version.
 * 
 * MusicBrainz for Android is distributed in the hope that it 
 * will be useful, but WITHOUT ANY WARRANTY; without even the implied 
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with MusicBrainz for Android. If not, see 
 * <http://www.gnu.org/licenses/>.
 */

package org.musicbrainz.android.api.handler;

import java.util.LinkedList;

import org.musicbrainz.android.api.data.LabelSearchStub;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class LabelSearchHandler extends MBHandler {
    
    private LinkedList<LabelSearchStub> results = new LinkedList<LabelSearchStub>();
    private LabelSearchStub stub;
    
    private boolean inTag;

    public LinkedList<LabelSearchStub> getResults() {
        return results;
    }
    
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {

        if (localName.equalsIgnoreCase("label")) {
            stub = new LabelSearchStub();
            stub.setMbid(atts.getValue("id"));
        } else if (localName.equalsIgnoreCase("name") && !inTag) {
            buildString();
        } else if (localName.equalsIgnoreCase("country")) {
            buildString();
        } else if (localName.equalsIgnoreCase("tag")) {
            inTag = true;
        }
    }
    
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {

        if (localName.equalsIgnoreCase("label")) {
            results.add(stub);
        } else if (localName.equalsIgnoreCase("name") && !inTag) {
            stub.setName(getString());
        } else if (localName.equalsIgnoreCase("country")) {
            stub.setCountry(getString());
        } else if (localName.equalsIgnoreCase("tag")) {
            inTag = true;
        }
    }

}
