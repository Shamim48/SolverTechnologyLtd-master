package com.Solver.Solver.Adepter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.Solver.Solver.ModelClass.Brands;
import com.Solver.Solver.ModelClass.Chat;
import com.Solver.Solver.ModelClass.Common_Resouces;
import com.Solver.Solver.ModelClass.Factories;
import com.Solver.Solver.ModelClass.Product;
import com.Solver.Solver.ModelClass.Quotation_masters;
import com.Solver.Solver.ModelClass.Sub_categories;
import com.Solver.Solver.Quotation_Request;
import com.Solver.Solver.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class QuotationAdapter extends RecyclerView.Adapter<QuotationAdapter.QuotationHolder> {

    private Context context;
    private List<Quotation_masters> quotation_mastersList;

    String subCategoryName ;
    String brandName;
   // String brandName;
    String factoryName;
    String factoryAddress;


    public QuotationAdapter(Context context, List<Quotation_masters> quotation_mastersList) {
        this.context = context;
        this.quotation_mastersList = quotation_mastersList;
    }

    @NonNull
    @Override
    public QuotationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       // View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.quotation_sample_row, parent, false);

      //  QuotationHolder quotationHolder=new QuotationHolder(view);

      //  return quotationHolder;

        View view= LayoutInflater.from(context).inflate(R.layout.quotation_sample_row,parent,false);
        QuotationAdapter.QuotationHolder quotationHolder=new QuotationAdapter.QuotationHolder(view);

        return quotationHolder ;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull QuotationHolder holder, final int position) {

        final Quotation_masters quotation_masters=quotation_mastersList.get(position);
        double totalPrice=0.00;

        int subCategoryId;
        int brandId;
        DatabaseReference subCategoryRef= FirebaseDatabase.getInstance().getReference().child("sub_categories");

        DatabaseReference brandRef=FirebaseDatabase.getInstance().getReference().child("brands");

        DatabaseReference factoryRef=FirebaseDatabase.getInstance().getReference().child("factories");


       int customerId= quotation_masters.getCustomer_id();

       Query customerQuery=factoryRef.orderByChild("customer_id").equalTo(customerId);

       customerQuery.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for (DataSnapshot data:dataSnapshot.getChildren()){
                   Factories factories=data.getValue(Factories.class);
                   factoryName=factories.getCompany_name();
                   factoryAddress=factories.getAddress();
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });



       if(quotation_masters.getRequest_status().equals("Pending")){

           holder.statusTv.setTextColor(Color.parseColor("#E98E10"));
           holder.statusTv.setText(quotation_masters.getRequest_status());

       }else if(quotation_masters.getRequest_status().equals("Complete")){
           holder.statusTv.setTextColor(Color.GREEN);
           holder.statusTv.setText(quotation_masters.getRequest_status());
       }

       holder.menuTv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               poUpMenu(view,quotation_masters,position);
           }
       });

        holder.date.setText(quotation_masters.getCreated_at());
        holder.qty.setText("QTY NO:"+quotation_masters.getQut_no());
        holder.factoryName.setText(factoryName);
        holder.factoryAddress.setText("Address : "+factoryAddress);
        holder.attnPerson.setText("Kind Attn :"+quotation_masters.getAttn_person());
        holder.ccPerson.setText("CC :"+quotation_masters.getCc_person());

        holder.quotation.setText(Html.fromHtml("<u>QUOTATION</u>",Html.FROM_HTML_MODE_LEGACY));
