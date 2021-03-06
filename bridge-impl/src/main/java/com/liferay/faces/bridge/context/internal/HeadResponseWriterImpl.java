/**
 * Copyright (c) 2000-2016 Liferay, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liferay.faces.bridge.context.internal;

import java.io.IOException;

import javax.faces.context.ResponseWriter;
import javax.portlet.MimeResponse;
import javax.portlet.PortletResponse;

import org.w3c.dom.Element;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * Custom {@link ResponseWriter} that has the ability to write to the <head>...</head> section of the portal page via
 * the standard Portlet 2.0 MimeResponse.MARKUP_HEAD_ELEMENT mechanism.
 *
 * @author  Neil Griffin
 */
public class HeadResponseWriterImpl extends HeadResponseWriterBase {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(HeadResponseWriterImpl.class);

	// Private Data Members
	private PortletResponse portletResponse;

	public HeadResponseWriterImpl(ResponseWriter wrappedResponseWriter, PortletResponse portletResponse) {
		super(wrappedResponseWriter);
		this.portletResponse = portletResponse;
	}

	@Override
	public Element createElement(String name) {
		return portletResponse.createElement(name);
	}

	@Override
	protected void addResourceToHeadSection(Element element, String nodeName) throws IOException {

		// NOTE: The Portlet 2.0 Javadocs for the addProperty method indicate that if the key already exists,
		// then the element will be added to any existing elements under that key name. There is a risk that
		// multiple portlet instances on the same portal page could cause multiple <script /> elements to be
		// added to the <head>...</head> section of the rendered portal page. See:
		// http://portals.apache.org/pluto/portlet-2.0-apidocs/javax/portlet/PortletResponse.html#addProperty(java.lang.String,
		// org.w3c.dom.Element)
		portletResponse.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, element);
		logger.debug(ADDED_RESOURCE_TO_HEAD, "portal", nodeName);
	}
}
