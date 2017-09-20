package sofianebattou.smarttrafficlights;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
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
        Log.d("Ip address:",theIp);

        try {
            Socket s= new Socket(theIp,communicationPort);
            if(arduinoNumberToConnectTo==1){
                interOne= new PrintWriter(s.getOutputStream(),true);
                arduinoNumberToConnectTo++;
                Toast.makeText(this, "Successfully connected to Intersection #1",
                        Toast.LENGTH_SHORT).show();
            }else if(arduinoNumberToConnectTo==2){
                interTwo= new PrintWriter(s.getOutputStream(),true);
                arduinoNumberToConnectTo++;
                Toast.makeText(this, "Successfully connected to Intersection #2",
                        Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Problem connecting to intersection.",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**Sends commands to the Arduinos so that they start running the default light sequences. */
    public void startRunning(View v){


    }
    /**To set the edittext in order to read the Ip address entered by the user.  */
    void setCustomEditText(CustomEditText a){
        myEditText=a;
        myEditText.setHint("Enter Arduino #"+arduinoNumberToConnectTo+" Ip address");
    }

    /**Sends the message specified to the intersection specified.  */
    void sendMessageTo(Character message, int intersection){

    }
}
