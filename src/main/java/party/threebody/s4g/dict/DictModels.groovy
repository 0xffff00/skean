package party.threebody.s4g.dict

import java.time.LocalTime

class Word {
	String word
	String qual
	String lang
	String type    
	
	
	@Override
	public String toString() {
		return "Word [word=" + word + ", qual=" + qual + ", lang=" + lang + ", type=" + type + "]";
	}
	
}

class Noun extends Word {
	 
	
}

class NounDTO extends Noun{
	List<Noun> wordsRelated
	
}

class Verb extends Word {
}
class Rel {
	String subject
	String verb
	String object
	String adverb
}