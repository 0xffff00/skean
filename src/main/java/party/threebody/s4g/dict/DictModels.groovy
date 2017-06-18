package party.threebody.s4g.dict

import java.time.LocalTime

class Word {
	String word
	String qual
	String lang
	String type
}

class Noun extends Word {
}
class Verb extends Word {
}
class Rel {
	String subject
	String verb
	String object
	String adverb
}