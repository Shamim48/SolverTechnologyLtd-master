package com.Solver.Solver.Adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Solver.Solver.ModelClass.Product;
import com.Solver.Solver.R;

import java.util.List;

public class SelectedProductArrayAdapter extends ArrayAdapter<Product> {

    private List<Product> productList;
    Context context;
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

        ImageButton closeBtn;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.selected_product_row, parent, false);

            productNameTv=convertView.findViewById(R.id.productNameSRId);
            quantity=convertView.findViewById(R.id.productQuantitySRId);
            closeBtn=convertView.findViewById(R.id.closeBtnSrId);

            try {
                productNameTv.setText(product.getProduct_name());
                quantity.setText(product.getQuantity());

            }catch (Exception e){


            }

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
    }

    public void setRemoveProduct(SelectedProductArrayAdapter.RemoveProductListener removeProductListener) {
        this.removeProductListener = removeProductListener;
    }
}
