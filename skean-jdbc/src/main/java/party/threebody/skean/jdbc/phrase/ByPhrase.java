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

package party.threebody.skean.jdbc.phrase;

import java.util.Collection;
import java.util.Map;

public class ByPhrase implements Phrase {

	private FromPhrase root;

	ByPhrase(FromPhrase root) {
		this.root = root;
	}

	//------ value filling --------
	public ValPhrase valArr(Collection<Object> vals){
		return root.valArr(vals);
	}

	public ValPhrase valArr(Object[] vals){
		return root.valArr(vals);
	}
	
	public ValPhrase valMap(Map<String,Object> vals){
		return root.valMap(vals);
	}
	
	public ValPhrase valObj(Object vals){
		return root.valObj(vals);
	}
	
	public ValPhrase val(Object... vals) {
		return root.val(vals);
	}


}
