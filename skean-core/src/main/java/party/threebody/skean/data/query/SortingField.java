package party.threebody.skean.data.query;

public class SortingField {
    private String name;
    private boolean isDesc;


    public SortingField(String name) {
        this.name = name;
    }

    public SortingField(String name, boolean isDesc) {
        this.name = name;
        this.isDesc = isDesc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDesc() {
        return isDesc;
    }

    public void setDesc(boolean isDesc) {
        this.isDesc = isDesc;
    }


}
