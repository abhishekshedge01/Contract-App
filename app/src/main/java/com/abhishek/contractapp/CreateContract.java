package com.abhishek.contractapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateContract extends AppCompatActivity {
    TextView end_date;
    private Spinner vendor_name, category_name;
    ArrayList<String> arrayList_parent;
    ArrayAdapter<String> arrayAdapter_parent;
    ArrayList<String> arrayList_select,arrayList_vodaphone, arrayList_o2, arrayList_vattenfall, arrayList_mcfit, arrayList_sky;
    ArrayAdapter<String> arrayAdapter_child;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    EditText cost;
    Button create_record;
    String vendorString = "";
    String categoryString = "";
    String newdate="";
    int day, month, year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contract);
        getSupportActionBar().hide();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        cost = findViewById(R.id.create_contract_costs);
        create_record = findViewById(R.id.create_contract_create_button);
        end_date = findViewById(R.id.create_contract_date);
        cost = findViewById(R.id.create_contract_costs);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String curr=dateFormat.format(date);
        end_date.setText(curr);


        end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(CreateContract.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month++;
                        String date="";
                        if(month<10 && dayOfMonth<10)
                        {
                            date = year+"-0"+ month+"-0"+dayOfMonth;

                        }
                        else if(month<10)
                        {
                            date = year+"-0"+ month+"-"+dayOfMonth;
                        }
                        else if(dayOfMonth<10)
                        {
                            date = year+"-"+ month+"-0"+dayOfMonth;
                        }
                        else
                        {
                            date = year+"-"+ month+"-"+dayOfMonth;
                        }
                        newdate=date;
                        end_date.setText(date);
                    }
                }, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });



        vendor_name = findViewById(R.id.create_contract_vendor);
        category_name = findViewById(R.id.create_contract_category);

        arrayList_parent = new ArrayList<>();
        arrayList_parent.add("Select a contract");
        arrayList_parent.add("Vodaphone");
        arrayList_parent.add("O2");
        arrayList_parent.add("Vattenfall");
        arrayList_parent.add("McFit");
        arrayList_parent.add("Sky");

        arrayAdapter_parent = new ArrayAdapter<>(getApplication(), R.layout.spinner_layout, arrayList_parent);
        vendor_name.setAdapter(arrayAdapter_parent);

        arrayList_select = new ArrayList<>();
        arrayList_select.add("Select a category");

        arrayList_vodaphone = new ArrayList<>();
        arrayList_vodaphone.add("Select a category");
        arrayList_vodaphone.add("Internet");
        arrayList_vodaphone.add("DSL");
        arrayList_vodaphone.add("Phone");
        arrayList_vodaphone.add("Mobile Phone");

        arrayList_o2 = new ArrayList<>();
        arrayList_o2.add("Select a category");
        arrayList_o2.add("Internet");
        arrayList_o2.add("DSL");

        arrayList_vattenfall = new ArrayList<>();
        arrayList_vattenfall.add("Select a category");
        arrayList_vattenfall.add("Electricity");
        arrayList_vattenfall.add("Gas");

        arrayList_mcfit = new ArrayList<>();
        arrayList_mcfit.add("Select a category");
        arrayList_mcfit.add("Gym");

        arrayList_sky = new ArrayList<>();
        arrayList_sky.add("Select a category");
        arrayList_sky.add("Paid TV");


        vendor_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                vendorString = vendor_name.getSelectedItem().toString();
                if (position == 0)
                {
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_layout, arrayList_select);
                    category_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            categoryString = category_name.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                if (position == 1) {
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_layout, arrayList_vodaphone);
                    category_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            categoryString = category_name.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                if (position == 2) {
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_layout, arrayList_o2);
                    category_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            categoryString = category_name.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                if (position == 3) {
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_layout, arrayList_vattenfall);
                    category_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            categoryString = category_name.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                if (position == 4) {
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_layout, arrayList_mcfit);
                    category_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            categoryString = category_name.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                if (position == 5) {
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_layout, arrayList_sky);
                    category_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            categoryString = category_name.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                category_name.setAdapter(arrayAdapter_child);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        create_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String xcost = cost.getText().toString();
                String xdate = end_date.getText().toString();
                int costnum=0;
                if(!xcost.isEmpty()) {
                    costnum = Integer.parseInt(xcost);
                }

                if(vendorString.equals("Select a contract") && categoryString.equals("Select a category"))
                {
                    Toast.makeText(CreateContract.this, "Please select a contract and category option", Toast.LENGTH_SHORT).show();
                }

                else if (vendorString.equals("Select a contract"))
                {
                    Toast.makeText(getApplicationContext(), "Please select a contract option", Toast.LENGTH_SHORT).show();
                }

                else if (categoryString.equals("Select a category"))
                {
                    Toast.makeText(getApplicationContext(), "Please select a category option", Toast.LENGTH_SHORT).show();
                }

                else if(xcost.isEmpty())
                {
                    cost.setError("cost field empty");
                }

                else if(costnum<0)
                {
                    cost.setError("cost field empty");
                }

                else if(isDateAfter(newdate,curr))
                {
                    end_date.setError("Re-enter the date!");
                    Toast.makeText(CreateContract.this, "The date entered by you has passed by, pls re-enter a new date", Toast.LENGTH_LONG).show();
                }

                else
                {
                    DocumentReference documentReference = firebaseFirestore.collection("users").document(firebaseUser.getUid()).collection("contract").document();
                    Map<String, Object> note = new HashMap<>();
                    note.put("vendor", vendorString);
                    note.put("category", categoryString);
                    note.put("cost",xcost);
                    note.put("date",xdate);

                    documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(CreateContract.this, "Record Saved Successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ContractList.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CreateContract.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    public static boolean isDateAfter(String startDate,String endDatex)
    {
        try
        {
            String myFormatString = "yyyy-M-dd"; // for example
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);
            Date date1 = df.parse(endDatex);
            Date startingDate = df.parse(startDate);

            if (date1.after(startingDate))
                return true;
            else
                return false;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent(getApplicationContext(),ContractList.class);
        startActivity(intent);
        finish();
    }
}