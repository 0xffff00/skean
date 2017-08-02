package party.threebody.skean.util;

import java.util.Collection;
import java.util.List;
/**
 * Basic DAG(Directed Acyclic Graph)
 * @author hzk
 *r
 *
 * @param <V> type of Vertex
 * @param <W> type of Edge
 */
public interface BasicDAG<V,E> {

	BasicDAG<V,E> getPredecessorDAG(V v);
	BasicDAG<V,E> getSuccessorDAG(V v);
	
	List<V> listVerticesByTopologicalSorting();
	Collection<E> getEdgesByBeginVertex(V v);
	Collection<E> getEdgesByEndVertex(V v);
	
	
}
class Vertex<V>{
	V val;
}
class DirectedEdge<V,W>{
	V begin;
	V end;
	W weight;
}