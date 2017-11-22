package sofianebattou.smarttrafficlights;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Menu extends AppCompatActivity {

    /**Port used to communicate with the arduinos. */
    private static int communicationPort=44;

    /**EditText used by the user to enter the Ip address of the Arduino.  */
    private CustomEditText myEditText=null;

    static int arduinoNumberToConnectTo=1;

    /**PrintWriter used to communicate with the first intersection. */
    static PrintWriter interOne = null;

    /**Printwriter used to communicate with the second intersection. */
    static PrintWriter interTwo=null;

    static OutputStream outOne=null;
    static OutputStream outTwo=null;
    static String TAG="Menu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void showMap(View v){
        startActivity(new Intent(Menu.this, Map.class));
    }

    /** Searches for the Arduinos and saves them as clients so that messages can be sent to them later. */
    public void connectToArduinos(View v){
        DialogFragment newFragment=new ArduinoIpFragment();
        newFragment.show(getFragmentManager(),"enter_ip");
    }

    /**Connects to the Arduino whose Ip address was just entered.  */
    void connect(){
        Editable ip=myEditText.getText();
        String theIp= ip.toString();
        System.out.println("Ip address:"+theIp);
        new RetrieveFeedTask().execute(theIp);
    }

    /**Sends commands to the Arduinos so that they start running the default light sequences. */
    public void startRunning(View v){

        class sendCommandTask extends AsyncTask<String, Void, Void> {

            //  private Exception exception;

            protected Void doInBackground(String... ip) {
                try {
                    byte a=1;
                    outOne.write(a);

                } catch (IOException e) {
                    e.printStackTrace();
                /*Toast.makeText(this, "Problem connecting to intersection.",
                        Toast.LENGTH_LONG).show();*/
                }
                return null;}
        }

        new sendCommandTask().execute("Null");
        Log.d(TAG,"Sent three chars to the controller.");

    }
    /**Sends commands to the Arduinos so that they start running the default light sequences. */
    public static void startRunning(final int intersection){

        class sendCommandTask extends AsyncTask<String, Void, Void> {

            //  private Exception exception;

            protected Void doInBackground(String... ip) {
                try {
                    if(intersection==1){

                        byte a=1;
                        outOne.write(a);
                    }else if (intersection==2){

                        byte a=1;
                        outTwo.write(a);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                /*Toast.makeText(this, "Problem connecting to intersection.",
                        Toast.LENGTH_LONG).show();*/
                }
                return null;}
        }

        new sendCommandTask().execute("Null");
        Log.d(TAG,"Sent three chars to the controller.");

    }



    /**To set the edittext in order to read the Ip address entered by the user.  */
    void setCustomEditText(CustomEditText a){
        myEditText=a;
        myEditText.setHint("Enter Arduino #"+arduinoNumberToConnectTo+" Ip address");
        Log.d("Menu","myEditText object just got initialized.");
    }

    /**Sends the message specified to the intersection specified.  */
    void sendMessageTo(Character message, int intersection){

    }

    class RetrieveFeedTask extends AsyncTask<String, Void, Void> {

        //  private Exception exception;

        protected Void doInBackground(String... ip) {
            try {
                String host=ip[0];
                Socket s= new Socket(host,communicationPort);
                if(arduinoNumberToConnectTo==1){
                    outOne=s.getOutputStream();
                    interOne= new PrintWriter(s.getOutputStream(),true);
                    arduinoNumberToConnectTo++;
                   /* Toast.makeText(this, "Successfully connected to Intersection #1",
                            Toast.LENGTH_SHORT).show();*/
                }else if(arduinoNumberToConnectTo==2){
                    outTwo=s.getOutputStream();
                    interTwo= new PrintWriter(s.getOutputStream(),true);
                    arduinoNumberToConnectTo++;
                    /*Toast.makeText(this, "Successfully connected to Intersection #2",
                            Toast.LENGTH_SHORT).show();*/
                }

            } catch (IOException e) {
                e.printStackTrace();
                /*Toast.makeText(this, "Problem connecting to intersection.",
                        Toast.LENGTH_LONG).show();*/
            }
            return null;}
    }
}