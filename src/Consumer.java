import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.util.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.ObjectOutputStream;


public class Consumer extends Thread {

    FileOutputStream out;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    String videoname;
    Socket conn;
    String folder;


public Consumer(Socket conn, String videoname,String folder,ObjectOutputStream oos, ObjectInputStream ois) {

    this.conn = conn;
    this.videoname=videoname;
    this.folder=folder;
    this.oos = oos;
    this.ois = ois;
}


    @Override
    public void run() {

        try {


            oos.writeObject(videoname);
            out = new FileOutputStream(folder+"/"+videoname);
            oos.flush();

            byte[] bytes = new byte[512];
            int count = 0;
            while ((count=ois.read(bytes)) > 0 ) {
                out.write(bytes);

            }
            out.flush();

            System.out.println("Finished video receiving");


        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{

            try {
                ois.close();
                oos.close();
                out.close();
                conn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }


}