try {

String nameType;
StringBuffer productLt=new StringBuffer();
        for (Product product:quotation_masters.getProductList()){

            subCategoryId=product.getSub_category_id();
            brandId=product.getBrand_id();

            Query sub_category=subCategoryRef.orderByChild("category_id").equalTo(subCategoryId);

            Query brandQuery=brandRef.orderByChild("brand_id").equalTo(brandId);

            sub_category.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot data:dataSnapshot.getChildren()) {

                        Sub_categories sub_categories = data.getValue(Sub_categories.class);

                        subCategoryName = sub_categories.getSub_cat_name().toString();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            brandQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot data:dataSnapshot.getChildren()) {
                        Brands brands = data.getValue(Brands.class);
                        brandName = brands.getBrand_name();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            try {
                nameType=product.getName_type()+" :";
                if (nameType.equals("name :")){
                    nameType="";
                }
                productLt.append(subCategoryName+"\nBrand : "+brandName+"\n"+nameType+product.getProduct_name()+"\nQuantity : "+product.getQuantity()+"\nDescription : "+Html.fromHtml(product.getDescription(),Html.FROM_HTML_MODE_LEGACY)+"\n\n");
            }catch (Exception e){

            }
        }

        holder.productList.setText(productLt);
}catch (NullPointerException e){

}
        // totalPrice=String.format("%f",quotation_masters.getTotal_amount());
        try {

            holder.totalPrice.setText("Total Price : "+quotation_masters.getTotal_amount());
            holder.discount.setText("Discount : "+quotation_masters.getDiscount());
            holder.vat.setText("Vat0% : "+quotation_masters.getVat());
            holder.grandAmount.setText("Grand Amount : "+quotation_masters.getGrand_amount());
        }catch (Exception e){

        }
    }

    @Override
    public int getItemCount() {
        return quotation_mastersList.size();
    }

    public class QuotationHolder extends RecyclerView.ViewHolder{

        private TextView date;
        private TextView qty;
        private TextView factoryName;
        private TextView factoryAddress;
        private TextView attnPerson;
        private TextView ccPerson;
        private TextView quotation;
        private TextView productList;
        private TextView totalPrice;
        private TextView discount;
        private TextView vat;
        private TextView grandAmount;
        private TextView statusTv;
        private TextView menuTv;

        public QuotationHolder(@NonNull View itemView) {
            super(itemView);

            date=itemView.findViewById(R.id.dateQsTvId);
            qty=itemView.findViewById(R.id.qtyQsTvId);
            factoryName=itemView.findViewById(R.id.factoryNameQsTvId);
            factoryAddress=itemView.findViewById(R.id.factoryAddressQsTvId);
            attnPerson=itemView.findViewById(R.id.attnPersonQsTvId);
            ccPerson=itemView.findViewById(R.id.ccPersonQsTvId);
            quotation=itemView.findViewById(R.id.quotationQsTvId);
            productList=itemView.findViewById(R.id.productListQsTvId);
            totalPrice=itemView.findViewById(R.id.totalPriceQsTvId);
            discount=itemView.findViewById(R.id.discountQsTvId);
            vat=itemView.findViewById(R.id.vatQsTvId);
            grandAmount=itemView.findViewById(R.id.grandAmountQsTvId);
            statusTv=itemView.findViewById(R.id.statusTvId);
            menuTv=itemView.findViewById(R.id.menuIconId);

        }
    }
    public void poUpMenu(View view, final Quotation_masters quotation_masters, final int position){
        PopupMenu popupMenu=new PopupMenu(context,view);
        final MenuInflater inflater=popupMenu.getMenuInflater();
        inflater.inflate(R.menu.user_row_popup,popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

          /*
            int itemId=menuItem.getItemId();
            if(itemId==R.id.replyMSGMIId){

            }else if(itemId==R.id.editMenuItmId){

            }else if(itemId==R.id.deleteMenuItmId){

            }*/

                switch (menuItem.getItemId()) {

                    case R.id.editMenuItmId:

                        Intent intent=new Intent(context, Quotation_Request.class);
                        intent.putExtra("Quot_edit","Quot_edit");
                        intent.putExtra("Quot_key",quotation_mastersList.get(position).getQut_key());
                        context.startActivity(intent);

                        break;

                    case R.id.deleteMenuItmId:

                        final AlertDialog.Builder builder=new AlertDialog.Builder(context);
                        builder.setTitle("Are you sure..?");
                        builder.setMessage("You want to delete..?");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String key = quotation_mastersList.get(position).getQut_key();
                                FirebaseAuth auth=FirebaseAuth.getInstance();
                                String userId=auth.getCurrentUser().getUid();

                                DatabaseReference  quotationRef= FirebaseDatabase.getInstance().getReference().child("quotation_req_masters");
                                quotationRef.child(key).removeValue();

                                Toast.makeText(context,"Delete Success",Toast.LENGTH_SHORT).show();
                                dialogInterface.cancel();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();

                        break;
/*
                    gmChat.remove(position);
                  //gmChat.notify();
                    notifyItemRemoved(position);
                    String key = groupMessage.getMsgKey();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Group").child(groupMessage.getGroupName());
                    ref.child(key).removeValue();
                    Toast.makeText(mContext,"Delete Success",Toast.LENGTH_SHORT).show();
*/
                }
                return false;
            }
        });

    }

}
