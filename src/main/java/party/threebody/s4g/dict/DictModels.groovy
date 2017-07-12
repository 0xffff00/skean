package party.threebody.s4g.dict

import java.time.LocalTime

class Word {
	String word
	String qual
	String lang
	String type    
	
	
	@Override
	public String toString() {
		if (qual==null || qual.isEmpty()){
			return word;
		}
		return word+'('+qual+')';
	}
	
}

class Noun extends Word {
	 
	
}

class NounDTO extends Noun{
	List<Noun> wordsRelated
	
}

class Verb extends Word {
}
