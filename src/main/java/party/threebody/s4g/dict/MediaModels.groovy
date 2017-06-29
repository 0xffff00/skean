package party.threebody.s4g.dict

import java.time.Duration
import java.time.LocalTime

interface hasDuration{
	Duration getDuration()
	
}
interface canHear{
	
}

interface canSee{
	
}
interface Picture extends canSee{
	
}

interface Photo extends Picture{
	
}
interface Drawing extends Picture{
	
}
interface Screenshot extends Picture{
	
}
abstract class Video implements canSee,canHear,hasDuration{
	
}
abstract class Audio implements canHear,hasDuration{

}