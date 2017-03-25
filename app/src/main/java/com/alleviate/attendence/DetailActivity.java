package com.alleviate.attendence;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class DetailActivity extends AppCompatActivity {

    String share_info="",username="user";
    StringBuilder attend_info = new StringBuilder("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        setTitle("Information");
        String name = "";
        Intent in = getIntent();
        username = in.getStringExtra("Username");

        TextView tname = (TextView)findViewById(R.id.name);
        TextView tuname = (TextView)findViewById(R.id.username);
        TextView tregno = (TextView)findViewById(R.id.regno);
        TextView tcontact = (TextView)findViewById(R.id.contact);
        TextView tsem = (TextView)findViewById(R.id.semester);
        TextView tyear = (TextView)findViewById(R.id.year);
        TextView taddress = (TextView)findViewById(R.id.address);
        TextView tattendance = (TextView)findViewById(R.id.attendance);

        SQLiteHelper db = new SQLiteHelper(getApplicationContext());
        SQLiteDatabase dbr = db.getReadableDatabase();

        String[] col = {db.db_info_id,db.db_info_no,db.db_info_name,db.db_info_username,db.db_info_contact,db.db_info_sem,db.db_info_year,db.db_info_address};

        String where = db.db_info_username+"='"+username+"'";

        Cursor cur =dbr.query(db.db_Itable, col, where, null, null, null, null);

        if(cur!=null){
            while (cur.moveToNext()){
                String regno =cur.getString(cur.getColumnIndex(db.db_info_no));
                name =cur.getString(cur.getColumnIndex(db.db_info_name));
                String uname =cur.getString(cur.getColumnIndex(db.db_info_username));
                long contact =cur.getLong(cur.getColumnIndex(db.db_info_contact));
                String sem =cur.getString(cur.getColumnIndex(db.db_info_sem));
                String year =cur.getString(cur.getColumnIndex(db.db_info_year));
                String add =cur.getString(cur.getColumnIndex(db.db_info_address));

                tname.setText(name);
                tuname.setText("("+uname+")");
                tregno.setText("Register No: "+regno);
                tcontact.setText("Contact No: "+contact);
                tsem.setText("Current "+sem);
                tyear.setText(year);
                taddress.setText("Address: "+add);

                share_info = "Name: "+name+"\nUsername: "+uname+"\nRegister No.: "+regno+"\nContact No.: "+contact+"\nCurrent: "+sem+"\nYear: "+year+".\n";


            }cur.close();
        }

        String query = "SELECT "+db.db_attend_sem+", "+db.db_attend_sub+", SUM("+db.db_attend_value+") FROM "+db.db_Atable+" WHERE "+db.db_attend_username+"='"+username+"' GROUP BY "+db.db_attend_sem+" , "+db.db_attend_sub;


        String[] attendcol = {db.db_attend_username,db.db_attend_sem,db.db_attend_sub,db.db_attend_value,db.db_attend_date};

        String attendwhere = db.db_attend_username+"='"+username+"'";

        //Cursor acur =dbr.query(db.db_Atable, attendcol, attendwhere, null, null, null, null);
        Cursor acur =dbr.rawQuery(query,null);

        int count = acur.getCount();

        SharedPreferences sf = PreferenceManager.getDefaultSharedPreferences(this);
        int total = sf.getInt("Total",0);


        if(count==0){
            tattendance.append(name+" has not attended any lectures.\n");
        }else {
            if(acur!=null){
                while (acur.moveToNext()){

                    String semester =acur.getString(0);
                    String subject =acur.getString(1);
                    int attend =acur.getInt(2);

                    tattendance.append(name+" - "+semester+" > "+subject+" - "+attend+" / "+total+" lectures.\n");
                    //attend_info.append(name+"\t"+semester+"\t"+subject+"\t"+attend+" / "+total+"\n");
                    attend_info.append(name+" - "+semester+" > "+subject+" - "+attend+" / "+total+" lectures.\n");

                    //tattendance.append(name+" attended "+semester+" - "+subject+"'s "+attend+" lectures of "+total+".\n");
                    //attend_info.append(name+" attended "+semester+" - "+subject+"'s "+attend+" lectures of "+total+".\n");

                }acur.close();
            }
        }

        dbr.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int menu_id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (menu_id){

            case R.id.action_share:

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                String columns_share = "Name - Semester > Subject - Attendance";
                sendIntent.putExtra(Intent.EXTRA_TEXT,share_info+"\n"+columns_share+"\n"+attend_info);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share to:"));

                break;

            case R.id.action_export:

                StringBuilder export_info = getData();

                if(isExternalStorageReadable()){

                    File file = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"attendance.html");
                    }else {
                        file = new File(Environment.getExternalStorageDirectory() + "/Documents", "attendance.html");
                    }
                        FileOutputStream outputStream;
                        String columns = "<table border=\"1\" style=\"width:100%\">" +
                                "  <tr>" +
                                "    <th>Name</th>" +
                                "    <th>Semester</th>" +
                                "    <th>Subject</th>" +
                                "    <th>Attendance</th>" +
                                "    <th>Period</th>" +
                                "  </tr>";
                        String data = columns+"\n"+export_info+"</table>";
                        try {
                            //File text_file = new File(file.getAbsolutePath(),username+".txt" );
                            outputStream = new FileOutputStream(file);
                            outputStream.write(data.getBytes());
                            outputStream.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    Toast.makeText(getApplicationContext(),"File created in Documents!",Toast.LENGTH_SHORT).show();

                    /*Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri uri = Uri.parse(file.toString());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setDataAndType(uri, "text/html");
                    startActivity(intent);*/

                    /*Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromFile(file));
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    startActivity(intent);*/

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setDataAndType(Uri.fromFile(file), "application/x-webarchive-xml");
                    startActivity(intent);


                }

                break;

            case R.id.action_info:

                Intent in = new Intent(DetailActivity.this,InfoActivity.class);
                in.putExtra("Uname",username);
                startActivity(in);

            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private StringBuilder getData() {

        StringBuilder export_info = new StringBuilder("");

        SQLiteHelper db = new SQLiteHelper(getApplicationContext());
        SQLiteDatabase dbr = db.getReadableDatabase();

        String query = "SELECT "+db.db_attend_username+", "+db.db_attend_sem+", "+db.db_attend_sub+", SUM("+db.db_attend_value+") FROM "+db.db_Atable+" GROUP BY "+db.db_attend_username+","+db.db_attend_sem+" , "+db.db_attend_sub;
        //String query = "SELECT "+db.db_attend_username+", "+db.db_attend_sem+", "+db.db_attend_sub+","+db.db_attend_value+" FROM "+db.db_Atable;


        String[] attendcol = {db.db_attend_username,db.db_attend_sem,db.db_attend_sub,db.db_attend_value,db.db_attend_date};

        String attendwhere = db.db_attend_username+"='"+username+"'";

        //Cursor acur =dbr.query(db.db_Atable, attendcol, attendwhere, null, null, null, null);
        Cursor acur =dbr.rawQuery(query,null);

        SharedPreferences sf = PreferenceManager.getDefaultSharedPreferences(this);
        int total = sf.getInt("Total",0);

            if(acur!=null){
                while (acur.moveToNext()){
                    String name = acur.getString(0);
                    String semester =acur.getString(1);
                    String subject =acur.getString(2);
                    int attend =acur.getInt(3);

                    export_info.append(
                            "  <tr>" +
                                    "    <td align=\"center\">"+name+"</td>" +
                                    "    <td align=\"center\">"+semester+"</td>" +
                                    "    <td align=\"center\">"+subject+"</td>" +
                                    "    <td align=\"center\">"+attend+" / "+total+"</td>" +
                                    "    <td align=\"center\">"+attend+" hrs</td>" +
                                    "  </tr>");

                }acur.close();
            }

        dbr.close();

        return export_info;
    }


    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

}


