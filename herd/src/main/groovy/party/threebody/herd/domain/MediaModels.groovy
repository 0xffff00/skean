package party.threebody.herd.domain

import java.time.Duration

interface hasDuration{
	Duration getDuration()
	
}

interface canHear{
	
}

interface canSee{
	
}

interface Picture extends canSee{
	
}

interface Photo1 extends Picture{
	
}

interface Drawing extends Picture{
	
}

interface Screenshot extends Picture{
	
}

abstract class Video implements canSee,canHear,hasDuration{
	
}

abstract class Audio implements canHear,hasDuration{

}