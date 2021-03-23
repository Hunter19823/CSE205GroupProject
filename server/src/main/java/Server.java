import java.io.IOException;

public class Server {
    public static void main(String[] args)
    {
        System.out.println("Hello server.");
        System.out.println("Server Starting...");
        try{
            ServerListenerThread listenerThread = new ServerListenerThread(48576,"localhost");
            listenerThread.start();
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("All Servers Finished.");
    }



    public static void onMessage(CommonWorkerThread workerThread, Object message){
        System.out.println("Received Message: "+message);
        if(message instanceof String){
            switch((String)message){
                case "Pong!":
                    workerThread.writeMessage("Silence");
                    workerThread.close();
                    break;
                case "Close":
                    workerThread.close();
                    break;
            }
        }
    }
}
