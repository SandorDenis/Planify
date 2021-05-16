package com.example.planify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.DocumentCollections;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private FirebaseFirestore firebaseFirestore;
    public List<String> ids = new ArrayList<>();
    public TextView roomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);

        /*firebaseFirestore = firebaseFirestore.getInstance();
        firebaseFirestore.collection("Rooms").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        ids.add(id);
                        System.out.println(id);
                    }
                }
            }
        });
         */

        firebaseFirestore = firebaseFirestore.getInstance();
        firebaseFirestore.collection("Rooms")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ids.add(document.getString("name"));
                            }
                        } else {
                            System.out.print("Error getting documents: " + task.getException());
                        }
                    }
                });

        for (String x: ids) {
            System.out.println(x);
            //System.out.println(x.substring(x.lastIndexOf("{") + 1));
        }


        //Button b = (Button)findViewById(R.id.rentbutton);
        //String buttonText = b.getText() + "";
        //System.out.println(buttonText);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        TextView textView = findViewById(R.id.textdate);
        textView.setText(currentDateString);

        roomName = findViewById(R.id.roomname);
        String roomPath = roomName.getText().toString();

        Button buttonTimeInterval1 = findViewById(R.id.timeinterval1);

        if (!textView.getText().equals("Pick a date for your meeting")){
            buttonTimeInterval1.setEnabled(true);
            firebaseFirestore = firebaseFirestore.getInstance();
            TextView dateTextView = findViewById(R.id.textdate);
            DocumentReference docRef = firebaseFirestore.collection("Rooms").document(roomPath);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            if ((document.get("rentDetails") + "").contains(dateTextView.getText() + ": 6:00AM - 7:00AM")){
                                buttonTimeInterval1.setEnabled(false);
                                for (String x: ids){
                                    System.out.println(x);
                                    //System.out.println(x.substring(x.lastIndexOf("{") + 1));
                                }
                            }
                        } else {
                            System.out.println("No such document");
                        }
                    } else {
                        System.out.println("get failed with " + task.getException());
                    }
                }
            });
        }

        Button buttonTimeInterval2 = findViewById(R.id.timeinterval2);
        if (!textView.getText().equals("Pick a date for your meeting")){
            buttonTimeInterval2.setEnabled(true);
            firebaseFirestore = firebaseFirestore.getInstance();
            TextView dateTextView = findViewById(R.id.textdate);
            DocumentReference docRef = firebaseFirestore.collection("Rooms").document(roomPath);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            if ((document.get("rentDetails") + "").contains(dateTextView.getText() + ": 7:00AM - 8:00AM")){
                                buttonTimeInterval2.setEnabled(false);
                            }
                        } else {
                            System.out.println("No such document");
                        }
                    } else {
                        System.out.println("get failed with " + task.getException());
                    }
                }
            });
        }

        Button buttonTimeInterval3 = findViewById(R.id.timeinterval3);
        if (!textView.getText().equals("Pick a date for your meeting")){
            buttonTimeInterval3.setEnabled(true);
            firebaseFirestore = firebaseFirestore.getInstance();
            TextView dateTextView = findViewById(R.id.textdate);
            DocumentReference docRef = firebaseFirestore.collection("Rooms").document("Room 2");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            if ((document.get("rentDetails") + "").contains(dateTextView.getText() + ": 8:00AM - 9:00AM")){
                                buttonTimeInterval2.setEnabled(false);
                            }
                        } else {
                            System.out.println("No such document");
                        }
                    } else {
                        System.out.println("get failed with " + task.getException());
                    }
                }
            });
        }

        Button buttonTimeInterval4 = findViewById(R.id.timeinterval4);
        if (!textView.getText().equals("Pick a date for your meeting")){
            buttonTimeInterval4.setEnabled(true);
            firebaseFirestore = firebaseFirestore.getInstance();
            TextView dateTextView = findViewById(R.id.textdate);
            DocumentReference docRef = firebaseFirestore.collection("Rooms").document("Room 2");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            if ((document.get("rentDetails") + "").contains(dateTextView.getText() + ": 9:00AM - 10:00AM")){
                                buttonTimeInterval2.setEnabled(false);
                            }
                        } else {
                            System.out.println("No such document");
                        }
                    } else {
                        System.out.println("get failed with " + task.getException());
                    }
                }
            });
        }

        Button buttonTimeInterval5 = findViewById(R.id.timeinterval5);
        if (!textView.getText().equals("Pick a date for your meeting")){
            buttonTimeInterval5.setEnabled(true);
            firebaseFirestore = firebaseFirestore.getInstance();
            TextView dateTextView = findViewById(R.id.textdate);
            DocumentReference docRef = firebaseFirestore.collection("Rooms").document("Room 2");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            if ((document.get("rentDetails") + "").contains(dateTextView.getText() + ": 10:00AM - 11:00AM")){
                                buttonTimeInterval2.setEnabled(false);
                            }
                        } else {
                            System.out.println("No such document");
                        }
                    } else {
                        System.out.println("get failed with " + task.getException());
                    }
                }
            });
        }

        Button buttonTimeInterval6 = findViewById(R.id.timeinterval6);
        if (!textView.getText().equals("Pick a date for your meeting")){
            buttonTimeInterval6.setEnabled(true);
            firebaseFirestore = firebaseFirestore.getInstance();
            TextView dateTextView = findViewById(R.id.textdate);
            DocumentReference docRef = firebaseFirestore.collection("Rooms").document("Room 2");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            if ((document.get("rentDetails") + "").contains(dateTextView.getText() + ": 11:00AM - 12:00PM")){
                                buttonTimeInterval2.setEnabled(false);
                            }
                        } else {
                            System.out.println("No such document");
                        }
                    } else {
                        System.out.println("get failed with " + task.getException());
                    }
                }
            });
        }

        Button buttonTimeInterval7 = findViewById(R.id.timeinterval7);
        if (!textView.getText().equals("Pick a date for your meeting")){
            buttonTimeInterval7.setEnabled(true);
            firebaseFirestore = firebaseFirestore.getInstance();
            TextView dateTextView = findViewById(R.id.textdate);
            DocumentReference docRef = firebaseFirestore.collection("Rooms").document("Room 2");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            if ((document.get("rentDetails") + "").contains(dateTextView.getText() + ": 12:00PM - 1:00PM")){
                                buttonTimeInterval2.setEnabled(false);
                            }
                        } else {
                            System.out.println("No such document");
                        }
                    } else {
                        System.out.println("get failed with " + task.getException());
                    }
                }
            });
        }

        Button buttonTimeInterval8 = findViewById(R.id.timeinterval8);
        if (!textView.getText().equals("Pick a date for your meeting")){
            buttonTimeInterval8.setEnabled(true);
            firebaseFirestore = firebaseFirestore.getInstance();
            TextView dateTextView = findViewById(R.id.textdate);
            DocumentReference docRef = firebaseFirestore.collection("Rooms").document("Room 2");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            if ((document.get("rentDetails") + "").contains(dateTextView.getText() + ": 1:00PM - 2:00PM")){
                                buttonTimeInterval2.setEnabled(false);
                            }
                        } else {
                            System.out.println("No such document");
                        }
                    } else {
                        System.out.println("get failed with " + task.getException());
                    }
                }
            });
        }

        Button buttonTimeInterval9 = findViewById(R.id.timeinterval9);
        if (!textView.getText().equals("Pick a date for your meeting")){
            buttonTimeInterval9.setEnabled(true);
            firebaseFirestore = firebaseFirestore.getInstance();
            TextView dateTextView = findViewById(R.id.textdate);
            DocumentReference docRef = firebaseFirestore.collection("Rooms").document("Room 2");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            if ((document.get("rentDetails") + "").contains(dateTextView.getText() + ": 2:00PM - 3:00PM")){
                                buttonTimeInterval2.setEnabled(false);
                            }
                        } else {
                            System.out.println("No such document");
                        }
                    } else {
                        System.out.println("get failed with " + task.getException());
                    }
                }
            });
        }

        Button buttonTimeInterval10 = findViewById(R.id.timeinterval10);
        if (!textView.getText().equals("Pick a date for your meeting")){
            buttonTimeInterval10.setEnabled(true);
            firebaseFirestore = firebaseFirestore.getInstance();
            TextView dateTextView = findViewById(R.id.textdate);
            DocumentReference docRef = firebaseFirestore.collection("Rooms").document("Room 2");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            if ((document.get("rentDetails") + "").contains(dateTextView.getText() + ": 3:00PM - 4:00PM")){
                                buttonTimeInterval2.setEnabled(false);
                            }
                        } else {
                            System.out.println("No such document");
                        }
                    } else {
                        System.out.println("get failed with " + task.getException());
                    }
                }
            });
        }

        Button buttonTimeInterval11 = findViewById(R.id.timeinterval11);
        if (!textView.getText().equals("Pick a date for your meeting")){
            buttonTimeInterval11.setEnabled(true);
            firebaseFirestore = firebaseFirestore.getInstance();
            TextView dateTextView = findViewById(R.id.textdate);
            DocumentReference docRef = firebaseFirestore.collection("Rooms").document("Room 2");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            if ((document.get("rentDetails") + "").contains(dateTextView.getText() + ": 4:00PM - 5:00PM")){
                                buttonTimeInterval2.setEnabled(false);
                            }
                        } else {
                            System.out.println("No such document");
                        }
                    } else {
                        System.out.println("get failed with " + task.getException());
                    }
                }
            });
        }

        Button buttonTimeInterval12 = findViewById(R.id.timeinterval12);
        if (!textView.getText().equals("Pick a date for your meeting")){
            buttonTimeInterval12.setEnabled(true);
            firebaseFirestore = firebaseFirestore.getInstance();
            TextView dateTextView = findViewById(R.id.textdate);
            DocumentReference docRef = firebaseFirestore.collection("Rooms").document("Room 2");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            if ((document.get("rentDetails") + "").contains(dateTextView.getText() + ": 5:00PM - 6:00PM")){
                                buttonTimeInterval2.setEnabled(false);
                            }
                        } else {
                            System.out.println("No such document");
                        }
                    } else {
                        System.out.println("get failed with " + task.getException());
                    }
                }
            });
        }

        Button buttonTimeInterval13 = findViewById(R.id.timeinterval13);
        if (!textView.getText().equals("Pick a date for your meeting")){
            buttonTimeInterval13.setEnabled(true);
            firebaseFirestore = firebaseFirestore.getInstance();
            TextView dateTextView = findViewById(R.id.textdate);
            DocumentReference docRef = firebaseFirestore.collection("Rooms").document("Room 2");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            if ((document.get("rentDetails") + "").contains(dateTextView.getText() + ": 6:00PM - 7:00PM")){
                                buttonTimeInterval2.setEnabled(false);
                            }
                        } else {
                            System.out.println("No such document");
                        }
                    } else {
                        System.out.println("get failed with " + task.getException());
                    }
                }
            });
        }

        Button buttonTimeInterval14 = findViewById(R.id.timeinterval14);
        if (!textView.getText().equals("Pick a date for your meeting")){
            buttonTimeInterval14.setEnabled(true);
            firebaseFirestore = firebaseFirestore.getInstance();
            TextView dateTextView = findViewById(R.id.textdate);
            DocumentReference docRef = firebaseFirestore.collection("Rooms").document("Room 2");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            if ((document.get("rentDetails") + "").contains(dateTextView.getText() + ": 7:00PM - 8:00PM")){
                                buttonTimeInterval2.setEnabled(false);
                            }
                        } else {
                            System.out.println("No such document");
                        }
                    } else {
                        System.out.println("get failed with " + task.getException());
                    }
                }
            });
        }

        Button buttonTimeInterval15 = findViewById(R.id.timeinterval15);
        if (!textView.getText().equals("Pick a date for your meeting")){
            buttonTimeInterval15.setEnabled(true);
            firebaseFirestore = firebaseFirestore.getInstance();
            TextView dateTextView = findViewById(R.id.textdate);
            DocumentReference docRef = firebaseFirestore.collection("Rooms").document("Room 2");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            if ((document.get("rentDetails") + "").contains(dateTextView.getText() + ": 8:00PM - 9:00PM")){
                                buttonTimeInterval2.setEnabled(false);
                            }
                        } else {
                            System.out.println("No such document");
                        }
                    } else {
                        System.out.println("get failed with " + task.getException());
                    }
                }
            });
        }

        Button buttonTimeInterval16 = findViewById(R.id.timeinterval16);
        if (!textView.getText().equals("Pick a date for your meeting")){
            buttonTimeInterval16.setEnabled(true);
            firebaseFirestore = firebaseFirestore.getInstance();
            TextView dateTextView = findViewById(R.id.textdate);
            DocumentReference docRef = firebaseFirestore.collection("Rooms").document("Room 2");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            if ((document.get("rentDetails") + "").contains(dateTextView.getText() + ": 9:00PM - 10:00PM")){
                                buttonTimeInterval2.setEnabled(false);
                            }
                        } else {
                            System.out.println("No such document");
                        }
                    } else {
                        System.out.println("get failed with " + task.getException());
                    }
                }
            });
        }
    }

    public void openDatePicker (View view){
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }

    public void rentTimeInterval1 (View view) {

        Button buttonTimeInterval1 = findViewById(R.id.timeinterval1);
        buttonTimeInterval1.setEnabled(false);
        firebaseFirestore = firebaseFirestore.getInstance();
        TextView textView = findViewById(R.id.textdate);
        roomName = findViewById(R.id.roomname);
        String roomPath = roomName.getText().toString();

        DocumentReference docRef = firebaseFirestore.collection("Rooms").document(roomPath);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("DocumentSnapshot data: " + document.get("rentDetails"));
                        Map<String, Object> map = new HashMap<>();
                        map.put("rentDetails", document.get("rentDetails") + "" + textView.getText() + ": 6:00AM - 7:00AM; ");
                        docRef.update(map);
                    } else {
                        System.out.println("No such document");
                    }
                } else {
                    System.out.println("get failed with " + task.getException());
                }
            }
        });
    }

    public void rentTimeInterval2 (View view) {

        Button buttonTimeInterval2 = findViewById(R.id.timeinterval2);
        buttonTimeInterval2.setEnabled(false);
        firebaseFirestore = firebaseFirestore.getInstance();
        TextView textView = findViewById(R.id.textdate);
        roomName = findViewById(R.id.roomname);
        String roomPath = roomName.getText().toString();

        DocumentReference docRef = firebaseFirestore.collection("Rooms").document(roomPath);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("DocumentSnapshot data: " + document.get("rentDetails"));
                        Map<String, Object> map = new HashMap<>();
                        map.put("rentDetails", document.get("rentDetails") + "" + textView.getText() + ": 7:00AM - 8:00AM; ");
                        docRef.update(map);
                    } else {
                        System.out.println("No such document");
                    }
                } else {
                    System.out.println("get failed with " + task.getException());
                }
            }
        });
    }

    public void rentTimeInterval3 (View view) {

        Button buttonTimeInterval3 = findViewById(R.id.timeinterval3);
        buttonTimeInterval3.setEnabled(false);
        firebaseFirestore = firebaseFirestore.getInstance();
        TextView textView = findViewById(R.id.textdate);
        roomName = findViewById(R.id.roomname);
        String roomPath = roomName.getText().toString();

        DocumentReference docRef = firebaseFirestore.collection("Rooms").document(roomPath);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("DocumentSnapshot data: " + document.get("rentDetails"));
                        Map<String, Object> map = new HashMap<>();
                        map.put("rentDetails", document.get("rentDetails") + "" + textView.getText() + ": 8:00AM - 9:00AM; ");
                        docRef.update(map);
                    } else {
                        System.out.println("No such document");
                    }
                } else {
                    System.out.println("get failed with " + task.getException());
                }
            }
        });
    }

    public void rentTimeInterval4 (View view) {

        Button buttonTimeInterval4 = findViewById(R.id.timeinterval4);
        buttonTimeInterval4.setEnabled(false);
        firebaseFirestore = firebaseFirestore.getInstance();
        TextView textView = findViewById(R.id.textdate);
        roomName = findViewById(R.id.roomname);
        String roomPath = roomName.getText().toString();

        DocumentReference docRef = firebaseFirestore.collection("Rooms").document(roomPath);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("DocumentSnapshot data: " + document.get("rentDetails"));
                        Map<String, Object> map = new HashMap<>();
                        map.put("rentDetails", document.get("rentDetails") + "" + textView.getText() + ": 9:00AM - 10:00AM; ");
                        docRef.update(map);
                    } else {
                        System.out.println("No such document");
                    }
                } else {
                    System.out.println("get failed with " + task.getException());
                }
            }
        });
    }

    public void rentTimeInterval5 (View view) {

        Button buttonTimeInterval5 = findViewById(R.id.timeinterval5);
        buttonTimeInterval5.setEnabled(false);
        firebaseFirestore = firebaseFirestore.getInstance();
        TextView textView = findViewById(R.id.textdate);
        roomName = findViewById(R.id.roomname);
        String roomPath = roomName.getText().toString();

        DocumentReference docRef = firebaseFirestore.collection("Rooms").document(roomPath);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("DocumentSnapshot data: " + document.get("rentDetails"));
                        Map<String, Object> map = new HashMap<>();
                        map.put("rentDetails", document.get("rentDetails") + "" + textView.getText() + ": 10:00AM - 11:00AM; ");
                        docRef.update(map);
                    } else {
                        System.out.println("No such document");
                    }
                } else {
                    System.out.println("get failed with " + task.getException());
                }
            }
        });
    }

    public void rentTimeInterval6 (View view) {

        Button buttonTimeInterval6 = findViewById(R.id.timeinterval6);
        buttonTimeInterval6.setEnabled(false);
        firebaseFirestore = firebaseFirestore.getInstance();
        TextView textView = findViewById(R.id.textdate);
        roomName = findViewById(R.id.roomname);
        String roomPath = roomName.getText().toString();

        DocumentReference docRef = firebaseFirestore.collection("Rooms").document(roomPath);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("DocumentSnapshot data: " + document.get("rentDetails"));
                        Map<String, Object> map = new HashMap<>();
                        map.put("rentDetails", document.get("rentDetails") + "" + textView.getText() + ": 11:00AM - 12:00PM; ");
                        docRef.update(map);
                    } else {
                        System.out.println("No such document");
                    }
                } else {
                    System.out.println("get failed with " + task.getException());
                }
            }
        });
    }

    public void rentTimeInterval7 (View view) {

        Button buttonTimeInterval7 = findViewById(R.id.timeinterval7);
        buttonTimeInterval7.setEnabled(false);
        firebaseFirestore = firebaseFirestore.getInstance();
        TextView textView = findViewById(R.id.textdate);
        roomName = findViewById(R.id.roomname);
        String roomPath = roomName.getText().toString();

        DocumentReference docRef = firebaseFirestore.collection("Rooms").document(roomPath);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("DocumentSnapshot data: " + document.get("rentDetails"));
                        Map<String, Object> map = new HashMap<>();
                        map.put("rentDetails", document.get("rentDetails") + "" + textView.getText() + ": 12:00PM - 1:00PM; ");
                        docRef.update(map);
                    } else {
                        System.out.println("No such document");
                    }
                } else {
                    System.out.println("get failed with " + task.getException());
                }
            }
        });
    }

    public void rentTimeInterval8 (View view) {

        Button buttonTimeInterval8 = findViewById(R.id.timeinterval8);
        buttonTimeInterval8.setEnabled(false);
        firebaseFirestore = firebaseFirestore.getInstance();
        TextView textView = findViewById(R.id.textdate);
        roomName = findViewById(R.id.roomname);
        String roomPath = roomName.getText().toString();

        DocumentReference docRef = firebaseFirestore.collection("Rooms").document(roomPath);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("DocumentSnapshot data: " + document.get("rentDetails"));
                        Map<String, Object> map = new HashMap<>();
                        map.put("rentDetails", document.get("rentDetails") + "" + textView.getText() + ": 1:00PM - 2:00PM; ");
                        docRef.update(map);
                    } else {
                        System.out.println("No such document");
                    }
                } else {
                    System.out.println("get failed with " + task.getException());
                }
            }
        });
    }

    public void rentTimeInterval9 (View view) {

        Button buttonTimeInterval9 = findViewById(R.id.timeinterval9);
        buttonTimeInterval9.setEnabled(false);
        firebaseFirestore = firebaseFirestore.getInstance();
        TextView textView = findViewById(R.id.textdate);
        roomName = findViewById(R.id.roomname);
        String roomPath = roomName.getText().toString();

        DocumentReference docRef = firebaseFirestore.collection("Rooms").document(roomPath);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("DocumentSnapshot data: " + document.get("rentDetails"));
                        Map<String, Object> map = new HashMap<>();
                        map.put("rentDetails", document.get("rentDetails") + "" + textView.getText() + ": 2:00PM - 3:00PM; ");
                        docRef.update(map);
                    } else {
                        System.out.println("No such document");
                    }
                } else {
                    System.out.println("get failed with " + task.getException());
                }
            }
        });
    }

    public void rentTimeInterval10 (View view) {

        Button buttonTimeInterval10 = findViewById(R.id.timeinterval10);
        buttonTimeInterval10.setEnabled(false);
        firebaseFirestore = firebaseFirestore.getInstance();
        TextView textView = findViewById(R.id.textdate);
        roomName = findViewById(R.id.roomname);
        String roomPath = roomName.getText().toString();

        DocumentReference docRef = firebaseFirestore.collection("Rooms").document(roomPath);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("DocumentSnapshot data: " + document.get("rentDetails"));
                        Map<String, Object> map = new HashMap<>();
                        map.put("rentDetails", document.get("rentDetails") + "" + textView.getText() + ": 3:00PM - 4:00PM; ");
                        docRef.update(map);
                    } else {
                        System.out.println("No such document");
                    }
                } else {
                    System.out.println("get failed with " + task.getException());
                }
            }
        });
    }

    public void rentTimeInterval11 (View view) {

        Button buttonTimeInterval11 = findViewById(R.id.timeinterval11);
        buttonTimeInterval11.setEnabled(false);
        firebaseFirestore = firebaseFirestore.getInstance();
        TextView textView = findViewById(R.id.textdate);
        roomName = findViewById(R.id.roomname);
        String roomPath = roomName.getText().toString();

        DocumentReference docRef = firebaseFirestore.collection("Rooms").document(roomPath);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("DocumentSnapshot data: " + document.get("rentDetails"));
                        Map<String, Object> map = new HashMap<>();
                        map.put("rentDetails", document.get("rentDetails") + "" + textView.getText() + ": 4:00PM - 5:00PM; ");
                        docRef.update(map);
                    } else {
                        System.out.println("No such document");
                    }
                } else {
                    System.out.println("get failed with " + task.getException());
                }
            }
        });
    }

    public void rentTimeInterval12 (View view) {

        Button buttonTimeInterval12 = findViewById(R.id.timeinterval12);
        buttonTimeInterval12.setEnabled(false);
        firebaseFirestore = firebaseFirestore.getInstance();
        TextView textView = findViewById(R.id.textdate);
        roomName = findViewById(R.id.roomname);
        String roomPath = roomName.getText().toString();

        DocumentReference docRef = firebaseFirestore.collection("Rooms").document(roomPath);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("DocumentSnapshot data: " + document.get("rentDetails"));
                        Map<String, Object> map = new HashMap<>();
                        map.put("rentDetails", document.get("rentDetails") + "" + textView.getText() + ": 5:00PM - 6:00PM; ");
                        docRef.update(map);
                    } else {
                        System.out.println("No such document");
                    }
                } else {
                    System.out.println("get failed with " + task.getException());
                }
            }
        });
    }

    public void rentTimeInterval13 (View view) {

        Button buttonTimeInterval13 = findViewById(R.id.timeinterval13);
        buttonTimeInterval13.setEnabled(false);
        firebaseFirestore = firebaseFirestore.getInstance();
        TextView textView = findViewById(R.id.textdate);
        roomName = findViewById(R.id.roomname);
        String roomPath = roomName.getText().toString();

        DocumentReference docRef = firebaseFirestore.collection("Rooms").document(roomPath);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("DocumentSnapshot data: " + document.get("rentDetails"));
                        Map<String, Object> map = new HashMap<>();
                        map.put("rentDetails", document.get("rentDetails") + "" + textView.getText() + ": 6:00PM - 7:00PM; ");
                        docRef.update(map);
                    } else {
                        System.out.println("No such document");
                    }
                } else {
                    System.out.println("get failed with " + task.getException());
                }
            }
        });
    }

    public void rentTimeInterval14 (View view) {

        Button buttonTimeInterval14 = findViewById(R.id.timeinterval14);
        buttonTimeInterval14.setEnabled(false);
        firebaseFirestore = firebaseFirestore.getInstance();
        TextView textView = findViewById(R.id.textdate);
        roomName = findViewById(R.id.roomname);
        String roomPath = roomName.getText().toString();

        DocumentReference docRef = firebaseFirestore.collection("Rooms").document(roomPath);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("DocumentSnapshot data: " + document.get("rentDetails"));
                        Map<String, Object> map = new HashMap<>();
                        map.put("rentDetails", document.get("rentDetails") + "" + textView.getText() + ": 7:00PM - 8:00PM; ");
                        docRef.update(map);
                    } else {
                        System.out.println("No such document");
                    }
                } else {
                    System.out.println("get failed with " + task.getException());
                }
            }
        });
    }

    public void rentTimeInterval15 (View view) {

        Button buttonTimeInterval15 = findViewById(R.id.timeinterval15);
        buttonTimeInterval15.setEnabled(false);
        firebaseFirestore = firebaseFirestore.getInstance();
        TextView textView = findViewById(R.id.textdate);
        roomName = findViewById(R.id.roomname);
        String roomPath = roomName.getText().toString();

        DocumentReference docRef = firebaseFirestore.collection("Rooms").document(roomPath);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("DocumentSnapshot data: " + document.get("rentDetails"));
                        Map<String, Object> map = new HashMap<>();
                        map.put("rentDetails", document.get("rentDetails") + "" + textView.getText() + ": 8:00PM - 9:00PM; ");
                        docRef.update(map);
                    } else {
                        System.out.println("No such document");
                    }
                } else {
                    System.out.println("get failed with " + task.getException());
                }
            }
        });
    }

    public void rentTimeInterval16 (View view) {

        Button buttonTimeInterval16 = findViewById(R.id.timeinterval16);
        buttonTimeInterval16.setEnabled(false);
        firebaseFirestore = firebaseFirestore.getInstance();
        TextView textView = findViewById(R.id.textdate);
        roomName = findViewById(R.id.roomname);
        String roomPath = roomName.getText().toString();

        DocumentReference docRef = firebaseFirestore.collection("Rooms").document(roomPath);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("DocumentSnapshot data: " + document.get("rentDetails"));
                        Map<String, Object> map = new HashMap<>();
                        map.put("rentDetails", document.get("rentDetails") + "" + textView.getText() + ": 9:00PM - 10:00PM; ");
                        docRef.update(map);
                    } else {
                        System.out.println("No such document");
                    }
                } else {
                    System.out.println("get failed with " + task.getException());
                }
            }
        });
    }
}