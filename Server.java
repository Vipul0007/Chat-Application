import java.net.*;
import java.io.*;


class Server{

    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

        //constructor
    public Server(){
        try {
            server = new ServerSocket(7777, 0, null);
            System.out.println("Srever is ready to accept connection");
            System.out.println("waiting...");
            socket = server.accept();       //input stream to read data -- output stream to write data

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void startReading(){                 //Multithreding to prform the task of reading and writing simultaneously
        //thread-read kark deta rahega

        Runnable r1 =()->{
            System.out.println("reader started..");
            try{

            while(true){
                String msg = br.readLine();
                if(msg.equals("exit"))
                {
                    System.out.println("Client has terminated the session");

                    socket.close();
                    break;
                }

                System.out.println("Client : "+msg);
            }
        }catch (Exception e) {
                    e.printStackTrace();
                }

        };

        new Thread(r1).start();


    }
    public void startWriting(){
        //thread-data user lega and thn send karega client tak
        Runnable r2=()->{
            System.out.println("Writer Started...");
            try{
            while(true && !socket.isClosed()){

               

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();

                    out.println(content);
                    out.flush();

                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }
                    
            
            }
        }catch (Exception e) {
                    e.printStackTrace();
                }

        };

        new Thread(r2).start();



    }


    public static void main(String[] args) {
        System.out.println("this is server...Starting the server");
        new Server();
    }
}