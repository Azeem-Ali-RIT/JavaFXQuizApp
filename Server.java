package MCQGame;

import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
//import java.io.BufferedReader;
import java.io.IOException;
import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
//import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
//import java.util.Random;
import java.util.Scanner;

import javafx.scene.control.Label;

public class Server {
    public void connect(){
        try{
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server is Searching for client");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String guess = in.readLine();
            int clientGuess = Integer.parseInt(guess);
            if(clientGuess == 1){
                listen(clientSocket, in);
            }
            else if(clientGuess == 0){
                fetch(clientSocket);
            }

            in.close();

            serverSocket.close();

        }catch (IOException e){
            System.out.println("ON the server part");
            e.printStackTrace();
        } 
        
    }

    private void listen(Socket clientSocket, BufferedReader in) throws UnknownHostException, IOException{

        try{
            //BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            //in.readLine();
            String toAdd = in.readLine();

            System.out.println(toAdd);

            in.close();

            String filename = "D:\\Java\\MCQGame\\leaderBoard.txt";

            FileReader fileW = new FileReader(filename);
            Scanner readEr = new Scanner(fileW);
            Boolean elinecheck = readEr.hasNextLine();
            fileW.close(); readEr.close();

            FileWriter file = new FileWriter(filename, true);
            BufferedWriter bUff = new BufferedWriter(file);

            if(elinecheck == true){bUff.newLine();}
            bUff.write(toAdd);  
            bUff.close();
        }

        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void fetch(Socket socket) throws FileNotFoundException{

        String[][] Leaderarray = LoadLeaderBoard();
        try{
            
            //Socket socket = new  Socket("localhost", 12345);
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            String strArr = new Gson().toJson(Leaderarray);

            out.println(strArr);
            out.flush();
            // inp.nextLine();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter resp = new PrintWriter(socket.getOutputStream(), true);
            
            //System.out.println(resp);

            in.close();
            out.close();
            socket.close();

        }catch (IOException e){
            System.out.println("lllllll");
            e.printStackTrace();
        }
    }


    public String[][] LoadLeaderBoard() throws FileNotFoundException{
        /* Creating 2-D array and reads leaderboard file and gives top 5 */
        String leaderBaord = "LEADER BOARD \n User | Score% | Seconds\n";
        int numPos = 0; 
        String[] usersAndScores;
        String fileName = "D:\\Java\\MCQGame\\leaderBoard.txt";
        int numOflines = getNumOfLines(fileName);
        System.out.println(numOflines);
        String[][] DArray = new String[numOflines][3];
        System.out.println("check");
        FileReader file;

        System.out.println("");
        try {

            file = new FileReader(fileName);
            Scanner readER = new Scanner(file);

            while(numPos < numOflines){
                usersAndScores = readER.nextLine().split(", ");
                
                DArray[numPos] = usersAndScores;

                numPos++;
            }
                //leaderBaord = leaderBaord + numPos + ". " + usersAndScores[1] + ": " + usersAndScores[0] + '\n';
                //numPos++;
            //
            readER.close();
            numPos = 1;

            System.out.println(DArray.length);

            DArray = Sort(DArray);

            System.out.println("o");        
           
            

            //toLoad.setText(leaderBaord);
            readER.close();
            return DArray;

        } catch (FileNotFoundException e) {
            System.out.println("Hi");
            e.printStackTrace();
            return null;
        }
            //BufferedReader readER = new BufferedReader(file);

        
    }


    public int getNumOfLines(String file) throws FileNotFoundException{
        /* Reads the file questionBase*/
        int count = 0;

        FileReader filetoRead = new FileReader(file);
        Scanner readER = new Scanner(filetoRead);
        System.out.println("check002");
        while(readER.hasNextLine()){
            System.out.println(readER.nextLine());
            count++;
            
        }
        System.out.println("check0");
        readER.close();
        return count;
    }

    public String[][] Sort(String[][] DArray){
        /*Uses insertion sort to sort the leaderboard from highest to lowest score */

        for (int i = 1; i < DArray.length; i++) {
            String[] key = DArray[i];
            int j = i - 1;
    
            while (j >= 0 && Integer.parseInt(DArray[j][0]) <= Integer.parseInt(key[0])) {

                if(Integer.parseInt(DArray[j][0]) == Integer.parseInt(key[0]) && Integer.parseInt(DArray[j][2]) < Integer.parseInt(key[2])){
                    break;
                }

                DArray[j + 1] = DArray[j];
                j = j - 1;
            }
            DArray[j + 1] = key;
        }

        return DArray;
    
    }

    public static void main(String[] args){

        Server server = new Server();

        
        while(true){
            server.connect();
        }
    }
    
}
