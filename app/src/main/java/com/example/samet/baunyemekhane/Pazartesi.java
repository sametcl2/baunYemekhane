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
    TextView textView2;
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
        textView2=view.findViewById(R.id.textView2);
        return view;
    }

    class Fetch extends AsyncTask<Void,Void,Void> {
        ProgressDialog progressDialog;
        Document doc;
        Element element;
        Elements elementt;
        Elements al;
        String title;
        String yemek;
        ArrayList<String> arrayList;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void...voids) {
            try {
                doc=Jsoup.connect("http://www.balikesir.edu.tr/baun/yemek_listesi").get();
                title=doc.select("h3").first().toString();
                element=doc.select("tr").get(1);
                elementt=element.select("td");
                al=elementt.select("strong");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            textView2.setText(title);
            for (Element element: al){
                arrayList.add(element.text());
            }
            mAdapter=new Adapter(arrayList);
            recyclerView.setAdapter(mAdapter);
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
