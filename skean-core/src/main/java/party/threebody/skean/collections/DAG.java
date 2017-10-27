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
