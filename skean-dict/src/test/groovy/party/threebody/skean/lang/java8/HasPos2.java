package party.threebody.skean.lang.java8;

public interface HasPos2 extends HasPos {

    default int getY(){
        return 2;
    }

}