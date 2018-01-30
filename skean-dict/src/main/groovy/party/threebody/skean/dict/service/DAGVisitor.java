package party.threebody.skean.dict.service;

import org.apache.commons.collections4.SetUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

/**
 * @param <V>
 * @param <E>
 * @author hzk
 * @since 2017-08-05
 */
public class DAGVisitor<V, E> {
    private Function<V, List<E>> edgeVisitor;
    private Function<E, V> destVertexMapper;
    private Set<V> verticesVisited;
    private Set<E> edgesVisited;
    private V sourceVertex;

    public DAGVisitor(Supplier<List<E>> allEdgesSupplier, Function<E, V> srcVertexMapper,
                      Function<E, V> destVertexMapper) {
        this.destVertexMapper = destVertexMapper;
        this.edgeVisitor = v -> allEdgesSupplier.get().stream().filter(e -> v.equals(srcVertexMapper.apply(e)))
                .collect(toList());
    }

    public DAGVisitor(Function<V, List<E>> edgeVisitor, Function<E, V> destVertexMapper) {
        this.destVertexMapper = destVertexMapper;
        this.edgeVisitor = edgeVisitor;
    }

    public void visitFrom(V root) {
        sourceVertex=root;
        verticesVisited = new HashSet<>();
        edgesVisited = new HashSet<>();
        dfs(root);
    }

    public Set<V> getVerticesVisited() {
        return verticesVisited;
    }
    public Set<V> getVerticesVisitedExceptSource() {
        return SetUtils.difference(verticesVisited, Collections.singleton(sourceVertex));
    }
    public Set<E> getEdgesVisited() {
        return edgesVisited;
    }

    private void dfs(V v) {
        if (v == null || verticesVisited.contains(v)) {
            return;
        }
        verticesVisited.add(v);
        List<E> edges = edgeVisitor.apply(v);
        edgesVisited.addAll(edges);
        WordService.logger.debug("dfs({}) -> edges{}", v, edges);
        if (edges.size() == 0) {
            return;
        }
        edges.forEach(edge -> dfs(destVertexMapper.apply(edge)));
    }
}
