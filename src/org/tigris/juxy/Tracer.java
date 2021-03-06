package org.tigris.juxy;

import java.io.PrintStream;

/**
 * <p/>
 *
 * @author Pavel Sher
 */
public class Tracer {
  private static String currentSystemid;
  private static int lastLine;
  private static StringBuffer buf;
  private static PrintStream traceOs;

  public static void startTracing(PrintStream os) {
    traceOs = os;
    buf = new StringBuffer(20);
    lastLine = -1;
    currentSystemid = null;
  }

  public static void trace(int line, int level, String systemId, String statement) {
    boolean sameSystemId = systemId.equals(currentSystemid);
    if (!sameSystemId) {
      traceOs.println("Tracing of the stylesheet " + systemId + " started");
      lastLine = -1;
    }

    if (line == lastLine)
      traceOs.print(unescapeMessage(statement));
    else {
      if (lastLine != -1) {
        traceOs.println();
      }
      traceOs.print(messageAndLocation(line, level, unescapeMessage(statement)));
    }

    currentSystemid = systemId;
    lastLine = line;
  }

  /**
   * Should be called when transformation is completed
   */
  public static void stopTracing() {
    traceOs.println();
    buf.delete(0, buf.length());
  }

  private static String unescapeMessage(String message) {
    return message.replaceAll("&#39;", "'");
  }

  private static String messageAndLocation(int line, int level, String message) {
    buf.delete(0, buf.length());
    buf.append(line);
    buf.append(":\t");
    for (int i = 0; i < level * TABSTOP; i++) {
      buf.append(' ');
    }

    buf.append(message);
    return buf.toString();
  }

  private final static int TABSTOP = 4;
}
