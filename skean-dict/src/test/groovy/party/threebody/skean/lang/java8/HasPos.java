package party.threebody.skean.lang.java8;

public interface HasPos {

    int getX();

    int getY();

    default String getName(){
        return "ANONYMOUS";
    }
}