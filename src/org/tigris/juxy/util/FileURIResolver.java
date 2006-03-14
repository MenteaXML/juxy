package org.tigris.juxy.util;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.File;

/**
 * $Id: FileURIResolver.java,v 1.2 2006-03-14 16:24:25 pavelsher Exp $
 * <p/>
 * @author Pavel Sher
 */
public class FileURIResolver implements URIResolver {
    public Source resolve(String href, String base) throws TransformerException {
        if (href == null) return null;

        URI hrefURI = null;
        try {
            hrefURI = new URI(href);
        } catch (URISyntaxException e) {
            hrefURI = new File(href).toURI();
        }

        URI baseURI = null;
        if (base != null && base.length() > 0) {
            try {
                baseURI = new URI(base);
            } catch (URISyntaxException e) {
                baseURI = new File(base).getAbsoluteFile().toURI();
            }
        }

        if (baseURI != null)
            hrefURI = baseURI.resolve(hrefURI);

        File file = null;
        if (hrefURI.isAbsolute() && hrefURI.getScheme().startsWith("file"))
            file = new File(hrefURI);
        else
            file = new File(href).getAbsoluteFile();

        if (file != null && file.exists())
            return new StreamSource(file.toURI().toString());

        return null;
    }
}
