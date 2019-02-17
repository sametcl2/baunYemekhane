package com.example.samet.baunyemekhane;

import android.app.ProgressDialog;
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

public class Pazartesi extends Fragment {

    View view;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager manager;
    ArrayList<String> arrayList=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.pazartesi,container,false);
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        manager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        Fetch fetch=new Fetch();
        fetch.execute();
        return view;
    }

    class Fetch extends AsyncTask<Void,Void,Void> {
        ProgressDialog progressDialog;
        Document doc;
        Elements elements;
        Element element;
        String title;
        Elements elementss;
        Elements strong;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Yemekler");
            progressDialog.setMessage("Yemek Bilgileri Çekiliyor...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void...voids) {
            try {
                doc=Jsoup.connect("http://www.balikesir.edu.tr/baun/yemek_listesi").get();
                title=doc.select("h3").first().text();
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
            MainActivity.toolbar.setTitle(title);
            if(arrayList.size()==0){
                for (int i=0; i<4; i++){
                    arrayList.add(strong.get(i).text());
                }
            }
            mAdapter=new Adapter(arrayList);
            recyclerView.setAdapter(mAdapter);
            progressDialog.dismiss();
        }
    }
}
class Adapter extends RecyclerView.Adapter<Adapter.Holder>{

    ArrayList<String> arrayListe;

    public Adapter(ArrayList<String> arrayList){
        arrayListe=arrayList;
    }

    @NonNull
    @Override
    public Adapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pazartesi_recycler,viewGroup,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.Holder holder, int i) {
        holder.textView.setText(arrayListe.get(i));
    }

    @Override
    public int getItemCount() {
        return arrayListe.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView textView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textView);
        }
    }
}