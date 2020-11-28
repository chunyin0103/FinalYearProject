package com.example.fyp4.staffui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fyp4.R;
import com.example.fyp4.Residentui.MainActivity;
import com.example.fyp4.Residentui.navigationFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class checkParkingFragment extends Fragment {
    private final String TAG = "CheckParkingFragment";
    private Button park1,park2,park3,park4,park5,park6,park7,park8,park9,park10,park11,park12,back;
    private EditText carplate,carBrand,carColor;
    private FirebaseFirestore firestore;
    private String plate,barnd,color;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = (ViewGroup)inflater.inflate(R.layout.fragment_checkavailable,container,false);
        firestore = FirebaseFirestore.getInstance();
        park1 = (Button) root.findViewById(R.id.park1);
        park2 = (Button) root.findViewById(R.id.park2);
        park3 = (Button) root.findViewById(R.id.park3);
        park4 = (Button) root.findViewById(R.id.park4);
        park5 = (Button) root.findViewById(R.id.park5);
        park6 = (Button) root.findViewById(R.id.park6);
        park7 = (Button) root.findViewById(R.id.park7);
        park8 = (Button) root.findViewById(R.id.park8);
        park9 =(Button) root.findViewById(R.id.park9);
        park10 =(Button) root.findViewById(R.id.park10);
        park11 =(Button) root.findViewById(R.id.park11);
        park12 =(Button) root.findViewById(R.id.park12);
        carplate = (EditText) root.findViewById(R.id.editTextCarPlat);
        carBrand = (EditText) root.findViewById(R.id.editTextCarBrand);
        carColor = (EditText) root.findViewById(R.id.editTextCarColor);
        back = (Button) root.findViewById(R.id.backfromPark);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent((Activity)getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });


            park1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference ref = firestore.collection("Parking").document();
                Map<String, String> parking = new HashMap<>();
                plate = carplate.getText().toString();
                barnd = carBrand.getText().toString();
                color = carColor.getText().toString();

                if (plate.isEmpty()) {
                    carplate.setError("Please Enter Your Car Plate Number.");
                    //return;
                }
                if(barnd.isEmpty()){
                    carBrand.setError("Plaese Enter Your Car Brands.");
                }
                if(color.isEmpty()){
                    carColor.setError("Please Enter Your Car Color.");
                }
                else {
                    parking.put("Car Plate No", plate);
                    parking.put("Car Brands: ",barnd);
                    parking.put("Car Color: ",color);
                    parking.put("Parking No", "1");
                    parking.put("Status", "Not Available");
                    park1.setBackgroundColor(Color.parseColor("#FF0000"));
                    park1.setClickable(false);
                    ref.set(parking).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "Car Details is Recorded. Please assign the visitor to the parking slot.");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "The Parking Slot is Not Available.");
                        }
                    });
                    Toast.makeText(getContext(), "Car Details is Recorded. Please assign the visitor to the parking slot.", Toast.LENGTH_SHORT).show();
                    Intent maps = new Intent((Activity) getActivity(), MainActivity2.class);
                    startActivity(maps);

                }
            }
        });
        park2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference ref = firestore.collection("Parking").document();
                Map<String, String> parking = new HashMap<>();
                plate = carplate.getText().toString();
                barnd = carBrand.getText().toString();
                color = carColor.getText().toString();

                if (plate.isEmpty()) {
                    carplate.setError("Please Enter Your Car Plate Number.");
                    //return;
                }
                if(barnd.isEmpty()){
                    carBrand.setError("Plaese Enter Your Car Brands.");
                }
                if(color.isEmpty()){
                    carColor.setError("Please Enter Your Car Color.");
                }
                else {
                    parking.put("Car Plate No", plate);
                    parking.put("Car Brands: ",barnd);
                    parking.put("Car Color: ",color);
                    parking.put("Parking No", "2");
                    parking.put("Status", "Not Available");
                    park2.setBackgroundColor(Color.parseColor("#FF0000"));
                    park2.setClickable(false);
                    ref.set(parking).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "Car Details is Recorded. Please assign the visitor to the parking slot.");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "The Parking Slot is Not Available");
                        }
                    });
                    Toast.makeText(getContext(), "Car Details is Recorded. Please assign the visitor to the parking slot.", Toast.LENGTH_SHORT).show();
                    Intent maps = new Intent((Activity) getActivity(), MainActivity2.class);
                    startActivity(maps);

                }
            }
        });
        park3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference ref = firestore.collection("Parking").document();
                Map<String, String> parking = new HashMap<>();
                plate = carplate.getText().toString();
                barnd = carBrand.getText().toString();
                color = carColor.getText().toString();

                if (plate.isEmpty()) {
                    carplate.setError("Please Enter Your Car Plate Number.");
                    //return;
                }
                if(barnd.isEmpty()){
                    carBrand.setError("Plaese Enter Your Car Brands.");
                }
                if(color.isEmpty()){
                    carColor.setError("Please Enter Your Car Color.");
                }
                else {
                    parking.put("Car Plate No", plate);
                    parking.put("Car Brands: ",barnd);
                    parking.put("Car Color: ",color);
                    parking.put("Parking No", "3");
                    parking.put("Status", "Not Available");
                    park3.setBackgroundColor(Color.parseColor("#FF0000"));
                    park3.setClickable(false);
                    ref.set(parking).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "Car Details is Recorded. Please assign the visitor to the parking slot.");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "The Parking Slot is Not Available");
                        }
                    });
                    Toast.makeText(getContext(), "Car Details is Recorded. Please assign the visitor to the parking slot.", Toast.LENGTH_SHORT).show();
                    Intent maps = new Intent((Activity) getActivity(), MainActivity2.class);
                    startActivity(maps);

                }
            }
        });
        park4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference ref = firestore.collection("Parking").document();
                Map<String, String> parking = new HashMap<>();
                plate = carplate.getText().toString();
                barnd = carBrand.getText().toString();
                color = carColor.getText().toString();

                if (plate.isEmpty()) {
                    carplate.setError("Please Enter Your Car Plate Number.");
                    //return;
                }
                if(barnd.isEmpty()){
                    carBrand.setError("Plaese Enter Your Car Brands.");
                }
                if(color.isEmpty()){
                    carColor.setError("Please Enter Your Car Color.");
                }
                else {
                    parking.put("Car Plate No", plate);
                    parking.put("Car Brands: ",barnd);
                    parking.put("Car Color: ",color);
                    parking.put("Parking No", "4");
                    parking.put("Status", "Not Available");
                    park4.setBackgroundColor(Color.parseColor("#FF0000"));
                    park4.setClickable(false);
                    ref.set(parking).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "Car Details is Recorded. Please assign the visitor to the parking slot.");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "The Parking Slot is Not Available");
                        }
                    });
                    Toast.makeText(getContext(), "Car Details is Recorded. Please assign the visitor to the parking slot.", Toast.LENGTH_SHORT).show();
                    Intent maps = new Intent((Activity) getActivity(), MainActivity2.class);
                    startActivity(maps);

                }
            }
        });
        park5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference ref = firestore.collection("Parking").document();
                Map<String, String> parking = new HashMap<>();
                plate = carplate.getText().toString();
                barnd = carBrand.getText().toString();
                color = carColor.getText().toString();

                if (plate.isEmpty()) {
                    carplate.setError("Please Enter Your Car Plate Number.");
                    //return;
                }
                if(barnd.isEmpty()){
                    carBrand.setError("Plaese Enter Your Car Brands.");
                }
                if(color.isEmpty()){
                    carColor.setError("Please Enter Your Car Color.");
                }
                else {
                    parking.put("Car Plate No", plate);
                    parking.put("Car Brands: ",barnd);
                    parking.put("Car Color: ",color);
                    parking.put("Parking No", "5");
                    parking.put("Status", "Not Available");
                    park4.setBackgroundColor(Color.parseColor("#FF0000"));
                    park5.setClickable(false);
                    ref.set(parking).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "Car Details is Recorded. Please assign the visitor to the parking slot.");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "The Parking Slot is Not Available");
                        }
                    });
                    Toast.makeText(getContext(), "Car Details is Recorded. Please assign the visitor to the parking slot.", Toast.LENGTH_SHORT).show();
                    Intent maps = new Intent((Activity) getActivity(), MainActivity2.class);
                    startActivity(maps);

                }
            }
        });
        park6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference ref = firestore.collection("Parking").document();
                Map<String, String> parking = new HashMap<>();
                plate = carplate.getText().toString();
                barnd = carBrand.getText().toString();
                color = carColor.getText().toString();

                if (plate.isEmpty()) {
                    carplate.setError("Please Enter Your Car Plate Number.");
                    //return;
                }
                if(barnd.isEmpty()){
                    carBrand.setError("Plaese Enter Your Car Brands.");
                }
                if(color.isEmpty()){
                    carColor.setError("Please Enter Your Car Color.");
                }
                else {
                    parking.put("Car Plate No", plate);
                    parking.put("Car Brands: ",barnd);
                    parking.put("Car Color: ",color);
                    parking.put("Parking No", "6");
                    parking.put("Status", "Not Available");
                    park6.setBackgroundColor(Color.parseColor("#FF0000"));
                    park6.setClickable(false);
                    ref.set(parking).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "Car Details is Recorded. Please assign the visitor to the parking slot.");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "The Parking Slot is Not Available");
                        }
                    });
                    Toast.makeText(getContext(), "Car Details is Recorded. Please assign the visitor to the parking slot.", Toast.LENGTH_SHORT).show();
                    Intent maps = new Intent((Activity) getActivity(), MainActivity2.class);
                    startActivity(maps);

                }
            }
        });
        park7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference ref = firestore.collection("Parking").document();
                Map<String, String> parking = new HashMap<>();
                plate = carplate.getText().toString();
                barnd = carBrand.getText().toString();
                color = carColor.getText().toString();

                if (plate.isEmpty()) {
                    carplate.setError("Please Enter Your Car Plate Number.");
                    //return;
                }
                if(barnd.isEmpty()){
                    carBrand.setError("Plaese Enter Your Car Brands.");
                }
                if(color.isEmpty()){
                    carColor.setError("Please Enter Your Car Color.");
                }
                else {
                    parking.put("Car Plate No", plate);
                    parking.put("Car Brands: ",barnd);
                    parking.put("Car Color: ",color);
                    parking.put("Parking No", "7");
                    parking.put("Status", "Not Available");
                    park7.setBackgroundColor(Color.parseColor("#FF0000"));
                    park7.setClickable(false);
                    ref.set(parking).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "Car Details is Recorded. Please assign the visitor to the parking slot.");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "The Parking Slot is Not Available");
                        }
                    });
                    Toast.makeText(getContext(), "Car Details is Recorded. Please assign the visitor to the parking slot.", Toast.LENGTH_SHORT).show();
                    Intent maps = new Intent((Activity) getActivity(), MainActivity2.class);
                    startActivity(maps);

                }
            }
        });
        park8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference ref = firestore.collection("Parking").document();
                Map<String, String> parking = new HashMap<>();
                plate = carplate.getText().toString();
                barnd = carBrand.getText().toString();
                color = carColor.getText().toString();

                if (plate.isEmpty()) {
                    carplate.setError("Please Enter Your Car Plate Number.");
                    //return;
                }
                if(barnd.isEmpty()){
                    carBrand.setError("Plaese Enter Your Car Brands.");
                }
                if(color.isEmpty()){
                    carColor.setError("Please Enter Your Car Color.");
                }
                else {
                    parking.put("Car Plate No", plate);
                    parking.put("Car Brands: ",barnd);
                    parking.put("Car Color: ",color);
                    parking.put("Parking No", "8");
                    parking.put("Status", "Not Available");
                    park8.setBackgroundColor(Color.parseColor("#FF0000"));
                    park8.setClickable(false);
                    ref.set(parking).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "Car Details is Recorded. Please assign the visitor to the parking slot.");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "The Parking Slot is Not Available");
                        }
                    });
                    Toast.makeText(getContext(), "Car Details is Recorded. Please assign the visitor to the parking slot.", Toast.LENGTH_SHORT).show();
                    Intent maps = new Intent((Activity) getActivity(), MainActivity2.class);
                    startActivity(maps);

                }
            }
        });
        park9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference ref = firestore.collection("Parking").document();
                Map<String, String> parking = new HashMap<>();
                plate = carplate.getText().toString();
                barnd = carBrand.getText().toString();
                color = carColor.getText().toString();

                if (plate.isEmpty()) {
                    carplate.setError("Please Enter Your Car Plate Number.");
                    //return;
                }
                if(barnd.isEmpty()){
                    carBrand.setError("Plaese Enter Your Car Brands.");
                }
                if(color.isEmpty()){
                    carColor.setError("Please Enter Your Car Color.");
                }
                else {
                    parking.put("Car Plate No", plate);
                    parking.put("Car Brands: ",barnd);
                    parking.put("Car Color: ",color);
                    parking.put("Parking No", "9");
                    parking.put("Status", "Not Available");
                    park9.setBackgroundColor(Color.parseColor("#FF0000"));
                    park9.setClickable(false);
                    ref.set(parking).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "Car Details is Recorded. Please assign the visitor to the parking slot.");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "The Parking Slot is Not Available");
                        }
                    });
                    Toast.makeText(getContext(), "Car Details is Recorded. Please assign the visitor to the parking slot.", Toast.LENGTH_SHORT).show();
                    Intent maps = new Intent((Activity) getActivity(), MainActivity2.class);
                    startActivity(maps);

                }
            }
        });
        park10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference ref = firestore.collection("Parking").document();
                Map<String, String> parking = new HashMap<>();
                plate = carplate.getText().toString();
                barnd = carBrand.getText().toString();
                color = carColor.getText().toString();

                if (plate.isEmpty()) {
                    carplate.setError("Please Enter Your Car Plate Number.");
                    //return;
                }
                if(barnd.isEmpty()){
                    carBrand.setError("Plaese Enter Your Car Brands.");
                }
                if(color.isEmpty()){
                    carColor.setError("Please Enter Your Car Color.");
                }
                else {
                    parking.put("Car Plate No", plate);
                    parking.put("Car Brands: ",barnd);
                    parking.put("Car Color: ",color);
                    parking.put("Parking No", "10");
                    parking.put("Status", "Not Available");
                    park10.setBackgroundColor(Color.parseColor("#FF0000"));
                    park10.setClickable(false);
                    ref.set(parking).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "Car Details is Recorded. Please assign the visitor to the parking slot.");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "The Parking Slot is Not Available");
                        }
                    });
                    Toast.makeText(getContext(), "Car Details is Recorded. Please assign the visitor to the parking slot.", Toast.LENGTH_SHORT).show();
                    Intent maps = new Intent((Activity) getActivity(), MainActivity2.class);
                    startActivity(maps);

                }
            }
        });
        park11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference ref = firestore.collection("Parking").document();
                Map<String, String> parking = new HashMap<>();
                plate = carplate.getText().toString();
                barnd = carBrand.getText().toString();
                color = carColor.getText().toString();

                if (plate.isEmpty()) {
                    carplate.setError("Please Enter Your Car Plate Number.");
                    //return;
                }
                if(barnd.isEmpty()){
                    carBrand.setError("Plaese Enter Your Car Brands.");
                }
                if(color.isEmpty()){
                    carColor.setError("Please Enter Your Car Color.");
                }
                else {
                    parking.put("Car Plate No", plate);
                    parking.put("Car Brands: ",barnd);
                    parking.put("Car Color: ",color);
                    parking.put("Parking No", "11");
                    parking.put("Status", "Not Available");
                    park11.setBackgroundColor(Color.parseColor("#FF0000"));
                    park11.setClickable(false);
                    ref.set(parking).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "Car Details is Recorded. Please assign the visitor to the parking slot.");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "The Parking Slot is Not Available");
                        }
                    });
                    Toast.makeText(getContext(), "Car Details is Recorded. Please assign the visitor to the parking slot.", Toast.LENGTH_SHORT).show();
                    Intent maps = new Intent((Activity) getActivity(), MainActivity2.class);
                    startActivity(maps);

                }
            }
        });
        park12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference ref = firestore.collection("Parking").document();
                Map<String, String> parking = new HashMap<>();
                plate = carplate.getText().toString();
                barnd = carBrand.getText().toString();
                color = carColor.getText().toString();

                if (plate.isEmpty()) {
                    carplate.setError("Please Enter Your Car Plate Number.");
                    //return;
                }
                if(barnd.isEmpty()){
                    carBrand.setError("Plaese Enter Your Car Brands.");
                }
                if(color.isEmpty()){
                    carColor.setError("Please Enter Your Car Color.");
                }
                else {
                    parking.put("Car Plate No", plate);
                    parking.put("Car Brands: ",barnd);
                    parking.put("Car Color: ",color);
                    parking.put("Parking No", "12");
                    parking.put("Status", "Not Available");
                    park12.setBackgroundColor(Color.parseColor("#FF0000"));
                    park12.setClickable(false);
                    ref.set(parking).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "Car Details is Recorded. Please assign the visitor to the parking slot.");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "The Parking Slot is Not Available");
                        }
                    });
                    Toast.makeText(getContext(), "Car Details is Recorded. Please assign the visitor to the parking slot.", Toast.LENGTH_SHORT).show();
                    Intent maps = new Intent((Activity) getActivity(), MainActivity2.class);
                    startActivity(maps);

                }
            }
        });

        return  root;
    }
}
