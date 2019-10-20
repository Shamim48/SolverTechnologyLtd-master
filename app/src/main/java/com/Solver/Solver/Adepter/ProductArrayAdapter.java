package com.Solver.Solver.Adepter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


import com.Solver.Solver.ModelClass.Product;
import com.Solver.Solver.ModelClass.productHolder;
import com.Solver.Solver.R;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.Solver.Solver.R.color.black_gray;


public class  ProductArrayAdapter extends ArrayAdapter<Product> {

    private List<Product> productList;
    Context context;
    private CheckedListener checkedListener;
     Product product;

    private boolean checked = false;

    public ProductArrayAdapter(Context context,  List<Product> productList) {
        super(context, 0, productList);
        this.productList = productList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        try {


         product = (Product) this.getItem(position);

        }catch (Exception e){

        }
        TextView textViewName;
        TextView desTv;
        final CheckBox productCb;
        final EditText quantityEd;
        final LinearLayout quantityLLt;
        final Button addQuantityBtn;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.productsample_row, parent, false);

            textViewName = convertView.findViewById(R.id.productNameTvId);
            desTv = convertView.findViewById(R.id.desTvId);
            productCb=convertView.findViewById(R.id.productCBId);
            quantityEd=convertView.findViewById(R.id.quantityEtPsId);
            quantityLLt=convertView.findViewById(R.id.quantityLLtId);
            addQuantityBtn=convertView.findViewById(R.id.addQuantityBtnId);

            quantityLLt.setVisibility(View.GONE);

            convertView.setTag(new productHolder(textViewName,desTv,productCb,quantityLLt));

            productCb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox cb= (CheckBox) view;
                    Product product1=(Product) cb.getTag();
                    product1.setChecked(cb.isChecked());

                    if(productCb.isChecked()) {
                       // quantityLLt.setVisibility(View.VISIBLE);
                     //  checkedListener.getCheckListener(position);

                    }
                     if(!productCb.isChecked()){
                       //76yd   ` checkedListener.removeProduct(position);
                        quantityLLt.setVisibility(View.GONE);
                        addQuantityBtn.setEnabled(true);
                        addQuantityBtn.setBackgroundResource(R.drawable.buttonbg);
                        quantityEd.setText("");
                    }
                }
            });

/*

            addQuantityBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String quantity=quantityEd.getText().toString();
                    if (quantity.isEmpty()){
                        Toast.makeText(getContext(),"Please Enter Quantity",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    checkedListener.getCheckListener(position);
                    int qut=Integer.parseInt(quantity);
                    productList.get(position).setQuantity(qut);
                    addQuantityBtn.setEnabled(false);
                    addQuantityBtn.setBackgroundResource(R.drawable.edittextbg);
                    addQuantityBtn.setTextColor(Color.GRAY);

                }
            });
*/


           /* productCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(checkedListener!=null){
                        if(productCb.isChecked()) {
                            checkedListener.getCheckListener(position);
                        }
                       if(!productCb.isChecked()){

                            //checkedListener.removeProduct(position);

                        }
                    }


                }

            });*/


        }else {

            productHolder viewHolder = (productHolder) convertView
                    .getTag();
            productCb = viewHolder.getCheckBox();
            textViewName = viewHolder.getProductNameTv();
            desTv = viewHolder.getDesTv();


        }

        productCb.setTag(product);

       // CircleImageView imageViewFlag = convertView.findViewById(R.id.profile_imageUiId);
     //   RelativeLayout parentLayout=convertView.findViewById(R.id.user_ItmRowId);




try {
    textViewName.setText(product.getProduct_name());
    desTv.setText(Html.fromHtml(product.getDescription(),Html.FROM_HTML_MODE_LEGACY));

}catch (Exception e){

}

           // productCb.setChecked(product.isChecked());
          //  productCb.setTag(product);

            // Display planet data
            productCb.setChecked(product.isChecked());



           /* if( productCb.isChecked()){
                productList.get(position).setIsCheck(true);
                productCb.setChecked(productList.get(position).isIsCheck());
            }*/

           // Glide.with(imageViewFlag).load(userinfo.getImageUrl()).placeholder(R.drawable.ic_insert_photo_black_24dp).into(imageViewFlag);


            // userNameAndID.setUserID(productList.get(position).getUserId());
            //  userNameAndID.setUserName(productList.get(position).getName());






       /* textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // DatabaseReference tagUserIdRef= FirebaseDatabase.getInstance().getReference().child("TagUserId");
              // tagUserIdRef.setValue(productList.get(position).getUserId());
                *//*Intent intent=new Intent(getContext(),Write_Schedule.class);
                intent.putExtra("UserId",productList.get(position).getUserId());
                getContext().startActivity(intent);*//*
            }
        });*/

      /*
        UserNameAndID userNameAndID=new UserNameAndID(productList.get(position).getName().toString(),productList.get(position).getUserId().toString());
        List<UserNameAndID> userNameAndIDList=new ArrayList<>();
        DatabaseReference tagUserIdRef= FirebaseDatabase.getInstance().getReference().child("TagUserId");
        tagUserIdRef.setValue(userNameAndID).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(),"Successful",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(),"Exception : "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                }
            }
        });

*/

      convertView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              checkedListener.getCheckListener(position);
          }
      });



        return convertView;
    }
/*
    @NonNull
    @Override
    public Filter getFilter() {
        return getEmployeeFilter;
    }

    private Filter getEmployeeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<SignUp> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(productList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (SignUp item : productList) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productList.clear();
            productList.addAll((Collection<? extends SignUp>) results.values);
            notifyDataSetChanged();
        }

       *//* @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((SignUp) resultValue).getName();
        }*//*
    };*/


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Product> filteredList=new ArrayList<>();
            if(constraint==null || constraint.length()==0){
                filteredList.addAll(productList);
            }else {
                String filterPattern=constraint.toString().toLowerCase().trim();

                for (Product userInfo : productList){
                    if (userInfo.getProduct_name().toLowerCase().contains(filterPattern)){
                        filteredList.add(userInfo);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {


            productList.clear();
            productList.addAll((Collection<? extends Product>) results.values);
          //  notifyDataSetChanged();

        }
    };




    public interface CheckedListener{

        void getCheckListener(int position);
        void removeProduct(int position);
    }

    public void setCheckedListener(CheckedListener checkedListener) {
        this.checkedListener = checkedListener;
    }
}
