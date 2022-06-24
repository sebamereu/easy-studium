package com.example.easy_studium;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExamFragment extends Fragment {
    private  TextView errorText;
    private EditText examName, examCFU;
    private Button saveExam;
    public static ArrayAdapter<String> adapter;
    public static ArrayAdapter<Exam> adapterExam;
    private ArrayList<String> arrayList;
    private ArrayList<Exam> arrayListExam;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExamFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExamFragment newInstance(String param1, String param2) {
        ExamFragment fragment = new ExamFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_exam, container, false);

        examName=view.findViewById(R.id.examName);
        examCFU=view.findViewById(R.id.examCFU);
        saveExam=view.findViewById(R.id.saveExamAction);
        errorText=view.findViewById(R.id.errorText);
        arrayList=Exam.arrayList1;
        arrayListExam=Exam.listExam;
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, arrayList);
        adapterExam = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, arrayListExam);



        saveExam.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (checkInput()) {
                    Exam e = new Exam(examName.getText().toString(), examCFU.getText().toString());
                    e.setName(examName.getText().toString());
                    e.setCfu(examCFU.getText().toString());
                    Exam.arrayList1.add(e.getName());
                    Exam.listExam.add(e);


                    replaceFragment(new DailyCalendarFragment());
                }
            }
        });

                return view;
    }

    private boolean checkInput() {
        int errors=0;
        int nCfu;
        if (examCFU.length()!=0) {
            nCfu = Integer.parseInt(String.valueOf(examCFU.getText()));
        }else nCfu=0;

        if(examName.length()==0) {
            errors++;
            examName.setError("Inserire il nome dell'esame");
        }else examName.setError(null);

        if(examCFU.length()==0
                || nCfu>30
                || !examCFU.getText().toString().matches("(.*[0-9].*)")
                || examCFU.getText().toString().matches("(.*[a-z].*)")
                || examCFU.getText().toString().matches("(.*[A-Z].*)")
                || examCFU.getText().toString().matches("^(?=.*[_.()$&@]).*$")){
            errors++;
            examCFU.setError("Inserire numero valido");
        }else examCFU.setError(null);


        switch (errors){
            case 0:
                errorText.setVisibility(View.GONE);
                errorText.setText("");
                break;
            case 1:
                errorText.setVisibility(View.VISIBLE);
                errorText.setText("Si è verificato un errore");
                break;
            default:
                errorText.setVisibility(View.VISIBLE);
                errorText.setText("Si sono verificati "+errors+" errori");
                break;
        }

        return errors==0;
    }

    void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}