package com.example.montlycalls;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Hashtable;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.montlycalls.MESSAGE";
	public Hashtable fcalls = new Hashtable();
	public Hashtable fcalls1 = new Hashtable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Album_db db = new Album_db(this);
        MonthlyLog db=new MonthlyLog(this);
        db.open();
        long l=db.insert();
        db.select();
        //DBAdapter db=new DBAdapter(this);
        //db.open();
        
        //long id = db.insertContact("Wei-Meng Lee", "weimenglee@learn2develop.net");
        //id = db.insertContact("Mary Jackson", "mary@jackson.com");
        //db.close();
        
        Log.d("op","Inserted a row" + db.toString() + "-" + l);
        
        Log.d("op", "Welcome");
        Button b1 =  (Button)findViewById(R.id.button1);
        Button b2 = (Button)findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				DatePicker dp1 = (DatePicker ) findViewById(R.id.datePicker1);
                DatePicker dp2 = (DatePicker ) findViewById(R.id.datePicker2);
                
                int yyyy1=dp1.getYear();
                int mm1=dp1.getMonth();
                int dd1=dp1.getDayOfMonth();
                
                int yyyy2=dp2.getYear();
                int mm2=dp2.getMonth();
                int dd2=dp2.getDayOfMonth();
                
                Log.d("OP","C:"+yyyy1+":"+mm1+":"+dd1+"-"+yyyy2+":"+mm2+":"+dd2);
                String y1=Integer.toString(yyyy1);
              
                String str1 = yyyy1+"-"+mm1+"-"+dd1+" 12:00:00";  //mm1+" "+dd1+" "+yyyy1+" 12:01:01.000 UTC";
                String str2 = yyyy2+"-"+mm2+"-"+dd2+" 12:00:00";
                SimpleDateFormat df1 = new SimpleDateFormat("M dd yyyy HH:mm:ss.SSS z");
                SimpleDateFormat df2 = new SimpleDateFormat("M dd yyyy HH:mm:ss.SSS z");
                
                java.util.Date date1=new java.util.Date();
                java.util.Date date2=new java.util.Date();
				try {
					date1 = new SimpleDateFormat("yyyy-M-dd HH:mm:ss").parse(str1);
					date2 = new SimpleDateFormat("yyyy-M-dd HH:mm:ss").parse(str2);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                
                /*java.util.Date date1=new java.util.Date();
                java.util.Date date2=new java.util.Date();
                try {
					//date1 = df1.parse(str1);
					date2 = df2.parse(str2);
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
                
				
				long dd11=date1.getTime();
                long dd21=date2.getTime();
                
                Log.d("OP",""+dd11+"&"+dd21); // 1055545912454
               
                
				Intent intent = new Intent(MainActivity.this, DisplayResult.class);
				Bundle b = new Bundle();
				b.putLong("from", dd11); //Your id
				b.putLong("to", dd21);
				intent.putExtras(b); //Put your id to your next Intent
				startActivity(intent);
				finish();
				
				//startActivity(new Intent("com.example.montlycalls.DisplayResult"));
				
				
			}
			
		});
		b1.setOnClickListener(new View.OnClickListener() {
			
	        
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//Toast.makeText(getBaseContext(), "Hello", Toast.LENGTH_LONG).show();

		        String[] projection = new String[] {CallLog.Calls.NUMBER, CallLog.Calls.DATE, CallLog.Calls.CACHED_NAME, CallLog.Calls.DURATION,CallLog.Calls.TYPE};
		        Uri contacts =  CallLog.Calls.CONTENT_URI;
		 
		        Cursor managedCursor = managedQuery(contacts, projection, null, null, CallLog.Calls.DATE + " ASC");
		        getColumnData(managedCursor);
		        
		        
			}
		});
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
                 
                 if(noss==0) continue;
                 DatePicker dp1 = (DatePicker ) findViewById(R.id.datePicker1);
                 DatePicker dp2 = (DatePicker ) findViewById(R.id.datePicker2);
                 
                 int yyyy1=dp1.getYear();
                 int mm1=dp1.getMonth();
                 int dd1=dp1.getDayOfMonth();
                 
                 int yyyy2=dp2.getYear();
                 int mm2=dp2.getMonth();
                 int dd2=dp2.getDayOfMonth();
                 
                 String y1=Integer.toString(yyyy1);
                 
                 
                 Date d1 = new Date(yyyy1,mm1+1,dd1);
                 Date d2 =  new Date(yyyy2,mm2+1,dd2);
                 
                 Log.d("op","" + (d1.getTime())+" - - "+ dd1);
                
                 if(d1.getTime()>=d2.getTime()) continue;
                 
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
             
             EditText et1 =  (EditText)findViewById(R.id.editText1);
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}

