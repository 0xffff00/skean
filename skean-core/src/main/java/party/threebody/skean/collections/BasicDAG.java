/*
 * Copyright 2017-present  Skean Project Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package party.threebody.skean.collections;

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