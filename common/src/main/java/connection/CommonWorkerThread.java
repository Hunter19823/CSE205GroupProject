package connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CommonWorkerThread extends Thread implements Subject<CommonWorkerThread,Object>{

    // Private fields.
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private List<IObserver> observers = new ArrayList<>();


    public CommonWorkerThread(Socket socket)
    {
        this.socket = socket;
        //this.socket.setKeepAlive(true);
    }

    // Entry point for this class.
    @Override
    public void run()
    {
        try {
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());

            Object message;
            // Send the first message.
            writeMessage("Ping!");
            while(socket.isConnected() && socket.isBound() && !socket.isClosed()){
                if((message = readMessage()) != null){
                    // Notify any observers of new messages.
                    notifyUpdate(message);
                }
            }

        } catch (IOException e) {
            // Log any errors.
            System.err.println("Problem with setting socket "+e.toString());
        } finally {
            // Close the socket when finally finished.
            close();
        }
        System.out.println("Finished with socket.");
    }

    public Object readMessage(){
        try {
            Object obj = objectInputStream.readObject();
            return obj;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
            //The line above will spam console.
        }

        return null;
    }

    public void writeMessage(Object obj){
        try {
            objectOutputStream.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void attach( IObserver<CommonWorkerThread,Object> IObserver )
    {
        observers.add(IObserver);
    }

    @Override
    public void detach( IObserver<CommonWorkerThread,Object> IObserver )
    {
        observers.remove(IObserver);
    }

    @Override
    public void notifyUpdate( Object message )
    {
        for(IObserver<CommonWorkerThread,Object> o : observers){
            o.onUpdate(this,message);
        }
    }

    public void close()
    {
        System.out.println("Closing Socket");
        if(objectInputStream != null){
            try {
                objectInputStream.close();
            } catch (IOException e) {}
        }
        if(objectOutputStream != null){
            try {
                objectOutputStream.close();
            } catch (IOException e) {}
        }
        if(socket != null) {
            try {
                socket.close();
            } catch (IOException e) {}
        }
    }
}
