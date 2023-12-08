import java.util.ArrayList;

public class Site {
    public String name;
    public ArrayList<Site> neighbours;
    private ArrayList<Integer> proceses;

    public Site(String name){
        this.name = name;
        this.neighbours=new ArrayList<>();
        this.proceses = new ArrayList<>();
    }
    public int getFirstProcess(){
        return this.proceses.get(0);
    }
    public int getLastProccess(){
        return this.proceses.get(this.proceses.size()-1);
    }
    public void addProcess(int id){
        this.proceses.add(id);
    }
}
