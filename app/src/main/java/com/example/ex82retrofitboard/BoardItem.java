package com.example.ex82retrofitboard;

public class BoardItem {

    int no;            //번호
    String name;       //작성자 이름
    String title;      //제목
    String msg;        //내용
    String price;      // 가격
    String file;       //업로드 이미지 파일 경로
    int favor;         // 좋아요 여부 [ mysql에 true, false를 1,0 으로 대체하여 저장 ]
    String date;       //작성일자

    public BoardItem() {
    }

    public BoardItem(int no, String name, String title, String msg, String price, String file, int favor, String date) {
        this.no = no;
        this.name = name;
        this.title = title;
        this.msg = msg;
        this.price = price;
        this.file = file;
        this.favor = favor;
        this.date = date;
    }
}
