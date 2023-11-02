public class SharedObject {
    boolean isReady = false;

    synchronized void waitForSignal() {
        while (!isReady) {
            try {
                wait(); // 线程等待信号
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    synchronized void sendSignal() {
        isReady = true;
        notify(); // 唤醒等待的线程
    }
}