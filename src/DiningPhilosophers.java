
// To create fork objects
class Fork  {}

// To create Philosopher object Using runnable interface
class Philosopher implements Runnable {

   
    private Fork leftFork;
    private Fork rightFork;

    // constructor
    public Philosopher(Fork leftFork, Fork rightFork) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }
    //To create a common method to simulatels all actions (thinking, acquiring left fork, acquiring right fork , eating, putting down left fork,putting down right fork )
   private void makeAction(String action) throws InterruptedException {
        System.out.println(
          Thread.currentThread().getName() + " " + action);
        //sleeping time varing between 200 and 1200;
        Thread.sleep(((int) (Math.random() * 1000)+200));
    }


    @Override
    public void run() {
        try {
            while (true) {
                
                // thinking actions
                makeAction(": Thinking");
                synchronized (leftFork) {
                    makeAction(": Picked up left fork");
                    synchronized (rightFork) {
                        // eating
                        makeAction(": Picked up right fork"); 

                        makeAction(": Eating");
                        
                        makeAction(": Put down right fork");
                    }
                    
                    // Back to thinking
                    makeAction(": Put down left fork. Waiting");
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
    }
}



public class DiningPhilosophers {

    public static void main(String[] args) throws Exception {

        final Philosopher[] philosophers = new Philosopher[5];
        Fork[] forks = new Fork[philosophers.length];

        for (int i = 0; i < forks.length; i++) {
            forks[i] = new Fork();
        }

        for (int i = 0; i < philosophers.length; i++) {
            Fork leftFork =  forks[i];
            Fork rightFork = forks[(i + 1) % forks.length];
            
            /*  In  order to prevent deadlock scenario we should switch right fork and left fork of a
                philasopher compare to other philosopher. Then only one philosopher can acquire 
                the both right fork and left fork. and start eating
            */
            if (i == philosophers.length - 1) {
                
                // According to above para, the last philosopher picks up the right fork first
                philosophers[i] = new Philosopher(rightFork, leftFork); 
            } else {
                philosophers[i] = new Philosopher(leftFork, rightFork);
            }
            
            Thread t 
              = new Thread(philosophers[i], "Philosopher " + (i + 1));
            t.start();
        }
    }
}

