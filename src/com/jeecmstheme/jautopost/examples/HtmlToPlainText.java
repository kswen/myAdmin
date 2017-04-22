package com.jeecmstheme.jautopost.examples;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;

import com.jeecmstheme.jautopost.Jautopost;
import com.jeecmstheme.jautopost.helper.StringUtil;
import com.jeecmstheme.jautopost.nodes.Document;
import com.jeecmstheme.jautopost.nodes.Element;
import com.jeecmstheme.jautopost.nodes.Node;
import com.jeecmstheme.jautopost.nodes.TextNode;
import com.jeecmstheme.jautopost.select.Elements;
import com.jeecmstheme.jautopost.select.NodeTraversor;
import com.jeecmstheme.jautopost.select.NodeVisitor;

/**
 * HTML to plain-text. This example program demonstrates the use of jautopost to convert HTML input to lightly-formatted
 * plain-text. That is divergent from the general goal of jautopost's .text() methods, which is to get clean data from a
 * scrape.
 * <p/>
 * Note that this is a fairly simplistic formatter -- for real world use you'll want to embrace and extend.
 *
 * @author jeecmstheme.com
 */
public class HtmlToPlainText {
    public static void main(String... args) throws IOException {
        // fetch the specified URL and parse to a HTML DOM
        Document doc = Jautopost.connect("http://www.applejia.com/").userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36").timeout(1000*30).get();
		Elements imgs = doc.select("["+"real_src"+"]").select("img");
		for (Element img : imgs) {
            	String imgName = "sss.jpg";
				img.attr("src", imgName);
	   }
        System.err.println(doc.html());
    }

    /**
     * Format an Element to plain-text
     * @param element the root element to format
     * @return formatted text
     */
    public String getPlainText(Element element) {
        FormattingVisitor formatter = new FormattingVisitor();
        NodeTraversor traversor = new NodeTraversor(formatter);
        traversor.traverse(element); // walk the DOM, and call .head() and .tail() for each node

        return formatter.toString();
    }

    // the formatting rules, implemented in a breadth-first DOM traverse
    private class FormattingVisitor implements NodeVisitor {
        private static final int maxWidth = 180;
        private int width = 0;
        private StringBuilder accum = new StringBuilder(); // holds the accumulated text

        // hit when the node is first seen
        public void head(Node node, int depth) {
            String name = node.nodeName();
            if (node instanceof TextNode)
                append(((TextNode) node).text()); // TextNodes carry all user-readable text in the DOM.
            else if (name.equals("li"))
                append("\n * ");
        }

        // hit when all of the node's children (if any) have been visited
        public void tail(Node node, int depth) {
            String name = node.nodeName();
            if (name.equals("br"))
                append("\n");
            else if (StringUtil.in(name, "p", "h1", "h2", "h3", "h4", "h5"))
                append("\n\n");
            else if (name.equals("a"))
                append(String.format(" <%s>", node.absUrl("href")));
        }

        // appends text to the string builder with a simple word wrap method
        private void append(String text) {
            if (text.startsWith("\n"))
                width = 0; // reset counter if starts with a newline. only from formats above, not in natural text
            if (text.equals(" ") &&
                    (accum.length() == 0 || StringUtil.in(accum.substring(accum.length() - 1), " ", "\n")))
                return; // don't accumulate long runs of empty spaces

            if (text.length() + width > maxWidth) { // won't fit, needs to wrap
                String words[] = text.split("\\s+");
                for (int i = 0; i < words.length; i++) {
                    String word = words[i];
                    boolean last = i == words.length - 1;
                    if (!last) // insert a space if not the last word
                        word = word + " ";
                    if (word.length() + width > maxWidth) { // wrap and reset counter
                        accum.append("\n").append(word);
                        width = word.length();
                    } else {
                        accum.append(word);
                        width += word.length();
                    }
                }
            } else { // fits as is, without need to wrap text
                accum.append(text);
                width += text.length();
            }
        }

        public String toString() {
            return accum.toString();
        }
    }
}
