package ak.akx.noticeapp.ui.report;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ak.akx.noticeapp.AddMaterial;
import ak.akx.noticeapp.AddReport;
import ak.akx.noticeapp.Material;
import ak.akx.noticeapp.MaterialAdapter;
import ak.akx.noticeapp.R;
import ak.akx.noticeapp.Report;
import ak.akx.noticeapp.ReportAdapter;

public class ReportFragment extends Fragment {

    private ReportViewModel mViewModel;

    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;

    List<Report> reportList;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.report_fragment, container, false);

        recyclerView=root.findViewById(R.id.recyclerViewReport);
        firebaseFirestore=FirebaseFirestore.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
        reportList=new ArrayList<>();


        loadReport();


        return root;
    }

    public void loadReport()
    {

        firebaseFirestore.collection("Attendence Report").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(!queryDocumentSnapshots.isEmpty())
                {
                    List<DocumentSnapshot> dsList=queryDocumentSnapshots.getDocuments();


                    for (DocumentSnapshot d : dsList)
                    {
                        Report report=d.toObject(Report.class);
                        reportList.add(report);
                    }
                    Collections.reverse(reportList);
                    initializeRecyclerView();


                }
                else
                {
                    Toast.makeText(getContext(), "No Attendence Report Available", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

   public void initializeRecyclerView()
    {
        ReportAdapter recyclerAdapter = new ReportAdapter(getContext(),reportList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    }