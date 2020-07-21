package com.example.ex82retrofitboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BoardAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<BoardItem> boardItems;

    public BoardAdapter(Context context, ArrayList<BoardItem> boardItems) {
        this.context = context;
        this.boardItems = boardItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.board_item, parent, false);
        VH holder = new VH(itemView);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    //inner class
    class VH extends RecyclerView.ViewHolder{

        ImageView iv;
        TextView tvTitle;
        TextView tvMsg;
        TextView tvPrice;
        ToggleButton tbFavor;

        public VH(@NonNull View itemView) {
            super(itemView);

            iv = itemView.findViewById(R.id.iv);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvMsg = itemView.findViewById(R.id.tv_msg);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tbFavor = itemView.findViewById(R.id.tb_favor);

            //좋아요 토글버튼 선택 리스너
            tbFavor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    //바꿔야 할 데이터 'favor'뿐이지만 나중에 확장성을 위해서....

                    //현재 누른 아이템 항목 얻어오기
                    BoardItem item = boardItems.get( getLayoutPosition() );
                    item.favor = isChecked? 1:0;

                    RetrofitService retrofitService = RetrofitHelper.getInstance().create(RetrofitService.class);
                    Call<BoardItem> call = retrofitService.updateData("updateFavor.php", item);
                    call.enqueue(new Callback<BoardItem>() {
                        @Override
                        public void onResponse(Call<BoardItem> call, Response<BoardItem> response) {
                            Toast.makeText(context, "aa", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<BoardItem> call, Throwable t) {
                            Toast.makeText(context,t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });

        }
    }


}
