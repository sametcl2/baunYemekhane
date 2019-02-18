package com.example.samet.baunyemekhane;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Persembe extends Fragment {
    View view;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager manager;
    ArrayList<String> parrayList=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.persembe,container,false);
        recyclerView=view.findViewById(R.id.recyclerView4);
        recyclerView.setHasFixedSize(true);
        manager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        for (int i=0; i<4; i++)
            parrayList.add(Pazartesi.strong.get(i+12).text());
        System.out.println(parrayList.size()+"persembe");
        adapter=new Adapter4(parrayList);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
class Adapter4 extends RecyclerView.Adapter<Adapter4.Holder>{
    ArrayList<String> arrayList;
    public Adapter4(ArrayList<String> arrayList){
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public Adapter4.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.persembe_recycler,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter4.Holder holder, int i) {
        holder.textView.setText(arrayList.get(i));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView textView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textView4);
        }
    }
}
