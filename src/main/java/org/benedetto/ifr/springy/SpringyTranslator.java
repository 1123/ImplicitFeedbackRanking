package org.benedetto.ifr.springy;

import org.apache.hadoop.util.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.benedetto.ifr.adjancencylist.Edge;
import org.benedetto.ifr.adjancencylist.HashMapGraph;
import org.benedetto.ifr.topologicalsort.reports.Context;
import org.benedetto.ifr.topologicalsort.reports.ReportUtils;
import org.benedetto.ifr.util.Mapper;
import org.benedetto.ifr.util.MyFunctional;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * This class translates a HashMapGraph to Springy notation, such that it can be displayed as a force directed graph
 * within the browser.
 **/

public class SpringyTranslator <N extends Comparable<N>> {

    public String translateEdges(List<Edge<N>> edges) {
        List<String> edgesSerialized = MyFunctional.map(edges, new Mapper<Edge<N>, String>() {
            @Override
            public String map(Edge<N> input) {
                return String.format("['%s', '%s']", input.getFrom().toString(), input.getTo().toString());
            }
        });
        return StringUtils.join(", ", edgesSerialized);
    }

    public String translateNodes(List<N> nodes) {
        List<String> nodesSerialized = MyFunctional.map(nodes, new Mapper<N, String>() {
            @Override
            public String map(N input) {
                return String.format("'%s'", input.toString());
            }
        });
        return StringUtils.join(", ", nodesSerialized);
    }

    public String translateGraph(HashMapGraph<N> graph) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("var graph = new Springy.Graph();");
        stringBuilder.append(System.getProperty("line.separator"));
        stringBuilder.append(this.translateNodes(graph.topNEdgeNodes(10)));
        stringBuilder.append(System.getProperty("line.separator"));
        stringBuilder.append(this.translateEdges(graph.topNEdges(10)));
        return stringBuilder.toString();
    }

    public String serializeToHtml(HashMapGraph<N> graph, int numberOfEdges) {
        VelocityContext context = new VelocityContext();
        StringWriter w = new StringWriter();
        context.put("nodes", this.translateNodes(graph.topNEdgeNodes(numberOfEdges)));
        context.put("edges", this.translateEdges(graph.topNEdges(numberOfEdges)));
        Template t = Context.getVelocityEngine().getTemplate("springyTemplate.vm");
        t.merge(context, w);
        return w.toString();
    }

    public void serializeToHtml(HashMapGraph<N> graph, int numberOfEdges, String path) throws FileNotFoundException {
        String serialized = this.serializeToHtml(graph, numberOfEdges);
        PrintWriter out = new PrintWriter(path);
        out.write(serialized);
        out.close();
    }

}
