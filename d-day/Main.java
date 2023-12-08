
public class Main {

    public static void main(String[] args) {
        Site A = new Site("A"); //Creating 4 sites
        Site B = new Site("B");
        Site C = new Site("C");
        Site D = new Site("D");

        //Each sites contains 3 processes
        A.addProcess(1);         A.addProcess(2);
        A.addProcess(3);

        B.addProcess(4);         B.addProcess(5);
        B.addProcess(6);

        C.addProcess(7);         C.addProcess(8);
        C.addProcess(9);

        D.addProcess(10);         D.addProcess(11);
        D.addProcess(12);

        //Sites dependencies
        A.neighbours.add(B); B.neighbours.add(C); C.neighbours.add(D);
        D.neighbours.add(A);

        //start algo at A,with initial probe msg
        Probe probe = new Probe(A.getFirstProcess(),A.getFirstProcess(),A.getLastProccess());

        dfsCycleDetect(probe,A);
        /*
            If i==k at any point,
                prints Deadlock Detected!
            else
                no output
         */
    }

    public static void dfsCycleDetect(Probe probe,Site curr){
        if(probe.i==probe.k){
            System.out.println("Deadlock Detected!");
            return;
        }

        for(Site neigh:curr.neighbours){
            dfsCycleDetect(new Probe(probe.i,curr.getLastProccess(),neigh.getFirstProcess()),neigh);
        }
    }

}