package com.Solver.Solver.Adepter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Solver.Solver.ModelClass.Product;
import com.Solver.Solver.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class SelectedProductArrayAdapter extends ArrayAdapter<Product> {

    private List<Product> productList;
    Context context;
     String productName;
    private SelectedProductArrayAdapter.RemoveProductListener removeProductListener;

    public SelectedProductArrayAdapter(Context context,  List<Product> productList) {

        super(context, 0, productList);
        this.productList = productList;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Product product = productList.get(position);
        TextView productNameTv;
        TextView quantity;
        String productNameType;

        final LinearLayout selectedPdtLlt;

        ImageButton closeBtn;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.selected_product_row, parent, false);

            productNameTv=convertView.findViewById(R.id.productNameSRId);
            quantity=convertView.findViewById(R.id.productQuantitySRId);
            closeBtn=convertView.findViewById(R.id.closeBtnSrId);
            selectedPdtLlt=convertView.findViewById(R.id.selectedProductLltId);


            DatabaseReference productNameRef= FirebaseDatabase.getInstance().getReference("product");
            productNameRef.child(productList.get(position).getProduct_id()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Product selectedProduct=dataSnapshot.getValue(Product.class);
                    productName=selectedProduct.getProduct_name();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            try {
                productNameType=product.getName_type()+": ";
                if(productNameType.equals("name: ")){
                    productNameType="";
                }
                productNameTv.setText(productNameType+productName);
            }catch (Exception e){

            }

            try {
                quantity.setText(String.format("%f",product.getQuantity()));
            }catch (Exception e){

            }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeProductListener.editProductSelectedPdt(position);
                }
            });

            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeProductListener.removeGridProduct(position);
                }
            });
        }

        return convertView;
    }

    public interface RemoveProductListener{

        void removeGridProduct(int position);
        void editProductSelectedPdt(int position);
    }

    public void setRemoveProduct(SelectedProductArrayAdapter.RemoveProductListener removeProductListener) {
        this.removeProductListener = removeProductListener;
    }
}
