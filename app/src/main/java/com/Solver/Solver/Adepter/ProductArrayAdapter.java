package com.Solver.Solver.Adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.Solver.Solver.ModelClass.Product;
import com.Solver.Solver.ModelClass.productHolder;
import com.Solver.Solver.R;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;



public class  ProductArrayAdapter extends ArrayAdapter<Product> {

    private List<Product> productList;
    Context context;
    private CheckedListener checkedListener;

    private boolean checked = false;



    public ProductArrayAdapter(Context context,  List<Product> productList) {
        super(context, 0, productList);
        this.productList = productList;
    }



    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Product product = (Product) this.getItem(position);
        TextView textViewName;
        TextView desTv;
        final CheckBox productCb;
        final EditText quantityEd;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.productsample_row, parent, false);

            textViewName = convertView.findViewById(R.id.productNameTvId);
            desTv = convertView.findViewById(R.id.desTvId);
            productCb=convertView.findViewById(R.id.productCBId);
            quantityEd=convertView.findViewById(R.id.quantityEtPsId);

            quantityEd.setVisibility(View.GONE);

            convertView.setTag(new productHolder(textViewName,desTv,productCb));

            productCb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox cb= (CheckBox) view;
                    Product product1=(Product) cb.getTag();
                    product1.setChecked(cb.isChecked());

                    if(productCb.isChecked()) {
                        checkedListener.getCheckListener(position);
                        quantityEd.setVisibility(View.VISIBLE);
                        String quantity=quantityEd.getText().toString();
                        int qut=Integer.parseInt(quantity);
                        productList.get(position).setQuantity(qut);

                    }
                     if(!productCb.isChecked()){
                        checkedListener.removeProduct(position);
                        quantityEd.setVisibility(View.GONE);
                    }
                }
            });
           /* productCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(checkedListener!=null){
                        if(productCb.isChecked()) {
                            checkedListener.getCheckListener(position);
                        }
                       if(!productCb.isChecked()){
                            checkedListener.removeProduct(position);
                        }
                    }


                }

            });
*/


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





            textViewName.setText(product.getProduct_name());
            desTv.setText(product.getDescription());

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
