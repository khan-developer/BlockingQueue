import java.awt.image.renderable.RenderableImageProducer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args) {

        int BOUND = 10;
        int N_CONSUMERS = Runtime.getRuntime().availableProcessors();
        int N_PRODUCERS = 4;
        int poisonPill = Integer.MAX_VALUE;
        int poisonPillPerProducer = N_CONSUMERS / N_PRODUCERS;
        int mod = N_CONSUMERS % N_PRODUCERS;


        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(BOUND);
        //Runnable producer = new Producer("Producer",queue);
        for (int i = 1; i < N_PRODUCERS; i++) {
            new Thread(new NumbersProducer(queue, poisonPill, poisonPillPerProducer)).start();
        }

        for (int j = 0; j < N_CONSUMERS; j++) {
            new Thread(new NumbersConsumer(queue, poisonPill)).start();
        }

        new Thread(new NumbersProducer(queue, poisonPill, poisonPillPerProducer+mod)).start();
    }
}
