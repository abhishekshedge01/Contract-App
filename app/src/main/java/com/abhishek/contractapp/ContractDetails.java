package com.abhishek.contractapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ContractDetails extends AppCompatActivity
{
    Button edit,delete;
    TextView tv1,tv2,tv3,tv4;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_contract_details);
        tv1=findViewById(R.id.contract_details_vendor);
        tv2=findViewById(R.id.contract_details_category);
        tv3=findViewById(R.id.contract_details_costs);
        tv4=findViewById(R.id.contract_details_endson);
        Intent data=getIntent();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        edit=findViewById(R.id.contract_details_edit_button);
        delete=findViewById(R.id.contract_details_delete_button);

        tv1.setText(data.getStringExtra("vendint"));
        tv2.setText(data.getStringExtra("cateint"));
        tv3.setText(data.getStringExtra("costint"));
        tv4.setText(data.getStringExtra("dateint"));

        String notexd=data.getStringExtra("noteId");
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(v.getContext(),EditContract.class);
                intent.putExtra("vendint",data.getStringExtra("vendint"));
                intent.putExtra("cateint",data.getStringExtra("cateint"));
                intent.putExtra("costint",data.getStringExtra("costint"));
                intent.putExtra("dateint",data.getStringExtra("dateint"));
                intent.putExtra("noteId",data.getStringExtra("noteId"));
                v.getContext().startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new AlertDialog.Builder(ContractDetails.this)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                DocumentReference documentreference=firebaseFirestore.collection("users").document(firebaseUser.getUid()).collection("contract").document(notexd);
                                documentreference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(v.getContext(),"Contract is Deleted Successfully!",Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(getApplicationContext(),ContractList.class);
                                        startActivity(intent);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(v.getContext(),"Contract is not Deleted!",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent(getApplicationContext(),ContractList.class);
        startActivity(intent);
        finish();
    }
}