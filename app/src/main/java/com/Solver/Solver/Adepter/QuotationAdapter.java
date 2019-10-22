package com.Solver.Solver.Adepter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.Solver.Solver.ModelClass.Common_Resouces;
import com.Solver.Solver.ModelClass.Quotation_masters;
import com.Solver.Solver.R;

import java.util.List;

public class QuotationAdapter extends RecyclerView.Adapter<QuotationAdapter.QuotationHolder> {

    private Context context;
    private List<Quotation_masters> quotation_mastersList;

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
    public void onBindViewHolder(@NonNull QuotationHolder holder, int position) {

        Quotation_masters quotation_masters=quotation_mastersList.get(position);
        double totalPrice=0.00;

            holder.date.setText(quotation_masters.getCreated_at());
            holder.qty.setText(quotation_masters.getQut_no());
            holder.factoryName.setText(quotation_masters.getCustomer_id());
            holder.factoryAddress.setText(quotation_masters.getCustomer_id());
            holder.attnPerson.setText(quotation_masters.getAttn_person());
            holder.ccPerson.setText(quotation_masters.getCc_person());

         //   holder.quotation.setText(Html.fromHtml("<u>QUOTATION</u>",Html.FROM_HTML_MODE_LEGACY));

         /* for (int i=0;i<=quotation_masters.getProductList().size();i++){
              holder.productList.append("Product Name"+quotation_masters.getProductList().get(i).getProduct_name()+"\n"+quotation_masters.getProductList().get(i).getQuantity()+"\n\n");

          }*/

          // totalPrice=String.format("%f",quotation_masters.getTotal_amount());
          holder.totalPrice.setText("Total Price :");
          holder.discount.setText("Discount :");
          holder.vat.setText("Vat0% :");
          holder.grandAmount.setText("Grand Amount :");

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

        }
    }
}
