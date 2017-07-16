package party.threebody.s4g.dict

class Rel {
	String sbj,oba,obj,adv,ti1,ti2
	Integer seq
	@Override
	public String toString() {
		def s="$sbj is $oba 's $obj"
		def s1=seq?",#$seq":''
		def s2=adv?",$adv":''
		def s3=(ti1||ti2)?",[$ti1,$ti2)":''
		s+s1+s2+s3		
	}
}
