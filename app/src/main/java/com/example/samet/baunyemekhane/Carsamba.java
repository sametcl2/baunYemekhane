package com.example.samet.baunyemekhane;

import android.os.AsyncTask;
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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Carsamba extends Fragment {
    View view;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager manager;
    ArrayList<String> cArrayList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.carsamba,container,false);
        recyclerView=view.findViewById(R.id.recyclerView3);
        recyclerView.setHasFixedSize(true);
        manager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        cArrayList=new ArrayList<>();
        return view;

    }
    class Fetch extends AsyncTask<Void,Void, Void>{
        Elements elements;
        Element element;
        String title;
        Elements elementss;
        Elements strong;
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            for (int i=0; i<4; i++)
                cArrayList.add(strong.get(i+12).text());
            adapter=new Adapter3(cArrayList);
            recyclerView.setAdapter(adapter);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc=Jsoup.connect("http://www.balikesir.edu.tr/baun/yemek_listesi").get();
                elements=doc.select("table");
                element=elements.select("tr").last();
                elementss=element.select("td");
                strong=elementss.select("strong");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
class Adapter3 extends RecyclerView.Adapter<Adapter3.Holder>{

    ArrayList<String> arrayList;
    public Adapter3(ArrayList<String> arrayList){
        this.arrayList=arrayList;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.carsamba_recycler,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
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
            textView=itemView.findViewById(R.id.textView3);
        }
    }
}
