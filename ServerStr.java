
package com.mycompany.servercalcolatrice;


import java.io.*;
import java.net.*;
import java.util.*;
public class ServerStr {
    //dati
    ServerSocket server = null;
    Socket client = null;
    String stringaRicevuta = null;
    String stringaModificata = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    
    int operando1;
    int operando2;
    double risultato;
    boolean controllo= false;
    
    public Socket attendi()
    {
        try 
        {
            //inizializzazione
            System.out.println("1 ... SERVER partito in esecuzione ...");
            server = new ServerSocket(6789);
            client = server.accept();
            server.close();
            inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outVersoClient = new DataOutputStream(client.getOutputStream());
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'istanzadel server ");
            System.exit(1);
        }
        return client;
    }
    
    public void comunica()
    {
        try
        {
            System.out.println("3 ... benvenuto client, mandami 2 operandi e 1 operatore ...");
            System.out.println("4 ... operando 1 :");
            stringaRicevuta= inDalClient.readLine();
            operando1 =Integer.parseInt(stringaRicevuta);
            System.out.println("7 ... operando 2 :");
            stringaRicevuta= inDalClient.readLine();
            operando2 =Integer.parseInt(stringaRicevuta);
            System.out.println("10 ... operatore :");
            stringaRicevuta= inDalClient.readLine();
            switch(stringaRicevuta){
                case "SOM" :
                    risultato=operando1 + operando2;
                    controllo=true;
                    break;
                case "SOT" :
                    risultato=operando1 - operando2;
                    controllo=true;
                    break;
                case "MOL" :
                    risultato=operando1 * operando2;
                    controllo=true;
                    break;
                case "DIV" : 
                    risultato=operando1 / operando2;
                    controllo=true;
                    break;
                default : 
                    System.out.println("operatore è erratto");
                    controllo=false;
                    break;
                    
                    
            }
            
            if(controllo=true)
            {
                System.out.println("11 ... calcolato il risultato con gli operandi ricevuti  : "+risultato);
                System.out.println("12 ... invio il risultato al client ...");
                stringaModificata = Double.toString(risultato);
                outVersoClient.writeBytes(stringaModificata+'\n');
            }
            else if (controllo=false )
            {
                System.out.println("11 ... errore nel inserimento dei dati");
                stringaModificata = "errore nel inserimento";
                outVersoClient.writeBytes(stringaModificata+'\n');
                
            }
            System.out.println("9 --- SERVER: fine elaborazione ... buona notte!");
            client.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Errore");
            System.exit(1);
        }
        
    }
    
    public static void main(String args[])
    {
        ServerStr servente = new ServerStr();
        servente.attendi();
        servente.comunica();
    }
    
}
