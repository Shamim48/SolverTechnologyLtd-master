package com.Solver.Solver.Adepter;

import android.content.Context;
import android.content.Intent;
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
    GroupMessage groupMsg;

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
        }else {

            View view=LayoutInflater.from(mContext).inflate(R.layout.groupchat_row,parent,false);
            return new GroupMsgAdapter.GroupMsgHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull GroupMsgAdapter.GroupMsgHolder holder, final int position) {

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
                         i.putExtra("imageUri",groupMsg.getImageUri());
                         mContext.startActivity(i);

                     }
                 });
                 holder.timeTv.setText(groupMsg.getTime());
                 holder.dateTv.setText(groupMsg.getDate());
                 holder.desTv.setText(groupMsg.getImageType());

                 holder.linearLayoutImage.setOnLongClickListener(new View.OnLongClickListener() {
                     @Override
                     public boolean onLongClick(View view) {

                         poUpMenu(view,groupMsg,position);

                         return true;
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
                         i.putExtra("imageUri",groupMsg.getImageUri());
                         mContext.startActivity(i);

                     }
                 });
                 holder.timeTv.setText(groupMsg.getTime());
                 holder.dateTv.setText(groupMsg.getDate());
                 holder.desTv.setText(groupMsg.getImageType());

                 holder.linearLayoutImage.setOnLongClickListener(new View.OnLongClickListener() {
                     @Override
                     public boolean onLongClick(View view) {

                         poUpMenu(view,groupMsg,position);

                         return true;
                     }
                 });

                 break;

             case MSG_TYPE_RIGHT:

                 holder.nameTv.setText(groupMsg.getName());
                 holder.subTv.setText(groupMsg.getSub());
                 holder.msgTv.setText(groupMsg.getMessage());
                 holder.timeTv.setText(groupMsg.getTime());
                 holder.dateTv.setText(groupMsg.getDate());
                 holder.tagClientTv.setText("Client: " + groupMsg.getTagClientName());
                 holder.sparePartTv.setText("Spare Parts: " + groupMsg.getSparePart());

                 holder.linearLayoutRight.setOnLongClickListener(new View.OnLongClickListener() {
                     @Override
                     public boolean onLongClick(View view) {

                         poUpMenu(view,groupMsg,position);


                        return true;
                     }
                 });
                 break;
             case MSG_TYPE_LEFT:

               //  ((GroupMsgHolder)holder).showGroupChat(groupMsg);
                 holder.nameTv.setText(groupMsg.getName());
                 holder.subTv.setText(groupMsg.getSub());
                 holder.msgTv.setText(groupMsg.getMessage());
                 holder.timeTv.setText(groupMsg.getTime());
                 holder.dateTv.setText(groupMsg.getDate());
                 holder.tagClientTv.setText("Client: " + groupMsg.getTagClientName());
                 holder.sparePartTv.setText("Spare Parts: " + groupMsg.getSparePart());

                 holder.linearLayoutLeft.setOnLongClickListener(new View.OnLongClickListener() {
                     @Override
                     public boolean onLongClick(View view) {

                         poUpMenu(view,groupMsg,position);


                         return true;
                     }
                 });

                break;
             case MSG_TYPE_REPLY:
                 holder.nameTv.setText(groupMsg.getName());
                 holder.subTv.setText(groupMsg.getSub());
                 holder.msgTv.setText(groupMsg.getMessage());
                 holder.timeTv.setText(groupMsg.getTime());
                 holder.dateTv.setText(groupMsg.getDate());
                 holder.tagClientTv.setText("Client: " + groupMsg.getTagClientName());
                 holder.sparePartTv.setText("Spare Parts: " + groupMsg.getSparePart());
                 holder.msgTv_Rpr.setText(groupMsg.getMsg_reply());
                 holder.nameTv_Rpr.setText(groupMsg.getName_reply());
                 holder.clientTv_Rpr.setText(groupMsg.getClient_reply());
                 holder.jobTv_Rpr.setText(groupMsg.getJob_reply());
                 holder.spearPartTv_Rpr.setText(groupMsg.getSpearPart_reply());



                 holder.replyParentLot.setOnLongClickListener(new View.OnLongClickListener() {
                     @Override
                     public boolean onLongClick(View view) {

                         poUpMenu(view,groupMsg,position);
                         return true;
                     }
                 });
                 break;
             case MSG_TYPE_REPLY_IMAGE:
                 holder.nameTv.setText(groupMsg.getName());
                 holder.subTv.setText(groupMsg.getSub());
                 holder.msgTv.setText(groupMsg.getMessage());
                 holder.timeTv.setText(groupMsg.getTime());
                 holder.dateTv.setText(groupMsg.getDate());
                 holder.tagClientTv.setText("Client: " + groupMsg.getTagClientName());
                 holder.sparePartTv.setText("Spare Parts: " + groupMsg.getSparePart());
                 holder.nameTv_Rpr.setText(groupMsg.getName_reply());

                 Glide.with(mContext).load(groupMsg.getImage_reply()).placeholder(R.drawable.ic_insert_photo_black_24dp).into(holder.chatImage);
                 holder.replyImageParentLot.setOnClickListener(new View.OnClickListener() {
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

    public class GroupMsgHolder extends RecyclerView.ViewHolder{

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


           chatImage=itemView.findViewById(R.id.imageViewMsgRowId);
           linearLayoutRight=itemView.findViewById(R.id.linearLayoutRightId);
           linearLayoutLeft=itemView.findViewById(R.id.linearLayoutLeftId);
           linearLayoutImage=itemView.findViewById(R.id.linearLayoutImageId);
           replyParentLot=itemView.findViewById(R.id.replyParentLotId);
           replyImageParentLot=itemView.findViewById(R.id.replyImageParentLotId);

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
            } else if(gmChat.get(position).getMsgType().equals("ReplyImage")){
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
