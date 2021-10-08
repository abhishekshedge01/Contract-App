package com.abhishek.contractapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ContractList extends AppCompatActivity {

    Button addcontract;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_list);
        addcontract=findViewById(R.id.contract_list_add_contract_button);
        addcontract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CreateContract.class);
                startActivity(intent);
            }
        });
    }
}