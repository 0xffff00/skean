package party.threebody.skean.collections;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author hzk
 * @since 2017-08-03
 */
public class DAG<V,E> {

	Map<V,List<E>> predecessorMap;
	Map<V,List<E>> successorMap;
	
	public void Mas(){
		
	}
	
	
}

class Edge<V>{
	V src;
	V dest;
	
}
