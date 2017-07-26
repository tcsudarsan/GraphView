package com.example.sudarsan.graphfinal;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText editName,editTotal,editSpent,editDate;
    Button btnAddData,btnviewAll;
    LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GraphView graph = (GraphView) findViewById(R.id.graph);


    db = new DatabaseHelper(this);

    editName = (EditText)findViewById(R.id.editText_name);
    editTotal = (EditText)findViewById(R.id.editText_total);
    editSpent = (EditText)findViewById(R.id.editText_spent);
    editDate=(EditText)findViewById(R.id.editText_date);
    btnAddData = (Button)findViewById(R.id.button_add);
    btnviewAll=(Button)findViewById(R.id.button_view);
    AddData();
    viewAll();
        /*trail version for graph with hard values*/
        //   int x1, y1,x2,y2;
        // int days1=0;
        //  int days2=10;
        //  int hoursspent=0;
        //  int totalhours=80;
        //  LineGraphSeries<DataPoint> lecture = new LineGraphSeries<DataPoint>();
        //   LineGraphSeries<DataPoint> exercise= new LineGraphSeries<DataPoint>();
        //  if (hoursspent < totalhours) for (int i = 0; i < totalhours; i++) {
        //     days1 = days1 + 1;
        //   hoursspent = hoursspent + 2;
        //    days2=days2+1;
        //   x2=days2;
        //  x1 = days1;
        //   y1 = hoursspent;
        //   y2= hoursspent;
        //    DataPoint v = new DataPoint(x1, y1);
        //    DataPoint v1 = new DataPoint(x2,y2);
        //   lecture.appendData(new DataPoint(x1, y1), true, totalhours);
        //   exercise.appendData(new DataPoint(x2, y2), true, totalhours);
        //    }
        // graph.addSeries(lecture);
        //  graph.addSeries(exercise);
        int x,y;/*initialise x n y coordinates*/
        int i=0;
        String query1="select date from tablename where ID ="+i+";";/*declaring and initialising query for date*/
        String query2="select hoursspent from tablename where ID="+i+";";/*declaring and initialising query for hoursspent on activity*/
        int z=db.count();/*count no of rows*/

        LineGraphSeries<DataPoint> activity = new LineGraphSeries<DataPoint>();/*define a object for graph*/
        for( i=1;i<=z;i++)
        {
            x=db.xaxis();/*assigning date to x-axis*/
            y=db.yaxis();/*assigning hoursspent to y axis*/
            DataPoint dp=new DataPoint(x,y);/*creating new datapoint for graph after every increment*/
            activity.appendData(new DataPoint(x,y),true,z);/*appending datapoint for activity if the condition is true when compared to Z*/
        }
        graph.addSeries(activity);/*draw graph for given points*/
}
    public  void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = db.insertData(editName.getText().toString(),
                                editTotal.getText().toString(),
                                editSpent.getText().toString(),editDate.getText().toString() );
                        if(isInserted =true)
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void viewAll(){
        btnviewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = db.getAllData();
                        if (res.getCount() == 0) {
                            showMessage("error","no data found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Id:" + res.getString(0) + "\n");
                            buffer.append("Name:" + res.getString(1) + "\n");
                            buffer.append("Total:" + res.getString(2) + "\n");
                            buffer.append("Spent:" + res.getString(3) + "\n");
                            buffer.append("Date:"+res.getString(4)+"\n");
                        }
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }
    public void showMessage(String title,String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}

