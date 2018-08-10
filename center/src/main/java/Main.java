import tcp.TCP_Connection_Thread;

public class Main {
    public static void main(String[] args) {

        TCP_Connection_Thread tcp_connection = new TCP_Connection_Thread();
        Thread tcp_thread = new Thread(tcp_connection);
        tcp_thread.start();
        try {
            tcp_thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
