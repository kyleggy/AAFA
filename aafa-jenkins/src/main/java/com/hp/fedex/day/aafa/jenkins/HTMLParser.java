package com.hp.fedex.day.aafa.jenkins;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.*;

/**
 * Created by zhouxi on 3/18/2015.
 */
public class HTMLParser {

    static String testResultUrl = "test-results.html";

    String testCaseName, testCaseSuiteName;


    private String root = "";

    public HTMLParser(String testReportUrl) {
        this.root = testReportUrl;

    }

    public Hashtable<String, String> retrieveFailedTestCases() {
        Hashtable<String, String> failTestCaseList = new Hashtable<String, String>();
        try {

            Document doc = Jsoup.connect(this.root + testResultUrl).get();
            Elements fails = doc.select("a.fail-img");
            for (Element link : fails) {
                testCaseName = "";
                testCaseSuiteName = "";
                String title = link.attr("title");
                if (title != null && !title.isEmpty()) {
                    //testCaseSuiteName = getNodeText(link);
                    //System.out.println("BBB" + testCaseSuiteName);
                } else { // get sub
                    testCaseName = getNodeText(link, NodeType.CHILD);
                    if (testCaseName != null && !testCaseName.isEmpty()) {
                        testCaseSuiteName = getNodeText(link, NodeType.PARENT);
                        failTestCaseList.put(testCaseName, testCaseSuiteName);
                    }
                }
            }

        } catch (java.io.IOException e) {
            System.out.println(e.getMessage());
        }

        printFailTestCase(failTestCaseList);
        return failTestCaseList;
    }


    public String getStackTrace(String detailUrl) {
        String result = "";
        try {

            Document doc = Jsoup.connect(this.root + detailUrl).get();
            Elements divs = doc.select("div.failure-container");
            for (Element div : divs) {
                Elements spans = div.getElementsByTag("span");
                if (spans.size() > 0) {
                    for (Element span : spans) {
                        Elements e = span.getElementsContainingText("Stack Trace: ");
                        if (e.size() > 0) {
                            result = e.get(0).text();
                            break;
                        }
                    }
                    break;
                }
            }
        } catch (java.io.IOException e) {
            System.out.println(e.getMessage());
        }

        return result;

    }

    public String getFailedStep(String detailUrl) {
        String result = "";
        try {

            Document doc = Jsoup.connect(this.root + detailUrl).get();
            Elements divs = doc.select("div.failure-container");
            for (Element div : divs) {
                Elements spans = div.getElementsByTag("span");
                if (spans.size() > 0) {
                    result = findFailedStep(div.parent().parent().parent().parent().parent());
                    break;
                }
            }
        } catch (java.io.IOException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    private String findFailedStep(Element el) {
        String result = "";
        Element next = el.previousElementSibling();
        do {
            //
            if (next.hasClass("test-step")) {
                Elements e = next.getElementsByTag("span");
                result = e.get(0).text();
                break;
            }
            next = next.previousElementSibling();
        } while (next != null);

        return result;
    }

    public String getDetailUrl(String testCaseSuiteName) {
        String result = "";
        try {
            Document doc = Jsoup.connect(this.root).get();
            String patten = "[href*=chrome-" + testCaseSuiteName + "]";
            Elements fails = doc.select(patten);
            if (fails.size() == 0) { //try with firefox
                patten = "[href*=firefox-" + testCaseSuiteName + "]";
                fails = doc.select(patten);
                if (fails.size() == 0) { //try with IE
                    patten = "[href*=ie-" + testCaseSuiteName + "]";
                    fails = doc.select(patten);
                }
            }

            for (Element link : fails) {
                String href = link.attr("href");
                //excludes /*fingerprint*/ and /*view*/
                if (href.endsWith("html")) {
                    result = href;
                    break;

                }

            }
        } catch (java.io.IOException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }


    private String getNodeText(Element e, NodeType type) {
        String result = "";
        switch (type) {
            case CHILD:
                List<Node> childNodes = e.childNodes();
                result = getNodeText(childNodes.get(0));
                break;
            case PARENT:
                Node parentNode = e.parentNode().parentNode().parentNode();
                Node node = parentNode.childNode(0).childNode(0);
                result = getNodeText(node);
                break;
            default:
                break;
        }
        return result;
    }

    private String getNodeText(Node node) {
        String result = "";

        String testCaseName = node.toString();
        //
        if (testCaseName != null && !testCaseName.isEmpty()) {
            String[] ary = testCaseName.split(" ");
            if (ary.length > 0) {
                result = ary[0];
            }
        }
        return result;
    }

    private void printFailTestCase(Hashtable<String, String> map) {

        Iterator<String> keySetIterator = map.keySet().iterator();
        while (keySetIterator.hasNext()) {
            String key = keySetIterator.next();
            System.out.println("AAA === testCaseName: " + key + " data-cont: " + map.get(key));
        }
    }

    enum NodeType {
        CHILD, PARENT
    }
}



