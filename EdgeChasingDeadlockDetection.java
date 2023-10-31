import java.util.ArrayList;
import java.util.Scanner;
class ProbeMessage{
    public int src;
    public int dest;
    public int to;

    public ProbeMessage(int src,int dest,int to){
        this.src=src;
        this.dest=dest;
        this.to=to;
    }
}
public class EdgeChasingDeadlockDetection{
    private static final Scanner s =new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Enter Number of Processes:");
        int nProcess=s.nextInt();
        s.nextLine();
        ArrayList<Integer>[] adjList = new ArrayList[nProcess];
        for(int i=0;i<nProcess;i++){
            System.out.println("Process "+i+ " is dependent on:");
            adjList[i]=new ArrayList<Integer>();
            adjList[i].add(s.nextInt());

            s.nextLine();
        }
        System.out.println("Enter initial PID to start algorithm:");
        int start= s.nextInt();
        for(int adj:adjList[start])
            edgeChasingDeadlockDetect(new ProbeMessage(start,adj,adj+1),adjList);
    }
    public static void edgeChasingDeadlockDetect(ProbeMessage probeMessage,ArrayList<Integer>[] adjList){
        System.out.println("Probe Msg: "+probeMessage.src+" "+probeMessage.dest+" "+probeMessage.to);
        if(probeMessage.to >= adjList.length) return;

        if(probeMessage.src==probeMessage.to) {
            System.out.println("Deadlocked detected in cluster! " +probeMessage.src+ " causing cycle.");

            return;
        }

        for(int dest:adjList[probeMessage.dest]){


            for(int to:adjList[dest]){
                edgeChasingDeadlockDetect(new ProbeMessage(probeMessage.src,dest,to),adjList);
            }



        }
    }
}