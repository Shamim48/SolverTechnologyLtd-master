package com.Solver.Solver.Adepter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Solver.Solver.GroupChatAtv;
import com.Solver.Solver.ModelClass.Chat;
import com.Solver.Solver.ModelClass.GroupImage;
import com.Solver.Solver.ModelClass.GroupMessage;
import com.Solver.Solver.R;
import com.Solver.Solver.ViewImage;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class GroupMsgAdapter extends RecyclerView.Adapter<GroupMsgAdapter.GroupMsgHolder> {

    private static  final int MSG_TYPE_LEFT = 0;
    private static  final int MSG_TYPE_RIGHT = 1;
    private static  final int MSG_TYPE_IMAGE = 2;
    private static final int MSG_TYPE_REPLY=3;
    private static final int MSG_TYPE_REPLY_IMAGE=4;
    private static final int MSG_TYPE_IMAGE_LEFT=5;
    private static final int MSG_TYPE_LOCATION_LEFT=6;
    private static final int MSG_TYPE_LOCATION_RIGHT=7;
    GroupMessage groupMsg;
    Bundle bundle=null;

    private Context mContext;
    private List<GroupMessage> gmChat;

    FirebaseUser fuser;

    public GroupMsgAdapter(Context mContext, List<GroupMessage> mChat) {
        this.mContext = mContext;
        this.gmChat = mChat;

    }

    @NonNull
    @Override
    public GroupMsgHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(mContext).inflate(R.layout.groupchat_row_right, parent, false);
       return new GroupMsgAdapter.GroupMsgHolder(view);
        }else if(viewType==MSG_TYPE_IMAGE){

            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.image_row, parent, false);

            return new GroupMsgAdapter.GroupMsgHolder(view);
        }else if(viewType==MSG_TYPE_IMAGE_LEFT){

            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.image_row_left, parent, false);

            return new GroupMsgAdapter.GroupMsgHolder(view);
        }else if(viewType==MSG_TYPE_REPLY){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.reply_row, parent, false);

            return new GroupMsgAdapter.GroupMsgHolder(view);
        }else if(viewType==MSG_TYPE_REPLY_IMAGE){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.image_reply_row,parent,false);
            return new GroupMsgAdapter.GroupMsgHolder(view);
        }else if(viewType==MSG_TYPE_LOCATION_LEFT){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.location_sample_row_left,parent,false);
            return new GroupMsgAdapter.GroupMsgHolder(view);
        }else if(viewType==MSG_TYPE_LOCATION_RIGHT){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.location_sample_row_right,parent,false);

            return new GroupMsgAdapter.GroupMsgHolder(view);
        }
        else {

            View view=LayoutInflater.from(mContext).inflate(R.layout.groupchat_row,parent,false);
            return new GroupMsgAdapter.GroupMsgHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull GroupMsgAdapter.GroupMsgHolder holder, final int position) {

        GroupMsgHolder Indicator=(GroupMsgHolder) holder;



     try {
         int viewType=holder.getItemViewType();
         groupMsg=(GroupMessage)gmChat.get(position);

         switch (viewType){
             case MSG_TYPE_IMAGE:

                // GroupImage groupImage=(GroupImage)gmChat.get(position);

                 //((GroupImageHolder)holder).showGroupImage(groupMsg);

                 holder.nameTv.setText(groupMsg.getName());
                 holder.desTv.setText(groupMsg.getMessage());
                 Glide.with(holder.chatImage).load(groupMsg.getImageUri()).placeholder(R.drawable.ic_insert_photo_black_24dp).into(holder.chatImage);
                 holder.chatImage.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         Intent i=new Intent(mContext, ViewImage.class);
                         i.putExtra("imageUri",gmChat.get(position).getImageUri());
                         mContext.startActivity(i);

                     }
                 });
                 holder.timeTv.setText(groupMsg.getTime());
                 holder.dateTv.setText(groupMsg.getDate());
                 holder.desTv.setText(groupMsg.getImageType());

                 holder.menuIconTv.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         poUpMenuForReplyAndDelete(view,groupMsg,position);

                     }
                 });

                 break;
             case MSG_TYPE_IMAGE_LEFT:

                 holder.nameTv.setText(groupMsg.getName());
                 holder.desTv.setText(groupMsg.getMessage());
                 Glide.with(holder.chatImage).load(groupMsg.getImageUri()).placeholder(R.drawable.ic_insert_photo_black_24dp).into(holder.chatImage);
                 holder.chatImage.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         Intent i=new Intent(mContext, ViewImage.class);
                         i.putExtra("imageUri",gmChat.get(position).getImageUri());
                         mContext.startActivity(i);

                     }
                 });
                 holder.timeTv.setText(groupMsg.getTime());
                 holder.dateTv.setText(groupMsg.getDate());
                 holder.desTv.setText(groupMsg.getImageType());

                 holder.menuIconTv.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         poUpMenu(view,groupMsg,position);

                     }
                 });
                 break;

             case MSG_TYPE_RIGHT:

                 holder.nameTv.setText(groupMsg.getName());
                 if(gmChat.get(position).getSub().equals("")){
                     holder.subTv.setVisibility(View.GONE);
                 }else {
                     holder.subTv.setText(groupMsg.getSub());
                 }
                 holder.msgTv.setText(groupMsg.getMessage());
                 holder.timeTv.setText(groupMsg.getTime());
                 holder.dateTv.setText(groupMsg.getDate());
                 if (gmChat.get(position).getTagClientName().equals("")){
                     holder.tagClientTv.setVisibility(View.GONE);
                 }else {
                     holder.tagClientTv.setText("Factory: " + groupMsg.getTagClientName());
                 }
                 if(gmChat.get(position).getSparePart().equals("")){
                     holder.sparePartTv.setVisibility(View.GONE);
                 }else{
                 holder.sparePartTv.setText("Spare Parts: " + groupMsg.getSparePart());
             }


                 holder.menuIconTv.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {

                         poUpMenuForReplyAndDelete(view,groupMsg,position);

                     }
                 });
                 break;
             case MSG_TYPE_LEFT:

               //  ((GroupMsgHolder)holder).showGroupChat(groupMsg);
                 holder.nameTv.setText(groupMsg.getName());
                 if(gmChat.get(position).getSub().equals("")){
                     holder.subTv.setVisibility(View.GONE);
                 }else {
                     holder.subTv.setText(groupMsg.getSub());
                 }
                 holder.msgTv.setText(groupMsg.getMessage());
                 holder.timeTv.setText(groupMsg.getTime());
                 holder.dateTv.setText(groupMsg.getDate());
                 if (gmChat.get(position).getTagClientName().equals("")){
                     holder.tagClientTv.setVisibility(View.GONE);
                 }else {
                     holder.tagClientTv.setText("Factory: " + groupMsg.getTagClientName());
                 }                 if(gmChat.get(position).getSparePart().equals("")){
                     holder.sparePartTv.setVisibility(View.GONE);
                 }else{
                     holder.sparePartTv.setText("Spare Parts: " + groupMsg.getSparePart());
                 }


                 holder.menuIconTv.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         poUpMenu(view,groupMsg,position);

                     }
                 });

                break;
             case MSG_TYPE_REPLY:
                 holder.nameTv.setText(groupMsg.getName());
                 if(gmChat.get(position).getSub().equals("")){
                     holder.subTv.setVisibility(View.GONE);
                 }else {
                     holder.subTv.setText(groupMsg.getSub());
                 }
                 holder.msgTv.setText(groupMsg.getMessage());
                 holder.timeTv.setText(groupMsg.getTime());
                 holder.dateTv.setText(groupMsg.getDate());
                 if (gmChat.get(position).getTagClientName().equals("")){
                     holder.tagClientTv.setVisibility(View.GONE);
                 }else {
                     holder.tagClientTv.setText("Factory: " + groupMsg.getTagClientName());
                 }                 if(gmChat.get(position).getSparePart().equals("")){
                     holder.sparePartTv.setVisibility(View.GONE);
                 }else{
                     holder.sparePartTv.setText("Spare Parts: " + groupMsg.getSparePart());
                 }
                 holder.msgTv_Rpr.setText(groupMsg.getMsg_reply());
                 holder.nameTv_Rpr.setText(groupMsg.getName_reply());
                 holder.clientTv_Rpr.setText(groupMsg.getClient_reply());
                 holder.jobTv_Rpr.setText(groupMsg.getJob_reply());
                 holder.spearPartTv_Rpr.setText(groupMsg.getSpearPart_reply());





                 holder.menuIconTv.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         poUpMenu(view,groupMsg,position);

                     }
                 });
                 break;
             case MSG_TYPE_REPLY_IMAGE:
                 holder.nameTv.setText(groupMsg.getName());
                 if(gmChat.get(position).getSub().equals("")){
                     holder.subTv.setVisibility(View.GONE);
                 }else {
                     holder.subTv.setText(groupMsg.getSub());
                 }
                 holder.msgTv.setText(groupMsg.getMessage());
                 holder.timeTv.setText(groupMsg.getTime());
                 holder.dateTv.setText(groupMsg.getDate());
                 if (gmChat.get(position).getTagClientName().equals("")){
                     holder.tagClientTv.setVisibility(View.GONE);
                 }else {
                     holder.tagClientTv.setText("Factory: " + groupMsg.getTagClientName());
                 }                 if(gmChat.get(position).getSparePart().equals("")){
                     holder.sparePartTv.setVisibility(View.GONE);
                 }else{
                     holder.sparePartTv.setText("Spare Parts: " + groupMsg.getSparePart());
                 }                 holder.nameTv_Rpr.setText(groupMsg.getName_reply());

                 Glide.with(mContext).load(groupMsg.getImage_reply()).placeholder(R.drawable.ic_insert_photo_black_24dp).into(holder.chatImage);

                 holder.menuIconTv.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         poUpMenu(view,groupMsg,position);
                     }
                 });
                 break;

             case MSG_TYPE_LOCATION_RIGHT:

                 holder.addressTv.setText(groupMsg.getAddress());
                 holder.nameTv.setText(groupMsg.getName());
                 holder.msgTv.setText(groupMsg.getMessage());
                 holder.timeTv.setText(groupMsg.getTime());
                 holder.dateTv.setText(groupMsg.getDate());

                 holder.mapImage.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(String.format("geo:%s,%s",gmChat.get(position).getLatitude(),gmChat.get(position).getLongitude())));
                         if(intent.resolveActivity(mContext.getPackageManager()) !=null){
                             mContext.startActivity(intent);
                         }
                     }
                 });

                 holder.addressTv.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("geo:%s,%s",gmChat.get(position).getLatitude(),gmChat.get(position).getLongitude())));
                         if(intent.resolveActivity(mContext.getPackageManager())!=null){
                             mContext.startActivity(intent);
                         }
                     }
                 });
                 holder.menuIconTv.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         poUpMenuForReplyAndDelete(view,groupMsg,position);
                     }
                 });
                 break;
    case MSG_TYPE_LOCATION_LEFT:

                 holder.addressTv.setText(groupMsg.getAddress());
                 holder.nameTv.setText(groupMsg.getName());
                 holder.msgTv.setText(groupMsg.getMessage());
                 holder.timeTv.setText(groupMsg.getTime());
                 holder.dateTv.setText(groupMsg.getDate());

                 holder.mapImage.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(String.format("geo:%s,%s",gmChat.get(position).getLatitude(),gmChat.get(position).getLongitude())));
                         if(intent.resolveActivity(mContext.getPackageManager()) !=null){
                             mContext.startActivity(intent);
                         }
                     }
                 });

                 holder.addressTv.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(String.format("geo:%s,%s",gmChat.get(position).getLatitude(),gmChat.get(position).getLongitude())));
                         if(intent.resolveActivity(mContext.getPackageManager()) !=null){
                             mContext.startActivity(intent);
                         }
                     }
                 });

        holder.menuIconTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                poUpMenu(view,groupMsg,position);
            }
        });
                 break;

         }
     }catch (Exception e){
       //  Toast.makeText(mContext,"Exception: "+e.getMessage().trim(),Toast.LENGTH_LONG).show();
     }

    }

   /* @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder int position) {

        int viewType=holder.getItemViewType();
        switch (viewType){
            case MSG_TYPE_IMAGE:

                GroupImage groupImage=(GroupImage)gmChat.get(position);

                ((GroupImageHolder)holder).showGroupImage(groupImage);

                break;

            case MSG_TYPE_RIGHT:

                break;
                default:

        }

         groupMessage= (GroupMessage) gmChat.get(position);

    }*/

    @Override
    public int getItemCount() {
        return gmChat.size();
    }

    public class GroupMsgHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {

        private TextView nameTv;
        private TextView subTv;
        private TextView msgTv;
        private TextView timeTv;
        private TextView dateTv;
        private TextView tagClientTv;
        private TextView sparePartTv;
        private TextView msgTv_Rpr;
        private TextView nameTv_Rpr;
        private TextView clientTv_Rpr;
        private TextView jobTv_Rpr;
        private TextView spearPartTv_Rpr;
        public TextView desTv;
        private TextView menuIconTv;
        private TextView addressTv;
        private ImageView mapImage;

        private MapView mapView;
        GoogleMap googleMap;

        private ImageView chatImage;
        LinearLayout linearLayoutRight;
        LinearLayout linearLayoutLeft;
        LinearLayout linearLayoutImage;
        LinearLayout replyParentLot;
        LinearLayout replyImageParentLot;



       public GroupMsgHolder(@NonNull View itemView) {
           super(itemView);
           nameTv=itemView.findViewById(R.id.nameTv_GMRId);
           subTv=itemView.findViewById(R.id.subTv_GMRId);
           msgTv=itemView.findViewById(R.id.msgTv_GMRId);
           timeTv=itemView.findViewById(R.id.timeTvId);
           dateTv=itemView.findViewById(R.id.dateTvId);
           tagClientTv=itemView.findViewById(R.id.clientTv_GMRId);
           sparePartTv=itemView.findViewById(R.id.sparePartTv_GMRId);
           msgTv_Rpr=itemView.findViewById(R.id.msgTv_RprId);
           nameTv_Rpr=itemView.findViewById(R.id.nameTv_RprId);
           clientTv_Rpr=itemView.findViewById(R.id.clientTv_RprId);
           jobTv_Rpr=itemView.findViewById(R.id.subTv_RprId);
           spearPartTv_Rpr=itemView.findViewById(R.id.sparePartTv_RprId);
           desTv = itemView.findViewById(R.id.desTv_GMRId);
           menuIconTv=itemView.findViewById(R.id.menuIconId);
           addressTv=itemView.findViewById(R.id.addressTv_GMRId);
           mapImage=itemView.findViewById(R.id.mapImageId);

           chatImage=itemView.findViewById(R.id.imageViewMsgRowId);
           linearLayoutRight=itemView.findViewById(R.id.linearLayoutRightId);
           linearLayoutLeft=itemView.findViewById(R.id.linearLayoutLeftId);
           linearLayoutImage=itemView.findViewById(R.id.linearLayoutImageId);
           replyParentLot=itemView.findViewById(R.id.replyParentLotId);
           replyImageParentLot=itemView.findViewById(R.id.replyImageParentLotId);

          /* SupportMapFragment mapFragment = (SupportMapFragment) itemView.getSupportFragmentManager()
                   .findFragmentById(R.id.map);
           mapFragment.getMapAsync(this);*/

           mapView = itemView.findViewById(R.id.mapViewId);
         //  mapView.onCreate(bundle);
          // mapView.getMapAsync(this);


       }

       public void showGroupChat(GroupMessage groupMessage){
          nameTv.setText(groupMessage.getName());
           subTv.setText(groupMessage.getSub());
           msgTv.setText(groupMessage.getMessage());
           timeTv.setText(groupMessage.getTime());
           dateTv.setText(groupMessage.getDate());
          tagClientTv.setText("Client: " + groupMessage.getTagClientName());
           sparePartTv.setText("Spare Parts: " + groupMessage.getSparePart());

       }

        @Override
        public void onMapReady(GoogleMap googleMap) {

            this.googleMap = googleMap;
           // Location location = locations.get();
            LatLng latLng=new LatLng(groupMsg.getLatitude(),groupMsg.getLongitude());

            this.googleMap.addMarker(new MarkerOptions().position(latLng).title(groupMsg.getAddress()));
            this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20f));


        }
    }

