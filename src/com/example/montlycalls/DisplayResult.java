package com.example.montlycalls;

import java.sql.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.widget.DatePicker;
import android.widget.EditText;

public class DisplayResult extends Activity {
	public Hashtable fcalls = new Hashtable();
	public Hashtable fcalls1 = new Hashtable();
	long from;
	long to;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_result);
		
		
		Bundle b = getIntent().getExtras();
		from= b.getLong("from");
		to = b.getLong("to");
		
		
		Log.d("op", "From : " + from + " --- To : "+to);
		
		
		
		 String[] projection = new String[] {CallLog.Calls.NUMBER, CallLog.Calls.DATE, CallLog.Calls.CACHED_NAME, CallLog.Calls.DURATION,CallLog.Calls.TYPE};
        Uri contacts =  CallLog.Calls.CONTENT_URI;
 
        Cursor managedCursor = managedQuery(contacts, projection, null, null, CallLog.Calls.DATE + " ASC");
        getColumnData(managedCursor);
        
        
	}

	private void getColumnData(Cursor cur){
        try{
           if (cur.moveToFirst()) {
             String name;
             String number;
             String secs;
             long date;
             int noss;
             int nameColumn = cur.getColumnIndex(CallLog.Calls.CACHED_NAME);
             int numberColumn = cur.getColumnIndex(CallLog.Calls.NUMBER);
             int dateColumn = cur.getColumnIndex(CallLog.Calls.DATE);             
             int nos = cur.getColumnIndex(CallLog.Calls.DURATION);
             int type = cur.getColumnIndex(CallLog.Calls.TYPE);
             
             System.out.println("Reading Call Details: ");
             do {
                 name = cur.getString(nameColumn);
                 number = cur.getString(numberColumn);
                 date = cur.getLong(dateColumn);
                 secs = cur.getString(nos);
                 noss = cur.getInt(nos);
                 /*
                  * type=4 is Outgoing
                 
                 */
                 
                 Log.d("op1","Call : " + date);
                 
                 if(!(from<date && date>to )) continue;
                 
                 if(type==4)
                 {
                	 Double d = Math.ceil(noss/60)+1;
                	 if(fcalls.get(number) != null)
                	 {
                		 fcalls.put(number, ((Integer) fcalls.get(number)) + d.intValue());
                		 Log.d("test",""+Math.floor(noss/60)+1);
                	 }
                	 else
                	 { //null means first time
                		 fcalls.put(number, d.intValue());
                		 if(name == null)
                		 {
                			 fcalls1.put(number, "Noname");
                		 }
                		 else
                		 {
                		 fcalls1.put(number, name);
                		 }
                		 Log.d("test","" +Math.ceil(noss/60)+1);
                	 }
                 }
                 
                 
                 Log.d("op", number + ":"+ String.format("%tc", new Date(date)) +":"+name+":"+noss+":"+type);
                 System.out.println(number + ":"+ new Date(date) +":"+name);
            } while (cur.moveToNext());
             
             EditText et1 =  (EditText)findViewById(R.id.editText2);
             et1.setText("");
             Enumeration fkcalls = fcalls.keys();
             while(fkcalls.hasMoreElements()) {
                String str = (String) fkcalls.nextElement();
                
                et1.setText(et1.getText() + str + ": " +fcalls.get(str) + ":"+fcalls1.get(str) + "\n");
             }
          }
      } 
  
      finally{
        cur.close();
      }
        
       /* try {
			com.mashape.unirest.http.HttpResponse<JsonNode> request = Unirest.get("https://indianmobilenumberinfo.p.mashape.com/index.php?mobilenos=9894227847%2C9247107730")
					  .header("X-Mashape-Authorization", "vwXnCWRGkyYD09ygaSSUuFdvtKSNFS1z")
					  .asJson();
			System.out.println(request.getCode());
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
        Log.d("op","Completed");
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_result, menu);
		return true;
	}

}
