package com.Solver.Solver.Adepter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.Solver.Solver.ClientShowDetails;
import com.Solver.Solver.ModelClass.Factories;
import com.Solver.Solver.R;

import java.util.List;

public class ClientAdepter  extends RecyclerView.Adapter<ClientAdepter.ClientHolder> {

    Context context;
    List<Factories> clientList;

    public ClientAdepter(Context context, List<Factories> clientList) {
        this.context = context;
        this.clientList = clientList;
    }

    @NonNull
    @Override
    public ClientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.client_sample_lyt,parent,false);
        ClientHolder clientHolder=new ClientHolder(view);

        return clientHolder ;
    }

    @Override
    public void onBindViewHolder(@NonNull ClientHolder holder, final int position) {

        final Factories client=clientList.get(position);
        holder.cpName.setText(clientList.get(position).getCompany_name());
        holder.cpAddress.setText(clientList.get(position).getLocation());
        holder.cpCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri="tel:"+client.getCc_phone();
                Intent callIntent=new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse(uri));
                context.startActivity(callIntent);

            }
        });

        holder.clientParentLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent clientIntent=new Intent(context, ClientShowDetails.class);
                clientIntent.putExtra("comName",clientList.get(position).getCompany_name());
                clientIntent.putExtra("comAddress",clientList.get(position).getLocation());
                clientIntent.putExtra("consultant",clientList.get(position).getCc_name());
                clientIntent.putExtra("comEmail",clientList.get(position).getCc_email());
                clientIntent.putExtra("comPhone",clientList.get(position).getCc_phone());
                context.startActivity(clientIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    class ClientHolder extends RecyclerView.ViewHolder{

        private TextView cpName;
        private TextView cpAddress;
        private ImageButton cpCallBtn;
        private LinearLayout clientParentLn;

       public ClientHolder(@NonNull View itemView) {
           super(itemView);
           cpName=itemView.findViewById(R.id.companyNameTvId);
           cpAddress=itemView.findViewById(R.id.companyAddressTvId);
           cpCallBtn=itemView.findViewById(R.id.cpCallBtnTbId);
           clientParentLn=itemView.findViewById(R.id.clientParentLn);

       }
   }
}
