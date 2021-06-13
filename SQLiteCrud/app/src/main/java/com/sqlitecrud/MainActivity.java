package com.sqlitecrud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    TextView datalist;
    TextView datalist_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(MainActivity.this);
        //ACCESS ALL BTN
        Button delete = findViewById(R.id.delete_data);
        Button insert = findViewById(R.id.insert_data);
        Button update = findViewById(R.id.update_data);
        Button read = findViewById(R.id.refresh_data);
        //
        datalist = findViewById(R.id.all_data_list);
        datalist_count = findViewById(R.id.data_list_count);

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowInputDialogue();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateIdDialog();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();

            }
        });
    }

    private void refreshData() {
        datalist_count.setText("ALL DATA COUNT : " + databaseHelper.getTotalCount());

        //Access CandidateModel ListData & Store into candidate list object
        List<CandidateModel> candidateModelList = databaseHelper.getAllCandidates();
        datalist.setText("");
        for (CandidateModel candidateModel : candidateModelList) {
            datalist.append("ID : " + candidateModel.getId() + " | Name : " + candidateModel.getName() + " | Email : " + candidateModel.getEmail() + " | PHONE : " + candidateModel.getPhone() + " \n\n");
        }
    }

    private void showDeleteDialog() {
        AlertDialog.Builder al = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.delete, null);
        al.setView(view);
        EditText id_input = view.findViewById(R.id.id_input);
        Button delete_btn = view.findViewById(R.id.delete_btn);
        final AlertDialog alertDialog = al.show();
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteCandidate(id_input.getText().toString());
                alertDialog.dismiss();
                refreshData();

            }
        });

    }

    private void showUpdateIdDialog() {
        AlertDialog.Builder al = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.update_id, null);
        al.setView(view);
        final EditText id_input = view.findViewById(R.id.id_input);
        Button fetch_btn = view.findViewById(R.id.update_id_btn);
        final AlertDialog alertDialog = al.show();
        fetch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataDialog(id_input.getText().toString());
                alertDialog.dismiss();
                refreshData();

            }
        });

    }

    private void showDataDialog(final String id) {
        CandidateModel candidateModel = databaseHelper.getCandidate(Integer.parseInt(id));
        //Access input_dialog layout file
        AlertDialog.Builder al = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.update, null);
        //Access all input EditText of input_dialog + insertButton
        final EditText name = view.findViewById(R.id.name);
        final EditText email = view.findViewById(R.id.email);
        final EditText phone = view.findViewById(R.id.phone);
        Button update_Btn = view.findViewById(R.id.update_btn);
        al.setView(view);

        //set CandidateModel Data into EditText Field of name,email...etc
        name.setText(candidateModel.getName());
        email.setText(candidateModel.getEmail());
        phone.setText(candidateModel.getPhone());

        final AlertDialog alertDialog = al.show();
        update_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CandidateModel candidateModel = new CandidateModel();
                //set data into CandidateModel Object from EditText input on button click
                candidateModel.setName(name.getText().toString());
                candidateModel.setId(id);
                candidateModel.setEmail(email.getText().toString());
                candidateModel.setPhone(phone.getText().toString());

                databaseHelper.updateCandidate(candidateModel);
                alertDialog.dismiss();
                refreshData();

            }
        });


    }

    private void ShowInputDialogue() {
        //Access input_dialog layout file
        AlertDialog.Builder al = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.insert, null);
        //Access all input EditText of input_dialog + insertButton
        EditText name = view.findViewById(R.id.name);
        EditText email = view.findViewById(R.id.email);
        EditText phone = view.findViewById(R.id.phone);
        Button insertBtn = view.findViewById(R.id.insert_btn);
        al.setView(view);

        AlertDialog alertDialog = al.show();
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CandidateModel candidateModel = new CandidateModel();
                //set data into CandidateModel Object from EditText input on button click
                candidateModel.setName(name.getText().toString());
                candidateModel.setEmail(email.getText().toString());
                candidateModel.setPhone(phone.getText().toString());
                Date date = new Date();
                candidateModel.setCreated_at("" + date.getTime());
                //pass object with addC method
                databaseHelper.AddCandidate(candidateModel);
                alertDialog.dismiss();
                refreshData();

            }
        });


    }
}