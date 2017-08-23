package com.jdkgroup.pms.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jdkgroup.models.Person;
import com.jdkgroup.pms.R;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {
    private ArrayList<Person> mPersonList;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private ItemClickListener itemClickListener;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        AppCompatImageView appIvSelect;
        AppCompatEditText appEdtCVV;
        AppCompatTextView appTvAccountNo;
        LinearLayout llAccountDetails;

        public ViewHolder(View v) {
            super(v);
            appIvSelect = (AppCompatImageView) v.findViewById(R.id.appIvSelect);
            appEdtCVV = (AppCompatEditText) v.findViewById(R.id.appEdtCVV);
            appTvAccountNo = (AppCompatTextView) v.findViewById(R.id.appTvAccountNo);
            llAccountDetails= (LinearLayout) v.findViewById(R.id.llAccountDetails);

            llAccountDetails.setOnClickListener(this);
            appEdtCVV.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.llAccountDetails:
                    itemClickListener.onUpdateAt(view, getAdapterPosition());
                    break;
            }
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void add(int position, Person item) {
        mPersonList.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(String item) {
        int position = mPersonList.indexOf(item);
        mPersonList.remove(position);
        notifyItemRemoved(position);
    }

    public PaymentAdapter(ArrayList<Person> personList, Context context) {
        mPersonList = personList;
        this.context = context;
        mPref = context.getSharedPreferences("person", Context.MODE_PRIVATE);
        mEditor = mPref.edit();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_payment, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (mPersonList.get(position).isSelected()) {
            holder.appIvSelect.setImageResource(R.drawable.ic_circle_select);
            holder.appEdtCVV.setVisibility(View.VISIBLE);
            holder.appEdtCVV.setText(null);
        } else {
            holder.appIvSelect.setImageResource(R.drawable.ic_circle_unselect);
            holder.appEdtCVV.setVisibility(View.GONE);
            holder.appEdtCVV.setText(null);
        }

        holder.appEdtCVV.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence query, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence cs, int start, int before, int count) {
                String cvvNo = cs.toString().toLowerCase();

                if(cvvNo.length() == 3) {
                   itemClickListener.onUpdateAtCVV(position, cvvNo);
                }
            }
        });
    }

    public void setSelected(int pos, int isSelect) {
        try {
            if (mPersonList.size() > 1) {
                mPersonList.get(mPref.getInt("position", 0)).setSelected(false);
                mEditor.putInt("position", pos);
                mEditor.commit();
            }
            if(isSelect == 1) {
                mPersonList.get(pos).setSelected(true);
            }
            else
            {
                mPersonList.get(0).setSelected(false);
            }
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mPersonList.size();
    }

    public interface ItemClickListener {
        void onUpdateAt(View view, int position);
        void onUpdateAtCVV(int position, String cvv);
    }
}
