package com.abhishek.contractapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class EditContract extends AppCompatActivity {

    EditText update_cost;
    TextView update_date;
    Button update_record_btn;
    int day, month, year;
    private Spinner edit_vendor, edit_category;
    ArrayList<String> arrayList_parent;
    ArrayAdapter<String> arrayAdapter_parent;
    ArrayList<String> arrayList_select,arrayList_vodaphone, arrayList_o2, arrayList_vattenfall, arrayList_mcfit, arrayList_sky;
    ArrayAdapter<String> arrayAdapter_child;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String editvendorString = "";
    String editcategoryString = "";
    String newdate="";
    Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contract);
        getSupportActionBar().hide();
        update_cost=findViewById(R.id.edit_contract_costs);
        update_date=findViewById(R.id.edit_contract_date);
        update_record_btn=findViewById(R.id.edit_contract_create_button);
        data=getIntent();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        edit_vendor=findViewById(R.id.edit_contract_vendor);
        edit_category=findViewById(R.id.edit_contract_category);
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();


        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String curr=dateFormat.format(date);

        update_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);



                DatePickerDialog dialog = new DatePickerDialog(EditContract.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month++;
                        String date="";
                        if(month < 10 && dayOfMonth<10){

                            date = year + "-" + "0"+month + "-" + "0"+dayOfMonth;
                        }

                        if(month < 10 ){

                            date = year + "-" + "0"+month + "-" + dayOfMonth;
                        }

                        if(dayOfMonth < 10 ){

                            date = year + "-" + month + "-" + "0"+dayOfMonth;
                        }

                        if(month >= 10 && dayOfMonth>=10)
                        {
                            date = year + "-" + month + "-" + dayOfMonth;
                        }

                        update_date.setText(date);
                        newdate=date;
                    }
                }, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        arrayList_parent = new ArrayList<>();
        arrayList_parent.add("Select a contract");
        arrayList_parent.add("Vodaphone");
        arrayList_parent.add("O2");
        arrayList_parent.add("Vattenfall");
        arrayList_parent.add("McFit");
        arrayList_parent.add("Sky");

        arrayAdapter_parent = new ArrayAdapter<>(getApplication(), R.layout.spinner_layout, arrayList_parent);
        edit_vendor.setAdapter(arrayAdapter_parent);

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



        update_cost.setText(data.getStringExtra("costint"));
        update_date.setText(data.getStringExtra("dateint"));
        if(data.getStringExtra("vendint").equals("Vodaphone"))
        {
            edit_vendor.setSelection(1);
        }

        else if(data.getStringExtra("vendint").equals("O2"))
        {
            edit_vendor.setSelection(2);
        }


        else if(data.getStringExtra("vendint").equals("Vattenfall"))
        {
            edit_vendor.setSelection(3);
        }


        else if(data.getStringExtra("vendint").equals("McFit"))
        {
            edit_vendor.setSelection(4);
        }

        else if(data.getStringExtra("vendint").equals("Sky"))
        {
            edit_vendor.setSelection(5);
        }

        if(edit_vendor.getSelectedItemPosition()==1)
        {
            if(data.getStringExtra("cateint").equals("Internet")) {
                edit_category.setSelection(1);
            }
            else if(data.getStringExtra("cateint").equals("DSL")) {
                edit_category.setSelection(2);
            }

            else if(data.getStringExtra("cateint").equals("Phone")) {
                edit_category.setSelection(3);
            }

            else if(data.getStringExtra("cateint").equals("Mobile Phone")) {
                edit_category.setSelection(4);
            }
        }

        if(edit_vendor.getSelectedItemPosition()==2)
        {
            if (data.getStringExtra("cateint").equals("Internet")) {
                edit_category.setSelection(1);
            } else if (data.getStringExtra("cateint").equals("DSL")) {
                edit_category.setSelection(2);
            }
        }

        if(edit_vendor.getSelectedItemPosition()==3)
        {
            if (data.getStringExtra("cateint").equals("Electricity")) {
                edit_category.setSelection(1);
            } else if (data.getStringExtra("cateint").equals("Gas")) {
                edit_category.setSelection(2);
            }
        }

        if(edit_vendor.getSelectedItemPosition()==4)
        {
            if (data.getStringExtra("cateint").equals("Gym"))
            {
                edit_category.setSelection(1);
            }
        }

        if(edit_vendor.getSelectedItemPosition()==5)
        {
            if (data.getStringExtra("cateint").equals("Paid TV"))
            {
                edit_category.setSelection(1);
            }
        }

        edit_vendor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                editvendorString = edit_vendor.getSelectedItem().toString();
                if (position == 0) {
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_layout, arrayList_select);
                    edit_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            editcategoryString = edit_category.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                if (position == 1) {
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_layout, arrayList_vodaphone);
                    edit_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            editcategoryString = edit_category.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                if (position == 2) {
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_layout, arrayList_o2);
                    edit_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            editcategoryString = edit_category.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                if (position == 3) {
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_layout, arrayList_vattenfall);
                    edit_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            editcategoryString = edit_category.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                if (position == 4) {
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_layout, arrayList_mcfit);
                    edit_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            editcategoryString = edit_category.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                if (position == 5) {
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_layout, arrayList_sky);
                    edit_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            editcategoryString = edit_category.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                edit_category.setAdapter(arrayAdapter_child);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        update_record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCost=update_cost.getText().toString();
                String newDate=update_date.getText().toString();

                if(editvendorString.equals("Select a contract") && editcategoryString.equals("Select a category"))
                {
                    Toast.makeText(EditContract.this, "Please select a contract and category option", Toast.LENGTH_SHORT).show();
                }

                else if (editvendorString.equals("Select a contract"))
                {
                    Toast.makeText(getApplicationContext(), "Please select a contract option", Toast.LENGTH_SHORT).show();
                }

                else if (editcategoryString.equals("Select a category"))
                {
                    Toast.makeText(getApplicationContext(), "Please select a category option", Toast.LENGTH_SHORT).show();
                }

                else if(newCost.isEmpty())
                {
                    update_cost.setError("cost field is empty");
                }

                else if(isDateAfter(newdate,curr))
                {
                    update_date.setError("Re-enter the date!");
                    Toast.makeText(EditContract.this, "The date entered by you has passed by, pls re-enter a new date", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    DocumentReference documentReference=firebaseFirestore.collection("users").document(firebaseUser.getUid()).collection("contract").document(data.getStringExtra("noteId"));
                    Map<String,Object> note = new HashMap<>();
                    note.put("vendor",editvendorString);
                    note.put("category",editcategoryString);
                    note.put("cost",newCost);
                    note.put("date",newDate);

                    documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(getApplicationContext(), "Record Updated Successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(EditContract.this,ContractList.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed to update!", Toast.LENGTH_SHORT).show();
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
            String myFormatString = "yyyy-MM-dd"; // for example
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
}