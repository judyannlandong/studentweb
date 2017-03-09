package com.example.studentweb;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends Activity implements OnItemSelectedListener, OnClickListener {

	EditText txtIdno,txtName;
	Spinner cboCourse,cboYear;
	Button btnSend,btnCancel;
	private String selectedCourse;
	private String selectedYear;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.txtIdno=(EditText) this.findViewById(R.id.editText1);
        this.txtName=(EditText) this.findViewById(R.id.editText2);
        this.cboCourse=(Spinner) this.findViewById(R.id.spinner1);
        this.cboYear=(Spinner) this.findViewById(R.id.spinner2);  
        this.btnSend=(Button) this.findViewById(R.id.button1);
        this.btnCancel=(Button) this.findViewById(R.id.button2);
    
    
        this.cboCourse.setOnItemSelectedListener(this);
        this.cboYear.setOnItemSelectedListener(this);
    
        this.btnSend.setOnClickListener(this);
        this.btnCancel.setOnClickListener(this);
        
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
        
    }


	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		selectedCourse=this.cboCourse.getItemAtPosition(arg2).toString();
		selectedYear=this.cboYear.getItemAtPosition(arg2).toString();
		
	}


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onClick(View arg0) {
	   int id=arg0.getId();
	   switch(id){
	   case R.id.button1:
	   
		   String idno=this.txtIdno.getText().toString();
		   String name=this.txtName.getText().toString();
		   
		   String[] mname=name.split("\\");
		   StringBuffer cleanName=new StringBuffer();
		   for(String s:mname){
			   cleanName.append(s).append("%20");
		   }
	   
		   try {
			URL url=new URL("http://10.0.2.2/student/addstudent.php?idno="+idno+"&name="+cleanName.toString()+"&course="+selectedCourse+"&year="+selectedYear);
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			
			InputStream is=conn.getInputStream();
			int c=0;
			StringBuffer sb=new StringBuffer();
			while((c=is.read())!=-1){
				
				sb.append((char)c);
			}
		   
			is.close();
			conn.disconnect();
		   
		   
		   Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
		   
			
		   } catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
		   
	   case R.id.button2:
	     this.txtIdno.setText("");
	     this.txtName.setText("");
	     this.cboCourse.setSelection(0);
	     this.cboYear.setSelection(0);
	     this.txtIdno.requestFocus();
	   }
		
		
	}

}