/*
   public class GroupImageHolder extends RecyclerView.ViewHolder{
       private TextView nameTv;
       private TextView timeTv;
       private TextView dateTv;
       private ImageView chatImage;
       public GroupImageHolder(@NonNull View itemView) {
           super(itemView);
           nameTv=itemView.findViewById(R.id.nameTv_GMRId);
           chatImage=itemView.findViewById(R.id.imageGCId);
           timeTv=itemView.findViewById(R.id.timeTvId);
           dateTv=itemView.findViewById(R.id.dateTvId);
       }

       public void showGroupImage(GroupMessage groupImage){
           nameTv.setText(groupImage.getName());
           Glide.with(chatImage).load(groupImage.getImageUri()).placeholder(R.drawable.ic_insert_photo_black_24dp).into(chatImage);
          timeTv.setText(groupImage.getTime());
          dateTv.setText(groupImage.getDate());
       }
   }*/
 public void poUpMenu(View view, final GroupMessage groupMessage, final int position){
    PopupMenu popupMenu=new PopupMenu(mContext,view);
    MenuInflater inflater=popupMenu.getMenuInflater();
    inflater.inflate(R.menu.reply_menu,popupMenu.getMenu());
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
                case R.id.replyGmId:

                    String action;
                    Intent intent=new Intent(mContext, GroupChatAtv.class);
                    intent.putExtra("userName",gmChat.get(position).getName());
                    intent.putExtra("comName",gmChat.get(position).getTagClientName());
                    intent.putExtra("jobName",gmChat.get(position).getSub());
                    intent.putExtra("message",gmChat.get(position).getMessage());
                    intent.putExtra("spearParts",gmChat.get(position).getSparePart());
                    intent.putExtra("GroupName",gmChat.get(position).getGroupName());
                    intent.putExtra("image",gmChat.get(position).getImageUri());
                    intent.putExtra("msgTypeImage","ReplyImage");
                    intent.putExtra("msgType","Reply");
                    mContext.startActivity(intent);

                    Toast.makeText(mContext,"Coming son",Toast.LENGTH_SHORT).show();

                    break;

            }
            return false;
        }
    });

}

 public void poUpMenuForReplyAndDelete(View view, final GroupMessage groupMessage, final int position){
    PopupMenu popupMenu=new PopupMenu(mContext,view);
    MenuInflater inflater=popupMenu.getMenuInflater();
    inflater.inflate(R.menu.message_menu,popupMenu.getMenu());
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
                case R.id.replyMSGMIId:

                    String action;
                    Intent intent=new Intent(mContext, GroupChatAtv.class);
                    intent.putExtra("userName",gmChat.get(position).getName());
                    intent.putExtra("comName",gmChat.get(position).getTagClientName());
                    intent.putExtra("jobName",gmChat.get(position).getSub());
                    intent.putExtra("message",gmChat.get(position).getMessage());
                    intent.putExtra("spearParts",gmChat.get(position).getSparePart());
                    intent.putExtra("GroupName",gmChat.get(position).getGroupName());
                    intent.putExtra("image",gmChat.get(position).getImageUri());
                    intent.putExtra("msgTypeImage","ReplyImage");
                    intent.putExtra("msgType","Reply");
                    mContext.startActivity(intent);

                    Toast.makeText(mContext,"Coming son",Toast.LENGTH_SHORT).show();

                    break;

                case R.id.deleteMsgId:
                    // gmChat.remove(position);
                    //gmChat.notify();
                    // notifyItemRemoved(position);

                    final AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                    builder.setTitle("Are You sure..?");
                    builder.setMessage("You Want to delete..?");

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String key = gmChat.get(position).getMsgKey();
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Group").child(gmChat.get(position).getGroupName());
                            ref.child(key).removeValue();
                            Toast.makeText(mContext,"Delete Success",Toast.LENGTH_SHORT).show();
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

            }

            return false;
        }
    });

}

    @Override
    public int getItemViewType(int position) {

        try {
            fuser = FirebaseAuth.getInstance().getCurrentUser();
            if (gmChat.get(position).getMsgType().equals("image")) {
                if(gmChat.get(position).getSender().equals(fuser.getUid())) {
                    return MSG_TYPE_IMAGE;
                }else {
                    return MSG_TYPE_IMAGE_LEFT;
                }
            } else if (gmChat.get(position).getMsgType().equals("Reply")) {
                return MSG_TYPE_REPLY;
            }else if(gmChat.get(position).getMsgType().equals("Location")){
                if (gmChat.get(position).getSender().equals(fuser.getUid())) {
                    return MSG_TYPE_LOCATION_RIGHT;
                } else {
                    return MSG_TYPE_LOCATION_LEFT;
                }
            }
            else if(gmChat.get(position).getMsgType().equals("ReplyImage")){
                return MSG_TYPE_REPLY_IMAGE;
            }
            else if(gmChat.get(position).getSender().equals(fuser.getUid())) {

                if (gmChat.get(position).getSender().equals(fuser.getUid())) {
                    return MSG_TYPE_RIGHT;
                } else {
                    return MSG_TYPE_LEFT;
                }
            }

        } catch (Exception e) {

            return 0;
        }

   return 0; }

}
