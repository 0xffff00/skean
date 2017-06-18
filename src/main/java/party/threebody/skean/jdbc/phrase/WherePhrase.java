package party.threebody.skean.jdbc.phrase;

/**
 * neither 'where().valObj()' nor 'where().valMap()' impl yet
 * @author hzk
 * @since 2017-06-18
 */
public class WherePhrase implements Phrase {

	private FromPhrase root;

	// ------ value filling --------
	WherePhrase(FromPhrase root) {
		this.root = root;
	}

	public ValPhrase valArr(Object[] vals) {
		return root.valArr(vals);
	}


	public ValPhrase val(Object... vals) {
		return root.val(vals);
	}

	// ------ modifying --------
	public int insert() {
		return root.insert();
	}

	public int update() {
		return root.update();
	}

	public int delete() {
		return root.delete();
	}

}
