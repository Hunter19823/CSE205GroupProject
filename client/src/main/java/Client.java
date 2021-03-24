import connection.CommonWorkerThread;

import java.io.IOException;
import java.net.Socket;

public class Client {

    public static void main(String[] args)
    {
        System.out.println("Hello Client");
        try {
            CommonWorkerThread clientConnectionsThread = new CommonWorkerThread(new Socket("localhost",48576));
            clientConnectionsThread.attach(Client::onMessage);
            clientConnectionsThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finished Connecting to all servers.");
    }


    public static void onMessage(CommonWorkerThread workerThread, Object message){
        System.out.println("Received Message: "+message);
        if(message instanceof String){
            switch((String)message){
                case "Ping!":
                    workerThread.writeMessage("Pong!");
                    break;
                case "Close":
                case "Silence":
                    workerThread.close();
                    break;

            }
        }
    }
}
