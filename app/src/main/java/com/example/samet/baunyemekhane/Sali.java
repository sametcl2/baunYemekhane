package com.example.samet.baunyemekhane;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
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

public class Sali extends Fragment {
    View view;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager manager;
    ArrayList<String> sArrayList=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.sali,container,false);
        recyclerView=view.findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        manager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        Fetch fetch=new Fetch();
        fetch.execute();
        return view;
    }
    class Fetch extends AsyncTask<Void, Void, Void>{
        Document doc;
        Elements elements;
        Element element;
        Elements strong;
        Elements elementss;
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                doc=Jsoup.connect("http://www.balikesir.edu.tr/baun/yemek_listesi").get();
                elements=doc.select("table");
                element=elements.select("tr").last();
                elementss=element.select("td");
                strong=elementss.select("strong");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(sArrayList.size()==0){
                for(int i=0; i<4; i++)
                    sArrayList.add(strong.get(i+4).text());
            }
            adapter=new Adapter2(sArrayList);
            recyclerView.setAdapter(adapter);
        }
    }
}
class Adapter2 extends RecyclerView.Adapter<Adapter2.Holder>{

    ArrayList<String> arrayList;
    public Adapter2(ArrayList<String> arrayListe){
        arrayList=arrayListe;
    }

    @NonNull
    @Override
    public Adapter2.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sali_recycler,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter2.Holder holder, int i) {
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
            textView=itemView.findViewById(R.id.textView2);
        }
    }
}
