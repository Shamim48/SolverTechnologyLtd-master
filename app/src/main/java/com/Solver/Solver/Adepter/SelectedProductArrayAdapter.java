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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class SelectedProductArrayAdapter extends ArrayAdapter<Product> {

    private List<Product> productList;
    Context context;
     String productName;
     String productId;
    String productNameType;
    TextView productNameTv;
    private SelectedProductArrayAdapter.RemoveProductListener removeProductListener;

    public SelectedProductArrayAdapter(Context context,  List<Product> productList) {

        super(context, 0, productList);
        this.productList = productList;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Product product = productList.get(position);

        TextView quantity;


        final LinearLayout selectedPdtLlt;

        ImageButton closeBtn;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.selected_product_row, parent, false);

            productNameTv=convertView.findViewById(R.id.productNameSRId);
            quantity=convertView.findViewById(R.id.productQuantitySRId);
            closeBtn=convertView.findViewById(R.id.closeBtnSrId);
            selectedPdtLlt=convertView.findViewById(R.id.selectedProductLltId);
            productId=productList.get(position).getProduct_id();
            //productNameTv.setText(productId);

            DatabaseReference productNameRef= FirebaseDatabase.getInstance().getReference("product");
            Query productIdRef=productNameRef.orderByChild("product_id").equalTo(productId);
            productIdRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot data:dataSnapshot.getChildren()){

                        Product selectedProduct=data.getValue(Product.class);
                        productName=selectedProduct.getProduct_name();
                       productNameType=selectedProduct.getName_type()+": ";
                    }
                    try {

                        if(productNameType.equals("name: ")){
                            productNameType="";
                        }
                        productNameTv.setText(productNameType+productName);
                    }catch (Exception e){

                    }



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

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
