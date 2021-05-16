package com.example.planify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView roomList;
    private FirestoreRecyclerAdapter adapter;
    private FirestoreRecyclerOptions<Room> options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseFirestore = firebaseFirestore.getInstance();

        roomList = findViewById(R.id.recyclerview);

        Query query = firebaseFirestore.collection("Rooms");
        options = new FirestoreRecyclerOptions.Builder<Room>()
                .setQuery(query, Room.class).build();



        adapter = new FirestoreRecyclerAdapter<Room, RoomsViewHolder>(options) {
            @NonNull
            @Override
            public RoomsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_list_single, parent, false);
                return new RoomsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull RoomsViewHolder holder, int position, @NonNull Room model) {
                holder.description_id.setText("Features: " + model.getDescription());
                Picasso.get().load(model.getImage()).into(holder.image_id);
                holder.seats_id.setText("Seats: " + model.getSeats());
                holder.name_id.setText("Name: " + model.getName());
                holder.price_id.setText("Price: " + model.getPrice());
                holder.rentButton.setText("Rent " + model.getName());
            }
        };

        roomList.setHasFixedSize(true);
        roomList.setLayoutManager(new LinearLayoutManager(this));
        roomList.setAdapter(adapter);

    }

    private class RoomsViewHolder extends RecyclerView.ViewHolder{

        private TextView description_id;
        private TextView seats_id;
        private ImageView image_id;
        private TextView name_id;
        private TextView price_id;
        private Button rentButton;

        public RoomsViewHolder(@NonNull View itemView) {
            super(itemView);

            description_id = itemView.findViewById(R.id.description_id);
            seats_id = itemView.findViewById(R.id.seats_id);
            image_id = itemView.findViewById(R.id.image_id);
            name_id = itemView.findViewById(R.id.name_id);
            price_id = itemView.findViewById(R.id.price_id);
            rentButton = itemView.findViewById(R.id.rentbutton);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    public void openRentActivity(View view) {

        Intent intent = new Intent(this, RentActivity.class);
        startActivity(intent);
    }

    public void onSortSeatsAsc(View v){
        adapter.stopListening();
        firebaseFirestore = firebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("Rooms").orderBy("seats", Query.Direction.ASCENDING);
        options = new FirestoreRecyclerOptions.Builder<Room>()
                .setQuery(query, Room.class).build();

        adapter = new FirestoreRecyclerAdapter<Room, RoomsViewHolder>(options) {
            @NonNull
            @Override
            public RoomsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_list_single, parent, false);
                return new RoomsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull RoomsViewHolder holder, int position, @NonNull Room model) {
                holder.description_id.setText("Features: " + model.getDescription());
                Picasso.get().load(model.getImage()).into(holder.image_id);
                holder.seats_id.setText("Seats: " + model.getSeats());
                holder.name_id.setText("Name: " + model.getName());
                holder.price_id.setText("Price: " + model.getPrice());
                holder.rentButton.setText("Rent " + model.getName());
            }
        };

        roomList.setHasFixedSize(true);
        roomList.setLayoutManager(new LinearLayoutManager(this));
        roomList.setAdapter(adapter);

        adapter.startListening();
        adapter.notifyDataSetChanged();
    }

    public void onSortSeatsDesc(View v){
        adapter.stopListening();
        firebaseFirestore = firebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("Rooms").orderBy("seats", Query.Direction.DESCENDING);
        options = new FirestoreRecyclerOptions.Builder<Room>()
                .setQuery(query, Room.class).build();

        adapter = new FirestoreRecyclerAdapter<Room, RoomsViewHolder>(options) {
            @NonNull
            @Override
            public RoomsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_list_single, parent, false);
                return new RoomsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull RoomsViewHolder holder, int position, @NonNull Room model) {
                holder.description_id.setText("Features: " + model.getDescription());
                Picasso.get().load(model.getImage()).into(holder.image_id);
                holder.seats_id.setText("Seats: " + model.getSeats());
                holder.name_id.setText("Name: " + model.getName());
                holder.price_id.setText("Price: " + model.getPrice());
                holder.rentButton.setText("Rent " + model.getName());
            }
        };

        roomList.setHasFixedSize(true);
        roomList.setLayoutManager(new LinearLayoutManager(this));
        roomList.setAdapter(adapter);

        adapter.startListening();
        adapter.notifyDataSetChanged();
    }

    public void onSortPriceAsc(View v){
        adapter.stopListening();
        firebaseFirestore = firebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("Rooms").orderBy("price", Query.Direction.ASCENDING);
        options = new FirestoreRecyclerOptions.Builder<Room>()
                .setQuery(query, Room.class).build();

        adapter = new FirestoreRecyclerAdapter<Room, RoomsViewHolder>(options) {
            @NonNull
            @Override
            public RoomsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_list_single, parent, false);
                return new RoomsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull RoomsViewHolder holder, int position, @NonNull Room model) {
                holder.description_id.setText("Features: " + model.getDescription());
                Picasso.get().load(model.getImage()).into(holder.image_id);
                holder.seats_id.setText("Seats: " + model.getSeats());
                holder.name_id.setText("Name: " + model.getName());
                holder.price_id.setText("Price: " + model.getPrice());
                holder.rentButton.setText("Rent " + model.getName());
            }
        };

        roomList.setHasFixedSize(true);
        roomList.setLayoutManager(new LinearLayoutManager(this));
        roomList.setAdapter(adapter);

        adapter.startListening();
        adapter.notifyDataSetChanged();
    }

    public void onSortPriceDesc(View v){
        adapter.stopListening();
        firebaseFirestore = firebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("Rooms").orderBy("price", Query.Direction.DESCENDING);
        options = new FirestoreRecyclerOptions.Builder<Room>()
                .setQuery(query, Room.class).build();

        adapter = new FirestoreRecyclerAdapter<Room, RoomsViewHolder>(options) {
            @NonNull
            @Override
            public RoomsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_list_single, parent, false);
                return new RoomsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull RoomsViewHolder holder, int position, @NonNull Room model) {
                holder.description_id.setText("Features: " + model.getDescription());
                Picasso.get().load(model.getImage()).into(holder.image_id);
                holder.seats_id.setText("Seats: " + model.getSeats());
                holder.name_id.setText("Name: " + model.getName());
                holder.price_id.setText("Price: " + model.getPrice());
                holder.rentButton.setText("Rent " + model.getName());
            }
        };

        roomList.setHasFixedSize(true);
        roomList.setLayoutManager(new LinearLayoutManager(this));
        roomList.setAdapter(adapter);

        adapter.startListening();
        adapter.notifyDataSetChanged();
    }

}