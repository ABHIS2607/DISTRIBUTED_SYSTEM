import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
class Process {
    private int processId;
    private boolean alive;

    public Process(int processId) {
        this.processId = processId;
        this.alive = true;
    }
public void receiveAYA() {
        alive = true;
        System.out.println("Process " + processId + " received AYA message. It is alive.");
    }
    public void sendAYA(Process neighbor) {
        System.out.println("Process " + processId + " sends AYA message to Process " + neighbor.processId);
        neighbor.receiveAYA();
    }

    public boolean isAlive() {
        return alive;
    }
}
public class EdgeChasing {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();

        System.out.print("Enter the periodicity of AYA messages (in milliseconds): ");
        int periodicity = scanner.nextInt();

        System.out.print("Enter the duration after which a process failure occurs (in milliseconds): ");
        int failureDuration = scanner.nextInt();
       Process[] processes = new Process[numProcesses];
       for (int i = 0; i < numProcesses; i++) {
            processes[i] = new Process(i);
        }
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < numProcesses; i++) {
                    Process sender = processes[i];
                    Process receiver = processes[(i + 1) % numProcesses];
                    sender.sendAYA(receiver);
                }
            }
        }, 0, periodicity);
        Timer failureTimer = new Timer(true);
        failureTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                int processToFail = 2;
                processes[processToFail].receiveAYA(); 
            }
        }, failureDuration);
        try {
            Thread.sleep(failureDuration + periodicity * 10); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        scanner.close();
    }
}
