

class Process {
    private int id;
    private double weight;
    private boolean active;
    private boolean idle;

    public Process(int id) {
        this.id = id;
        this.weight = 0;
        this.active = false;
        this.idle = true;
    }

    public Process(int id,boolean isActive,int wt) {
        this.id = id;
        this.weight = wt;
        this.active = isActive;
        this.idle = false;
    }


    public void receiveComputationMessage(double weight) {
        if (!isActive()) {
            System.out.println("Receiving computation Message at "+this.id+" wt:"+weight+"\n");
            this.weight += weight;
            this.active = true;


            /*
                DO COMPUTATION
             */
        }
    }

    public void sendComputationMessage(Process receiver, double weight) {

        if (isActive()) {
            System.out.println("Sending Computation Message from "+this.id+" to "+receiver.id+" wt:"+weight);
            double w1 = weight;
            double w2 = Math.abs(weight-this.weight);
            receiver.receiveComputationMessage(w1);
            this.weight = w2;
        }
    }

    public void sendControlMessage(Process receiver, double weight) {
        if (isActive()) {
            System.out.println("Sending Control Message from "+this.id+" to "+receiver.id+" wt:"+weight);
            double w1 = weight;
            double w2 = Math.abs(weight-this.weight);
            this.weight = w2;
            receiver.receiveControlMessage(w1);
            if(this.weight==0) this.idle=true;
        }
    }

    public void receiveControlMessage(double weight) {
        if (isActive()) {
            System.out.println("Receiving Control Message at "+this.id+" wt:"+weight+"\n");
            this.weight += weight;
        }
    }

    public boolean isIdle() {
        return idle;
    }
    public double getWeight(){

        return this.weight;
    }
    public boolean isActive() {
        return active;
    }
}

public class TerminationDetection {
    public static void main(String[] args) {
        Process controllingAgent = new Process(3,true,1);


        Process[] processes = new Process[3];
        for (int i = 0; i < processes.length; i++) {
            processes[i] = new Process(i);
        }
        System.out.println("Process 3 is the controlling agent,while others are idle process is distributed system\n");

        // Simulation - Controlling agent starts computation
        controllingAgent.sendComputationMessage(processes[0], 0.5); // Send B(0.5) to process 2
        controllingAgent.sendComputationMessage(processes[1], 0.3); // Send B(0.3) to process 3
        controllingAgent.sendComputationMessage(processes[2], 0.2); // Send B(0.3) to process 3

        // Simulation - Processes becoming idle i.e. termination starts
        processes[2].sendControlMessage(controllingAgent, 0.2); // Send C(0.2) to controlling agent
        processes[1].sendControlMessage(controllingAgent, 0.3); // Send C(0.2) to controlling agent
        processes[0].sendControlMessage(controllingAgent, 0.5); // Send C(0.2) to controlling agent

        // Check if all processes are idle
        boolean allIdle = controllingAgent.getWeight()
                ==1.0;

        System.out.println("\n\nFinal Weight at controlling agent: "+controllingAgent.getWeight());
        if (allIdle) {
            System.out.println("Termination detected.");
        } else {
            System.out.println("Termination not yet detected.");
        }
    }
}
