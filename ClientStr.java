
package com.mycompany.clientcalcolatrice;
import java.awt.BorderLayout;
import java.io.*;
import java.net.*;
public class ClientStr {
    //socket
    String nomeServer = "localHost";
    int portaServer = 6789;
    Socket miosocket;
    //dati
    BufferedReader tastiera;
    String stringaUtente;
    String stringaRicevutaDalServer;
    DataOutputStream outVersoServer;
    BufferedReader inDalServer;
    
    public void comunica() {
        try
        {
            //inserimento dati
            System.out.println("5 ... inserisci il 1 operando:"+'\n');
            stringaUtente = tastiera.readLine();
            System.out.println("6 ... invio il 1 operando al server e attendo ...");
            outVersoServer.writeBytes(stringaUtente+'\n');
            System.out.println("8 ... inserisci il 2 operando:"+'\n');
            stringaUtente = tastiera.readLine();
            System.out.println("9 ... invio il 2 operando al server e attendo ...");
            outVersoServer.writeBytes(stringaUtente+'\n');
            System.out.println("11 ... Inserisci SOM per fare la somma \n inserisci SOT per fare la sottrazione \n inserisci MOL per fare la moltiplicazione \n inserisci DIV per fare la divisione :");
            stringaUtente = tastiera.readLine();
            System.out.println("9 ... invio il operatore al server e attendo ...");
            outVersoServer.writeBytes(stringaUtente+'\n');
            
            //attesa del server + risposta
            stringaRicevutaDalServer=inDalServer.readLine();
            System.out.println("12 ... risposta dal server "+'\n'+stringaRicevutaDalServer);
            //chiude la connessione 
            System.out.println("9 ... CLIENT:termina elaborazione e chiude connessione");
            miosocket.close();
        }
        //controllo errori
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la comunicazione col server");
            System.exit(1);
        }
    }
    public Socket connetti(){
        System.out.println("2 ... CLIENT partito in esecuzione ...");
        try
        {
            //inizializzazione
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            miosocket = new Socket(nomeServer,portaServer);
            outVersoServer = new DataOutputStream(miosocket.getOutputStream());
            inDalServer = new BufferedReader(new InputStreamReader(miosocket.getInputStream()));
        }
        //errori
        catch(UnknownHostException e)
        {
            System.err.println("host sconoscito");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione");
            System.exit(1);
        }
        return miosocket;
    }
    public static void main(String args[])
    {
        ClientStr cliente = new ClientStr();
        cliente.connetti();
        cliente.comunica();
    }
   
}

