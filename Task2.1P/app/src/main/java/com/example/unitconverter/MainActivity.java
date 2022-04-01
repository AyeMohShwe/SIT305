package com.example.unitconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText myEditText;
    ImageButton metre, thermometer, kilogram;
    TextView myTextView1, myTextView2, myTextView3, myTextView4, myTextView5, myTextView6;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dropdown_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        myEditText = (EditText) findViewById(R.id.myEditText);

        TextView myTextView1 = findViewById(R.id.myTextView1);
        TextView myTextView2 = findViewById(R.id.myTextView2);
        TextView myTextView3 = findViewById(R.id.myTextView3);
        TextView myTextView4 = findViewById(R.id.myTextView4);
        TextView myTextView5 = findViewById(R.id.myTextView5);
        TextView myTextView6 = findViewById(R.id.myTextView6);

        metre = (ImageButton) findViewById(R.id.metre);
        metre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parent.getItemAtPosition(pos).equals("Metre")){
                    float num = Integer.parseInt(myEditText.getText().toString());
                    myTextView1.setText(String.format("%.2f", num*100));
                    myTextView4.setText("Centimetre");
                    myTextView2.setText(String.format("%.2f", num*3.281));
                    myTextView5.setText("Foot");
                    myTextView3.setText(String.format("%.2f", num*39.37));
                    myTextView6.setText("Inch");
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please select the correct conversion icon.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        thermometer = (ImageButton) findViewById(R.id.thermometer);
        thermometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parent.getItemAtPosition(pos).equals("Celsius")){
                    float num = Integer.parseInt(myEditText.getText().toString());
                    myTextView1.setText(String.format("%.2f", (num*9/5)+32));
                    myTextView4.setText("Fahrenheit");
                    myTextView2.setText(String.format("%.2f", num+273.15));
                    myTextView5.setText("Kelvin");
                    myTextView3.setText("");
                    myTextView6.setText("");

                }
                else{
                    Toast.makeText(getApplicationContext(),"Please select the correct conversion icon.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        kilogram = (ImageButton) findViewById(R.id.kilogram);
        kilogram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parent.getItemAtPosition(pos).equals("Kilogram")){
                    float num = Integer.parseInt(myEditText.getText().toString());
                    myTextView1.setText(String.format("%.2f", num*1000));
                    myTextView4.setText("Gram");
                    myTextView2.setText(String.format("%.2f", num*35.275));
                    myTextView5.setText("Ounce(Oz)");
                    myTextView3.setText(String.format("%.2f", num*2.205));
                    myTextView6.setText("Pound(lb)");
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please select the correct conversion icon.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }
}