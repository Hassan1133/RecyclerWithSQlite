package com.example.recycler_with_sqlite;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdp extends RecyclerView.Adapter<UserAdp.Holder> {

    private static final String TAG = "UserAdp";
    String stringPhone, stringName;
    private Context context;
    private List<User> list;

    private EditText name, phone;

    private DatabaseHelper databaseHelper;

    public UserAdp(Context context, List<User> list) {
        this.context = context;
        this.list = list;
        databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_design, parent, false);
        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        User user = list.get(position);

        holder.nameData.setText(user.getName());
        holder.phoneData.setText(user.getPhone());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);

                Button cancelBtn = dialog.findViewById(R.id.cancelBtn);
                Button okBtn = dialog.findViewById(R.id.okBtn);

                name = dialog.findViewById(R.id.name);
                name.setText(user.getName());

                phone = dialog.findViewById(R.id.phone);
                phone.setText(user.getPhone());

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isValid()) {

                            updateData(user.getId());

                            user.setName(stringName);
                            user.setPhone(stringPhone);
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    }
                });

            }
        });
    }

    private boolean isValid() {
        stringName = name.getText().toString();
        stringPhone = phone.getText().toString();

        boolean valid = true;

        if (stringName.length() < 3) {
            name.setError("Enter valid name");
            valid = false;
        }
        if (stringPhone.length() < 11 || stringPhone.length() > 11) {
            phone.setError("Enter valid phone no");
            valid = false;
        }

        return valid;
    }

    private void updateData(int id) {
        int res = databaseHelper.updateData(id, name.getText().toString().trim(), phone.getText().toString().trim());
        if (res == 1) {
            Toast.makeText(context, "Data updated Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Data not updated Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView nameData, phoneData;
        ImageView edit;

        public Holder(@NonNull View itemView) {
            super(itemView);

            nameData = itemView.findViewById(R.id.nameData);
            phoneData = itemView.findViewById(R.id.phoneData);
            edit = itemView.findViewById(R.id.editBtn);
        }
    }
}
