package com.Solver.Solver.Adepter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.Solver.Solver.ModelClass.Brands;
import com.Solver.Solver.ModelClass.Product;
import com.Solver.Solver.ModelClass.Quotation_masters;
import com.Solver.Solver.ModelClass.Sub_categories;
import com.Solver.Solver.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class QuotationArrayAdapter extends ArrayAdapter<Quotation_masters> {
    private List<Quotation_masters> quotationList;
    Context context;
    String subCategoryName ;
    String brandName;


    private boolean checked = false;

    public QuotationArrayAdapter(Context context,  List<Quotation_masters> quotationList) {
        super(context, 0, quotationList);
        this.quotationList = quotationList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Quotation_masters quotation_masters=quotationList.get(position);
         TextView date;
         TextView qty;
         TextView factoryName;
         TextView factoryAddress;
         TextView attnPerson;
         TextView ccPerson;
         TextView quotation;
         TextView productList;
         TextView totalPrice;
         TextView discount;
         TextView vat;
         TextView grandAmount;




        int subCategoryId;
        int brandId;
        DatabaseReference subCategoryRef= FirebaseDatabase.getInstance().getReference().child("sub_categories");

        DatabaseReference brandRef=FirebaseDatabase.getInstance().getReference().child("brands");


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.quotation_sample_row, parent, false);

                }

        date=convertView.findViewById(R.id.dateQsTvId);
        qty=convertView.findViewById(R.id.qtyQsTvId);
        factoryName=convertView.findViewById(R.id.factoryNameQsTvId);
        factoryAddress=convertView.findViewById(R.id.factoryAddressQsTvId);
        attnPerson=convertView.findViewById(R.id.attnPersonQsTvId);
        ccPerson=convertView.findViewById(R.id.ccPersonQsTvId);
        quotation=convertView.findViewById(R.id.quotationQsTvId);
        productList=convertView.findViewById(R.id.productListQsTvId);
        totalPrice=convertView.findViewById(R.id.totalPriceQsTvId);
        discount=convertView.findViewById(R.id.discountQsTvId);
        vat=convertView.findViewById(R.id.vatQsTvId);
        grandAmount=convertView.findViewById(R.id.grandAmountQsTvId);


        date.setText(quotation_masters.getCreated_at());
        qty.setText("QTY NO:"+quotation_masters.getQut_no());
        factoryName.setText("Factory Name");
        factoryAddress.setText("Factory Address");
        attnPerson.setText("Kind Attn :"+quotation_masters.getAttn_person());
        ccPerson.setText("CC :"+quotation_masters.getCc_person());

          quotation.setText(Html.fromHtml("<u>QUOTATION</u>",Html.FROM_HTML_MODE_LEGACY));

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

            //  productLt.append(subCategoryName+"\nProduct Name :"+product.getProduct_name()+"\nBrand :"+brandName+"\nDescription :"+Html.fromHtml(product.getDescription(),Html.FROM_HTML_MODE_LEGACY)+"\nQuantity :2"+"\n\n");

              productLt.append(subCategoryName+"\nProduct Name :"+product.getProduct_name()+"\nBrand :"+brandName+"\nDescription :"+Html.fromHtml(product.getDescription(),Html.FROM_HTML_MODE_LEGACY)+"\nQuantity :2"+"\n\n");

          }

          productList.setText(productLt);

        // totalPrice=String.format("%f",quotation_masters.getTotal_amount());
        totalPrice.setText("Total Price :");
        discount.setText("Discount :");
        vat.setText("Vat0% :");
        grandAmount.setText("Grand Amount :");

        return convertView;
    }


}
