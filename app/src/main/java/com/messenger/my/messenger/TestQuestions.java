package com.messenger.my.messenger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class TestQuestions extends Fragment {

    String counterOfFragment;
    Integer counter = 1;
    EditText answer, question;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_test_questions, container, false);


        Bundle bundle = getArguments();
        counterOfFragment = "0";
        if (bundle != null) {
            counterOfFragment = bundle.getString("Value", "0");
        }



        Button button = rootView.findViewById(R.id.upload);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                answer = rootView.findViewById(R.id.answer);
                question = rootView.findViewById(R.id.question1);


                // объявляем переменную
                final DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();

// сохраняем данные
                mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        mRef.child("Books").child(counterOfFragment).child("Tests").child(counter.toString()).child("Answer").setValue(answer);
                        mRef.child("Books").child(counterOfFragment).child("Tests").child(counter.toString()).child("Question").setValue(question);
                        mRef.child("Books").child(counterOfFragment).child("Tests").child(counter.toString()).child("counter").setValue(counter);

                    }
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Error")
                                .setMessage(databaseError.getMessage())
                                .setCancelable(false)
                                .setNegativeButton("Ок, закрыть",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });


                Toast.makeText(getContext(),"Тест успешно загружен!", Toast.LENGTH_LONG).show();
                Fragment fragment = new Profile();
                FragmentTransaction fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                fragmentManager.replace(R.id.container2, fragment).commit();
            }
        });




        Button button2 = rootView.findViewById(R.id.next_test);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                counter++;
                Fragment fragment = new TestQuestions();
                FragmentTransaction fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                fragmentManager.replace(R.id.container2, fragment).commit();
            }
        });


        return rootView;
    }




}
