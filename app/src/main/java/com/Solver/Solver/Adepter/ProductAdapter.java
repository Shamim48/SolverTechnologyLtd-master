package com.Solver.Solver.Adepter;

import android.content.Context;
import android.content.Intent;
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
import com.Solver.Solver.ModelClass.Client;
import com.Solver.Solver.ModelClass.Product;
import com.Solver.Solver.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    Context context;
    List<Product> clientList;

    public ProductAdapter(Context context, List<Product> clientList) {
        this.context = context;
        this.clientList = clientList;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.client_sample_lyt,parent,false);
        ProductHolder clientHolder=new ProductHolder(view);

        return clientHolder ;
    }


    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, final int position) {

        final Product client=clientList.get(position);
        holder.cpName.setText(clientList.get(position).getProduct_name());
        holder.cpAddress.setText(clientList.get(position).getDescription());
      /*
        holder.cpCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri="tel:"+client.getPhone();
                Intent callIntent=new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse(uri));
                context.startActivity(callIntent);

            }
        });*/

       /* holder.clientParentLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent clientIntent=new Intent(context, ClientShowDetails.class);
                clientIntent.putExtra("comName",clientList.get(position).getCompanyName());
                clientIntent.putExtra("comAddress",clientList.get(position).getAddress());
                clientIntent.putExtra("consultant",clientList.get(position).getConsultPerson());
                clientIntent.putExtra("comEmail",clientList.get(position).getEmail());
                clientIntent.putExtra("comPhone",clientList.get(position).getPhone());
                context.startActivity(clientIntent);

            }
        });*/
    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder{

        private TextView cpName;
        private TextView cpAddress;
        private ImageButton cpCallBtn;
        private LinearLayout clientParentLn;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);

            cpName=itemView.findViewById(R.id.companyNameTvId);
            cpAddress=itemView.findViewById(R.id.companyAddressTvId);
            cpCallBtn=itemView.findViewById(R.id.cpCallBtnTbId);
            clientParentLn=itemView.findViewById(R.id.clientParentLn);

        }
    }
}


