package com.abhishek.contractapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ContractList extends AppCompatActivity {
    Button addcontract;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerView;
    FirestoreRecyclerAdapter<model, viewholder> noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_list);
        addcontract = findViewById(R.id.contract_list_add_contract_button);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        addcontract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateContract.class);
                startActivity(intent);
                finish();
            }
        });



        Query query = firebaseFirestore.collection("users").document(firebaseUser.getUid()).collection("contract").orderBy("date",Query.Direction.ASCENDING).startAt(String.valueOf(android.text.format.DateFormat.format("yyyy-MM-dd", new java.util.Date())));
        FirestoreRecyclerOptions<model> allusers = new FirestoreRecyclerOptions.Builder<model>().setQuery(query, model.class).build();
        noteAdapter = new FirestoreRecyclerAdapter<model, viewholder>(allusers) {
            @Override
            protected void onBindViewHolder(@NonNull viewholder holder, int position, @NonNull model model) {

                holder.vendor.setText(model.getVendor());
                holder.category.setText(model.getCategory());
                holder.cost.setText("$ "+model.getCost());
                holder.date.setText(model.getDate());

                String docId=noteAdapter.getSnapshots().getSnapshot(position).getId();

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(v.getContext(),ContractDetails.class);

                        intent.putExtra("vendint",model.getVendor());
                        intent.putExtra("cateint",model.getCategory());
                        intent.putExtra("costint",model.getCost());
                        intent.putExtra("dateint",model.getDate());
                        intent.putExtra("noteId",docId);
                        startActivity(intent);
                        finish();
                    }
                });
            }

            @NonNull
            @Override
            public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
                return new viewholder(view);
            }
        };

        recyclerView = findViewById(R.id.contract_list_recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(noteAdapter);
    }

    private class viewholder extends RecyclerView.ViewHolder {
        private TextView vendor;
        private TextView category;
        private TextView cost;
        private TextView date;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            vendor = itemView.findViewById(R.id.contract_list_vendor);
            category = itemView.findViewById(R.id.contract_list_category);
            cost = itemView.findViewById(R.id.contract_list_costs);
            date=itemView.findViewById(R.id.contract_list_endson);
        }
    }

    @Override
    public void onBackPressed()
    {
        finishAffinity();
    }


    @Override
    protected void onStart() {
        super.onStart();
        noteAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (noteAdapter != null) {
            noteAdapter.stopListening();
        }
    }
}