package org.tigris.juxy;

import junit.framework.TestCase;
import org.w3c.dom.Document;
import org.tigris.juxy.xpath.XPathExpr;

public class UTestVariableBase extends TestCase
{
    public UTestVariableBase(String s)
    {
        super(s);
    }

    public void testEmptyName()
    {
        try
        {
            new VariableBase(null, "aa");
            fail("An exception expected");
        }
        catch (IllegalArgumentException ex) {}

        try
        {
            new VariableBase("   ", "aa");
            fail("An exception expected");
        }
        catch (IllegalArgumentException ex) {}

        VariableBase v = new VariableBase("  zzz", "aa");
        assertEquals("zzz", v.getQname());

        v = new VariableBase("  zzz", (XPathExpr)null);
        assertEquals("zzz", v.getQname());

        v = new VariableBase("  zzz", (Document)null);
        assertEquals("zzz", v.getQname());
    }
}
