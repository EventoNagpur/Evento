package evento.example.com.evento;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.common.data.DataBufferObserverSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;

/**
 * Created by sony on 6/16/2017.
 */

public class CollegeAdapter extends RecyclerView.Adapter<CollegeAdapter.ViewHolder> {

    ArrayList<CollegeItem> data;
    Context m;

    CollegeAdapter(ArrayList<CollegeItem> data,Context m)
    {
        this.data=data;this.m=m;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.college_cardview, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final int a=position;
        final CollegeItem ci=data.get(position);
        final String key=ci.getKey();
        holder.data.setText(ci.shortDescription());
        holder.upvotetextView.setText(ci.getUpvotes()+"");
        final  TextView tv=holder.upvotetextView;
        holder.up.setChecked(GloVar.collegeUpvoted.contains(key));


        holder.up.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final int operation;
                DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
                DatabaseReference ref1=ref.child("users/"+GloVar.uid+"/vote/college");
                final int up=ci.getUpvotes();

                if(isChecked)
                {
                    ref1.child(key).setValue(1);
                    operation=1;
                    int r=up+1;
                    ci.setUpvotes(r);
                    GloVar.colList.set(a,ci);
                    tv.setText(r+"");
                    GloVar.collegeUpvoted.add(key);
                }
                else
                {
                    ref1.child(key).setValue(0);
                    operation=-1;
                    if(up > 0) {
                        int r=up-1;
                        ci.setUpvotes(r);
                        GloVar.colList.set(a,ci);
                        tv.setText(r+"");
                    }
                    GloVar.collegeUpvoted.remove(key);

                }

                DatabaseReference upvotesRef = ref.child("events/college-events/competitions/"+key+"/upvotes");
                upvotesRef.runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData mutableData) {
                        Integer currentValue = mutableData.getValue(Integer.class);
                        if (currentValue == null) {
                            if(up == 1)
                                mutableData.setValue(1);
                            else
                                mutableData.setValue(0);

                        } else {
                            mutableData.setValue(currentValue + operation);
                        }

                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(DatabaseError databaseError, boolean committed, DataSnapshot dataSnapshot) {
                        System.out.println("Transaction completed");
                    }
                });
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(m,CollegeEventDetails.class);
                GloVar.transferObject=data.get(a);
                m.startActivity(i);
            }
        });




    }

    @Override
    public int getItemCount() {
        return data.size();    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView data,upvotetextView;
        public ToggleButton up;
        public ViewHolder(View v) {
            super(v);
            data =(TextView) v.findViewById(R.id.data);
            up =(ToggleButton) v.findViewById(R.id.upvotes);
            upvotetextView=(TextView) v.findViewById(R.id.upvoteTextview);
        }
    }
}
