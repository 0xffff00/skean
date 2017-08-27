package party.threebody.skean.jdbc.phrase;

import java.util.Map;

public class ByPhrase implements Phrase {

	private FromPhrase root;

	ByPhrase(FromPhrase root) {
		this.root = root;
	}

	//------ value filling --------
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
