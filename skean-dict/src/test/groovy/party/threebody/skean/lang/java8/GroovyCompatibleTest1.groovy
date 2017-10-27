package party.threebody.skean.lang.java8

class GroovyCompatibleToJava8DefaultMethodsTest1 {

    // test groovy's compatibility with Java 8 default methods
    public static void main(String[] args){
      HasPosImpl has1j=new HasPosImpl();
        println has1j.getX()
        println has1j.getY()

        HasPosGrImpl has1g=new HasPosGrImpl();
        println has1g.getX()
        println has1g.getY()
       // println has1g.getX()
//        Teen teen=new Teen()

//        println teen.age
//
//        println teen.age1
    }
}

trait hasAge{
    int getAge(){
        return 0;
    }
    abstract int getAge1();

}
trait hasAge1 {
    int getAge1(){
        return 10;
    }
    abstract int getAge2();
}

class Teen implements hasAge,hasAge1{
    @Override
    int getAge2() {
        return 0
    }
}


class HasPosGrImpl extends HasPos1A2A{

}