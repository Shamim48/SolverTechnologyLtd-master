package com.Solver.Solver.Adepter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Solver.Solver.GroupChatAtv;
import com.Solver.Solver.MessageActivity;
import com.Solver.Solver.ModelClass.Chat;
import com.Solver.Solver.R;
import com.Solver.Solver.ViewImage;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static  final int MSG_TYPE_LEFT = 0;
    public static  final int MSG_TYPE_RIGHT = 1;
    public static  final int MSG_TYPE_IMAGE = 2;
    public static  final int MSG_TYPE_IMAGE_LEFT = 3;
    public static  final int MSG_REPLY_IMAGE_LEFT = 4;
    public static  final int MSG_REPLY_IMAGE_RIGHT = 5;
    public static  final int MSG_REPLY_LEFT = 6;
    public static  final int MSG_REPLY_RIGHT = 7;

    private Context mContext;
    private List<Chat> mChat;
    private String imageurl;
    FirebaseUser fuser;

    public MessageAdapter(Context mContext, List<Chat> mChat, String imageurl){
        this.mChat = mChat;
        this.mContext = mContext;
        this.imageurl = imageurl;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }else if(viewType==MSG_TYPE_IMAGE) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.image_row, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }else if(viewType==MSG_TYPE_IMAGE_LEFT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.image_row_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }else if(viewType==MSG_REPLY_LEFT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.msgreply_row, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }else if(viewType==MSG_REPLY_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.msgreply_row_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }else if(viewType==MSG_REPLY_IMAGE_LEFT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.msgimage_reply, parent, false);
            return new MessageAdapter.ViewHolder(view);

        }else if(viewType==MSG_REPLY_IMAGE_RIGHT) {

            View view = LayoutInflater.from(mContext).inflate(R.layout.msgimagereply_row_right, parent, false);
            return new MessageAdapter.ViewHolder(view);

        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, final int position) {

        final Chat chat = mChat.get(position);
        int viewType=holder.getItemViewType();
        switch (viewType){
            case 2:
                holder.desTv.setText(chat.getMessage());
                holder.nameTv.setText(chat.getSenderName());
                holder.timeTv.setText(chat.getTime());
                holder.dateTv.setText(chat.getDate());
                Glide.with(mContext).load(chat.getImageUri()).placeholder(R.drawable.ic_image_black_24dp).into(holder.image);

                holder.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(mContext, ViewImage.class);
                        i.putExtra("imageUri",chat.getImageUri());
                        mContext.startActivity(i);
                    }
                });
              /*  if (imageurl.equals("")){
                    holder.profile_image.setImageResource(R.mipmap.ic_launcher);
                } else {
                    //Glide.with(mContext).load(imageurl).into(holder.profile_image);
                }

                if (position == mChat.size()-1){
                    if (chat.isIsseen()){
                        holder.txt_seen.setText("Seen");
                    } else {
                        holder.txt_seen.setText("Delivered");
                    }
                } else {
                    holder.txt_seen.setVisibility(View.GONE);
                }*/
                break;
            case 3:
                holder.desTv.setText(chat.getMessage());
                holder.nameTv.setText(chat.getSenderName());
                holder.timeTv.setText(chat.getTime());
                holder.dateTv.setText(chat.getDate());

                try {

                    holder.image.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            poUpMenu(view,chat,position);
                            return false;
                        }
                    });
                }catch (Exception e){

                }

                Glide.with(mContext).load(chat.getImageUri()).placeholder(R.drawable.ic_image_black_24dp).into(holder.image);
                holder.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(mContext, ViewImage.class);
                        i.putExtra("imageUri",chat.getImageUri());
                        mContext.startActivity(i);
                    }
                });

                break;
            case 0:
                    holder.show_message.setText(chat.getMessage());
                    if (imageurl.equals("")){
                        holder.profile_image.setImageResource(R.mipmap.ic_launcher);
                    } else {
                        Glide.with(mContext).load(imageurl).into(holder.profile_image);
                    }

                   /* if (position == mChat.size()-1){
                        if (chat.isIsseen()){
                            holder.text_seenRight.setText("Seen");
                        } else {
                            holder.text_seenRight.setText("Delivered");
                        }

                    } else {
                        holder.text_seenRight.setVisibility(View.GONE);
                    }

*/

                    holder.menuText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            poUpMenu(view,chat,position);

                        }
                    });

                    break;
            case 1:
                holder.shw_messageRight.setText(chat.getMessage());

                if (position == mChat.size()-1){
                    if (chat.isIsseen()){
                        holder.text_seenRight.setText("Seen");
                    } else {
                        holder.text_seenRight.setText("Delivered");
                    }
                } else {
                    holder.text_seenRight.setVisibility(View.GONE);
                }
               break;
            case 6:
                holder.replyTextTv.setText(chat.getReplyText());
                holder.replyMsgtv.setText(chat.getMessage());
                holder.timeTv.setText(chat.getTime());
                holder.dateTv.setText(chat.getDate());
                break;
              case 7:
                holder.replyTextTv.setText(chat.getReplyText());
                holder.replyMsgtv.setText(chat.getMessage());
                  holder.timeTv.setText(chat.getTime());
                  holder.dateTv.setText(chat.getDate());
                break;
            case MSG_REPLY_IMAGE_LEFT:

                Glide.with(mContext).load(chat.getImageUri()).into(holder.image);
                holder.imageReplyText.setText(chat.getMessage());
                holder.timeTv.setText(chat.getTime());
                holder.dateTv.setText(chat.getDate());
                holder.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(mContext, ViewImage.class);
                        i.putExtra("imageUri",chat.getImageUri());
                        mContext.startActivity(i);
                    }
                });
                break;
            case MSG_REPLY_IMAGE_RIGHT:

                Glide.with(mContext).load(chat.getImageUri()).into(holder.image);
                holder.imageReplyText.setText(chat.getMessage());
                holder.timeTv.setText(chat.getTime());
                holder.dateTv.setText(chat.getDate());
                holder.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(mContext, ViewImage.class);
                        i.putExtra("imageUri",chat.getImageUri());
                        mContext.startActivity(i);
                    }
                });
                break;


        }
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView show_message;
        public ImageView profile_image;
        public TextView txt_seen;
        public TextView nameTv;
        public TextView desTv;
        public ImageView image;
        public TextView shw_messageRight;
        public TextView text_seenRight;
        public TextView timeTv;
        public TextView dateTv;
        public TextView replyTextTv;
        public TextView replyMsgtv;
        public  ImageView imageViewMsgRow;
        public TextView imageReplyText;
        public TextView menuText;

        RelativeLayout msgLot;
        LinearLayout replyLinearLayout;
        LinearLayout linearLayoutImage;
        LinearLayout replyParentLot;
        LinearLayout replyImageParentLot;

        public ViewHolder(View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            //txt_seen = itemView.findViewById(R.id.txt_seenRight);
            nameTv = itemView.findViewById(R.id.nameTv_GMRId);
            desTv = itemView.findViewById(R.id.desTv_GMRId);
            image=itemView.findViewById(R.id.imageViewMsgRowId);
            shw_messageRight=itemView.findViewById(R.id.show_messageRight);
            text_seenRight=itemView.findViewById(R.id.txt_seenRight);
            timeTv=itemView.findViewById(R.id.timeTvId);
            dateTv=itemView.findViewById(R.id.dateTvId);
            replyTextTv=itemView.findViewById(R.id.msgTv_RprLId);
            replyMsgtv=itemView.findViewById(R.id.msgTv_replyLId);
            //imageViewMsgRow=itemView.findViewById(R.id.imageViewMsgRowId);
            imageReplyText=itemView.findViewById(R.id.msgTv_GMRId);
            menuText=itemView.findViewById(R.id.menuIconId);

            //Layout
           // msgLot =itemView.findViewById(R.id.chatLoyId);
            replyLinearLayout =itemView.findViewById(R.id.linearLayoutImageRLId);
           // linearLayoutImage=itemView.findViewById(R.id.linearLayoutImageId);
           // replyParentLot=itemView.findViewById(R.id.replyParentLotId);
           // replyImageParentLot=itemView.findViewById(R.id.replyImageParentLotId);
        }
    }



    @Override
    public int getItemViewType(int position) {
        try {

        fuser = FirebaseAuth.getInstance().getCurrentUser();
         if(mChat.get(position).getMsg_Type().equals("image")) {
             if(mChat.get(position).getSender().equals(fuser.getUid())) {
                 return MSG_TYPE_IMAGE;
             }else {
                 return MSG_TYPE_IMAGE_LEFT;
             }
        }
         else if(mChat.get(position).getMsg_Type().equals("msgReply")){

             if(mChat.get(position).getSender().equals(fuser.getUid())){
                 return MSG_REPLY_RIGHT;
             }else {
                 return MSG_REPLY_LEFT;
             }

         }else if(mChat.get(position).getMsg_Type().equals("imageReply")){
             if(mChat.get(position).getSender().equals(fuser.getUid())){
                 return MSG_REPLY_IMAGE_RIGHT;

             }else {
                 return MSG_REPLY_IMAGE_LEFT;
             }

         }
         else if(mChat.get(position).getMsg_Type().equals("text")){
             if (mChat.get(position).getSender().equals(fuser.getUid())) {
                 return MSG_TYPE_RIGHT;
             } else {
                 return MSG_TYPE_LEFT;
             }
         }

        }catch (Exception e){
             return 0;
        }

    return 0;}

    public void poUpMenu(View view, final Chat chat, final int position){
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
                        Intent intent=new Intent(mContext, MessageActivity.class);
                        intent.putExtra("message",mChat.get(position).getMessage());
                        intent.putExtra("image",mChat.get(position).getImageUri());
                        intent.putExtra("msgTypeImage","imageReply");
                        intent.putExtra("msgType","msgReply");
                        intent.putExtra("userid",chat.getSender());
                        intent.putExtra("senderName",chat.getSenderName());
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

}